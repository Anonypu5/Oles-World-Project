package no.Strohm.game2D.entity.mob;

import no.Strohm.game2D.InputHandler;
import no.Strohm.game2D.Multiplayer.Client;
import no.Strohm.game2D.Multiplayer.OnlinePlayers;
import no.Strohm.game2D.containers.ChestContainer;
import no.Strohm.game2D.containers.Inventory;
import no.Strohm.game2D.containers.ItemContainer;
import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.graphics.SpriteSheet;
import no.Strohm.game2D.items.Item;
import no.Strohm.game2D.items.ItemStack;
import no.Strohm.game2D.items.interfaces.Usable;
import no.Strohm.game2D.state.State;
import no.Strohm.game2D.util.Vector2i;
import no.Strohm.game2D.world.World;
import no.Strohm.game2D.world.tiles.Tile;

import java.io.IOException;

/**
 * Created by Ole on 15/12/13.
 */
public class Player extends Mob {

	public InputHandler input;
	private boolean justAttacked = false;
	private Inventory inventory;
	private ItemContainer container;
	private boolean inInv = true;
	private int moveTimer = 10;
	private boolean containerOpen = false;

	public Player(int x, int y, World world, InputHandler input) {
		super(x + 8, y + 8, world, "player", 40, 1, new Vector2i(11, 8), new Vector2i(6, 2));
		this.input = input;
		this.inventory = new Inventory(this);
		this.container = new ChestContainer(); // TODO: temporary code, START
		container.setPlayer(this);
		inventory.addItem(Item.IRON_SWORD, 2);
		inventory.addItem(Item.WOOD, 120);
		container.addItem(Item.STONE, 53); // TODO: temporary code, END
	}

	public void tick() {
		if (OnlinePlayers.onlineOn) {
			try {
				String pos = "setPlayerStat;" + Client.gameTag + ";" + this.pos.getX() + ";" + this.pos.getY() + ";" + moving + ";" + anim + ";" + dir + ";";
				Client.dataOutputStream.writeUTF(pos);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (moveTimer > 0) moveTimer--;
		if (!containerOpen) {
			double xm = 0;
			double ym = 0;
			if (input.up) ym -= stats.getSpeed();
			if (input.down) ym += stats.getSpeed();
			if (input.right) xm += stats.getSpeed();
			if (input.left) xm -= stats.getSpeed();
			move(xm, ym);

			if (attackTimer > 0) attackTimer--;

			if (input.attack) {
				if (!justAttacked) {
					attack();
					attackTimer = stats.getAttackSpeed();
					justAttacked = true;
				}
			} else {
				justAttacked = false;
			}

			if (input.use) {
				openInv();
				moveTimer = 10;
			}

			if (anim < 7500 && moving) anim++;
			else if (anim >= 7500 && moving) anim = 0;

			if (input.escape && moveTimer <= 0) {
				State.setState(State.pauseId);
				moveTimer = 10;
			}
			return;
		}

		inventory.use(container, this);

//		if (container != null) {
//			if (input.right) {
//				inInv = false;
//			}
//			if (input.left) {
//				inInv = true;
//			}
//			if (inventory.isEmpty()) inInv = false;
//			else if (container.isEmpty()) inInv = true;
//			inventory.setFocus(inInv);
//			container.setFocus(!inInv);
//
//			if (inInv) {
//				inventory.tick();
//
//				if (input.enter && !inventory.isEmpty() && moveTimer <= 0) {
//					ItemStack stack = inventory.getSelectedItemStack();
//					inventory.removeSelectedItemStack();
//					container.addItem(stack.getItemInStackID(), stack.getAmount());
//					moveTimer = 20;
//				} else if (input.attack && moveTimer <= 0) {
//					if (inInv && !inventory.isEmpty()) {
//						Item active = getActiveItem();
//						ItemStack stack = inventory.getSelectedItemStack();
//						if (Item.getItemByID(stack.getItemInStackID()) instanceof Usable) {
//							if (active != Item.FIST) {
//								inventory.addItem(active, 1);
//							}
//							setActiveItem(Item.getItemByID(stack.getItemInStackID()));
//							inventory.removeSelectedItems(1);
//						}
//					}
//					moveTimer = 20;
//				}
//			} else {
//				container.tick();
//				if (input.enter && !container.isEmpty() && moveTimer <= 0) {
//					ItemStack stack = container.getSelectedItemStack();
//					container.removeSelectedItemStack();
//					inventory.addItem(stack.getItemInStackID(), stack.getAmount());
//					moveTimer = 20;
//				} else if (input.attack && moveTimer <= 0) {
//					if (!inInv && container != null && !container.isEmpty()) {
//						Item active = getActiveItem();
//						ItemStack stack = container.getSelectedItemStack();
//						if (Item.getItemByID(stack.getItemInStackID()) instanceof Usable) {
//							if (active != Item.FIST) {
//								container.addItem(active, 1);
//							}
//							setActiveItem(Item.getItemByID(stack.getItemInStackID()));
//							inventory.removeSelectedItems(1);
//						}
//					}
//					moveTimer = 20;
//				}
//			}
//		} else {
//			inInv = true;
//			inventory.setFocus(true);
//			inventory.tick();
//			if (input.attack && moveTimer <= 0) {
//				if (inInv && !inventory.isEmpty()) {
//					Item active = getActiveItem();
//					ItemStack stack = inventory.getSelectedItemStack();
//					if (Item.getItemByID(stack.getItemInStackID()) instanceof Usable) {
//						if (active != Item.FIST) {
//							inventory.addItem(active, 1);
//						}
//						setActiveItem(Item.getItemByID(stack.getItemInStackID()));
//						inventory.removeSelectedItems(1);
//					}
//				}
//				moveTimer = 20;
//			}
//		}

//		if (input.use && moveTimer <= 0) {
//			toggleOpen();
//			moveTimer = 30;
//			inventory.setMoveTimer(moveTimer);
//			if (container != null) {
//				container.setMoveTimer(moveTimer);
//				container.setFocus(false);
//				container.setPlayer(null);
//			}
//			inInv = true;
//			inventory.setFocus(true);
//			container = null;
//		}

	}

	public void render(Screen screen) {
		int[] pixelX = {0, 1, 0, 1};
		int effect = 0;
		int frame = 0;

		if (dir == 0) {
			pixelX = new int[]{2, 3, 2, 3};
		} else if (dir == 1) {
			pixelX = new int[]{4, 5, 4, 5};
		} else if (dir == 2) {
			pixelX = new int[]{0, 1, 0, 1};
		} else if (dir == 3) {
			pixelX = new int[]{5, 4, 5, 4};
			effect = 1;
		}
		if (moving) {
			if (dir == 0 || dir == 2) {
				if (anim % 20 < 10) frame = 1;
				else frame = 2;
			} else if (dir == 1 || dir == 3) {
				if (anim % 40 < 10) frame = 0;
				else if (anim % 40 < 20) frame = 1;
				else if (anim % 40 < 30) frame = 0;
				else frame = 2;
			}
			if ((dir == 0 || dir == 2) && frame == 2) {
				pixelX = new int[]{pixelX[1], pixelX[0], pixelX[3], pixelX[2]};
				effect = 1;
				frame = 1;
			}
		}

		int xx = (int) pos.getX() - 8;
		int yy = (int) pos.getY() - 8;
		screen.render(sheet, pixelX[0], frame * 2, 8, xx, yy, true, effect);
		screen.render(sheet, pixelX[1], frame * 2, 8, xx + 8, yy, true, effect);
		screen.render(sheet, pixelX[2], 1 + frame * 2, 8, xx, yy + 8, true, effect);
		screen.render(sheet, pixelX[3], 1 + frame * 2, 8, xx + 8, yy + 8, true, effect);

		renderInfo(screen);
	}

	private void renderInfo(Screen screen) {
		screen.renderArea(0x4F4F4F, 0, screen.w, screen.h - 18, screen.h, false);
		screen.renderText("Health:" + stats.getHealth() + "/" + stats.getMaxHealth(), 5, screen.h - 17, 0xC60000, false);
		screen.renderText("  Mana:" + stats.getMana() + "/" + stats.getMaxMana(), 5, screen.h - 8, 0x1616FF, false);
		Item activeItem = getActiveItem();
		String itemName = activeItem.getTag().split("\\.")[0];
		screen.renderText(itemName, screen.w - (10 + itemName.length() * 8), screen.h - 13, 0x1616FF, false);
		screen.render(SpriteSheet.itemIcons, activeItem.getIconX(), activeItem.getIconY(), 8, screen.w - (20 + itemName.length() * 8), screen.h - 13, false, 0);
		if (containerOpen) {
			inventory.renderContainer(screen);
			if (container != null) container.renderContainer(screen);
		}
	}

	private void attack() {
		Tile steppedOn = world.getTile((int) pos.getX() >> 4, (int) pos.getY() >> 4);
		if (steppedOn.solid()) {
			steppedOn.getHurt(this, stats.getDamage());
			return;
		}

		((Usable) getActiveItem()).use(this, world);
	}

	private void openInv() {
		toggleOpen();
	}

	public void toggleOpen() {
		if (moveTimer > 0) return;
		containerOpen = !containerOpen;
		if (containerOpen) {
			Item active = getActiveItem();
			if (active != Item.FIST && active != null) inventory.addItem(active, 1);
			setActiveItem(Item.FIST);
		}
	}

	public Inventory getInventory() {
		return inventory;
	}

	public boolean isInInv() {
		return inInv;
	}

	public void setInInv(boolean inInv) {
		this.inInv = inInv;
	}

	public void exitContainer() {
		container = null;
	}

	public void setMoveTimer(int moveTimer) {
		this.moveTimer = moveTimer;
	}
}
