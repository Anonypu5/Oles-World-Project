package no.Strohm.game2D.containers;

import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.graphics.SpriteSheet;
import no.Strohm.game2D.items.Item;
import no.Strohm.game2D.items.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ole on 26/01/14.
 */
public abstract class ItemContainer extends Container {

    private List<ItemStack> items = new ArrayList<ItemStack>();
    private int selected = 0;

    public ItemContainer(String tag) {
        super(tag + ".item");
    }

    public void tick() {
        if (items.size() > 0) {
            if (player.input.up && moveTimer <= 0) {
                selected--;
                moveTimer = 12;
            }
            if (player.input.down && moveTimer <= 0) {
                selected++;
                moveTimer = 12;
            }
            if (selected < 0) selected = items.size() - 1;
            if (selected >= items.size()) selected = 0;
        }

        if (moveTimer > 0) moveTimer--;
    }

    public void renderContainer(Screen screen) {
        int width = dimensions.getX();
        int height = dimensions.getY();
        int x = pos.getX() - width * 16 / 2;
        int y = pos.getY();

        screen.render(SpriteSheet.effects, 0, 0, 16, x, y, false, 0);
        screen.render(SpriteSheet.effects, 0, 0, 16, x + (width - 1) * 16, y, false, 4);
        screen.render(SpriteSheet.effects, 0, 0, 16, x, y + (height - 1) * 16, false, 6);
        screen.render(SpriteSheet.effects, 0, 0, 16, x + (width - 1) * 16, y + (height - 1) * 16, false, 5);
        for (int i = 0; i < width - 2; i++) {
            screen.render(SpriteSheet.effects, 4, 0, 16, x + ((i + 1) * 16), y, false, 0);
            screen.render(SpriteSheet.effects, 1, 0, 16, x + ((i + 1) * 16), y + (height - 1) * 16, false, 5);
        }
        screen.render(SpriteSheet.effects, 3, 0, 16, x + 16, y, false, 0);
        screen.render(SpriteSheet.effects, 3, 0, 16, x + (width - 2) * 16, y, false, 1);
        for (int i = 0; i < height - 2; i++) {
            screen.render(SpriteSheet.effects, 1, 0, 16, x, y + ((i + 1) * 16), false, 6);
            screen.render(SpriteSheet.effects, 1, 0, 16, x + (width - 1) * 16, y + ((i + 1) * 16), false, 4);
        }
        for (int i = 0; i < height - 2; i++) {
            for (int j = 0; j < width - 2; j++) {
                screen.render(SpriteSheet.effects, 2, 0, 16, x + ((j + 1) * 16), y + ((i + 1) * 16), false, 0);
            }
        }

        screen.renderText(getDisplayName(), x + (width * 8) - getDisplayName().length() * 4, y + 4, 0x0000FF, false);

        if (items.size() > 0) {
            int middle = (selected < 5 ? 5 : selected);

            if (items.size() > 11) {
                if (middle > items.size() - 6) middle = items.size() - 6;
            }

            if (items.size() < 11) middle = 5;

            for (int i = middle - 5; i < (middle + 6 < items.size() ? middle + 6 : items.size()); i++) {
                ItemStack stack = items.get(i);

                int offs = i - middle + 5;

                if (i == getSelected() && focused)
                    screen.renderArea(0x564545, x + 9, x + ((width - 1) * 16) + 7, y + 14 + (offs * 11), y + 25 + (offs * 11), false);
                else if (i == getSelected())
                    screen.renderArea(0x6B5356, x + 9, x + ((width - 1) * 16) + 7, y + 14 + (offs * 11), y + 25 + (offs * 11), false);

                screen.render(SpriteSheet.itemIcons, stack.getIconX(), stack.getIconY(), 8, x + 9, y + 16 + (offs * 11), false, 0);
                screen.renderText(stack.getAmount() + " " + stack.getItemName().split("\\.")[0], x + 18, y + 16 + (offs * 11), 0xFFFFFF, false);
            }
        }
    }

    private ItemStack getItemStack(Item item) {
        int ID = item.getID();
        ItemStack stack;

        for (int i = 0; i < items.size(); i++) {
            stack = items.get(i);
            if (stack.getItemInStackID() == ID && stack.getAmount() < stack.getMaxStackSize())
                return stack;
        }
        stack = new ItemStack(item);
        items.add(stack);
        return stack;
    }

    private int getItemStackIndex(Item item) {
        int ID = item.getID();
        ItemStack stack;

        for (int i = 0; i < items.size(); i++) {
            stack = items.get(i);
            if (stack.getItemInStackID() == ID && stack.getAmount() < stack.getMaxStackSize())
                return i;
        }
        stack = new ItemStack(item);
        items.add(stack);
        return items.size() - 1;
    }

    private ItemStack getItemStackBackwards(Item item) {
        int ID = item.getID();
        ItemStack stack;

        for (int i = items.size() - 1; i >= 0; i--) {
            stack = items.get(i);
            if (stack.getItemInStackID() == ID && stack.getAmount() <= stack.getMaxStackSize())
                return stack;
        }
        stack = new ItemStack(item);
        items.add(stack);
        return stack;
    }

    private int getItemStackIndexBackwards(Item item) {
        int ID = item.getID();
        ItemStack stack;

        for (int i = items.size() - 1; i >= 0; i--) {
            stack = items.get(i);
            if (stack.getItemInStackID() == ID && stack.getAmount() <= stack.getMaxStackSize())
                return i;
        }
        stack = new ItemStack(item);
        items.add(stack);
        return items.size() - 1;
    }

    private ItemStack getItemStack(int sel) {
        return items.get(sel);
    }

    private void removeItemStack(ItemStack stack) {
        items.remove(stack);
    }

    private void removeItemStack(int index) {
        items.remove(index);
    }

    public void addItem(Item item, int amount) {
        if (amount == 0 || item.getMaxStackSize() == 0) return;
        int amt = amount;

        ItemStack stack;
        while (amt > 0) {
            stack = getItemStack(item);
            int addAmt = (amt < stack.getMaxStackSize() - stack.getAmount() ? amt : stack.getMaxStackSize() - stack.getAmount());

            amt -= addAmt;

            stack.addItem(addAmt);
        }
    }

    public void addItem(int ID, int amount) {
        addItem(Item.getItemByID(ID), amount);
    }

    public void addItemStack(ItemStack stack) {
        addItem(Item.getItemByID(stack.getItemInStackID()), stack.getAmount());
    }

    public void removeItem(Item item, int amount) {
        if (amount <= 0 || item.getMaxStackSize() == 0) return;
        int totalAmount = getItemAmount(item);
        int amt = (amount <= totalAmount ? amount : totalAmount);

        int stackIndex;
        while (amt > 0) {
            stackIndex = getItemStackIndexBackwards(item);
            ItemStack stack = items.get(stackIndex);
            int removeAmt = amt;

            if (removeAmt > stack.getAmount()) {
                removeAmt = stack.getAmount();
            }
            amt -= removeAmt;

            stack.removeItem(removeAmt);
            if (stack.getAmount() <= 0) {
                removeItemStack(stackIndex);
                amt = 0;
            }
        }
    }

    public void removeItemByID(int id, int amount) {
        removeItem(Item.getItemByID(id), amount);
    }

    private void removeItemFromIndex(int sel, int amount) {
        if (sel >= items.size()) return;
        ItemStack stack = getItemStack(sel);
        int amt = (amount <= stack.getAmount() ? amount : stack.getAmount());
        stack.removeItem(amt);
        if (stack.getAmount() <= 0) removeItemStack(stack);
    }

    public void removeSelectedItems(int amount) {
        removeItemFromIndex(selected, amount);
    }

    public void removeSelectedItemStack() {
        removeItemStack(getItemStack(selected));
    }

    public int getItemAmount(Item item) {
        int amount = 0;

        for (ItemStack stack : items) {
            if (stack.getItemInStackID() == item.getID()) amount += stack.getAmount();
        }

        return amount;
    }

    public ItemStack getSelectedItemStack() {
        return getItemStack(selected);
    }

    protected int getSelected() {
        return selected;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
