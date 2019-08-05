package de.matze.Blocks.mechanics.gui.guicomponents;

import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.graphics.VertexArray;
import de.matze.Blocks.maths.*;
import de.matze.Blocks.mechanics.gui.font.FontType;
import de.matze.Blocks.mechanics.gui.font.Text;

public class Label extends GUIComponent  implements Animation_Matrix{

	private Text text;
	private VertexArray mesh;
	
	private Vector2f min;
	private Vector2f max;
	
	private Matrix4f model_matrix;
	private Matrix4f animation_matrix;

	public Label(Loader loader, FontType font, String text, Vector2f offset, double size) {
		super(GUIComponentTypes.Label);
		min = new Vector2f();
		max = new Vector2f();
		this.text = new Text(font, text);
		this.text.setFontSize(size);
		mesh = this.text.getMeshData(loader, max, min);
		model_matrix = Matrix4f.translate(new Vector3f((float) this.text.getFontSize() * 99f * (float) offset.x, -(float) this.text.getFontSize() * 99f * (float) offset.y, 0));
		model_matrix = Matrix4f.translate(new Vector3f(offset.x, offset.y, 0));
		transform(model_matrix);
		animation_matrix = Matrix4f.identity();
	}
	
	private void transform(Matrix4f m) {
		Matrix4f min = Matrix4f.translate(new Vector3f(this.min.x, this.min.y, 0));
		Matrix4f max = Matrix4f.translate(new Vector3f(this.max.x, this.max.y, 0));

		min = m.multiply(min);
		max = m.multiply(max);

		this.min.x = min.elements[0 + 3 * 4];
		this.min.y = min.elements[1 + 3 * 4];

		this.max.x = max.elements[0 + 3 * 4];
		this.max.y = max.elements[1 + 3 * 4];
	}
	
	public VertexArray getMesh() {
		return mesh;
	}
	
	public Text getText() {
		return text;
	}
	
	public Vector2f getMin() {
		return min;
	}
	
	public Vector2f getMax() {
		return max;
	}
	
	public Matrix4f getModelMatrix() {
		return model_matrix;
	}

	public void setAnimationMatrix(Matrix4f animation_matrix) {
		this.animation_matrix = animation_matrix;
	}

	public Matrix4f getAnimationMatrix() {
		return this.animation_matrix;
	}
}