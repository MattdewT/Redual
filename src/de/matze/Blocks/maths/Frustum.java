package de.matze.Blocks.maths;

public class Frustum {

	private Plane[] p;

	public Frustum(Matrix4f m) {
		p = new Plane[6];

		for(int i = 0; i != 6; i++)
			p[i] = new Plane();

		ExtractPlanesGl(m);
	}

	@SuppressWarnings("PointlessArithmeticExpression")
    public void ExtractPlanesGl(Matrix4f m) {
		p[0].setAll(m.elements[3 + 0 * 4] - m.elements[0 + 0 * 4],
					m.elements[3 + 1 * 4] - m.elements[0 + 1 * 4],
					m.elements[3 + 2 * 4] - m.elements[0 + 2 * 4],
					m.elements[3 + 3 * 4] - m.elements[0 + 3 * 4]);

		p[1].setAll(m.elements[3 + 0 * 4] + m.elements[0 + 0 * 4],
					m.elements[3 + 1 * 4] + m.elements[0 + 1 * 4],
					m.elements[3 + 2 * 4] + m.elements[0 + 2 * 4],
					m.elements[3 + 3 * 4] + m.elements[0 + 3 * 4]);

		p[2].setAll(m.elements[3 + 0 * 4] + m.elements[1 + 0 * 4],
					m.elements[3 + 1 * 4] + m.elements[1 + 1 * 4],
					m.elements[3 + 2 * 4] + m.elements[1 + 2 * 4],
					m.elements[3 + 3 * 4] + m.elements[1 + 3 * 4]);

		p[3].setAll(m.elements[3 + 0 * 4] - m.elements[1 + 0 * 4],
					m.elements[3 + 1 * 4] - m.elements[1 + 1 * 4],
					m.elements[3 + 2 * 4] - m.elements[1 + 2 * 4],
					m.elements[3 + 3 * 4] - m.elements[1 + 3 * 4]);

		p[4].setAll(m.elements[3 + 0 * 4] - m.elements[2 + 0 * 4],
					m.elements[3 + 1 * 4] - m.elements[2 + 1 * 4],
					m.elements[3 + 2 * 4] - m.elements[2 + 2 * 4],
					m.elements[3 + 3 * 4] - m.elements[2 + 3 * 4]);

		p[5].setAll(m.elements[3 + 0 * 4] + m.elements[2 + 0 * 4],
					m.elements[3 + 1 * 4] + m.elements[2 + 1 * 4],
					m.elements[3 + 2 * 4] + m.elements[2 + 2 * 4],
					m.elements[3 + 3 * 4] + m.elements[2 + 3 * 4]);

		for(int i = 0; i != 6; i++)
			p[i].normalize();
	}

	public boolean inFrustum(Vector3f center, float radius) {

		for(int i = 0; i != 6; i++) {
			if(center.dot(p[i].getNormal()) + p[i].getD() + radius <= 0)
				return false;
		}

		return true;
	}

}
