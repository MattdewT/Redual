package de.matze.Blocks.mechanics.gui.guicomponents;

import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.mechanics.gui.colorselector.ColorWheel;

/**
 * @autor matze
 * @date 18.04.2017
 */

public class ColorSelector extends GUIComponent implements Animation_Matrix{

    private ColorWheel colorWheel;
    private Matrix4f ml_matrix;
    private Matrix4f animation_matrix;
    private float MixValue;

    public ColorSelector(ColorWheel colorWheel, Matrix4f ml_matrix) {
        super(GUIComponentTypes.ColorSelector);
        this.colorWheel = colorWheel;
        this.ml_matrix = ml_matrix;
        this.animation_matrix = Matrix4f.identity();
        MixValue = 1.0f;
    }

    public void setAnimationMatrix(Matrix4f animation_matrix) {
        this.animation_matrix = animation_matrix;
    }

    public Matrix4f getAnimationMatrix() {
        return this.animation_matrix;
    }

    public ColorWheel getColorWheel() {
        return colorWheel;
    }

    public Matrix4f getModelMatrix() {
        return ml_matrix;
    }

    public float getMixValue() {
        return MixValue;
    }

    public void setMixValue(float mixValue) {
        MixValue = mixValue;
    }
}
