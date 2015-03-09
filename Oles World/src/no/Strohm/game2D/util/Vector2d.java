package no.Strohm.game2D.util;

/**
 * Created by Ole on 20/12/13.
 */
public class Vector2d {

    private double x;
    private double y;

    public Vector2d() {
        set(0,0);
    }

    public Vector2d(double x, double y) {
        set(x, y);
    }

    public Vector2d(Vector2d v) {
        set(v.getX(), v.getY());
    }

    public Vector2i toVector2i() {
        return new Vector2i((int)this.getX(), (int)this.getY());
    }

    public static Vector2i toVector2i(Vector2d v) {
        return new Vector2i((int)v.getX(), (int)v.getY());
    }

    public Vector2d set(double xp, double yp) {
        x = xp;
        y = yp;
        return this;
    }

    public Vector2d add(double xa, double ya) {
        x += xa;
        y += ya;
        return this;
    }

    public Vector2d sub(double xs, double ys) {
        x -= xs;
        y -= ys;
        return this;
    }

    public Vector2d mul(double xs, double ys) {
        x *= xs;
        y *= ys;
        return this;
    }

    public Vector2d div(double xs, double ys) {
        x /= xs;
        y /= ys;
        return this;
    }

    public Vector2d add(Vector2d v) {
        x += v.getX();
        y += v.getY();
        return this;
    }

    public Vector2d sub(Vector2d v) {
        x -= v.getX();
        y -= v.getY();
        return this;
    }

    public Vector2d mul(Vector2d v) {
        x *= v.getX();
        y *= v.getY();
        return this;
    }

    public Vector2d div(Vector2d v) {
        x /= v.getX();
        y /= v.getY();
        return this;
    }

    public Vector2d setX(double x) {
        this.x = x;
        return this;
    }

    public Vector2d setY(double y) {
        this.y = y;
        return this;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


}
