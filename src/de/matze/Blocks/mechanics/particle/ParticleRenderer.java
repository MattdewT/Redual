package de.matze.Blocks.mechanics.particle;

import de.matze.Blocks.entities.components.CameraComponent;
import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.graphics.VertexArray;
import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.utils.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL31;

import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * @author matze tiroch
 * @version 1.0
 *          Created by matze on 06.09.16.
 */
public class ParticleRenderer {

    private ParticleShader shader;
    private VertexArray quad;
    private static final float[] VERTICES = {-0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f};
    private static final int MAX_INSTANCE = 1000000;
    private static final int INSTANCE_DATA_LENGTH = 16;

    private static final FloatBuffer buffer = BufferUtils.createFloatBuffer(new float[MAX_INSTANCE * INSTANCE_DATA_LENGTH]);

    private Loader loader;
    private int vbo;
    private int pointer;

    public ParticleRenderer(Loader loader, Matrix4f pr_matrix) {
        this.loader = loader;
        quad = loader.loadToVAO(VERTICES, 2);
        shader = new ParticleShader();
        shader.enable();
        shader.setProjectionMatrix(pr_matrix);
        this.vbo = loader.creatEmptyVbo(INSTANCE_DATA_LENGTH * MAX_INSTANCE);
        loader.addInstancedAttribute(quad.getVaoID(), vbo, 1, 4, INSTANCE_DATA_LENGTH, 0);
        loader.addInstancedAttribute(quad.getVaoID(), vbo, 2, 4, INSTANCE_DATA_LENGTH, 4);
        loader.addInstancedAttribute(quad.getVaoID(), vbo, 3, 4, INSTANCE_DATA_LENGTH, 8);
        loader.addInstancedAttribute(quad.getVaoID(), vbo, 4, 4, INSTANCE_DATA_LENGTH, 12);
        shader.disable();
    }

    public void render(List<Particle> particles, CameraComponent cam) {
        Matrix4f viewMatrix = Matrix4f.ViewMatrix(cam.getViewPoint(), cam.getPosition());
        pointer = 0;
        prepare();
        float[] vboData = new float[particles.size() * INSTANCE_DATA_LENGTH];
        for(Particle p : particles) {
            updateModelViewMatrix(p, viewMatrix, vboData);
        }
        loader.updateVbo(vbo, vboData, buffer);
        GL31.glDrawArraysInstanced(GL11.GL_TRIANGLE_STRIP, 0, quad.getCount(), particles.size());
        finish();
    }

    private void prepare() {
        shader.enable();
        glBindVertexArray(quad.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);
        glEnableVertexAttribArray(4);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDepthMask(false);
    }

    private void finish() {
        glDepthMask(true);
        glDisable(GL_BLEND);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);
        glDisableVertexAttribArray(4);
        glBindVertexArray(0);
        shader.disable();
    }

    public void cleanUp() {
        shader.cleanUp();
    }

    private void updateModelViewMatrix(Particle p, Matrix4f view_matrix, float[] vboData) {
        Matrix4f ml_matrix = Matrix4f.translate(p.getPosition());
        ml_matrix.elements[0 + 0 * 4] = view_matrix.elements[0 + 0 * 4];
        ml_matrix.elements[0 + 1 * 4] = view_matrix.elements[1 + 0 * 4];
        ml_matrix.elements[0 + 2 * 4] = view_matrix.elements[2 + 0 * 4];
        ml_matrix.elements[1 + 0 * 4] = view_matrix.elements[0 + 1 * 4];
        ml_matrix.elements[1 + 1 * 4] = view_matrix.elements[1 + 1 * 4];
        ml_matrix.elements[1 + 2 * 4] = view_matrix.elements[2 + 1 * 4];
        ml_matrix.elements[2 + 0 * 4] = view_matrix.elements[0 + 2 * 4];
        ml_matrix.elements[2 + 1 * 4] = view_matrix.elements[1 + 2 * 4];
        ml_matrix.elements[2 + 2 * 4] = view_matrix.elements[2 + 2 * 4];
        //Matrix4f.rotate((float) Math.toRadians(p.getRotation()), new Vector3f(1, 0, 0), ml_matrix, ml_matrix);
        Matrix4f modelviewmatrix = Matrix4f.multiply(ml_matrix, view_matrix);
        //ml_matrix.rotate((float) Math.toRadians(70));
        //Matrix4f.scale(new Vector3f(p.getScale(), p.getScale(), p.getScale()), modelviewmatrix, modelviewmatrix);
        storeMatrixData(vboData, modelviewmatrix);
    }

    private void storeMatrixData(float[] vboData, Matrix4f matrix) {
        vboData[pointer++] = matrix.elements[0 + 0 * 4];
        vboData[pointer++] = matrix.elements[1 + 0 * 4];
        vboData[pointer++] = matrix.elements[2 + 0 * 4];
        vboData[pointer++] = matrix.elements[3 + 0 * 4];
        vboData[pointer++] = matrix.elements[0 + 1 * 4];
        vboData[pointer++] = matrix.elements[1 + 1 * 4];
        vboData[pointer++] = matrix.elements[2 + 1 * 4];
        vboData[pointer++] = matrix.elements[3 + 1 * 4];
        vboData[pointer++] = matrix.elements[0 + 2 * 4];
        vboData[pointer++] = matrix.elements[1 + 2 * 4];
        vboData[pointer++] = matrix.elements[2 + 2 * 4];
        vboData[pointer++] = matrix.elements[3 + 2 * 4];
        vboData[pointer++] = matrix.elements[0 + 3 * 4];
        vboData[pointer++] = matrix.elements[1 + 3 * 4];
        vboData[pointer++] = matrix.elements[2 + 3 * 4];
        vboData[pointer++] = matrix.elements[3 + 3 * 4];
    }

}
