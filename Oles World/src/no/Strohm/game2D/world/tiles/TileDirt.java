package no.Strohm.game2D.world.tiles;

import no.Strohm.game2D.Game;
import no.Strohm.game2D.Multiplayer.Client;
import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.items.Item;
import no.Strohm.game2D.world.World;

import java.io.IOException;

public class TileDirt extends Tile {


    public TileDirt(int x, int y, World world) {
        super(dirtId, 10, x, y, world, "dirt");
    }

    public void tick() {

    }

    public void render(Screen screen) {
        int xx = pos.getX() << 4;
        int yy = pos.getY() << 4;
        screen.render(sheet, 0, 1, 16, xx, yy, true, 0);
    }

    public void die() {
        super.die();
        world.setTile(holeId, pos.getX(), pos.getY());

        dropItem(1, 1, Item.DIRT, 1);
    }
}
