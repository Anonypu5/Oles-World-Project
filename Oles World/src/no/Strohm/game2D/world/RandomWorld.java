package no.Strohm.game2D.world;

import no.Strohm.game2D.Game;
import no.Strohm.game2D.InputHandler;
import no.Strohm.game2D.state.StateGame;
import no.Strohm.game2D.world.tiles.Tile;

public class RandomWorld extends World {

    public RandomWorld(int width, int height, InputHandler input) {
        super(width, height, input);
    }

    protected void generateWorld() {
        if(!Game.Online) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int tile = r.nextInt(Tile.dirtId);
                    tiles[y][x] = Tile.createTile(tile, x, y, this);
                }
            }
        }
    }
}
