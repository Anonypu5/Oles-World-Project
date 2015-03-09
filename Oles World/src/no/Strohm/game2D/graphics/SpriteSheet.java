package no.Strohm.game2D.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Ole on 14/12/13.
 */
public class SpriteSheet {

    public int w, h;
    public int[] pixels;
    public static SpriteSheet tiles = new SpriteSheet("/textures/TileSheet.png");
    public static SpriteSheet mobs = new SpriteSheet("/textures/MobSheet.png");
    public static SpriteSheet effects = new SpriteSheet("/textures/EffectSheet.png");
    public static SpriteSheet itemIcons = new SpriteSheet("/textures/IconSheet.png");

    public SpriteSheet(String path) {
        load(path);
    }

    private void load(String path) {
        try {
            BufferedImage img = ImageIO.read(SpriteSheet.class.getResource(path));
            w = img.getWidth();
            h = img.getHeight();
            pixels = img.getRGB(0, 0, w, h, null, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not load SpriteSheet, Path: " + path);
            System.exit(0);
        }
    }

}
