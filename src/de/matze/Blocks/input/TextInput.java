package de.matze.Blocks.input;

import org.lwjgl.glfw.GLFWCharCallback;

/**
 * @autor matze
 * @date 18.02.2017
 */

public class TextInput extends GLFWCharCallback {

    @Override
    public void invoke(long window, int codepoint) {
        System.out.print((char) codepoint);
    }
}
