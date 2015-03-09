/**
 * Created by Ole on 14/12/13.
 */
public class Screen {
	public final int w;
	public final int h;
	private int xOffset, yOffset;
	private int[] pixels;

	public Screen(int w, int h) {
		this.w = w;
		this.h = h;
		pixels = new int[w * h];
	}

	public void copy(int[] pixels) {
		if (this.pixels.length == pixels.length) {
			System.arraycopy(this.pixels, 0, pixels, 0, pixels.length);
		} else {
			System.out.println("Can not copy array!");
		}
	}

	public void render(SpriteSheet sheet, int xPix, int yPix, int size, int xPos, int yPos, boolean offset, int effect) {
		if (offset) {
			xPos -= xOffset;
			yPos -= yOffset;
		}
		int xPix1 = xPix * size;
		int yPix1 = yPix * size;
		for (int y = 0; y < size; y++) {
			int yy = yPos + y;
			int yp = yPix1 + y;
			for (int x = 0; x < size; x++) {
				int xx = xPos + x;
				int xp = xPix1 + x;
				if (xx < -15 || xx >= w || yy < 0 || yy >= h) break;
				if (xx < 0) xx = 0;
				if (effect == 1 || effect == 3) {
					xp = xPix1 + (size - 1) - x;
				}
				if (effect == 2 || effect == 3) {
					yp = yPix1 + (size - 1) - y;
				} else {
					if (effect == 4) {
						xp = xPix1 + y;
						yp = yPix1 + (size - 1) - x;
					} else if (effect == 5) {
						xp = xPix1 + (size - 1) - x;
						yp = yPix1 + (size - 1) - y;
					} else if (effect == 6) {
						xp = xPix1 + (size - 1) - y;
						yp = yPix1 + x;
					}
				}
				int col = sheet.pixels[xp + yp * sheet.w];
				if (col != 0xFFFF00FF) pixels[xx + yy * w] = col;
			}
		}
	}

	public void renderArea(int col, int xMin, int xMax, int yMin, int yMax, boolean offset) {
		if (offset) {
			xMin -= xOffset;
			xMax -= xOffset;
			yMin -= yOffset;
			yMax -= yOffset;
		}
		for (int y = yMin; y < yMax; y++) {
			for (int x = xMin; x < xMax; x++) {
				if (x < 0 || x >= w || y < 0 || y >= h) break;
				pixels[x + y * w] = col;
			}
		}
	}

	public void renderText(String msg, int xPos, int yPos, int textCol, boolean offset) {
		if (offset) {
			xPos -= xOffset;
			yPos -= yOffset;
		}
		SpriteSheet sheet = SpriteSheet.effects;
		msg = msg.toUpperCase();
		for (int s = 0; s < msg.length(); s++) {
			int xPix = Text.text.indexOf(msg.charAt(s));
			int yPix = 0;
			if (xPix > 31) {
				xPix -= 32;
				yPix += 1;
			}
			for (int y = 0; y < 8; y++) {
				int yy = yPos + y;
				int yp = ((30 + yPix) * 8) + y;
				for (int x = 0; x < 8; x++) {
					int xx = xPos + x + (s * 8);
					int xp = (xPix * 8) + x;
					if (xx < 0 || xx >= w || yy < 0 || yy >= h) return;
					int col = sheet.pixels[xp + yp * sheet.w];
					if (col != 0xFFFF00FF) pixels[xx + yy * w] = textCol;
				}
			}
		}
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}
