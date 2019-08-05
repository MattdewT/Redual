package de.matze.Blocks.mechanics.gui.colorpicking;

import de.matze.Blocks.graphics.Fbo;
import de.matze.Blocks.input.MousePos;
import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.mechanics.gui.guicomponents.ColorPickingBox;
import de.matze.Blocks.utils.BufferUtils;
import de.matze.Blocks.utils.WindowUtils;

import java.nio.IntBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.*;

/**
 * @autor matze
 * @date 13.02.2017
 */
public class ColorPickingRenderer {

    private static Fbo fbo;
    private static IntBuffer pixelbuffer;

    private ColorPickingShader shader;


    public ColorPickingRenderer(Matrix4f gui_pr_matrix) {
        shader = new ColorPickingShader();
        shader.enable();
        shader.setProjectionMatrix(gui_pr_matrix);
        shader.disable();
        fbo = new Fbo(800, 600, Fbo.NONE);
        pixelbuffer = BufferUtils.createIntBuffer(new int[4]);
    }


    public static int getTexture() {
        return fbo.getColourTexture();
    }


    public static int getID() {
        fbo.bindFrameBuffer();

        int x = (int) MousePos.getMouseX();
        int y = (int) MousePos.getMouseY();

        glReadBuffer(GL_COLOR_ATTACHMENT0);
        glReadPixels(x, WindowUtils.getHeight() - y, 1, 1, GL_RGB, GL_UNSIGNED_BYTE, pixelbuffer);

        int pickedID =
                pixelbuffer.get(0) +
                        pixelbuffer.get(1) * 256 +
                        pixelbuffer.get(2) * 256 * 256;

        fbo.unbindFrameBuffer();
//      System.out.println(pickedID);
        return pickedID;
    }


    public void render(List<ColorPickingBox> boxes) {
        fbo.bindFrameBuffer();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        shader.enable();
        for (ColorPickingBox box : boxes) {
            shader.setModelMatrix(Matrix4f.identity());
            shader.setColor(box.getColor());
            shader.setAnimationMatrix(box.getAnimationMatrix());
            glBindVertexArray(box.getMesh().getVaoID());
            glEnableVertexAttribArray(0);
            glDrawArrays(GL_TRIANGLE_STRIP, 0, box.getMesh().getCount());
            glDisableVertexAttribArray(0);
            glBindVertexArray(0);
        }
        shader.disable();

        fbo.unbindFrameBuffer();
    }

}