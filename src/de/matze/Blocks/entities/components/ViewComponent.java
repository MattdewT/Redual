package de.matze.Blocks.entities.components;

import de.matze.Blocks.maths.Vector3f;

/**
 * Created by matze on 31.08.16.
 */
public class ViewComponent extends Component {

    public Vector3f viewpoint;

    public ViewComponent() {
        super(ComponentTypes.ViewPoint);
        viewpoint = new Vector3f();
    }

    public void invertPitch() {
        viewpoint.x = -viewpoint.x;
    }
}
