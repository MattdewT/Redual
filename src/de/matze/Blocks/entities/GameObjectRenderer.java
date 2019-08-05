package de.matze.Blocks.entities;

import de.matze.Blocks.entities.components.Component;
import de.matze.Blocks.entities.components.Mesh;
import de.matze.Blocks.entities.components.TransformComponent;
import de.matze.Blocks.entities.textureRenderer.TextureRenderer;
import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.maths.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @autor matze
 * @date 26.04.2017
 */

public class GameObjectRenderer {

    private TextureRenderer textureRenderer;

    private HashMap<Matrix4f, List<Mesh>> texture_meshes;

    public GameObjectRenderer(Matrix4f pr_matrix, Loader loader) {
        textureRenderer = new TextureRenderer(pr_matrix, loader.loadTexture("res/textures/noTexture.png"));

        texture_meshes = new HashMap<>();
    }

    public void renderScene(List<GameObject> game_objects) {
        for(GameObject b : game_objects) {
            TransformComponent trans = (TransformComponent) b.getComponent(Component.ComponentTypes.Transform);
            processMesh(b.getComponentList(Component.ComponentTypes.Mesh), trans.getModelMatrix());
        }
        render(texture_meshes);
    }

    private void processMesh(List<Component> meshes_components, Matrix4f ml_matrix) {
        List<Mesh> meshes = new ArrayList<>();
        for(Component m_c : meshes_components) {
            meshes.add((Mesh) m_c);
        }
        texture_meshes.put(ml_matrix, meshes);
    }

    private void render(HashMap<Matrix4f, List<Mesh>> texture_meshes) {
        textureRenderer.render(texture_meshes);
        texture_meshes.clear();
    }

}
