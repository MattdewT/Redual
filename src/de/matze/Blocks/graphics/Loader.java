package de.matze.Blocks.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.FloatBuffer;
import java.util.*;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.*;

import de.matze.Blocks.utils.*;

public class Loader {

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();


    //---------------------------------vao & vbo - stuff------------------------------------
    public VertexArray loadToVAO(float[] verticies, float[] textureCoords) {
        int VaoID = createVAO();
        AddAttributeList(verticies, 0, 2);
        AddAttributeList(textureCoords, 1, 2);
        unbindVAO();
        return new VertexArray(VaoID, verticies.length / 2);
    }

    public VertexArray loadToVAO(float[] verticies, float[] textureCoords, float[] normals, int[] indices) {
        int VaoID = createVAO();
        bindIndicesBuffer(indices);
        AddAttributeList(verticies, 0, 3);
        AddAttributeList(textureCoords, 1, 2);
        AddAttributeList(normals, 2, 3);
        unbindVAO();
        return new VertexArray(VaoID, indices.length);
    }

    public VertexArray loadToVAO(float[] verticies, float[] textureCoords, int[] indices) {
        int VaoID = createVAO();
        bindIndicesBuffer(indices);
        AddAttributeList(verticies, 0, 3);
        AddAttributeList(textureCoords, 1, 2);
        unbindVAO();
        return new VertexArray(VaoID, indices.length);
    }

    public VertexArray loadToVAO(float[] verticies, int[] indices) {
        int VaoID = createVAO();
        bindIndicesBuffer(indices);
        AddAttributeList(verticies, 0, 2);
        unbindVAO();
        return new VertexArray(VaoID, indices.length);
    }


    public VertexArray loadToVAO(float[] positions, int dimensions) {
        int vaoID = createVAO();
        this.AddAttributeList(positions, 0, dimensions);
        unbindVAO();
        return new VertexArray(vaoID, positions.length / dimensions);
    }

    public void AddAttributeList(float[] data, int attributeNumber, int coordinateSize) {
        int vboID = glGenBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(data), GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber, coordinateSize, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public int bindIndicesBuffer(int[] indices) {
        int ibo = glGenBuffers();
        vbos.add(ibo);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(indices), GL_STATIC_DRAW);
        return ibo;
    }

    private int createVAO() {
        int vao = glGenVertexArrays();
        vaos.add(vao);
        glBindVertexArray(vao);
        return vao;
    }

    public int creatEmptyVbo (int floatCount) {
        int vbo = GL15.glGenBuffers();
        vbos.add(vbo);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, floatCount * 4, GL_STREAM_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return vbo;
    }

    public void updateVbo(int vbo, float[] data, FloatBuffer buffer) {
        buffer.clear();
        buffer.put(data);
        buffer.flip();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, buffer.capacity() * 4, GL_STREAM_DRAW);
        glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void addInstancedAttribute (int vao, int vbo, int attribute, int dataSize, int instancedDataLength, int offset) {
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBindVertexArray(vao);
        glVertexAttribPointer(attribute, dataSize, GL_FLOAT, false, instancedDataLength * 4, offset * 4);
        glVertexAttribDivisor(attribute, 1);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void unbindVAO() {
        glBindVertexArray(0);
    }

    //------------------------------------aabb---------------------------------------------
//	private aabb LoadAABB(float[] verticies) {
//		Vector3f min = new Vector3f();
//		Vector3f max = new Vector3f();
//
//		for (int i = 0; i < verticies.length ; i += 3) {
//			if(verticies[i] < min.x) {
//				min.x = verticies[i];
//			} else if(verticies[i] > max.x) {
//				max.x = verticies[i];
//			}
//			if(verticies[i+1] < min.y) {
//				min.y = verticies[i+1];
//			} else if(verticies[i+1] > max.y) {
//				max.y = verticies[i+1];
//			}
//			if(verticies[i+2] < min.z) {
//				min.z = verticies[i+2];
//			} else if(verticies[i+2] > max.z) {
//				max.z = verticies[i+2];
//			}
//		}
//
//		return new aabb(min, max);
//	}
    //---------------------------------------------Texture stuff ----------------------------------
    public int loadCubeMap(String[] textureFiles) {
        int texID = glGenTextures();
        glActiveTexture(GL13.GL_TEXTURE0);
        glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texID);

        for (int i = 0; i < textureFiles.length; i++) {
            TextureData data = decodeTextureFile("res/textures/skybox/" + textureFiles[i] + ".png");
            GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA, data.getWidth(), data.getHeight(), 0,
                    GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data.getBuffer());
        }

        glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, 0);
        textures.add(texID);
        return texID;
    }

    public Texture loadTexture(String path) {
        TextureData data = decodeTextureFile(path);

        int texID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, data.getWidth(), data.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data.getBuffer());
        glBindTexture(GL_TEXTURE_2D, 0);
        textures.add(texID);
        return new Texture(texID, data);

    }

    private TextureData decodeTextureFile(String path) {
        int[] pixels = null;
        int width = 0, height = 0;
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(path));
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] data = new int[width * height];
        for (int i = 0; i < width * height; i++) {
            assert pixels != null;
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00) >> 8;
            int b = (pixels[i] & 0xff);

            data[i] = a << 24 | b << 16 | g << 8 | r;
        }
        return new TextureData(BufferUtils.createIntBuffer(data), width, height);
    }

    //-----------------------------------------------------------------------------------------
    public void cleanUp() {
        for (int vao : vaos) {
            glDeleteVertexArrays(vao);
        }
        for (int vbo : vbos) {
            glDeleteBuffers(vbo);
        }
        for (int texture : textures) {
            glDeleteTextures(texture);
        }
    }

    public static float[] listToArray(List<Float> listOfFloats) {
        float[] array = new float[listOfFloats.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = listOfFloats.get(i);
        }
        return array;
    }

    public static int[] listToArrayInt(List<Integer> listOfIntegers) {
        int[] array = new int[listOfIntegers.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = listOfIntegers.get(i);
        }
        return array;
    }
}
