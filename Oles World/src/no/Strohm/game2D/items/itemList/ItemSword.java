package no.Strohm.game2D.items.itemList;

import no.Strohm.game2D.entity.mob.Mob;
import no.Strohm.game2D.items.Item;
import no.Strohm.game2D.items.interfaces.Usable;
import no.Strohm.game2D.world.World;

/**
 * Created by Ole on 29/04/2014.
 */
public abstract class ItemSword extends Item implements Usable {

    public ItemSword(int ID, String tag, int iconX, int iconY) {
        super(ID, tag + ".sword", iconX, iconY, 1);
    }

    @Override
    public void use(Mob source, World world) {

    }
}
