package no.Strohm.game2D.entity.particles;

import no.Strohm.game2D.entity.Entity;
import no.Strohm.game2D.graphics.SpriteSheet;
import no.Strohm.game2D.world.World;

import java.util.Random;

/**
 * Created by Ole on 20/12/13.
 */
public class Particle extends Entity {

    protected int life;
    protected Random random;

    public Particle(double x, double y, World world, SpriteSheet sheet, String tag) {
        super(x, y, world, sheet, tag + ".particle");
        random = new Random();
    }

    protected void die() {
        world.removeEntity(this);
    }
}
