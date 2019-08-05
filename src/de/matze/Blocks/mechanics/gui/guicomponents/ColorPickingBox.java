package de.matze.Blocks.mechanics.gui.guicomponents;

import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.graphics.VertexArray;
import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.maths.Vector2f;
import de.matze.Blocks.maths.Vector3f;

/**
 * @autor matze
 * @date 11.02.2017
 *
 * ColorPicking, this class represent the
 */

public class ColorPickingBox extends GUIComponent implements Animation_Matrix{

    private VertexArray mesh;
    private Matrix4f ml_matrix;
    private int button_id;
    private Vector3f color;
    private Matrix4f animation_matrix;

    public ColorPickingBox(Loader loader, int button_id, Vector2f min, Vector2f max, Matrix4f ml_matrix) {
        super(GUIComponentTypes.ColorPickingBox);
        this.ml_matrix = ml_matrix;
        this.button_id = button_id;

        this.color = getColor(button_id);

        float margin = 0f;

        mesh = loader.loadToVAO(new float[] {
                max.x + margin, max.y + margin,
                min.x - margin, max.y + margin,
                max.x + margin, min.y - margin,
                min.x - margin, min.y - margin
        }, 2);

        animation_matrix = Matrix4f.identity();
    }

    private static Vector3f getColor(int id) {
        Vector3f color = new Vector3f();

        color.x = (id & 0x000000FF) >>  0; //r
        color.y = (id & 0x0000FF00) >>  8; //g
        color.z = (id & 0x00FF0000) >> 16; //b

        color.x /= 255.0f;
        color.y /= 255.0f;
        color.z /= 255.0f;

        return color;
    }

    public VertexArray getMesh() {
        return mesh;
    }

    public Matrix4f getModelMatrix() {
        return ml_matrix;
    }

    public int getButton_id() {
        return button_id;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setAnimationMatrix(Matrix4f animation_matrix) {
        this.animation_matrix = animation_matrix;
    }

    public Matrix4f getAnimationMatrix() {
        return this.animation_matrix;
    }
}
