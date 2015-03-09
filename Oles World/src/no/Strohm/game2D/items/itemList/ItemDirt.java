package no.Strohm.game2D.items.itemList;

import no.Strohm.game2D.entity.mob.Mob;
import no.Strohm.game2D.entity.mob.Player;
import no.Strohm.game2D.items.Item;
import no.Strohm.game2D.items.interfaces.Resource;
import no.Strohm.game2D.items.interfaces.Usable;
import no.Strohm.game2D.util.Vector2d;
import no.Strohm.game2D.util.Vector2i;
import no.Strohm.game2D.world.World;
import no.Strohm.game2D.world.tiles.Tile;

import java.util.Vector;

/**
 * Created by Ole on 25/01/14.
 */
public class ItemDirt extends Item implements Resource, Usable {

    public ItemDirt(int ID) {
        super(ID, "dirt", 4, 0, 99);
    }

    @Override
    public void use(Mob source, World world) {
        int dir = source.dir;
        Vector2i pos = Vector2d.toVector2i(source.getPos());

        Vector2i targeted;
        //TODO: fix this shit.
        if (dir == Mob.dirUp) {
            targeted = new Vector2i(pos.getX() >> 4, (pos.getY() >> 4) - 1);
        } else if (dir == Mob.dirRight) {
            targeted = new Vector2i((pos.getX() >> 4) + 1, pos.getY() >> 4);
        } else if (dir == Mob.dirDown) {
            targeted = new Vector2i(pos.getX() >> 4, ((pos.getY()) >> 4) + 1);
        } else if (dir == Mob.dirLeft) {
            targeted = new Vector2i((pos.getX() >> 4) - 1, pos.getY() >> 4);
        } else {
            targeted = new Vector2i(-1, -1);
        }

        if(world.getTile(targeted.getX(), targeted.getY()).id == Tile.holeId && source instanceof Player) {
            source.setActiveItem(null);
            world.setTile(Tile.dirtId, targeted.getX(), targeted.getY());
        }
    }
}
