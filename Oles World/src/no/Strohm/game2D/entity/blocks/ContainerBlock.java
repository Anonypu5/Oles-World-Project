package no.Strohm.game2D.entity.blocks;

import no.Strohm.game2D.containers.ItemContainer;
import no.Strohm.game2D.world.World;

/**
 * Created by Ole on 14/02/14.
 */
public abstract class ContainerBlock extends Block {

    ItemContainer container;

    public ContainerBlock(int x, int y, World world, String tag, ItemContainer container) {
        super(x, y, world, tag + ".container");
        this.container = container;
    }

    public ItemContainer getContainer() {
        return container;
    }
}
