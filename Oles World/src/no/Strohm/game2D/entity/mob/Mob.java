package no.Strohm.game2D.entity.mob;

import no.Strohm.game2D.entity.Entity;
import no.Strohm.game2D.entity.particles.ParticleText;
import no.Strohm.game2D.graphics.SpriteSheet;
import no.Strohm.game2D.items.Item;
import no.Strohm.game2D.items.interfaces.Usable;
import no.Strohm.game2D.util.Vector2i;
import no.Strohm.game2D.world.World;

/**
 * Created by Ole on 15/12/13.
 */
public abstract class Mob extends Entity {

    public static final int dirUp = 0;
    public static final int dirRight = 1;
    public static final int dirDown = 2;
    public static final int dirLeft = 3;

    private int sizeX;
    private int sizeY;
    private int midX;
    private int midY;

    public int dir;
    protected Stats stats;
    protected int attackTimer;
    protected boolean moving = false;
    protected int anim = 0;
    protected int invincibleTimer = 5;
    private Entity lastHurtByEntity;

    private Item activeItem = Item.FIST;

    public Mob(int x, int y, World world, String tag, int attackSpeed, double speed, Vector2i size, Vector2i mid) {
        super(x, y, world, SpriteSheet.mobs, tag + ".mob");
        stats = new Stats(speed, attackSpeed);
        sizeX = size.getX();
        sizeY = size.getY();
        midX = mid.getX();
        midY = mid.getY();
    }

    private boolean slowed() {
        return (world.getTileAt(pos.toVector2i()).slows());
    }

    protected void move(double xm, double ym) {
        if (xm == 0 && ym == 0) {
            moving = false;
            return;
        }
        if (slowed()) {
            xm *= 0.5;
            ym *= 0.5;
        }
        moving = true;
        if (ym > 0) dir = dirDown;
        else if (ym < 0) dir = dirUp;
        else if (xm > 0) dir = dirRight;
        else if (xm < 0) dir = dirLeft;
        if (!collides(xm, 0)) {
            pos.add(xm, 0);
        }
        if (!collides(0, ym)) {
            pos.add(0, ym);
        }
    }

    private boolean collides(double xm, double ym) {
        for (int c = 0; c < 4; c++) {
            int xt = (int) (pos.getX() + xm) + c % 2 * sizeX - midX;
            int yt = (int) (pos.getY() + ym) + c / 2 * sizeY - midY;
            if (world.getTileAt(new Vector2i(xt, yt)).solid()) return true;
        }
        return false;
    }

    public boolean isInvincible() {
        return invincibleTimer > 0;
    }

    public void getHurt(Entity source, int dmg) {
        if (isInvincible()) return;
        System.out.println(pos.getX() + ", " + pos.getY());
        lastHurtByEntity = source;
        stats.hurt(dmg);
        world.addEntity(new ParticleText(pos.getX(), pos.getY(), world, "" + dmg, 0xff0000));
        if (stats.getHealth() <= 0) die();
    }

    public void die() {
        System.out.println(tag + " At " + pos.getX() + ", " + pos.getY() + " Killed by " + lastHurtByEntity.tag);
    }

    public void setActiveItem(Item item) {
        System.out.println("Set Item");
        if(item == null) {
            activeItem = Item.FIST;
        }
        if (!(item instanceof Usable)) return;
        activeItem = item;
    }

    public Item getActiveItem() {
        return activeItem;
    }

    public void use() {
        Item active = getActiveItem();
        if(!(active instanceof Usable)) return;
        ((Usable) active).use(this, world);
    }

    public Stats getStats() {
        return stats;
    }

}
