package de.matze.Blocks.mechanics.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.matze.Blocks.maths.Matrix4f;
import de.matze.Blocks.mechanics.gui.background.BackGroundRenderer;
import de.matze.Blocks.mechanics.gui.colorpicking.ColorPickingRenderer;
import de.matze.Blocks.mechanics.gui.colorselector.ColorSelectorRenderer;
import de.matze.Blocks.mechanics.gui.font.FontRenderer;
import de.matze.Blocks.mechanics.gui.font.FontType;
import de.matze.Blocks.mechanics.gui.guicomponents.*;
import de.matze.Blocks.mechanics.gui.guicomponents.GUIComponent.GUIComponentTypes;

public class GUIRenderer {

	private FontRenderer fontRenderer;
	private BackGroundRenderer backgroundRenderer;
	private ColorPickingRenderer colorPickingRenderer;
	private ColorSelectorRenderer colorSelectorRenderer;
	
	private HashMap<FontType, List<Label>> texts;
	private List<BackGround> backgrounds;
	private List<ColorPickingBox> colorPickingBoxes;
	private List<ColorSelector> colorSelectors;
	
	public GUIRenderer(Matrix4f pr_matrix) {
		this.fontRenderer = new FontRenderer(pr_matrix);
		backgroundRenderer = new BackGroundRenderer(pr_matrix);
		colorPickingRenderer = new ColorPickingRenderer(pr_matrix);
		colorSelectorRenderer = new ColorSelectorRenderer(pr_matrix);
		texts = new HashMap<>();
		backgrounds = new ArrayList<>();
		colorPickingBoxes = new ArrayList<>();
		colorSelectors = new ArrayList<>();
	}
	
	public void renderGUI(List<GUIObject> guis) {
		for(GUIObject gui : guis) {
			if(!gui.isEnable() && !gui.isAnimated()) {
				continue;
			} else{
				if(gui.isAnimated())	{												//check if animation is given
					if(!gui.getComponent(GUIComponentTypes.Animation).isEnable())		//check if animation is done
						continue;														//skip gui object
				}
				processColorSelector(gui.getComponentList(GUIComponentTypes.ColorSelector));
				processText(gui.getComponentList(GUIComponentTypes.Label));
				processBackGround(gui.getComponentList(GUIComponentTypes.BackGround));
				processColorPickingBox(gui.getComponentList(GUIComponentTypes.ColorPickingBox));
			}
		}
		render(texts, backgrounds, colorPickingBoxes, colorSelectors);
	}
	
	private void render(HashMap<FontType, List<Label>> texts, List<BackGround> backgrounds, List<ColorPickingBox> colorPickingBoxes, List<ColorSelector> colorSelectors) {
		backgroundRenderer.render(backgrounds);
		fontRenderer.render(texts);
		colorPickingRenderer.render(colorPickingBoxes);
		colorSelectorRenderer.render(colorSelectors);
		texts.clear();
		backgrounds.clear();
		colorPickingBoxes.clear();
		colorSelectors.clear();
	}
	
	private void processText(List<GUIComponent> textguicomponents) {
		for(GUIComponent textguicomponent : textguicomponents) {
			FontType font = ((Label) textguicomponent).getText().getFont();
			List<Label> textBatch = texts.get(font);
			if(textBatch == null) {
				textBatch = new ArrayList<>();
				texts.put(font, textBatch);
			}
			textBatch.add((Label) textguicomponent);
		}
	}
	
	private void processBackGround(List<GUIComponent> backgroundguicomponents) {
		for(GUIComponent c : backgroundguicomponents) {
			BackGround e = (BackGround) c;
			backgrounds.add(e);
		}
	}

	private void processColorPickingBox(List<GUIComponent> boxes) {
		for(GUIComponent box : boxes) {
			colorPickingBoxes.add(((ColorPickingBox) box));
		}
	}


	private void processColorSelector(List<GUIComponent> colorselectorguicomponents) {
		for(GUIComponent colorSelector : colorselectorguicomponents) {
			colorSelectors.add(((ColorSelector) colorSelector));
		}
	}
	
}
