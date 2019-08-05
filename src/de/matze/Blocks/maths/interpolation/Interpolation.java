package de.matze.Blocks.maths.interpolation;

/**
 * @autor matze
 * @date 16.04.2017
 */

public class Interpolation {

    private InterpolationTypes interpolationType;

    public enum InterpolationTypes {
        Linear, Trigonometric
    }

    public Interpolation(InterpolationTypes type) {
        this.interpolationType = type;
    }

    public InterpolationTypes getInterpolationType() {
        return interpolationType;
    }
}
