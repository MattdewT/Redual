package de.matze.Blocks.mechanics.gui;

import de.matze.Blocks.input.MousePos;
import de.matze.Blocks.maths.*;
import de.matze.Blocks.utils.WindowUtils;

public class GUIMousePicker {
	
	private Vector2f currentRay;
	private Matrix4f gui_pr_matrix;
	
	public GUIMousePicker(Matrix4f gui_pr_matrix) {
		this.gui_pr_matrix = gui_pr_matrix;
		currentRay = new Vector2f();
		update();
	}
	
	public void update() {
		currentRay = calculateMouseRay();	
//		System.out.println("x: " + currentRay.x + " y: " + currentRay.y + " z: " + currentRay.z);
	}

	private Vector2f calculateMouseRay() {
		float mouseX = (float) MousePos.getMouseX();
		float mouseY = (float) MousePos.getMouseY();
		Vector2f normalizedCoords = getNormalisedDeviceCoordinates(mouseX, mouseY);
		Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1.0f, 1.0f);
		Vector4f eyeCoords = toEyeCoords(clipCoords);
		Vector2f worldRay =  toWorldCoords(eyeCoords);
		return worldRay;
	}

	private Vector2f toWorldCoords(Vector4f eyeCoords) {
		return new Vector2f(eyeCoords.x, eyeCoords.y);
	}

	private Vector4f toEyeCoords(Vector4f clipCoords) {
		Matrix4f invertedProjection = Matrix4f.invert(gui_pr_matrix, null);
		Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}

	public Vector2f getNormalisedDeviceCoordinates(float mouseX, float mouseY) {
		float x = (2.0f * mouseX) / WindowUtils.getWidth() - 1f;
		float y = (2.0f * mouseY) / WindowUtils.getHeight() - 1f;
		return new Vector2f(x, -y);
	}

	/**
	 * @return the currentRay
	 */
	public Vector2f getCurrentRay() {
		return currentRay;
	}

}
