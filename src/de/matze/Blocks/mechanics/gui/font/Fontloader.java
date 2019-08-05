package de.matze.Blocks.mechanics.gui.font;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.utils.FileUtils;

public class Fontloader {
	
	public static FontType LoadFont(Loader loader, String FontPath, String PNGPath) {
		String font_file = FileUtils.loadAsString(FontPath);
		
		BufferedReader reader = new BufferedReader(new StringReader(font_file));
		FontType font = new FontType(loader, PNGPath);
		String line = null;
		
		try {
			while (true) {
				line = reader.readLine();
				if(line == null) break;
				String[] currentLine = line.split("\\s+");
				Character currentChar = new Character(getvalue(currentLine[1]), getvalue(currentLine[2]),
						getvalue(currentLine[3]), getvalue(currentLine[4]),
						getvalue(currentLine[5]), getvalue(currentLine[6]),
						getvalue(currentLine[7]), getvalue(currentLine[8]));
				font.chars.put(currentChar.getId(), currentChar);
			}
		} catch (IOException e) {
			System.err.println("Failed to load font:" + FontPath);
			e.printStackTrace();
		}
		
		return font;
	}
	
	private static int getvalue(String element) {
		String[] current = element.split("=");
		return Integer.parseInt(current[1]);
	}
}
