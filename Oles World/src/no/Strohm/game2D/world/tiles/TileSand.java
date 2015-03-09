package no.Strohm.game2D.world.tiles;

import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.world.World;

/**
 * Created by Ole on 18/12/13.
 */
public class TileSand extends Tile {


    public TileSand(int x, int y, World world) {
        super(sandId, 10, x, y, world, "sand");
    }

    public void tick() {

    }

    public void render(Screen screen) {
        screen.render(sheet, 4, 0, 16, pos.getX() << 4, pos.getY() << 4, true, 0);
    }

    public void die() {
        super.die();
        world.setTile(holeId, pos.getX(), pos.getY());
    }
}
