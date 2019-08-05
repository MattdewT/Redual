package de.matze.Blocks.entities.components;

import de.matze.Blocks.entities.GameObject;
import de.matze.Blocks.maths.Vector3f;
import de.matze.Blocks.maths.aabb;

/**
 * Axes Aline Bounding Box Component
 *
 * Created by matze on 01.09.16.
 */
public class aabbComponent extends Component {

    private aabb box;

    /**
     * @param min minimum Vector of aabb
     * @param max maximum Vector of aabb
     * @param g move aabb with GameObjects g TransformComponent
     */
    public aabbComponent(Vector3f min, Vector3f max, GameObject g) {
        super(ComponentTypes.aabb);
        box = new aabb(min, max);
        box.moveAABB(((TransformComponent) g.getComponent(ComponentTypes.Transform)).getModelMatrix());
    }

    public aabb getBox() {
        return box;
    }
}
