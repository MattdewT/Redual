package de.matze.Blocks.input;

import org.lwjgl.glfw.GLFWScrollCallback;

public class MouseWheel extends GLFWScrollCallback{
	
	private static double mouse_wheelX = 0;
	private static double mouse_wheelY = 0;
	
	private static double mouse_wheelDX = 0;
	private static double mouse_wheelDY = 0;
	
	private static double oldmouse_wheelX = 0;
	private static double oldmouse_wheelY = 0;

	@Override
	public void invoke(long window, double xoffset, double yoffset) {
		oldmouse_wheelX = mouse_wheelX;
		oldmouse_wheelY = mouse_wheelY;
		
        mouse_wheelX = xoffset;
        mouse_wheelY = yoffset;
        
        setMouse_wheelDX(oldmouse_wheelX - mouse_wheelX);
        setMouse_wheelDY(oldmouse_wheelY - mouse_wheelY);
	}

	public static double getMouse_wheelDX() {
		return mouse_wheelDX;
	}

	public static void setMouse_wheelDX(double mouse_wheelDX) {
		MouseWheel.mouse_wheelDX = mouse_wheelDX;
	}

	public static double getMouse_wheelDY() {
		return mouse_wheelDY;
	}

	public static void setMouse_wheelDY(double mouse_wheelDY) {
		MouseWheel.mouse_wheelDY = mouse_wheelDY;
	}

	public static double getMouse_wheelX() {
		return mouse_wheelX;
	}

	public static double getMouse_wheelY() {
		return mouse_wheelY;
	}

}
