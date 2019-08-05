package de.matze.Blocks.mechanics.gui.font;

public class Character {
	private double id, width, heigth, xoffset, yoffset, xadvance, texxmax, texymax, texxmin, texymin;
	

	public Character (double id, double x, double y, double width, double heigth, double xoffset, double yoffset, double xadvance) {
		this.id = id;
		this.texxmin = x / 512;
		this.texymin = y / 512;
		this.texxmax = texxmin + width / 512;
		this.texymax = texymin + heigth / 512;
		this.width = width;
		this.heigth = heigth;
		this.xoffset = xoffset;
		this.yoffset = yoffset;
		this.xadvance = xadvance - 5; //ToDO: check 10
	}

	public void PrintChar() {
		System.out.println("id: " + getId() + " x: " + getTexXmin() + " y: " +  getTexYmin() + " width: " + getWidth() + " heigth: " + getHeigth() + " xoffset: " + getXoffset() + " yoffset: " + getYoffset() + " xadvance: " + getXadvance());
	}

	public double getId() {
		return id;
	}

	public double getWidth() {
		return width;
	}

	public double getHeigth() {
		return heigth;
	}

	public double getXoffset() {
		return xoffset;
	}

	public double getYoffset() {
		return yoffset;
	}

	public double getXadvance() {
		return xadvance;
	}
	
	public double getTexXmax() {
		return texxmax;
	}
	
	public double getTexYmax() {
		return texymax;
	}
	
	public double getTexXmin() {
		return texxmin;
	}
	
	public double getTexYmin() {
		return texymin;
	}
}
