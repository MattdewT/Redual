package de.matze.Blocks.mechanics.postProcessing;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.graphics.VertexArray;

	public class PostProcessingUtils {
		
	private static final float[] verticies =  { -1, 1, -1, -1, 1, 1, 1, -1 };
	protected static VertexArray mesh;
	
	private PostProcessingUtils() {}
	
	public static void init(Loader loader) {
		mesh = loader.loadToVAO(verticies, 2);
	}
	
	public static void start() {
		glDisable(GL_DEPTH_TEST);
		glBindVertexArray(mesh.getVaoID());
		glEnableVertexAttribArray(0);
	}
	
	public static void stop() {
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		glEnable(GL_DEPTH_TEST);
	}

}
