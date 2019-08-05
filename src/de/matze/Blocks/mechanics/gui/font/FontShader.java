package de.matze.Blocks.mechanics.gui.font;

import de.matze.Blocks.graphics.Shader;
import de.matze.Blocks.maths.Matrix4f;

public class FontShader extends Shader{

	private final static String vertPath = "src/de/matze/Blocks/mechanics/gui/font/FontShader.vs";
    private final static String fragPath = "src/de/matze/Blocks/mechanics/gui/font/FontShader.fs";

    private int Location_pr_matrix;
    private int Location_ml_matrix;
    private int Location_animation_matrix;

    public FontShader() {
        super(vertPath, fragPath);
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
        bindAttribute(1, "textures");
    }

    @Override
    protected void getAllUniformLocations() {
        Location_pr_matrix = getUniformLocation("pr_matrix");
        Location_ml_matrix = getUniformLocation("ml_matrix");
        Location_animation_matrix = getUniformLocation("anim_matrix");
    }

    public void setProjectionMatrix(Matrix4f gui_pr_matrix) {
        super.setUniformMat4f(Location_pr_matrix, gui_pr_matrix);
    }

    public void setModelMatrix(Matrix4f ml_matrix) {
        super.setUniformMat4f(Location_ml_matrix, ml_matrix);
    }

    public void setAnimationMatrix(Matrix4f anim_matrix) {
        super.setUniformMat4f(Location_animation_matrix, anim_matrix);
    }
}
