package no.Strohm.game2D.world.tiles;

import no.Strohm.game2D.entity.Entity;
import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.world.World;

/**
 * Created by Ole on 18/12/13.
 */
public class TileHole extends Tile {


    public TileHole(int x, int y, World world) {
        super(holeId, 1, x, y, world, "hole");
    }

    public void tick() {

    }

    public void render(Screen screen) {
        screen.render(sheet, 0, 1, 16, pos.getX() << 4, pos.getY() << 4, true, 0);
        screen.render(sheet, 0, 2, 16, pos.getX() << 4, pos.getY() << 4, true, 0);
    }

    public void getHurt(Entity source, int dmg) {
    }

    public void die() {
    }

    public boolean solid() {
        return false;
    }
}
