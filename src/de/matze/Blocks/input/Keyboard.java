package de.matze.Blocks.input;

import org.lwjgl.glfw.GLFW;


/**
*Input Abfrage der Tastatur.
*Ueberschreibt die Inputabfrage.
*
*@author matze tiroch
*@version 1.1
*/

import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard extends GLFWKeyCallback {
	
	public static boolean[] keys = new boolean[65536];

	public void invoke(long window, int key, int scancode, int action, int mods) {
		try{
			keys[key] = action != GLFW.GLFW_RELEASE;
		} catch(Exception e) {
			System.out.println("Invalid Key");
		}
	}
	
	public static boolean isKeyDown(int keycode) {
		return keys[keycode];
	}
}