package de.matze.Blocks.graphics;

import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class VertexArray {

    private int VaoID;
    private int count;

    public VertexArray(int VaoID, int count) {
        this.VaoID = VaoID;
        this.count = count;
    }

    public int getVaoID() {
        return VaoID;
    }

    public int getCount() {
        return count;
    }

    public void bind() {
        glBindVertexArray(VaoID);
    }

    public void unbind() {
        glBindVertexArray(0);
    }
}