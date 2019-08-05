package de.matze.Blocks.entities;

/**
 * Created by matze on 26.08.16.
 */
import java.util.*;

import de.matze.Blocks.entities.components.*;
import de.matze.Blocks.entities.components.Component.ComponentTypes;
import de.matze.Blocks.mechanics.gui.guicomponents.GUIComponent;

public class GameObject {

    private List<Component> Components;
    private int ID;

    public GameObject(int ID) {
        this.ID = ID;
        Components = new ArrayList<>();
    }

    public void addComponent(Component component) {
        Components.add(component);
    }

    public int getID() {
        return ID;
    }

    public Component getComponent(ComponentTypes type) {
        for(Component p : Components) {
            if(p.getComponentType() == type) {
                return p;
            }
        }
        return null;
    }

    public List<Component> getComponentList(Component.ComponentTypes type) {
        List<Component> list = new ArrayList<>();
        for(Component p : Components) {
            if(p.getComponentType() == type) {
                list.add(p);
            }
        }
        return list;
    }
}
