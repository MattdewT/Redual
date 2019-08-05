package de.matze.Blocks.maths;

public class aabb {
	
	private Vector3f min;
	private Vector3f max;
	
	public aabb(Vector3f min, Vector3f max) {
		this.min = min;
		this.max = max;
	}
	
	public Vector3f getCenter() {
		return new Vector3f((max.x + min.x) /2, (max.y + min.y) /2, (max.z + min.z) /2);
	}
	
	public void moveAABB(Matrix4f ml_matrix) {
		min = new Vector3f(ml_matrix.elements[0 + 3 * 4] + min.x,
		                   ml_matrix.elements[1 + 3 * 4] + min.y,
		                   ml_matrix.elements[2 + 3 * 4] + min.z);
		max = new Vector3f(ml_matrix.elements[0 + 3 * 4] + max.x,
               			   ml_matrix.elements[1 + 3 * 4] + max.y,
               			   ml_matrix.elements[2 + 3 * 4] + max.z);
	}
	

	public float getRadius() {
		Vector3f vector = new Vector3f((max.x - min.x)/2, (max.y - min.y)/2, (max.z - min.z)/2);
		return (float) Math.sqrt(vector.x*vector.x + vector.y*vector.y + vector.z*vector.z);
	}

	public Vector3f getMin() {
		return min;
	}

	public Vector3f getMax() {
		return max;
	}
	
}
