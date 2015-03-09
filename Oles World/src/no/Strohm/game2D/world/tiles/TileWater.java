package no.Strohm.game2D.world.tiles;

import no.Strohm.game2D.entity.Entity;
import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.world.World;

/**
 * Created by Ole on 17/12/13.
 */
public class TileWater extends Tile {

	int anim = 0;

	public TileWater(int x, int y, World world) {
		super(waterId, 1, x, y, world, "water");
	}

	public void tick() {
		if (anim < 6000) anim++;
		else anim = 0;

		if (anim % 30 == 0) {
			boolean u = world.getTile(pos.getX(), pos.getY() - 1).id == holeId;
			boolean d = world.getTile(pos.getX(), pos.getY() + 1).id == holeId;
			boolean l = world.getTile(pos.getX() - 1, pos.getY()).id == holeId;
			boolean r = world.getTile(pos.getX() + 1, pos.getY()).id == holeId;

			if (u && (random.nextInt(5)) == 0) {
				world.setTile(waterId, pos.getX(), pos.getY() - 1);
			}
			if (d && (random.nextInt(5)) == 0) {
				world.setTile(waterId, pos.getX(), pos.getY() + 1);
			}
			if (l && (random.nextInt(5)) == 0) {
				world.setTile(waterId, pos.getX() - 1, pos.getY());
			}
			if (r && (random.nextInt(5)) == 0) {
				world.setTile(waterId, pos.getX() + 1, pos.getY());
			}
		}
	}

	// TODO: remake shore-rendering to be set in each tile instead of in the water tile.
	public void render(Screen screen) {
		boolean u = world.getTile(pos.getX(), pos.getY() - 1).id != waterId;
		boolean d = world.getTile(pos.getX(), pos.getY() + 1).id != waterId;
		boolean l = world.getTile(pos.getX() - 1, pos.getY()).id != waterId;
		boolean r = world.getTile(pos.getX() + 1, pos.getY()).id != waterId;
		boolean ul = world.getTile(pos.getX() - 1, pos.getY() - 1).id != waterId;
		boolean ur = world.getTile(pos.getX() + 1, pos.getY() - 1).id != waterId;
		boolean dl = world.getTile(pos.getX() - 1, pos.getY() + 1).id != waterId;
		boolean dr = world.getTile(pos.getX() + 1, pos.getY() + 1).id != waterId;

		boolean reur, redr, reul, redl;
		reur = redl = redr = reul = false;

		int xx = pos.getX() << 4;
		int yy = pos.getY() << 4;

		int aOffs = 0;
		if (anim % 20 < 10) aOffs = 0;
		else aOffs = 2;

		if (!u && !d && !l && !r) {
			screen.render(sheet, 6, 0, 8, xx, yy, true, 0);
			screen.render(sheet, 6, 0, 8, xx + 8, yy, true, 0);
			screen.render(sheet, 6, 0, 8, xx, yy + 8, true, 0);
			screen.render(sheet, 6, 0, 8, xx + 8, yy + 8, true, 0);
		} else {
			screen.render(sheet, 4, 0, 16, xx, yy, true, 0);
			if (u && d && l && r) {
				reur = redl = redr = reul = true;
				screen.render(sheet, 6, 2, 8, xx, yy, true, 0);
				screen.render(sheet, 6, 2, 8, xx + 8, yy, true, 4);
				screen.render(sheet, 6, 2, 8, xx, yy + 8, true, 6);
				screen.render(sheet, 6, 2, 8, xx + 8, yy + 8, true, 5);
			} else if (u && !l && !r && !d) {
				reur = reul = true;
				screen.render(sheet, 6, 1, 8, xx, yy, true, 0);
				screen.render(sheet, 6, 1, 8, xx + 8, yy, true, 0);

				screen.render(sheet, 6, 0, 8, xx, yy + 8, true, 0);
				screen.render(sheet, 6, 0, 8, xx + 8, yy + 8, true, 0);
			} else if (d && !l && !r && !u) {
				redr = redl = true;
				screen.render(sheet, 6, 1, 8, xx, yy + 8, true, 5);
				screen.render(sheet, 6, 1, 8, xx + 8, yy + 8, true, 5);

				screen.render(sheet, 6, 0, 8, xx, yy, true, 0);
				screen.render(sheet, 6, 0, 8, xx + 8, yy, true, 0);
			} else if (l && !u && !d && !r) {
				redl = reul = true;
				screen.render(sheet, 6, 1, 8, xx, yy, true, 6);
				screen.render(sheet, 6, 1, 8, xx, yy + 8, true, 6);

				screen.render(sheet, 6, 0, 8, xx + 8, yy, true, 0);
				screen.render(sheet, 6, 0, 8, xx + 8, yy + 8, true, 0);
			} else if (r && !u && !d && !l) {
				reur = redr = true;
				screen.render(sheet, 6, 1, 8, xx + 8, yy, true, 4);
				screen.render(sheet, 6, 1, 8, xx + 8, yy + 8, true, 4);

				screen.render(sheet, 6, 0, 8, xx, yy, true, 0);
				screen.render(sheet, 6, 0, 8, xx, yy + 8, true, 0);
			} else if (r && l && !u && !d) {
				reur = redl = redr = reul = true;
				screen.render(sheet, 6, 1, 8, xx, yy, true, 6);
				screen.render(sheet, 6, 1, 8, xx + 8, yy, true, 4);
				screen.render(sheet, 6, 1, 8, xx, yy + 8, true, 6);
				screen.render(sheet, 6, 1, 8, xx + 8, yy + 8, true, 4);
			} else if (u && d && !r && !l) {
				reur = redl = redr = reul = true;
				screen.render(sheet, 6, 1, 8, xx, yy, true, 0);
				screen.render(sheet, 6, 1, 8, xx + 8, yy, true, 0);
				screen.render(sheet, 6, 1, 8, xx, yy + 8, true, 5);
				screen.render(sheet, 6, 1, 8, xx + 8, yy + 8, true, 5);
			} else if (u && l && !r && !d) {
				reur = redl = reul = true;
				screen.render(sheet, 6, 2, 8, xx, yy, true, 0);
				screen.render(sheet, 6, 1, 8, xx + 8, yy, true, 0);
				screen.render(sheet, 6, 1, 8, xx, yy + 8, true, 6);

				screen.render(sheet, 6, 0, 8, xx + 8, yy + 8, true, 0);
			} else if (u && r && !l && !d) {
				reur = redr = reul = true;
				screen.render(sheet, 6, 1, 8, xx, yy, true, 0);
				screen.render(sheet, 6, 2, 8, xx + 8, yy, true, 4);
				screen.render(sheet, 6, 1, 8, xx + 8, yy + 8, true, 4);

				screen.render(sheet, 6, 0, 8, xx, yy + 8, true, 0);
			} else if (d && l && !r && !u) {
				redl = redr = reul = true;
				screen.render(sheet, 6, 1, 8, xx, yy, true, 6);
				screen.render(sheet, 6, 2, 8, xx, yy + 8, true, 6);
				screen.render(sheet, 6, 1, 8, xx + 8, yy + 8, true, 5);

				screen.render(sheet, 6, 0, 8, xx + 8, yy, true, 0);
			} else if (d && r && !l && !u) {
				reur = redl = redr = true;
				screen.render(sheet, 6, 1, 8, xx, yy + 8, true, 5);
				screen.render(sheet, 6, 1, 8, xx + 8, yy, true, 4);
				screen.render(sheet, 6, 2, 8, xx + 8, yy + 8, true, 5);

				screen.render(sheet, 6, 0, 8, xx, yy, true, 0);
			} else if (d && u && l && !r) {
				reur = redl = redr = reul = true;
				screen.render(sheet, 6, 2, 8, xx, yy, true, 0);
				screen.render(sheet, 6, 2, 8, xx, yy + 8, true, 6);
				screen.render(sheet, 6, 1, 8, xx + 8, yy, true, 0);
				screen.render(sheet, 6, 1, 8, xx + 8, yy + 8, true, 5);
			} else if (d && u && r && !l) {
				reur = redl = redr = reul = true;
				screen.render(sheet, 6, 1, 8, xx, yy, true, 0);
				screen.render(sheet, 6, 1, 8, xx, yy + 8, true, 5);
				screen.render(sheet, 6, 2, 8, xx + 8, yy, true, 4);
				screen.render(sheet, 6, 2, 8, xx + 8, yy + 8, true, 5);
			} else if (u && l && r && !d) {
				reur = redl = redr = reul = true;
				screen.render(sheet, 6, 2, 8, xx, yy, true, 0);
				screen.render(sheet, 6, 1, 8, xx, yy + 8, true, 6);
				screen.render(sheet, 6, 2, 8, xx + 8, yy, true, 4);
				screen.render(sheet, 6, 1, 8, xx + 8, yy + 8, true, 4);
			} else if (d && l && r && !u) {
				reur = redl = redr = reul = true;
				screen.render(sheet, 6, 1, 8, xx, yy, true, 6);
				screen.render(sheet, 6, 2, 8, xx, yy + 8, true, 6);
				screen.render(sheet, 6, 1, 8, xx + 8, yy, true, 4);
				screen.render(sheet, 6, 2, 8, xx + 8, yy + 8, true, 5);
			}
		}
		int xp = 6;
		int yp = 3;
		if (!reul && ul) {
			screen.render(sheet, 8, 0, 8, xx, yy, true, 0);
			screen.render(sheet, xp, yp, 8, xx, yy, true, 0);
		}
		if (!reur && ur) {
			screen.render(sheet, 8, 0, 8, xx + 8, yy, true, 0);
			screen.render(sheet, xp, yp, 8, xx + 8, yy, true, 4);
		}
		if (!redr && dr) {
			screen.render(sheet, 8, 0, 8, xx + 8, yy + 8, true, 0);
			screen.render(sheet, xp, yp, 8, xx + 8, yy + 8, true, 5);
		}
		if (!redl && dl) {
			screen.render(sheet, 8, 0, 8, xx, yy + 8, true, 0);
			screen.render(sheet, xp, yp, 8, xx, yy + 8, true, 6);
		}
	}

	public void getHurt(Entity source, int dmg) {

	}

	public void die() {

	}

	public boolean slows() {
		return true;
	}
}
