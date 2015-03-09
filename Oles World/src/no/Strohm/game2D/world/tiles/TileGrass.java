package no.Strohm.game2D.world.tiles;

import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.world.World;

/**
 * Created by Ole on 15/12/13.
 */
public class TileGrass extends Tile {

	int tick = 0;

	public TileGrass(int x, int y, World world) {
		super(grassId, 10, x, y, world, "grass");
	}

	public void tick() {
		tick++;
		if (tick >= 6000) tick = 0;

		if (tick % 600 == 0) {
			boolean u = world.getTile(pos.getX(), pos.getY() - 1).id == dirtId;
			boolean d = world.getTile(pos.getX(), pos.getY() + 1).id == dirtId;
			boolean l = world.getTile(pos.getX() - 1, pos.getY()).id == dirtId;
			boolean r = world.getTile(pos.getX() + 1, pos.getY()).id == dirtId;

			if (u && (random.nextInt(10)) == 0) {
				world.setTile(grassId, pos.getX(), pos.getY() - 1);
			}
			if (d && (random.nextInt(10)) == 0) {
				world.setTile(grassId, pos.getX(), pos.getY() + 1);
			}
			if (l && (random.nextInt(10)) == 0) {
				world.setTile(grassId, pos.getX() - 1, pos.getY());
			}
			if (r && (random.nextInt(10)) == 0) {
				world.setTile(grassId, pos.getX() + 1, pos.getY());
			}
		}
	}

	public void render(Screen screen) {
		int xx = pos.getX() << 4;
		int yy = pos.getY() << 4;
		screen.render(sheet, 0, 0, 16, xx, yy, true, 0);
	}

	public void die() {
		super.die();
		world.setTile(dirtId, pos.getX(), pos.getY());
	}
}
