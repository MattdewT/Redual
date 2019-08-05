package de.matze.Blocks.maths;


/**
 * Beschriebt einen Dreidimensionalen Vector und gibt einige Grundrechenarten
 *
 * @author matze tiroch
 * @version 1.1
 */

public class Vector3f {

    public float x, y, z;

    public Vector3f() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(Vector3f vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
    }

    /* ***************************MemberFunktions*************************** */

    public void normalize() {
        float norm;

        norm = (float) (1.0 / Math.sqrt(x * x + y * y + z * z));
        x *= norm;
        y *= norm;
        z *= norm;
    }


    public float dot(Vector3f vec) {
        if (null == vec) {
            return 0;
        }
        return x * vec.x + y * vec.y + z * vec.z;
    }

    public void scale(float a) {
        x *= a;
        y *= a;
        z *= a;
    }

    /* ***************************StaticFunktions*************************** */

    /**
     * Normaliezirt einen Vector
     */
    public static Vector3f normalize(Vector3f vector) {
        float norm;

        norm = (float) (1.0 / Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z));
        vector.x *= norm;
        vector.y *= norm;
        vector.z *= norm;

        return vector;
    }

    public static void add(Vector3f vec1, Vector3f vec2, Vector3f target) {
        target.x = vec1.x + vec2.x;
        target.y = vec1.y + vec2.y;
        target.z = vec1.z + vec2.z;
    }

    public static float getLength(Vector3f b) {
        return (float) Math.sqrt(b.x * b.x + b.y * b.y + b.z * b.z);
    }

    public static Vector3f scale(Vector3f b, float a) {
        Vector3f c = new Vector3f(b);
        c.scale(a);
        return c;
    }


}