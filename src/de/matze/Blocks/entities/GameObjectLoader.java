package de.matze.Blocks.entities;

import de.matze.Blocks.entities.components.Mesh;
import de.matze.Blocks.entities.components.TransformComponent;
import de.matze.Blocks.entities.components.shipComponents.ShipMovement;
import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.graphics.Texture;
import de.matze.Blocks.graphics.VertexArray;
import de.matze.Blocks.maths.Vector3f;
import de.matze.Blocks.utils.FileUtils;
import de.matze.Blocks.utils.OBJLoader;

/**
 * @autor matze
 * @date 25.04.2017
 */

public class GameObjectLoader {

    Loader loader;

    public GameObjectLoader(Loader loader) {
        this.loader = loader;
    }

    public GameObject loadGameObject(String path) {

        StringBuilder str = new StringBuilder();
        str.append(path);
        str.append("/gameobject");
        String file_path = str.toString();

        String[] gui_info = FileUtils.loadAsString(file_path).split("\n");

        int id = Integer.parseInt(gui_info[0]);
        GameObject gameObject = new GameObject(id);

        for (int i = 1; i < gui_info.length; i++) {
            String[] currentLine = gui_info[i].split(" ");

            switch(currentLine[0]) {
                case "Body":   handleBody(gameObject, currentLine, path, i);
                    break;
                case "Magazin":     handleMagazin(gameObject, currentLine, file_path, i);
                    break;
                case "Transform":    handleTransform(gameObject, currentLine, file_path, i);
                    break;
                case "ShipMovement":    handleShipMovement(gameObject, currentLine, file_path, i);
                    break;
                default:    System.err.println("Error in " + file_path + " - Line:" + ++i);
                    break;
            }
        }

        return gameObject;
    }

    private void handleBody(GameObject b, String[] info, String path, int i) {
        try {
            StringBuilder str = new StringBuilder();
            str.append(path);
            str.append('/');
            str.append(info[1]);
            VertexArray vao = OBJLoader.loadObjModel(str.toString(), loader);
            str.setLength(0);

            str.append(path);
            str.append('/');
            str.append(info[2]);
            Texture tex = loader.loadTexture(str.toString());

            b.addComponent(new Mesh(vao, tex));

            //System.out.println(vao.getCount());
        } catch (Exception e){
            System.err.println("Error while parsing Body in: " + path + "gameobject - Line: " + ++i);
        }
    }

    private void handleShipMovement(GameObject b, String[] info, String path, int i) {
        try {
            ShipMovement shipMovement = new ShipMovement(b, Float.parseFloat(info[1]));
            b.addComponent(shipMovement);
        } catch (Exception e){
            System.err.println("Error while parsing ShipMovement in: " + path + " - Line: " + ++i);
        }
    }

    private void handleMagazin(GameObject b, String[] info, String path, int i) {
        try {

        } catch (Exception e){
            System.err.println("Error while parsing Magazin in: " + path + " - Line: " + ++i);
        }
    }

    private void handleTransform(GameObject b, String[] info, String path, int i) {
        try {
            b.addComponent(new TransformComponent(new Vector3f(), new Vector3f()));
        } catch (Exception e){
            System.err.println("Error while parsing Transform in: " + path + " - Line: " + ++i);
        }
    }
}
