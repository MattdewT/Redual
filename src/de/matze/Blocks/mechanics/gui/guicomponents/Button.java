package de.matze.Blocks.mechanics.gui.guicomponents;

import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.mechanics.logic.Trigger;

public class Button extends GUIComponent {

	private ColorPickingBox colorPickingBox;

	private Trigger trigger;
	private boolean mouse_over;
	private int id;
	private Label text;

	public Button(Label text, ColorPickingBox colorPickingBox, Trigger trigger) {
		super(GUIComponentTypes.Button);
		this.colorPickingBox = colorPickingBox;
		this.text = text;
		this.id = colorPickingBox.getButton_id();
		this.trigger = trigger;
	}

	public boolean update(int id, boolean mouse_click) {
		mouse_over = (this.id == id) ? true : false;
		trigger.value = (mouse_click) ? mouse_over : false;
		return trigger.value;
	}

	public void mouse_over() {
		System.out.println(text.getText().getText());
	}
}
