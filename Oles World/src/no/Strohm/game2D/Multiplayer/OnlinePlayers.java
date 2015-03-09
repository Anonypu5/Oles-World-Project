package no.Strohm.game2D.Multiplayer;

import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.graphics.SpriteSheet;

public class OnlinePlayers {

	public static boolean onlineOn = false;
	public int xPos = 20, yPos = 20;
    public String gameTag = "";
    public boolean moving = false;
    public int dir = 0;
    public int anim = 0;

	public OnlinePlayers(String gameTag) {
        this.gameTag = gameTag;
	}

	public void render(Screen screen) { // WIP
        int[] pixelX = {0, 1, 0, 1};
        int effect = 0;
        int frame = 0;

        if (dir == 0) {
            pixelX = new int[]{2, 3, 2, 3};
        } else if (dir == 1) {
            pixelX = new int[]{4, 5, 4, 5};
        } else if (dir == 2) {
            pixelX = new int[]{0, 1, 0, 1};
        } else if (dir == 3) {
            pixelX = new int[]{5, 4, 5, 4};
            effect = 1;
        }
        if (moving) {
            if (dir == 0 || dir == 2) {
                if (anim % 20 < 10) frame = 1;
                else frame = 2;
            } else if (dir == 1 || dir == 3) {
                if (anim % 40 < 10) frame = 0;
                else if (anim % 40 < 20) frame = 1;
                else if (anim % 40 < 30) frame = 0;
                else frame = 2;
            }
            if ((dir == 0 || dir == 2) && frame == 2) {
                pixelX = new int[]{pixelX[1], pixelX[0], pixelX[3], pixelX[2]};
                effect = 1;
                frame = 1;
            }
        }

        int xx = xPos - 8;
        int yy = yPos - 8;
        screen.render(SpriteSheet.mobs, pixelX[0], frame * 2, 8, xx, yy, true, effect);
        screen.render(SpriteSheet.mobs, pixelX[1], frame * 2, 8, xx + 8, yy, true, effect);
        screen.render(SpriteSheet.mobs, pixelX[2], 1 + frame * 2, 8, xx, yy + 8, true, effect);
        screen.render(SpriteSheet.mobs, pixelX[3], 1 + frame * 2, 8, xx + 8, yy + 8, true, effect);
	}

}
