package no.Strohm.game2D.items.itemList;

import no.Strohm.game2D.entity.mob.Mob;
import no.Strohm.game2D.items.Item;
import no.Strohm.game2D.items.interfaces.Usable;
import no.Strohm.game2D.util.Vector2d;
import no.Strohm.game2D.util.Vector2i;
import no.Strohm.game2D.world.World;

/**
 * Created by Ole on 10/05/2014.
 */
public class ItemFist extends Item implements Usable {

    public ItemFist(int ID) {
        super(ID, "fist", 3, 0, 0);
    }

    @Override
    public void use(Mob source, World world) {
        int dir = source.dir;
        Vector2i pos = Vector2d.toVector2i(source.getPos());

        //TODO: fix this shit.
        if (dir == Mob.dirUp) {
            world.getTile(pos.getX() >> 4, (pos.getY() >> 4) - 1).getHurt(source, source.getStats().getDamage());
        } else if (dir == Mob.dirRight) {
            world.getTile((pos.getX() >> 4) + 1, pos.getY() >> 4).getHurt(source, source.getStats().getDamage());
        } else if (dir == Mob.dirDown) {
            world.getTile(pos.getX() >> 4, ((pos.getY()) >> 4) + 1).getHurt(source, source.getStats().getDamage());
        } else if (dir == Mob.dirLeft) {
            world.getTile((pos.getX() >> 4) - 1, pos.getY() >> 4).getHurt(source, source.getStats().getDamage());
        }
    }

}
