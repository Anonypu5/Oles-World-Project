package no.Strohm.game2D.entity.particles;

import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.world.World;

import java.util.Random;

/**
 * Created by Ole on 20/12/13.
 */
public class ParticleText extends Particle {

    private final String message;
    private Random random = new Random();
    private double moveX;
    private double moveY;
    private int col;
    private double xOffs;
    private double yOffs;

    public ParticleText(double x, double y, World world, String msg, int col) {
        super(x, y, world, null, "TextParticle");
        this.message = msg;
        moveX = (random.nextDouble() - 0.5) * 2;
        moveY = (random.nextDouble() - 0.5) * 2;
        this.col = col;
        life = 25;
    }

    public ParticleText(double x, double y, World world, String msg, int col, int lifetime) {
        super(x, y, world, null, "TextParticle");
        this.message = msg;
        moveX = (random.nextDouble() - 0.5) * 2;
        moveY = (random.nextDouble() - 0.5) * 2;
        this.col = col;
        life = lifetime;
    }

    public ParticleText(double x, double y, World world, String msg, int col, int lifetime, double direction) {
        super(x, y, world, null, "TextParticle");
        this.message = msg;
        moveX = Math.cos(Math.toRadians(direction)) * 0.75;
        moveY = Math.sin(Math.toRadians(direction)) * 0.75;
        this.col = col;
        life = lifetime;
    }

    public void tick() {
        double xm = (random.nextInt(4) < 2 ? moveX : 0);
        double ym = (random.nextInt(4) < 2 ? moveY : 0);

        xOffs += xm;
        yOffs += ym;

        life--;
        if (life <= 0) die();
    }

    public void render(Screen screen) {
        screen.renderText(message, ((int) pos.getX() << 4) + (int) xOffs, ((int) pos.getY() << 4) + (int) yOffs, col, true);
    }
}
