package de.matze.Blocks.maths.interpolation;

/**
 * @autor matze
 * @date 16.04.2017
 */

public class Trigonometric extends Interpolation{

    private float x1;
    private float x2;

    private float a;
    private float b;

    public Trigonometric(float x1, float x2) {
        super(InterpolationTypes.Trigonometric);
        this.x1 = x1;
        this.x2 = x2;
        a = 0.5f * (x1 - x2);
        b = x1 - a;
    }

    public float interpolate(double percentage) {
        return (float) (a * Math.sin((percentage - 0.5) * Math.PI) + b);
    }
}
