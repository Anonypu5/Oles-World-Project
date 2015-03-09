package no.Strohm.game2D.containers;

import no.Strohm.game2D.Game;
import no.Strohm.game2D.InputHandler;
import no.Strohm.game2D.entity.mob.Player;
import no.Strohm.game2D.items.Item;
import no.Strohm.game2D.items.ItemStack;
import no.Strohm.game2D.items.interfaces.Usable;
import no.Strohm.game2D.util.Vector2i;

/**
 * Created by Ole on 25/01/14.
 */
public class Inventory extends ItemContainer {

    public Inventory(Player player) {
        super("inventory");
        setPlayer(player);
        setFocus(true);
        dimensions = new Vector2i(9, 9);
        pos = new Vector2i(Game.WIDTH / 4, (Game.HEIGHT - (19 + dimensions.getY() * 16)) / 2);
    }

	@Override
	public void use(Container other, Player user) {
		InputHandler input = user.input;
		boolean inInv = user.isInInv();

		if (other != null && other instanceof ItemContainer) {
			if (user.input.right) {
				player.setInInv(false);
			}
			if (input.left) {
				player.setInInv(true);
			}
			ItemContainer container = (ItemContainer) other;
			if (this.isEmpty()) player.setInInv(false);
			else if (container.isEmpty()) player.setInInv(true);
			this.setFocus(inInv);
			container.setFocus(!inInv);

			if (inInv) {
				this.tick();

				if (input.enter && !this.isEmpty() && moveTimer <= 0) {
					ItemStack stack = this.getSelectedItemStack();
					this.removeSelectedItemStack();
					container.addItem(stack.getItemInStackID(), stack.getAmount());
					player.setMoveTimer(20);
				} else if (input.attack && moveTimer <= 0) {
					if (inInv && !this.isEmpty()) {
						Item active = player.getActiveItem();
						ItemStack stack = this.getSelectedItemStack();
						if (Item.getItemByID(stack.getItemInStackID()) instanceof Usable) {
							if (active != Item.FIST) {
								this.addItem(active, 1);
							}
							player.setActiveItem(Item.getItemByID(stack.getItemInStackID()));
							this.removeSelectedItems(1);
						}
					}
					player.setMoveTimer(20);
				}
			} else {
				container.tick();
				if (input.enter && !container.isEmpty() && moveTimer <= 0) {
					ItemStack stack = container.getSelectedItemStack();
					container.removeSelectedItemStack();
					this.addItem(stack.getItemInStackID(), stack.getAmount());
					player.setMoveTimer(20);
				} else if (input.attack && moveTimer <= 0) {
					if (!inInv && !container.isEmpty()) {
						Item active = player.getActiveItem();
						ItemStack stack = container.getSelectedItemStack();
						if (Item.getItemByID(stack.getItemInStackID()) instanceof Usable) {
							if (active != Item.FIST) {
								container.addItem(active, 1);
							}
							player.setActiveItem(Item.getItemByID(stack.getItemInStackID()));
							this.removeSelectedItems(1);
						}
					}
					player.setMoveTimer(20);
				}
			}
		} else {
			inInv = true;
			this.setFocus(true);
			this.tick();
			if (input.attack && moveTimer <= 0) {
				if (inInv && !this.isEmpty()) {
					Item active = player.getActiveItem();
					ItemStack stack = this.getSelectedItemStack();
					if (Item.getItemByID(stack.getItemInStackID()) instanceof Usable) {
						if (active != Item.FIST) {
							this.addItem(active, 1);
						}
						player.setActiveItem(Item.getItemByID(stack.getItemInStackID()));
						this.removeSelectedItems(1);
					}
				}
				player.setMoveTimer(20);
			}
		}

		if (input.use && moveTimer <= 0) {
			player.toggleOpen();
			player.setMoveTimer(30);
			this.setMoveTimer(moveTimer);
			if (other != null) {
				other.setMoveTimer(moveTimer);
				other.setFocus(false);
				other.setPlayer(null);
			}
			player.setInInv(true);
			this.setFocus(true);
			player.exitContainer();
		}
	}
}
