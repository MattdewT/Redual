package de.matze.Blocks.graphics;

import de.matze.Blocks.utils.TextureData;

public class Texture {
	
	private int width, height;
	private int texID;
	
	public Texture(int tex_id, TextureData data) {
		this.texID = tex_id;
		this.height = data.getHeight();
		this.width = data.getWidth();
	}
	
	public Texture(int tex_id, int width, int heigth) {
		this.texID = tex_id;
		this.height = heigth;
		this.width = width;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getTexID() {
		return texID;
	}
	
//	public void bind() {
//		glBindTexture(GL_TEXTURE_2D, texture);
//	}
//	
//	public void unbind() {
//		glBindTexture(GL_TEXTURE_2D, 0);
//	}

}