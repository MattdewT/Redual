package de.matze.Blocks.mechanics.gui.background;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;

import java.util.List;

import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.mechanics.gui.guicomponents.BackGround;

public class BackGroundRenderer {
	
	private BackGroundShader shader;
	
	public BackGroundRenderer(Matrix4f gui_pr_matrix) {
		shader = new BackGroundShader();
		shader.enable();
		shader.setProjectionMatrix(gui_pr_matrix);
		shader.disable();
	}
	
	public void render(List<BackGround> backgrounds) {
		shader.enable();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		for(BackGround background : backgrounds) {
			shader.setAlpha(background.getAlpha());
			shader.setAnimationMatrix(background.getAnimationMatrix());
			glBindTexture(GL_TEXTURE_2D, background.getTexture());
			glBindVertexArray(background.getMesh().getVaoID());
			glEnableVertexAttribArray(0);
			glDrawArrays(GL_TRIANGLE_STRIP, 0, background.getMesh().getCount());
			glDisableVertexAttribArray(0);
			glBindVertexArray(0);
		}
		glDisable(GL_BLEND);
		shader.disable();
	}

}
