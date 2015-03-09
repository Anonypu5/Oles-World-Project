package no.Strohm.game2D.world;

import no.Strohm.game2D.InputHandler;
import no.Strohm.game2D.entity.Entity;
import no.Strohm.game2D.entity.Spawner;
import no.Strohm.game2D.entity.blocks.Block;
import no.Strohm.game2D.entity.blocks.BlockChest;
import no.Strohm.game2D.entity.mob.Mob;
import no.Strohm.game2D.entity.mob.Player;
import no.Strohm.game2D.entity.particles.ParticleItemPickup;
import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.items.Item;
import no.Strohm.game2D.items.ItemStack;
import no.Strohm.game2D.util.Vector2i;
import no.Strohm.game2D.world.tiles.Tile;
import no.Strohm.game2D.world.tiles.TileVoid;
import no.Strohm.game2D.Multiplayer.OnlinePlayers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Ole on 15/12/13.
 */
public abstract class World {

	public static OnlinePlayers onlinePlayers[] = new OnlinePlayers[0];
	public int width, height;
	public static Tile[][] tiles;
	Random r;
	private List<Entity> entities = new ArrayList<Entity>();
	private Spawner spawner;

	public World(int width, int height, InputHandler input) {
		this.width = width;
		this.height = height;
		tiles = new Tile[height][width];
		spawner = new Spawner(this);
		addEntity(new Player(100, 100, this, input));
		r = new Random();
		generateWorld();
		setTile(Tile.grassId, 6, 6);
		setTile(Tile.grassId, 7, 6);
		setTile(Tile.grassId, 6, 7);
		setTile(Tile.grassId, 7, 7);
		addEntity(new ParticleItemPickup(100, 120, this, new ItemStack(Item.WOOD, Item.WOOD.getMaxStackSize())));
	}

	protected abstract void generateWorld();

	public synchronized List<Entity> getEntities() {
		return entities;
	}

	public List<Block> getBlocks() {
		List<Block> blocks = new ArrayList<Block>();
		for (Entity e : getEntities()) {
			if (e.tag.contains("block")) blocks.add((Block) e);
		}
		return blocks;
	}

	public List<Mob> getMobs() {
		List<Mob> mobs = new ArrayList<Mob>();
		for (Entity e : getEntities()) {
			if (e instanceof Mob) mobs.add((Mob) e);
		}
		return mobs;
	}

	public List<Player> getPlayers() {
		List<Player> players = new ArrayList<Player>();
		for (Mob m : getMobs()) {
			if (m instanceof Player) players.add((Player) m);
		}
		return players;
	}

	public synchronized List<Entity> getEntities(int radius, Vector2i pos) {
		List<Entity> entities1 = new ArrayList<Entity>();
		for (Entity e : getEntities()) {
			Vector2i tPos = e.getPos().toVector2i().sub(pos);
			int distance = (int) Math.sqrt(Math.abs(tPos.getX() * tPos.getX()) + Math.abs(tPos.getY() * tPos.getY()));
			if (distance <= radius) entities1.add(e);
		}
		return entities1;
	}

	public List<Mob> getMobs(int radius, Vector2i pos) {
		List<Mob> mobs = new ArrayList<Mob>();
		for (Entity e : getEntities(radius, pos)) {
			if (e instanceof Mob) mobs.add((Mob) e);
		}
		return mobs;
	}

	public List<Player> getPlayers(int radius, Vector2i pos) {
		List<Player> players = new ArrayList<Player>();
		for (Mob m : getMobs(radius, pos)) {
			if (m instanceof Player) players.add((Player) m);
		}
		return players;
	}

	public List<Mob> getMobsOnTile(Vector2i tilePos) {
		List<Mob> mobs = new ArrayList<Mob>();

		for (Mob m : getMobs()) {
			if (isOnTile(m, new Vector2i(tilePos.getX() << 4, tilePos.getY() << 4))) mobs.add(m);
		}

		return mobs;
	}

	public boolean isOnTile(Entity e, Vector2i pos) {
		return e.getPos().getX() == pos.getX() && e.getPos().getY() == pos.getY();
	}

	public Tile getTileAt(Vector2i pos) {
		int xx = pos.getX() >> 4;
		int yy = pos.getY() >> 4;

		return getTile(xx, yy);
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) return new TileVoid(x, y, this);
		return tiles[y][x];
	}

	public void addEntity(Entity e) {
		if (!entities.contains(e)) entities.add(e);
	}

	public void removeEntity(Entity e) {
		if (entities.contains(e)) entities.remove(e);
	}

	public void setTile(int id, int x, int y) {
		tiles[y][x] = Tile.createTile(id, x, y, this);
	}

	public void tick() {
		spawner.tick();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < height; x++) {
				getTile(x, y).tick();
			}
		}

		List<Player> players = new ArrayList<Player>();
		for (int i = 0; i < entities.size(); i++) {
			Entity e = getEntities().get(i);
			if (!(e.tag.contains("player"))) e.tick();
			else players.add((Player) e);
		}

		for (Player p : players) {
			p.tick();
		}

	}

	public void render(Screen screen) {
		Vector2i pos = getPlayers().get(0).getPos().toVector2i();
		int xOffset = pos.getX() - screen.w / 2;
		int yOffset = pos.getY() - screen.h / 2;
		screen.setOffset(xOffset, yOffset);

		int x0 = xOffset >> 4;
		int x1 = (xOffset + screen.w + 16) >> 4;
		int y0 = yOffset >> 4;
		int y1 = (yOffset + screen.h + 16) >> 4;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(screen);
			}
		}

		List<Entity> entities = getEntities();
		List<Player> players = new ArrayList<Player>();
		for (Entity e : entities) {
			if (!(e.tag.contains("player"))) e.render(screen);
			else players.add((Player) e);
		}

		for (OnlinePlayers op : onlinePlayers) {
			if (op != null) {
				op.render(screen);
			}
		}

		for (Player p : players) {
			p.render(screen);
		}
	}

}
