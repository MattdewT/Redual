package de.matze.Blocks.maths;

public class Plane {

	private Vector3f normal;
	private float d;
	
	public Plane() {
		this.normal = new Vector3f();
		this.d = 0;
	}
	
	public void normalize() {
		float norm;

        norm = (float) (1.0/Math.sqrt(normal.x*normal.x + normal.y*normal.y + normal.z*normal.z));
        normal.x *= norm;
        normal.y *= norm;
        normal.z *= norm;
        d *= norm;
	}

	public Vector3f getNormal() {
		return normal;
	}

	public float getD() {
		return d;
	}

	public void setAll(float a, float b, float c, float d) {
		this.normal = new Vector3f(a, b, c);
		this.d = d;
	}
}
