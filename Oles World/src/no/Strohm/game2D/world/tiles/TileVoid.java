package no.Strohm.game2D.world.tiles;

import no.Strohm.game2D.entity.Entity;
import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.world.World;

/**
 * Created by Ole on 15/12/13.
 */
public class TileVoid extends Tile {

    public TileVoid(int x, int y, World world) {
        super(voidId, 9999, x, y, world, "void");
    }

    public void tick() {
    }

    public void render(Screen screen) {
        boolean u = world.getTile(pos.getX(), pos.getY() - 1).id == voidId;
        boolean ul = world.getTile(pos.getX() - 1, pos.getY() - 1).id == voidId;
        boolean l = world.getTile(pos.getX() - 1, pos.getY()).id == voidId;
        boolean ur = world.getTile(pos.getX() + 1, pos.getY() - 1).id == voidId;
        boolean r = world.getTile(pos.getX() + 1, pos.getY()).id == voidId;
        boolean dl = world.getTile(pos.getX() - 1, pos.getY() + 1).id == voidId;
        boolean d = world.getTile(pos.getX(), pos.getY() + 1).id == voidId;
        boolean dr = world.getTile(pos.getX() + 1, pos.getY() + 1).id == voidId;

        boolean c1 = l && ul && u;
        boolean c2 = u && ur && r;
        boolean c3 = l && d && dl;
        boolean c4 = r && dr && d;

        int xx = pos.getX() << 4;
        int yy = pos.getY() << 4;

        int[] xPos = {2, 2, 2, 2};
        int[] yPos = {0, 0, 1, 1};
        int[] effect = {0, 1, 0, 1};

        if (c1) {
            xPos[0] = 3;
            yPos[0] = 0;
            effect[0] = 0;
        }
        if (c2) {
            xPos[1] = 3;
            yPos[1] = 0;
            effect[1] = 1;
        }
        if (c3) {
            xPos[2] = 3;
            yPos[2] = 0;
            effect[2] = 1;
        }
        if (c4) {
            xPos[3] = 3;
            yPos[3] = 0;
            effect[3] = 0;
        }

        screen.render(sheet, xPos[0], yPos[0], 8, xx, yy, true, effect[0]);
        screen.render(sheet, xPos[1], yPos[1], 8, xx + 8, yy, true, effect[1]);
        screen.render(sheet, xPos[2], yPos[2], 8, xx, yy + 8, true, effect[2]);
        screen.render(sheet, xPos[3], yPos[3], 8, xx + 8, yy + 8, true, effect[3]);
    }

    public void getHurt(Entity source, int dmg) {
    }

    public void die() {
    }

    public boolean breakAble() {
        return false;
    }

    public boolean solid() {
        return true;
    }
}
