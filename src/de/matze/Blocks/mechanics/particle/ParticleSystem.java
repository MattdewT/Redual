package de.matze.Blocks.mechanics.particle;

/**
 * @author matze tiroch
 * @version 1.0
 *          Created by matze on 10.09.16.
 */
import de.matze.Blocks.utils.WindowUtils;
import de.matze.Blocks.maths.Vector3f;

public class ParticleSystem {

    private float pps;
    private float speed;
    private float gravityComplient;
    private float lifeLength;

    public ParticleSystem(float pps, float speed, float gravityComplient, float lifeLength) {
        this.pps = pps;
        this.speed = speed;
        this.gravityComplient = gravityComplient;
        this.lifeLength = lifeLength;
    }

    public void generateParticles(Vector3f systemCenter){
        float delta = WindowUtils.getFrameTimeSeconds();
        float particlesToCreate = pps * delta;
        int count = (int) Math.floor(particlesToCreate);
        float partialParticle = particlesToCreate % 1;
        for(int i=0;i<count;i++){
            emitParticle(systemCenter);
        }
        if(Math.random() < partialParticle){
            emitParticle(systemCenter);
        }
    }

    private void emitParticle(Vector3f center){
        float dirX = (float) Math.random() * 2f - 1f;
        float dirZ = (float) Math.random() * 2f - 1f;
        Vector3f velocity = new Vector3f(dirX, 1, dirZ);
        velocity.normalize();
        velocity.scale(speed);
        new Particle(new Vector3f(center), velocity, gravityComplient, lifeLength, 0, 1);
    }



}
