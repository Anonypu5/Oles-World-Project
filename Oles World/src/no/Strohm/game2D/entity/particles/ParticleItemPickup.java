package no.Strohm.game2D.entity.particles;

import no.Strohm.game2D.containers.ItemContainer;
import no.Strohm.game2D.entity.mob.Player;
import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.graphics.SpriteSheet;
import no.Strohm.game2D.items.ItemStack;
import no.Strohm.game2D.util.Vector2d;
import no.Strohm.game2D.world.World;

import java.util.List;

/**
 * Created by Ole on 13/04/14.
 */
public class ParticleItemPickup extends Particle {

    private ItemStack stack;
    private Vector2d velocity;
    private double friction = 0.6;
    private int timer;

    public ParticleItemPickup(double x, double y, World world, ItemStack stack) {
        super(x, y, world, SpriteSheet.itemIcons, stack.getItemName() + ".itempickup");
        this.stack = stack;
        velocity = new Vector2d((random.nextInt(3) - 1), (random.nextInt(3) - 1));
        timer = 0;
    }

    public void tick() {
        timer++;
        pos.add(velocity);
        velocity.mul(friction, friction);
        if (timer >= 10) {
            List<Player> players = world.getPlayers(12, pos.toVector2i());
            if (players.size() > 0) {
                Player player = players.get(random.nextInt(players.size()));
                addToContainer(player.getInventory(), (int) player.getPos().getX(), (int) player.getPos().getY());
            }
            timer = 0;
        }
        else if(timer >= 60 * 3) {
            die();
        }
    }

    public void render(Screen screen) {
        screen.render(sheet, stack.getIconX(), stack.getIconY(), 8, (int) pos.getX(), (int) pos.getY(), true, 0);
    }

    public void addToContainer(ItemContainer container, int x, int y) {
        container.addItemStack(stack);
        world.addEntity(new ParticleText(x >> 4, y >> 4, world, "+" + stack.getAmount() + " " + stack.getItemName(), 0xE60000, 60, 250 + random.nextInt(5) * 10));
        die();
    }

    public int getAmount() {
        return stack.getAmount();
    }

    public int getItemInStackID() {
        return stack.getItemInStackID();
    }

    public String getName() {
        return stack.getItemName();
    }

}
