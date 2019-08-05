package de.matze.Blocks.maths;

public class Vector4f {

    public float x, y, z, w;

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4f() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 0;
    }

    public Vector4f(Vector4f vector) {
        this.x = vector.x;
        this.y = vector.y;
        this.z = vector.z;
        this.w = vector.w;
    }
    
    public void normalize() {
        float norm;

        norm = (float) (1.0 / Math.sqrt(x * x + y * y + z * z + w * w));
        x *= norm;
        y *= norm;
        z *= norm;
        w *= norm;
    }
    
    public static Vector4f normalize(Vector4f v) {
    	v.normalize();
    	return v;
    }
}
