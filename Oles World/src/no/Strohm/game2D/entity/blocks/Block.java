package no.Strohm.game2D.entity.blocks;

import no.Strohm.game2D.entity.Entity;
import no.Strohm.game2D.world.World;

/**
 * Created by Ole on 26/01/14.
 */
public abstract class Block extends Entity {

    public Block(int x, int y, World world, String tag) {
        super(x, y, world, null, tag + ".block");
    }
}