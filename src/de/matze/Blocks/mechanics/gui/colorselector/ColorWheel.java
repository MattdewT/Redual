package de.matze.Blocks.mechanics.gui.colorselector;

import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.graphics.VertexArray;
import de.matze.Blocks.maths.Vector2f;
import de.matze.Blocks.maths.Vector3f;
import de.matze.Blocks.mechanics.gui.GUIMousePicker;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * @autor matze
 * @date 16.04.2017
 */

public class ColorWheel {

    private VertexArray mesh;
    private Vector3f current_hue_color = new Vector3f();

    private Vector2f x1;
    private Vector2f x2;
    private Vector2f x3;

    private float radius = 30f;

    private Vector2f offset;

    public ColorWheel(Loader loader, Vector2f offset) {
        mesh = SetUpMesh(loader);
        this.offset = offset;
    }

    public Vector3f update(Vector2f b) {
        Vector2f a = new Vector2f(1f, 0f);

        b = new Vector2f(b.x - offset.x, b.y - offset.y);

        float length = (float) Math.sqrt(b.x * b.x + b.y * b.y);
        if(length < radius && length > radius - radius * 0.2f) {
            b.normalize();
            float scalar = a.dot(b);
            float current_hue = (float) (Math.acos(scalar) * (180/Math.PI));
            if(b.y < 0) {
                current_hue = 360 - current_hue;
            }
            this.current_hue_color = hsvToRgb(current_hue, 100, 100);
        }   else if (length < 0.8f * radius) {

            //Barycentric Coordinates
            float w1 = ((x2.y - x3.y) * (b.x - x3.x) + (x3.x - x2.x) * (b.y - x3.y)) /
                    ((x2.y - x3.y) * (x1.x - x3.x) + (x3.x - x2.x) * (x1.y - x3.y));

            float w2 = ((x3.y - x1.y) * (b.x - x3.x) + (x1.x - x3.x) * (b.y - x3.y)) /
                    ((x2.y - x3.y) * (x1.x - x3.x) + (x3.x - x2.x) * (x1.y - x3.y));

//            float w3 = 1 - w1 - w2;

//            System.out.println("1: " + w1 + " 2: " + w2 + " 3: " + w3);

            Vector3f result = new Vector3f();
            Vector3f.add(result, Vector3f.scale(new Vector3f(1, 1, 1), w1), result);
            Vector3f.add(result, Vector3f.scale(current_hue_color, w2), result);
            return result;
        }
        return current_hue_color;
    }

    private VertexArray SetUpMesh(Loader loader) {
        List<Float> verticies = new ArrayList<>();
        List<Float> color = new ArrayList<>();
        List<Integer> indicies = new ArrayList<>();

        float diameter =  radius * 0.2f;

        int jumps = 10;

        float DEG2RAD = 3.14159f / 180f;
        for(int i = -30; i <= 330; i+= jumps) {

            Vector3f a = hsvToRgb(i, 100, 100);

            color.add(a.x);
            color.add(a.y);
            color.add(a.z);

            verticies.add((float) (radius * cos(i * DEG2RAD)));
            verticies.add((float) (radius * sin(i * DEG2RAD)));

            color.add(a.x);
            color.add(a.y);
            color.add(a.z);

            verticies.add((float) ((radius  - diameter) * cos(i * DEG2RAD)));
            verticies.add((float) ((radius  - diameter) * sin(i * DEG2RAD)));
        }

        int b;

        for(b = 0; b < (720/jumps); b+=2) {
            indicies.add(b);
            indicies.add(b+3);
            indicies.add(b+1);
            indicies.add(b+1);
            indicies.add(b+3);
            indicies.add(b+2);
        }
        //set up inner triangle
        x1 = new Vector2f(((float) ((0.8f * radius) * cos(330 * DEG2RAD)))
                ,((float) ((0.8f * radius) * sin(330 * DEG2RAD))));

        verticies.add(x1.x);
        verticies.add(x1.y);

        color.add(1f);
        color.add(1f);
        color.add(1f);

        x2 = new Vector2f(((float) ((0.8f * radius) * cos(210 * DEG2RAD)))
                ,((float) ((0.8f * radius) * sin(210 * DEG2RAD))));

        verticies.add(x2.x);
        verticies.add(x2.y);

        color.add(-1f);
        color.add(-1f);
        color.add(-1f);

        x3 = new Vector2f(((float) ((0.8f * radius) * cos(90 * DEG2RAD)))
                ,((float) ((0.8f * radius) * sin(90 * DEG2RAD))));

        verticies.add(x3.x);
        verticies.add(x3.y);

        color.add(0f);
        color.add(0f);
        color.add(0f);

        indicies.add(b);
        indicies.add(b++);
        indicies.add(b++);

        VertexArray mesh = loader.loadToVAO(Loader.listToArray(verticies), Loader.listToArrayInt(indicies));

        glBindVertexArray(mesh.getVaoID());
        loader.AddAttributeList(Loader.listToArray(color), 1, 3);
        glBindVertexArray(0);

        return mesh;
    }

    /**
     * @param H
     *            0-360
     * @param S
     *            0-100
     * @param V
     *            0-100
     * @return color in Vector3f
     */
    public static Vector3f hsvToRgb(float H, float S, float V) {

        float R, G, B;

        H /= 360f;
        S /= 100f;
        V /= 100f;

        if (S == 0)
        {
            R = V * 255;
            G = V * 255;
            B = V * 255;
        } else {
            float var_h = H * 6;
            if (var_h == 6)
                var_h = 0; // H must be < 1
            int var_i = (int) Math.floor((double) var_h); // Or ... var_i =
            // floor( var_h )
            float var_1 = V * (1 - S);
            float var_2 = V * (1 - S * (var_h - var_i));
            float var_3 = V * (1 - S * (1 - (var_h - var_i)));

            float var_r;
            float var_g;
            float var_b;
            if (var_i == 0) {
                var_r = V;
                var_g = var_3;
                var_b = var_1;
            } else if (var_i == 1) {
                var_r = var_2;
                var_g = V;
                var_b = var_1;
            } else if (var_i == 2) {
                var_r = var_1;
                var_g = V;
                var_b = var_3;
            } else if (var_i == 3) {
                var_r = var_1;
                var_g = var_2;
                var_b = V;
            } else if (var_i == 4) {
                var_r = var_3;
                var_g = var_1;
                var_b = V;
            } else {
                var_r = V;
                var_g = var_1;
                var_b = var_2;
            }

            R = var_r;
            G = var_g;
            B = var_b;
        }


        return new Vector3f(R, G, B);
    }

    public VertexArray getMesh() {
        return mesh;
    }

    public Vector3f getCurrentHueColor() {
        return current_hue_color;
    }
}
