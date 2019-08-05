package de.matze.Blocks.mechanics.gui.background;

import de.matze.Blocks.graphics.Shader;
import de.matze.Blocks.maths.Matrix4f;

public class BackGroundShader extends Shader {

	private final static String vertPath = "src/de/matze/Blocks/mechanics/gui/background/BackGroundShader.vs";
    private final static String fragPath = "src/de/matze/Blocks/mechanics/gui/background/BackGroundShader.fs";
    
    private int Location_pr_matrix;
	private int Location_animation_matrix;
	private int Location_Alpha;
	
	public BackGroundShader() {
		super(vertPath, fragPath);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		Location_pr_matrix = getUniformLocation("pr_matrix");
		Location_animation_matrix = getUniformLocation("anim_matrix");
		Location_Alpha = getUniformLocation("alpha");
	}
	
	public void setProjectionMatrix(Matrix4f gui_pr_matrix) {
		setUniformMat4f(Location_pr_matrix, gui_pr_matrix);
	}

	public void setAnimationMatrix(Matrix4f anim_matrix) {
		super.setUniformMat4f(Location_animation_matrix, anim_matrix);
	}

	public void setAlpha(float alpha) {
		super.setUniform1f(Location_Alpha, alpha);
	}
}
