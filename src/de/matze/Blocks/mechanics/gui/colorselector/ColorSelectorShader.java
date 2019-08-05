package de.matze.Blocks.mechanics.gui.colorselector;

import de.matze.Blocks.graphics.Shader;
import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.maths.Vector3f;

/**
 * @autor matze
 * @date 21.02.2017
 */

public class ColorSelectorShader extends Shader{

    private final static String vertPath = "src/de/matze/Blocks/mechanics/gui/colorselector/ColorSelectorShader.vs";
    private final static String fragPath = "src/de/matze/Blocks/mechanics/gui/colorselector/ColorSelectorShader.fs";

    private int Location_pr_matrix;
    private int Location_custom_color;
    private int Location_ml_matrix;
    private int Location_animation_matrix;
    private int  Location_mix_value;

    public ColorSelectorShader() {
        super(vertPath, fragPath);
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
        bindAttribute(1, "color");
    }

    @Override
    protected void getAllUniformLocations() {
        Location_pr_matrix = getUniformLocation("pr_matrix");
        Location_custom_color = getUniformLocation("custom_color");
        Location_ml_matrix = getUniformLocation("ml_matrix");
        Location_animation_matrix = getUniformLocation("anim_matrix");
        Location_mix_value = getUniformLocation("mix_value");
    }

    public void setProjectionMatrix(Matrix4f gui_pr_matrix) {
        setUniformMat4f(Location_pr_matrix, gui_pr_matrix);
    }

    public void setCustomColor(Vector3f color) {
        setUniform3f(Location_custom_color, color);
    }

    public void setModelMatrix(Matrix4f ml_matrix) {
        setUniformMat4f(Location_ml_matrix, ml_matrix);
    }

    public void setAnimationMatrix(Matrix4f anim_matrix) {
        super.setUniformMat4f(Location_animation_matrix, anim_matrix);
    }

    public void setMixValue(float mixValue) {
        super.setUniform1f(Location_mix_value, mixValue);
    }
}
