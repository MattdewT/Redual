package de.matze.Blocks.entities.textureRenderer;

import de.matze.Blocks.graphics.Shader;
import de.matze.Blocks.maths.Matrix4f;

/**
 * @autor matze
 * @date 26.04.2017
 */

public class TextureShader extends Shader {

    private final static String vertPath = "src/de/matze/Blocks/entities/textureRenderer/TextureShader.vs";
    private final static String fragPath = "src/de/matze/Blocks/entities/textureRenderer/TextureShader.fs";

    private int Location_pr_matrix;
    private int Location_model_matrix;

    public TextureShader() {
        super(vertPath, fragPath);
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
        bindAttribute(1, "texture");
    }

    @Override
    protected void getAllUniformLocations() {
        Location_pr_matrix = getUniformLocation("pr_matrix");
        Location_model_matrix = getUniformLocation("ml_matrix");
    }

    public void setProjectionMatrix(Matrix4f pr_matrix) {
        setUniformMat4f(Location_pr_matrix, pr_matrix);
    }

    public void setModelMatrix(Matrix4f ml_matrix) {
        super.setUniformMat4f(Location_model_matrix, ml_matrix);
    }
}
