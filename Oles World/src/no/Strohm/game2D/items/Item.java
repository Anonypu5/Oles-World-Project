package no.Strohm.game2D.items;

import no.Strohm.game2D.items.itemList.*;

/**
 * Created by Ole on 25/01/14.
 */
public class Item {

    //maximum of 256 items { 0 - 255 }
    private static Item[] items = new Item[256];
    private final int ID;
    private final int iconX, iconY;
    private final String tag;
    private final int maxStackSize;

    public static final Item WOOD = new ItemWood(0);
    public static final Item STONE = new ItemStone(1);
    public static final Item IRON_SWORD = new ItemSwordIron(2);
    public static final Item FIST = new ItemFist(3);
    public static final Item DIRT = new ItemDirt(4);

    public Item(int ID, String tag, int iconX, int iconY, int maxStackSize) {
        this.ID = ID;
        this.tag = tag + ".item";
        this.iconX = iconX;
        this.iconY = iconY;
        this.maxStackSize = maxStackSize;
        if (items[ID] == null) items[ID] = this;
        else if (ID >= items.length)
            System.err.println("the ID " + ID + " Is too high");
        else
            System.err.println("An Item with the ID " + ID + " Already exists");
    }

    public static Item getItemByID(int id) {
        return items[id];
    }

    public int getIconX() {
        return iconX;
    }

    public int getIconY() {
        return iconY;
    }

    public String getTag() {
        return tag;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public int getID() {
        return ID;
    }
}
