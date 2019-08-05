package de.matze.Blocks.maths;

public class Vector2f {

    public float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f() {
        x = 0.0f;
        y = 0.0f;
    }

	public Vector2f(Vector2f a) {
		x = a.x;
		y = a.y;
	}

	public void normalize() {
        float norm = (float) (1.0 / Math.sqrt(x * x + y * y));
        x *= norm;
        y *= norm;
    }

    public float dot(Vector2f b) {
        float scalar = x * b.x + y * b.y;
        return scalar;
    }

    //ToDo: check this function
    public Vector3f cross(Vector2f b) {
        return new Vector3f(0,
                            0,
                            x * b.y - b.x * y);
    }

}
