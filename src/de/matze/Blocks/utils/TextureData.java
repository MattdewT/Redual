package de.matze.Blocks.utils;

import java.nio.IntBuffer;

public class TextureData {

    private int width;
    private int height;
    private IntBuffer buffer;

    public TextureData(IntBuffer buffer, int width, int height) {
        this.buffer = buffer;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public IntBuffer getBuffer() {
        return buffer;
    }

}
