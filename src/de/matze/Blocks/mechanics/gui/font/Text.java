package de.matze.Blocks.mechanics.gui.font;

import java.util.ArrayList;
import java.util.List;

import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.graphics.VertexArray;
import de.matze.Blocks.maths.Vector2f;

public class Text {
	private double FontSize = 0.09d;
	
	private FontType font;
	private String Text;
		
	public Text(FontType font, String Text) {
		this.Text = Text;
		this.font = font;
	}
	
	public VertexArray getMeshData (Loader loader, Vector2f max, Vector2f min) {
		double curserX = 0;
		double curserY = 0;
		
		List<Float> verticies = new ArrayList<>();
		List<Float> texCoords = new ArrayList<>();
	
		char[] currentText = Text.toCharArray();
		
		for (int i = 0; i != currentText.length; i++) {
			if(currentText[i] == '/') {
				if(currentText[++i] == 'n') {
					curserY += 70d * FontSize;
					curserX = 0;
					continue;
				}

			}
			
			double ascii = (double) currentText[i];
			Character currentChar = font.chars.get(ascii);
			addVerticesForCharacter(curserX, curserY, currentChar, FontSize, verticies);
			addTexCoords(texCoords, currentChar.getTexXmin(), currentChar.getTexYmin(), currentChar.getTexXmax(), currentChar.getTexYmax());
			curserX += currentChar.getXadvance() * FontSize;
		}
		
		float[] vert = listToArray(verticies);
		
		for (int i = 0; i < vert.length ; i += 2) {
			if(vert[i] < min.x) {
				min.x = vert[i];
			} 
			if(vert[i] > max.x) {
				max.x = vert[i];
			}
			if(vert[i+1] < min.y) {
				min.y = vert[i+1];
			} 
			if(vert[i+1] > max.y) {
				max.y = vert[i+1];
			}
		}
		
		return loader.loadToVAO(vert, listToArray(texCoords));
	}
	
	private void addVerticesForCharacter(double curserX, double curserY, Character character, double fontSize,
			List<Float> vertices) {
		double x = curserX + (character.getXoffset() * fontSize);
		double y = curserY + (character.getYoffset() * fontSize);
		double maxX = x + (character.getWidth() * fontSize);
		double maxY = y + (character.getHeigth() * fontSize);
		double properX = (2 * x) - 1;
		double properY = (-2 * y) + 1;
		double properMaxX = (2 * maxX) - 1;
		double properMaxY = (-2 * maxY) + 1;
		addVertices(vertices, properX, properY, properMaxX, properMaxY);
		
	}

	private void addVertices(List<Float> vertices, double x, double y, double maxX, double maxY) {
		vertices.add((float) x);
		vertices.add((float) y);
		vertices.add((float) x);
		vertices.add((float) maxY);
		vertices.add((float) maxX);
		vertices.add((float) maxY);
		vertices.add((float) maxX);
		vertices.add((float) maxY);
		vertices.add((float) maxX);
		vertices.add((float) y);
		vertices.add((float) x);
		vertices.add((float) y);
	}
	
	private static void addTexCoords(List<Float> texCoords, double x, double y, double maxX, double maxY) {
		texCoords.add((float) x);
		texCoords.add((float) y);
		texCoords.add((float) x);
		texCoords.add((float) maxY);
		texCoords.add((float) maxX);
		texCoords.add((float) maxY);
		texCoords.add((float) maxX);
		texCoords.add((float) maxY);
		texCoords.add((float) maxX);
		texCoords.add((float) y);
		texCoords.add((float) x);
		texCoords.add((float) y);
	}
	
	private static float[] listToArray(List<Float> listOfFloats) {
		float[] array = new float[listOfFloats.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = listOfFloats.get(i);
		}
		return array;
	}
	
	public void setFontSize(double size) {
		this.FontSize = size;
	}
	
	public double getFontSize() {
		return this.FontSize;
	}
	
	public FontType getFont() {
		return font;
	}
	
	public String getText() {
		return Text;
	}
	
}
