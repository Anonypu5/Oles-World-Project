package no.Strohm.game2D.containers;

import no.Strohm.game2D.Game;
import no.Strohm.game2D.util.Vector2i;

/**
 * Created by Ole on 26/01/14.
 */
public class ChestContainer extends ItemContainer {

    public ChestContainer() {
        super("Chest");
        dimensions = new Vector2i(9, 9);
        pos = new Vector2i(Game.WIDTH / 4 * 3, ((Game.HEIGHT - (19 + dimensions.getY() * 16)) / 2));
    }
}
