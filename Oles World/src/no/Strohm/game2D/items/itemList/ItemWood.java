package no.Strohm.game2D.items.itemList;

import no.Strohm.game2D.items.Item;
import no.Strohm.game2D.items.interfaces.Resource;

/**
 * Created by Ole on 25/01/14.
 */
public class ItemWood extends Item implements Resource{

    public ItemWood(int ID) {
        super(ID, "wood", 0, 0, 99);
    }

}
