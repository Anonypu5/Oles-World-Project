package no.Strohm.game2D.util;

/**
 * Created by Ole on 15/12/13.
 */
public class Vector2i {

    private int x;
    private int y;

    public Vector2i() {
        set(0,0);
    }

    public Vector2i(int x, int y) {
        set(x, y);
    }

    public Vector2i(Vector2i v) {
        set(v.getX(), v.getY());
    }

    public Vector2d toVector2d() {
        return new Vector2d((double)this.getX(), (double)this.getY());
    }

    public static Vector2d toVector2d(Vector2i v) {
        return new Vector2d((double)v.getX(), (double)v.getY());
    }

    public Vector2i set(int xp, int yp) {
        x = xp;
        y = yp;
        return this;
    }

    public Vector2i add(int xa, int ya) {
        x += xa;
        y += ya;
        return this;
    }

    public Vector2i sub(int xs, int ys) {
        x -= xs;
        y -= ys;
        return this;
    }

    public Vector2i mul(double xs, double ys) {
        x *= xs;
        y *= ys;
        return this;
    }

    public Vector2i div(double xs, double ys) {
        x /= xs;
        y /= ys;
        return this;
    }

    public Vector2i add(Vector2i v) {
        x += v.getX();
        y += v.getY();
        return this;
    }

    public Vector2i sub(Vector2i v) {
        x -= v.getX();
        y -= v.getY();
        return this;
    }

    public Vector2i mul(Vector2i v) {
        x *= v.getX();
        y *= v.getY();
        return this;
    }

    public Vector2i div(Vector2i v) {
        x /= v.getX();
        y /= v.getY();
        return this;
    }

    public Vector2i setX(int x) {
        this.x = x;
        return this;
    }

    public Vector2i setY(int y) {
        this.y = y;
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
