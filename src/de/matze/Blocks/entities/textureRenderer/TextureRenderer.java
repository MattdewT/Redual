package de.matze.Blocks.entities.textureRenderer;

import de.matze.Blocks.entities.components.Mesh;
import de.matze.Blocks.graphics.Texture;
import de.matze.Blocks.maths.Matrix4f;

import java.util.HashMap;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * @autor matze
 * @date 26.04.2017
 */

public class TextureRenderer {

    private TextureShader shader;

    private Texture noTex;

    public TextureRenderer(Matrix4f pr_matrix , Texture noTex) {
        shader = new TextureShader();
        shader.enable();
        shader.setProjectionMatrix(pr_matrix);
        shader.disable();
        this.noTex = noTex;
    }

    public void render(HashMap<Matrix4f, List<Mesh>> meshes) {
        shader.enable();
        for(Matrix4f ml_matrix : meshes.keySet()) {
            shader.setModelMatrix(ml_matrix);
            for(Mesh m : meshes.get(ml_matrix)) {
                if(m.isHasTexture()) {
                    glBindTexture(GL_TEXTURE_2D, m.getTex().getTexID());
                } else {
                    glBindTexture(GL_TEXTURE_2D, noTex.getTexID());
                }
                glBindVertexArray(m.getVao().getVaoID());
                glEnableVertexAttribArray(0);
                glEnableVertexAttribArray(1);
                glDrawElements(GL_TRIANGLES, m.getVao().getCount(), GL_UNSIGNED_INT, 0);
                glDisableVertexAttribArray(0);
                glDisableVertexAttribArray(1);
                glBindVertexArray(0);
            }
        }

        shader.disable();
    }

}
