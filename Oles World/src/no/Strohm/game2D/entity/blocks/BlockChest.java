package no.Strohm.game2D.entity.blocks;

import no.Strohm.game2D.containers.ChestContainer;
import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.graphics.SpriteSheet;
import no.Strohm.game2D.world.World;

/**
 * Created by Ole on 26/01/14.
 */
public class BlockChest extends ContainerBlock {

    public BlockChest(int x, int y, World world) {
        super(x, y, world, "chest", new ChestContainer());
    }

    public void tick() {

    }

    public void render(Screen screen) {
        screen.render(SpriteSheet.tiles, 0, 10, 16, (int) getPos().getX(), (int) getPos().getY(), true, 0);
    }
}
