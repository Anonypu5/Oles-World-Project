package no.Strohm.game2D.entity;

import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.graphics.SpriteSheet;
import no.Strohm.game2D.util.Vector2d;
import no.Strohm.game2D.world.World;

/**
 * Created by Ole on 15/12/13.
 */
public class Entity {

    public final String tag;
    protected Vector2d pos;
    protected World world;
    protected SpriteSheet sheet;

    public Entity(double x, double y, World world, SpriteSheet sheet, String tag) {
        pos = new Vector2d(x, y);
        this.world = world;
        this.sheet = sheet;
        this.tag = tag + ".entity";
    }

    public void tick() {
    }

    public void render(Screen screen) {
    }

    public Vector2d getPos() {
        return pos;
    }
}
