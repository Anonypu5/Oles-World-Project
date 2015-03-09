package no.Strohm.game2D.world.tiles;

import no.Strohm.game2D.entity.particles.ParticleItemPickup;
import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.items.Item;
import no.Strohm.game2D.items.ItemStack;
import no.Strohm.game2D.world.World;

/**
 * Created by Ole on 18/12/13.
 */
public class TileStone extends Tile {


    public TileStone(int x, int y, World world) {
        super(stoneId, 50, x, y, world, "stone");
    }

    public void tick() {

    }

    public void render(Screen screen) {
        boolean u = world.getTile(pos.getX(), pos.getY() - 1).id == stoneId;
        boolean d = world.getTile(pos.getX(), pos.getY() + 1).id == stoneId;
        boolean l = world.getTile(pos.getX() - 1, pos.getY()).id == stoneId;
        boolean r = world.getTile(pos.getX() + 1, pos.getY()).id == stoneId;

        boolean ul = world.getTile(pos.getX() - 1, pos.getY() - 1).id == stoneId;
        boolean ur = world.getTile(pos.getX() + 1, pos.getY() - 1).id == stoneId;
        boolean dl = world.getTile(pos.getX() - 1, pos.getY() + 1).id == stoneId;
        boolean dr = world.getTile(pos.getX() + 1, pos.getY() + 1).id == stoneId;

        int xx = pos.getX() << 4;
        int yy = pos.getY() << 4;
        if (u && d && l && r && ul && ur && dl && dr) {
            screen.render(sheet, 5, 1, 8, xx, yy, true, 0);
            screen.render(sheet, 5, 1, 8, xx + 8, yy, true, 0);
            screen.render(sheet, 5, 1, 8, xx, yy + 8, true, 0);
            screen.render(sheet, 5, 1, 8, xx + 8, yy + 8, true, 0);
        } else {
            screen.render(sheet, 0, 1, 16, xx, yy, true, 0);
            if (!u && !d && !l && !r && !ul && !ur && !dl && !dr) {
                screen.render(sheet, 4, 0, 8, xx, yy, true, 0);
                screen.render(sheet, 4, 0, 8, xx + 8, yy, true, 4);
                screen.render(sheet, 4, 0, 8, xx, yy + 8, true, 6);
                screen.render(sheet, 4, 0, 8, xx + 8, yy + 8, true, 5);
            } else {
                //Top-Left corner rendering
                if (!u && !l) {
                    screen.render(sheet, 4, 0, 8, xx, yy, true, 0);
                } else if (u && !l) {
                    screen.render(sheet, 4, 1, 8, xx, yy, true, 6);
                } else if (!u && l) {
                    screen.render(sheet, 4, 1, 8, xx, yy, true, 0);
                } else if (u && l && ul) {
                    screen.render(sheet, 5, 1, 8, xx, yy, true, 0);
                } else if (u && l && !ul) {
                    screen.render(sheet, 5, 0, 8, xx, yy, true, 0);
                }
                //Top-Right Corner rendering
                if (!u && !r) {
                    screen.render(sheet, 4, 0, 8, xx + 8, yy, true, 4);
                } else if (u && !r) {
                    screen.render(sheet, 4, 1, 8, xx + 8, yy, true, 4);
                } else if (!u && r) {
                    screen.render(sheet, 4, 1, 8, xx + 8, yy, true, 0);
                } else if (u && r && ur) {
                    screen.render(sheet, 5, 1, 8, xx + 8, yy, true, 0);
                } else if (u && r && !ur) {
                    screen.render(sheet, 5, 0, 8, xx + 8, yy, true, 4);
                }

                //Bottom-Left Corner rendering
                if (!d && !l) {
                    screen.render(sheet, 4, 0, 8, xx, yy + 8, true, 6);
                } else if (d && !l) {
                    screen.render(sheet, 4, 1, 8, xx, yy + 8, true, 6);
                } else if (!d && l) {
                    screen.render(sheet, 4, 1, 8, xx, yy + 8, true, 5);
                } else if (d && l && dl) {
                    screen.render(sheet, 5, 1, 8, xx, yy + 8, true, 0);
                } else if (d && l && !dl) {
                    screen.render(sheet, 5, 0, 8, xx, yy + 8, true, 6);
                }

                //Bottom-Right Corner rendering
                if (!d && !r) {
                    screen.render(sheet, 4, 0, 8, xx + 8, yy + 8, true, 5);
                } else if (d && !r) {
                    screen.render(sheet, 4, 1, 8, xx + 8, yy + 8, true, 4);
                } else if (!d && r) {
                    screen.render(sheet, 4, 1, 8, xx + 8, yy + 8, true, 5);
                } else if (d && r && dr) {
                    screen.render(sheet, 5, 1, 8, xx + 8, yy + 8, true, 0);
                } else if (d && r && !dr) {
                    screen.render(sheet, 5, 0, 8, xx + 8, yy + 8, true, 5);
                }

            }
        }
    }

    public boolean solid() {
        return true;
    }

    public void onDeath() {

        dropItem(1, 3, Item.STONE);
    }

    public void die() {
        super.die();
        world.setTile(dirtId, pos.getX(), pos.getY());
    }
}
