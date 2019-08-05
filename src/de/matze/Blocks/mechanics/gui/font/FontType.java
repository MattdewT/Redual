package de.matze.Blocks.mechanics.gui.font;

import java.util.HashMap;

import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.graphics.Texture;

public class FontType {
	
	String textureAtlasLoc;
	public Texture textureAtlas;
	HashMap <Double, Character> chars  = new HashMap<>();
	
	public FontType (Loader loader, String textureAtlasLoc) {
		this.textureAtlasLoc = textureAtlasLoc;
		textureAtlas = loader.loadTexture(textureAtlasLoc);
	}
}
