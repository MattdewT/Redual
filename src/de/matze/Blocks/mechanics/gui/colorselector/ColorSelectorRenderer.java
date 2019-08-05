package de.matze.Blocks.mechanics.gui.colorselector;

import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.mechanics.gui.guicomponents.ColorSelector;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * @autor matze
 * @date 21.02.2017
 */

public class ColorSelectorRenderer {

    private ColorSelectorShader shader;



    public ColorSelectorRenderer(Matrix4f gui_pr_matrix) {
        shader = new ColorSelectorShader();
        shader.enable();
        shader.setProjectionMatrix(gui_pr_matrix);
        shader.disable();
    }

    public void render(List<ColorSelector> colorSelectors) {

        shader.enable();

        for(ColorSelector c : colorSelectors) {
            shader.setCustomColor(c.getColorWheel().getCurrentHueColor());
            shader.setAnimationMatrix(c.getAnimationMatrix());
            shader.setModelMatrix(c.getModelMatrix());
            shader.setMixValue(c.getMixValue());

            glBindVertexArray(c.getColorWheel().getMesh().getVaoID());
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
            glDrawArrays(GL_TRIANGLE_STRIP,0, c.getColorWheel().getMesh().getCount());
            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glBindVertexArray(0);
        }

        shader.disable();

    }


}
