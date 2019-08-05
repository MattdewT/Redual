package de.matze.Blocks.mechanics.gui.font;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.HashMap;
import java.util.List;

import de.matze.Blocks.graphics.VertexArray;
import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.mechanics.gui.guicomponents.Label;

public class FontRenderer {

	private FontShader shader;
	
	public FontRenderer(Matrix4f pr_matrix) {
		this.shader = new FontShader();
		this.shader.enable();
		this.shader.setProjectionMatrix(pr_matrix);
		this.shader.setModelMatrix(Matrix4f.identity());
		this.shader.disable();
	}
	
	public void render(HashMap<FontType, List<Label>> texts) {
        shader.enable();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		for(FontType font : texts.keySet()) {
        	for(Label t : texts.get(font)) {
        		glBindTexture(GL_TEXTURE_2D, t.getText().getFont().textureAtlas.getTexID());
        		shader.setModelMatrix(t.getModelMatrix());
        		shader.setAnimationMatrix(t.getAnimationMatrix());
        		renderText(t.getMesh());
        	}
        }
		glBindTexture(GL_TEXTURE_2D, 0);
		glDisable(GL_BLEND);
		shader.disable();
    }
	
	private void renderText(VertexArray mesh) {
		glBindVertexArray(mesh.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glDrawArrays(GL_TRIANGLES, 0, mesh.getCount());
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
}