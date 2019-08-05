package de.matze.Blocks.mechanics.gui.guicomponents;

import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.maths.Vector3f;
import de.matze.Blocks.maths.interpolation.Trigonometric;
import de.matze.Blocks.utils.WindowUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor matze
 * @date 15.04.2017
 */

public class Animation extends  GUIComponent {

    private List<GUIComponent> components;
    private float start_position;
    private float end_position;
    private double duration;          //in ms
    private double passed_time;         //in ms
    private Vector3f axis;

    private boolean animate;
    private boolean animate_reverse;

    double percentage;

    private Trigonometric interpolation;

    public Animation(float start_position, float end_position, double duration, Vector3f axis) {
        super(GUIComponentTypes.Animation);
        this.start_position = start_position;
        this.end_position = end_position;
        this.duration = duration;
        this.axis = axis;
        passed_time = 0;
        interpolation = new Trigonometric(start_position, end_position);
        components = new ArrayList<>();
        animate = false;
    }

    public void update() {
        if(animate) {
            passed_time += WindowUtils.getFrameTimeSeconds() * WindowUtils.get_animation_multiplier();
            percentage = passed_time / duration;
        } else if(animate_reverse) {
            passed_time -= WindowUtils.getFrameTimeSeconds() * WindowUtils.get_animation_multiplier();
            percentage = passed_time / duration;
        }
        float value = interpolation.interpolate(percentage);
        Matrix4f anim_matrix = Matrix4f.translate(new Vector3f(value * axis.x, value * axis.y, value * axis.z));

        for(GUIComponent c : components) {
            switch(c.getComponentType()) {
                case Label:
                    ((Label) c).setAnimationMatrix(anim_matrix);
                    break;
                case BackGround:
                    ((BackGround) c).setAnimationMatrix(anim_matrix);
                    if(animate)
                        ((BackGround) c).setAlpha((float) percentage * 0.5f + 0.5f);
                    if(animate_reverse)
                        ((BackGround) c).setAlpha(clamp(0.0f, 1.0f,1.0f - (1.0f - (float) percentage) * 1.5f));
                    break;
                case ColorPickingBox:
                    ((ColorPickingBox) c).setAnimationMatrix(anim_matrix);
                    break;
                case ColorSelector:
                    ((ColorSelector) c).setAnimationMatrix(anim_matrix);
                    if(animate)
                        ((ColorSelector) c).setMixValue((float) percentage);
                    else if(animate_reverse)
                        ((ColorSelector) c).setMixValue(clamp(0.0f, 1.0f, 1.0f - (1.0f - (float) percentage) * 3.0f));
                    break;
            }
        }
        if(percentage >= 1.00) {
            animate = false;
            animate_reverse = false;
        } else if(percentage <= 0.00) {
            animate_reverse = false;
            super.setEnable(false);
        }
    }

    public void addComponent(GUIComponent b) {
        components.add(b);
    }

    @Override
    public void setEnable(boolean isEnable) {
        animate = isEnable;
        animate_reverse = !isEnable;
        super.setEnable(true);
    }

    private float clamp(float MIN_VALUE, float MAX_VALUE, float value) {
        float result = value > MAX_VALUE ? MAX_VALUE : value < MIN_VALUE ? MIN_VALUE : value;
        return result;
    }

}

interface Animation_Matrix {
    void setAnimationMatrix(Matrix4f anim_matrix);
    Matrix4f getAnimationMatrix();
}
