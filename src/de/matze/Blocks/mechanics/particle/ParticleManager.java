package de.matze.Blocks.mechanics.particle;

import de.matze.Blocks.entities.components.CameraComponent;
import de.matze.Blocks.graphics.Loader;
import de.matze.Blocks.maths.Matrix4f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author matze tiroch
 * @version 1.0
 *          Created by matze on 06.09.16.
 */
public class ParticleManager {

    private static List<Particle> particles;
    private static ParticleRenderer renderer;

    private ParticleManager(){}

    public static void initParticleManager(Loader loader, Matrix4f pr_matrix) {
        renderer = new ParticleRenderer(loader, pr_matrix);
        particles = new ArrayList<>();
    }

    public static void update() {
        Iterator<Particle> iterator = particles.iterator();
        while(iterator.hasNext()) {
            Particle p = iterator.next();
            boolean stillAlive = p.update();
            if(!stillAlive) {
                iterator.remove();
            }
        }
     }

     public static void renderParticles(CameraComponent cam) {
         renderer.render(particles, cam);
     }

     public static void cleanUp() {
         renderer.cleanUp();
     }

     public static void addParticle(Particle p) {
         particles.add(p);
     }

}
