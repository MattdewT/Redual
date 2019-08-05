package de.matze.Blocks.mechanics.gui.colorpicking;

import de.matze.Blocks.graphics.Shader;
import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.maths.Vector3f;

/**
 * @autor matze
 * @date 13.02.2017
 */

public class ColorPickingShader extends Shader {

    private final static String vertPath = "src/de/matze/Blocks/mechanics/gui/colorpicking/ColorPickingShader.vs";
    private final static String fragPath = "src/de/matze/Blocks/mechanics/gui/colorpicking/ColorPickingShader.fs";


    private int Location_pr_matrix;
    private int Location_ml_matrix;
    private int Location_u_color;
    private int Location_animation_matrix;

    public ColorPickingShader() {
        super(vertPath, fragPath);
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
    }

    @Override
    protected void getAllUniformLocations() {
        Location_pr_matrix = getUniformLocation("pr_matrix");
        Location_ml_matrix = getUniformLocation("ml_matrix");
        Location_u_color = getUniformLocation("u_color");
        Location_animation_matrix = getUniformLocation("anim_matrix");
    }

    public void setProjectionMatrix(Matrix4f gui_pr_matrix) {
        setUniformMat4f(Location_pr_matrix, gui_pr_matrix);
    }

    public void setModelMatrix(Matrix4f ml_matrix) {
        setUniformMat4f(Location_ml_matrix, ml_matrix);
    }

    public void setColor(Vector3f color) {
        setUniform3f(Location_u_color, color);
    }

    public void setAnimationMatrix(Matrix4f anim_matrix) {
        super.setUniformMat4f(Location_animation_matrix, anim_matrix);
    }

}
