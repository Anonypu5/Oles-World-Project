package no.Strohm.game2D.containers;

import no.Strohm.game2D.entity.mob.Player;
import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.util.Vector2i;

/**
 * Created by Ole on 15/02/14.
 */
public abstract class Container {

    protected Player player;
    protected int moveTimer;
    protected boolean focused = false;
    protected String tag;
    protected Vector2i pos;
    protected Vector2i dimensions;

    public Container(String tag) {
        this.tag = tag + ".container";
        moveTimer = 10;
    }

    public abstract void tick();

    public abstract void renderContainer(Screen screen);


    public Container setPlayer(Player player) {
        this.player = player;
        return this;
    }

	public void use(Container other, Player user) {}

    public void setFocus(boolean focused) {
        this.focused = focused;
    }

    public void setMoveTimer(int moveTimer) {
        this.moveTimer = moveTimer;
    }

    public String getTag() {
        return tag;
    }

    public String getDisplayName() {
        return tag.split("\\.")[0];
    }
}
