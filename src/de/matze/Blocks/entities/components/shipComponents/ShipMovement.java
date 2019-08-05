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

    private float MoveSpeed;

    private TransformComponent trans;

    public ShipMovement(GameObject b, float MoveSpeed) {
        super(ComponentTypes.ShipMovement);
        this.trans = (TransformComponent) b.getComponent(ComponentTypes.Transform);
        this.MoveSpeed = MoveSpeed;
    }

    public void update() {

        if(Keyboard.keys[GLFW_KEY_W]){
            trans.getPos().y += MoveSpeed  * WindowUtils.getFrameTimeSeconds();
        }
        if(Keyboard.keys[GLFW_KEY_S]){
            trans.getPos().y -= MoveSpeed  * WindowUtils.getFrameTimeSeconds();
        }
        if(Keyboard.keys[GLFW_KEY_D]){
            trans.getPos().x += MoveSpeed  * WindowUtils.getFrameTimeSeconds();
//            turningRight = true;
        }
        if(Keyboard.keys[GLFW_KEY_A]){
            trans.getPos().x -= MoveSpeed * WindowUtils.getFrameTimeSeconds();
//            turningLeft = true;
        }

        if(trans.getPos().x > maxXPos)
            trans.getPos().x = maxXPos;
        if(trans.getPos().y > maxYPos)
            trans.getPos().y = maxYPos;
        if(trans.getPos().x < minXPos)
            trans.getPos().x = minXPos;
        if(trans.getPos().y < minYPos)
            trans.getPos().y = minYPos;
    }
}
