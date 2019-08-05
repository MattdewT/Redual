package de.matze.Blocks.input;

import org.lwjgl.glfw.GLFW;


/**
*Input Abfrage der MouseKnöpfe.
*Ueberschreibt die Inputabfrage.
*
*@author matze tiroch
*@version 1.1
*/

import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseButtons extends GLFWMouseButtonCallback {

	public static boolean[] buttons = new boolean[65536];
	
	@Override
	public void invoke(long window, int button, int action, int mods) {
		buttons[button] = action != GLFW.GLFW_RELEASE; 
	}

}
