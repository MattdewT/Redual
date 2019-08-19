package de.matze.Blocks.entities.components.shipComponents;

import de.matze.Blocks.entities.components.Component;
import de.matze.Blocks.maths.Vector3f;

public class Velocity extends Component {

    private Vector3f velocity;
    private float max_velocity;

    public Velocity(float max_velocity) {
        super(ComponentTypes.Velocity);
        velocity = new Vector3f();
        this.max_velocity = max_velocity;
    }

    public Velocity(Vector3f v) {
        super(ComponentTypes.Velocity);
        velocity = v;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public void update() {
        if (velocity.x > max_velocity)
            velocity.x = max_velocity;
        if (velocity.x < -max_velocity)
            velocity.x = -max_velocity;
        if (velocity.y > max_velocity)
            velocity.y = max_velocity;
        if (velocity.y < -max_velocity)
            velocity.y = -max_velocity;
    }
}
