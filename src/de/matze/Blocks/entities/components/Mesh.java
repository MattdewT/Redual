package de.matze.Blocks.entities.components;

import de.matze.Blocks.graphics.Texture;
import de.matze.Blocks.graphics.VertexArray;

/**
 * @autor matze
 * @date 18.04.2017
 */

public class Mesh extends Component{

    private VertexArray vao;
    private Texture tex;

    private boolean hasTexture;

    public Mesh(VertexArray vao) {
        super(ComponentTypes.Mesh);
        this.vao = vao;
        hasTexture = false;
    }

    public Mesh(VertexArray vao, Texture tex) {
        super(ComponentTypes.Mesh);
        this.vao = vao;
        this.tex = tex;
        hasTexture = true;
    }

    public VertexArray getVao() {
        return vao;
    }

    public Texture getTex() {
        return tex;
    }

    public boolean isHasTexture() {
        return hasTexture;
    }
}
