package de.matze.Blocks.mechanics.gui;

import java.util.ArrayList;
import java.util.List;

import de.matze.Blocks.mechanics.gui.guicomponents.GUIComponent;
import de.matze.Blocks.mechanics.gui.guicomponents.GUIComponent.GUIComponentTypes;

public class GUIObject {
	
    private List<GUIComponent> GUIComponents;
    private int ID;
    private boolean isEnable;

    private boolean isAnimated;

    public GUIObject(int ID) {
        this.ID = ID;
        GUIComponents = new ArrayList<>();
        setEnable(true);
        isAnimated = false;
    }

    public void addComponent(GUIComponent gui_component) {
    	GUIComponents.add(gui_component);
    	if(gui_component.getComponentType() == GUIComponentTypes.Animation)
    	    isAnimated = true;
    }

    public int getID() {
        return ID;
    }

    public GUIComponent getComponent(GUIComponentTypes type) {
        for(GUIComponent p : GUIComponents) {
            if(p.getComponentType() == type) {
                return p;
            }
        }
        return null;
    }
    
    public List<GUIComponent> getComponentList(GUIComponentTypes type) {
    	List<GUIComponent> list = new ArrayList<>();
        for(GUIComponent p : GUIComponents) {
            if(p.getComponentType() == type) {
                list.add(p);
            }
        }
    	return list;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
        for(GUIComponent c : GUIComponents) {
            c.setEnable(enable);
        }
    }

    public boolean isEnable() {
        return isEnable;
    }

    public boolean isAnimated() {
        return isAnimated;
    }
}
