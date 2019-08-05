package de.matze.Blocks.mechanics.particle;

import de.matze.Blocks.maths.Vector3f;
import de.matze.Blocks.utils.WindowUtils;

/**
 * @author matze tiroch
 * @version 1.0
 *          Created by matze on 06.09.16.
 */
public class Particle {

    private final float GRAVITY = 50;

    private Vector3f position;
    private Vector3f velocity;
    private float gravityEffect;
    private float lifeLength;
    private float elapsedTime;
    private float rotation;
    private float scale;

    public Particle(Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation, float scale) {
        this.position = position;
        this.velocity = velocity;
        this.gravityEffect = gravityEffect;
        this.lifeLength = lifeLength;
        this.rotation = rotation;
        this.scale = scale;
        ParticleManager.addParticle(this);
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public boolean update() {
        velocity.y -= GRAVITY * gravityEffect * WindowUtils.getFrameTimeSeconds();
        Vector3f change = new Vector3f(velocity);
        change.scale(WindowUtils.getFrameTimeSeconds());
        change.add(change, position, position);
        elapsedTime += WindowUtils.getFrameTimeSeconds();
        return elapsedTime < lifeLength;
    }
}
