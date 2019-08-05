package de.matze.Blocks.mechanics.gui.guicomponents;

public class GUIComponent {
	
	private boolean isEnable;
    private GUIComponentTypes GUIComponentType;

    public enum GUIComponentTypes {
        Label, BackGround, Button, ColorPickingBox, Animation, ColorSelector
    }

    public GUIComponent(GUIComponentTypes GUIComponentId) {
        this.GUIComponentType = GUIComponentId;
        setEnable(true);
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public GUIComponentTypes getComponentType() {
        return GUIComponentType;
    }

}
