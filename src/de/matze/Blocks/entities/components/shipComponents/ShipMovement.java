package de.matze.Blocks.entities.components.shipComponents;

import de.matze.Blocks.entities.GameObject;
import de.matze.Blocks.entities.components.Component;
import de.matze.Blocks.entities.components.TransformComponent;
import de.matze.Blocks.input.Keyboard;
import de.matze.Blocks.utils.WindowUtils;

import static org.lwjgl.glfw.GLFW.*;

/**
 * @autor matze
 * @date 26.04.2017
 */

public class ShipMovement extends Component{

    private final float maxXPos =  3.81f;
    private final float minXPos = -3.81f;
    public final float maxYPos =  2.74f;
    private final float minYPos = -2.74f;

    private float Acceleration;
    private float friction;

    private TransformComponent trans;
    private Velocity velo;

    public ShipMovement(GameObject b, float Acceleration, float friction) {
        super(ComponentTypes.ShipMovement);
        this.trans = (TransformComponent) b.getComponent(ComponentTypes.Transform);
        this.velo = (Velocity) b.getComponent(ComponentTypes.Velocity);
        this.Acceleration = Acceleration;
        this.friction = friction;
    }

    public void update() {

        boolean new_acceleration = false;

        if(Keyboard.keys[GLFW_KEY_W]){
            velo.getVelocity().y += Acceleration * WindowUtils.getFrameTimeSeconds();
            new_acceleration = true;
        }
        if(Keyboard.keys[GLFW_KEY_S]){
            velo.getVelocity().y -= Acceleration * WindowUtils.getFrameTimeSeconds();
            new_acceleration = true;
        }
        if(Keyboard.keys[GLFW_KEY_D]){
            velo.getVelocity().x += Acceleration * WindowUtils.getFrameTimeSeconds();
            new_acceleration = true;
//            turningRight = true;
        }
        if(Keyboard.keys[GLFW_KEY_A]){
            velo.getVelocity().x -= Acceleration * WindowUtils.getFrameTimeSeconds();
            new_acceleration = true;
//            turningLeft = true;
        }

        if(!new_acceleration) {
            if(velo.getVelocity().x >= 0) {
                velo.getVelocity().x -= friction * WindowUtils.getFrameTimeSeconds();
            }
            if(velo.getVelocity().x <= 0) {
                velo.getVelocity().x += friction * WindowUtils.getFrameTimeSeconds();
            }
            if(velo.getVelocity().y >= 0) {
                velo.getVelocity().y -= friction * WindowUtils.getFrameTimeSeconds();
            }
            if(velo.getVelocity().y <= 0) {
                velo.getVelocity().y += friction * WindowUtils.getFrameTimeSeconds();
            }
        }

        velo.round();           //get rid off floating point errors

        trans.getPos().x += velo.getVelocity().x * WindowUtils.getFrameTimeSeconds();
        trans.getPos().y += velo.getVelocity().y * WindowUtils.getFrameTimeSeconds();

        if(trans.getPos().x > maxXPos) {
            trans.getPos().x = maxXPos;
            velo.getVelocity().x = 0;
        }
        if(trans.getPos().y > maxYPos) {
            trans.getPos().y = maxYPos;
            velo.getVelocity().y = 0;
        }
        if(trans.getPos().x < minXPos) {
            trans.getPos().x = minXPos;
            velo.getVelocity().x = 0;
        }
        if(trans.getPos().y < minYPos) {
            trans.getPos().y = minYPos;
            velo.getVelocity().y = 0;
        }

        velo.update();
    }
}
