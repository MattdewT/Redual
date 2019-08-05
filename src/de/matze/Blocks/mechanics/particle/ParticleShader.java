package de.matze.Blocks.mechanics.particle;

import de.matze.Blocks.graphics.Shader;
import de.matze.Blocks.maths.Matrix4f;

/**
 * @author matze tiroch
 * @version 1.0
 *          Created by matze on 06.09.16.
 */
public class ParticleShader extends Shader {

    private final static String vertPath = "src/de/matze/Blocks/mechanics/particle/ParticleShader.vs";
    private final static String fragPath = "src/de/matze/Blocks/mechanics/particle/ParticleShader.fs";

    public ParticleShader() {
        super(vertPath, fragPath);
    }

    private int Location_pr_matrix;

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
    }

    @Override
    protected void getAllUniformLocations() {
        Location_pr_matrix = getUniformLocation("pr_matrix");
    }

    public void setProjectionMatrix(Matrix4f pr_matrix) {
        super.setUniformMat4f(Location_pr_matrix, pr_matrix);
    }
}
