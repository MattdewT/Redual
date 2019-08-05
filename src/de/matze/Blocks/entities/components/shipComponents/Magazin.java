package de.matze.Blocks.entities.components.shipComponents;

import de.matze.Blocks.entities.GameObject;
import de.matze.Blocks.entities.components.Component;
import de.matze.Blocks.entities.components.Mesh;
import de.matze.Blocks.entities.components.TransformComponent;
import de.matze.Blocks.utils.WindowUtils;

/**
 * @autor matze
 * @date 25.04.2017
 */

public class Magazin extends Component {

    private int size;
    private float reload_time;  //in ms
    private float passed_time;  //in ms
    private Mesh[] meshes;
    private int current_load;

    private TransformComponent trans;

    public Magazin(int size, float reload_time, GameObject b) {
        super(ComponentTypes.Magazin);
        this.size = size;
        this.reload_time = reload_time;
        meshes = new Mesh[size];
        passed_time = 0;
        current_load = size;
        this.trans = (TransformComponent) b.getComponent(ComponentTypes.Transform);
    }

    private int fill_counter = 0;

    public void addMagazinMaesh(Mesh mesh) {
        meshes[fill_counter++] = mesh;
    }

    public int getSize() {
        return size;
    }

    public float getReload_time() {
        return reload_time;
    }

    public void update() {
        passed_time += WindowUtils.getFrameTimeSeconds() * 1000;
        if(passed_time >= reload_time) {
            passed_time = 0;
            meshes[current_load].setEnable(true);
            current_load++;
            if(current_load > size -1 )
                current_load = size -1;
        }
    }

    public void setCurrentLoad(int i) {
        current_load = i;
        for(; i > 0; i--) {
            meshes[i].setEnable(false);
        }
    }
}
