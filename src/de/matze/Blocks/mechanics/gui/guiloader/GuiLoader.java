package de.matze.Blocks.mechanics.gui.guiloader;

import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.maths.Vector2f;
import de.matze.Blocks.maths.Vector3f;
import de.matze.Blocks.mechanics.gamemaster.GameStateMaster;
import de.matze.Blocks.mechanics.gui.GUIMousePicker;
import de.matze.Blocks.mechanics.gui.GUIObject;
import de.matze.Blocks.mechanics.gui.colorselector.ColorWheel;
import de.matze.Blocks.mechanics.gui.font.FontType;
import de.matze.Blocks.mechanics.gui.guicomponents.*;
import de.matze.Blocks.mechanics.logic.Trigger;
import de.matze.Blocks.utils.FileUtils;

/**
 * @autor matze
 * @date 10.04.2017
 */

public class GuiLoader {

    private FontType font;
    private Loader loader;
    private int Hblur_outputTexture;

    private int button_id_counter;

    public GuiLoader(Loader loader, FontType font, int hblur_outputTexture) {
        this.font = font;
        this.loader = loader;
        Hblur_outputTexture = hblur_outputTexture;
        button_id_counter = 1;
    }


    public GUIObject loadGui(String path) {

        StringBuilder str = new StringBuilder();
        str.append(path);
        str.append("/Gui");
        path = str.toString();

        String[] gui_info = FileUtils.loadAsString(path).split("\n");

        int id = Integer.parseInt(gui_info[0]);
        GUIObject object = new GUIObject(id);

        for (int i = 1; i < gui_info.length; i++) {
            String[] currentLine = gui_info[i].split(" ");

            switch(currentLine[0]) {
                case "Label":   handleLabel(object, currentLine, path, i);
                    break;
                case "Background":  handleBackground(object, currentLine, path, i);
                    break;
                case "Button":  handleButton(object, currentLine, path, i);
                    break;
                case "//":
                    break;
                case "Animation":   handleAnimation(object, currentLine, path, i);
                    break;
                case "ColorSelector":   handleColorSelector(object, currentLine, path, i);
                    break;
                default:    System.err.println("Error in " + path + " - Line:" + ++i);
                    break;
            }
        }
        return object;
    }

    private void handleLabel(GUIObject b, String[] info, String path, int i) {
        try {
            Vector2f offset = new Vector2f(Float.parseFloat(info[1]), Float.parseFloat(info[2]));
            double size = Double.parseDouble(info[3]);
            StringBuilder str = new StringBuilder();
            for(int z = 4; z < info.length; z++) {
                str.append(info[z]);
                str.append(" ");
            }
            Label l = new Label(loader, font, str.toString(), offset, size);
            b.addComponent(l);
        } catch (Exception e){
            System.err.println("Error while parsing Label in: " + path + " - Line: " + ++i);
        }
    }

    private void handleBackground(GUIObject b, String[] info, String path, int i) {
        try {
            if(info.length == 5) {
                BackGround bg = new BackGround(loader, Hblur_outputTexture, new Vector2f(Float.parseFloat(info[1]),Float.parseFloat(info[2])), new Vector2f(Float.parseFloat(info[3]), Float.parseFloat(info[4])));
                b.addComponent(bg);
            } else {
                b.addComponent(new BackGround(loader, b, Hblur_outputTexture));
            }
        } catch (Exception e){
            System.err.println("Error while parsing BackGround in: " + path + " - Line: " + ++i);
        }
    }

    private void handleButton(GUIObject b, String[] info, String path, int i) {
        try {
            Label l = (Label) b.getComponentList(GUIComponent.GUIComponentTypes.Label).get(Integer.parseInt(info[1]));
            int id;
            if(info[2].toCharArray()[0] == 'a') {
                id = button_id_counter;
                button_id_counter += 100;
            } else {
                id = Integer.parseInt(info[2]);
            }
            ColorPickingBox c = new ColorPickingBox(loader, id, l.getMin(), l.getMax(), l.getModelMatrix());
            b.addComponent(c);

            Trigger t = new Trigger();
            Button button = new Button(l, c, t);
            b.addComponent(button);

            String State_Info = info[3];
            GameStateMaster.GameStates state;
            switch(State_Info) {
                case "Play":
                    state = GameStateMaster.GameStates.Play;
                    break;
                case "Credits":
                    state = GameStateMaster.GameStates.Credits;
                    break;
                case "Options":
                    state = GameStateMaster.GameStates.Options;
                    break;
                case "Tutorial":
                    state = GameStateMaster.GameStates.Tutorial;
                    break;
                case "Menu":
                    state = GameStateMaster.GameStates.Menu;
                    break;
                case "Match":
                    state = GameStateMaster.GameStates.Match;
                    break;
                default:
                    state = GameStateMaster.GameStates.Invalid;
            }
            GameStateMaster.addController(t, state);

        } catch (Exception e){
            System.err.println("Error while parsing Button in: " + path + " - Line: " + ++i);
        }
    }

    private void handleAnimation(GUIObject b, String[] info, String path, int i) {
        try {
            float x1 = Integer.parseInt(info[1]);
            float x2 = Integer.parseInt(info[2]);
            double duration = Double.parseDouble(info[3]);
            Vector3f axis;
            switch(info[4]) {
                case "x":   axis = new Vector3f(1.0f, 0.0f ,0.0f);
                    break;
                case "y":   axis = new Vector3f(0.0f, 1.0f ,0.0f);
                    break;
                default:    axis = new Vector3f();
                    break;
            }

            Animation a = new Animation(x1, x2, duration, axis);

            for(int z = 5; z < info.length; z += 2) {
                switch(info[z]) {
                    case "Label":
                        a.addComponent(b.getComponentList(GUIComponent.GUIComponentTypes.Label).get(Integer.parseInt(info[z + 1])));
                        break;
                    case "Background":
                        a.addComponent(b.getComponentList(GUIComponent.GUIComponentTypes.BackGround).get(Integer.parseInt(info[z + 1])));
                        break;
                    case "ColorPickingBox":
                        a.addComponent(b.getComponentList(GUIComponent.GUIComponentTypes.ColorPickingBox).get(Integer.parseInt(info[z + 1])));
                        break;
                    case "ColorSelector":
                        a.addComponent(b.getComponentList(GUIComponent.GUIComponentTypes.ColorSelector).get(Integer.parseInt(info[z + 1])));
                        break;
                }
            }
            a.setEnable(true);
            b.addComponent(a);

        } catch (Exception e){
            System.err.println("Error while parsing Animation in: " + path + " - Line: " + ++i);
        }
    }

    private void handleColorSelector(GUIObject b, String[] info, String path, int i) {
        try {
            Vector3f offset = new Vector3f(Float.parseFloat(info[1]),Float.parseFloat(info[2]), 0f);

            Matrix4f ml_matrix = Matrix4f.translate(offset);
            b.addComponent(new ColorSelector(new ColorWheel(loader, new Vector2f(offset.x, offset.y)), ml_matrix));
        } catch (Exception e){
            System.err.println("Error while parsing ColorSelector in: " + path + " - Line: " + ++i);
        }
    }
}
