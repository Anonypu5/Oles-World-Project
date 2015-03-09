package no.Strohm.game2D.items;

/**
 * Created by Ole on 25/01/14.
 */
public class ItemStack {

    private int items = 0;
    private int itemInStackID;
    private int maxStackSize;
    private int iconX;
    private int iconY;
    private String tag;
    private String itemName;

    public ItemStack(Item item) {
        createStack(item);
    }

    private void createStack(Item item) {
        maxStackSize = item.getMaxStackSize();
        itemInStackID = item.getID();
        iconX = item.getIconX();
        iconY = item.getIconY();
        String[] nameParts = item.getTag().split("\\.");

        itemName = nameParts[0];
        tag = nameParts[0] + ".itemstack" + itemName.substring(nameParts[0].length());
    }

    public ItemStack(Item item, int items) {
        createStack(item);
        this.items = (items <= maxStackSize ? items : maxStackSize);
    }

    public void addItem(int amount) {
        int itemsLeft = maxStackSize - items;
        items += (amount <= itemsLeft ? amount : itemsLeft);
    }

    public void removeItem(int amount) {
        items -= (amount <= getAmount() ? amount : getAmount());
    }

    public int getAmount() {
        return items;
    }

    public int getItemInStackID() {
        return itemInStackID;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public int getIconY() {
        return iconY;
    }

    public int getIconX() {
        return iconX;
    }

    public String getItemName() {
        return itemName;
    }

    public String getTag() {
        return tag;
    }
}
