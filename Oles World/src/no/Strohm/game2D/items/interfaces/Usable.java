package no.Strohm.game2D.items.interfaces;

import no.Strohm.game2D.entity.mob.Mob;
import no.Strohm.game2D.world.World;

/**
 * Created by Ole on 29/04/2014.
 */
public interface Usable {

    public abstract void use(Mob source, World world);

}
