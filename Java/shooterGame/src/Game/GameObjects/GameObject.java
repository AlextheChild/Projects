package Game.GameObjects;

import java.awt.*;

public abstract class GameObject {
    public double x, y;
    public double mass;
    public int hitboxRadius;

    public abstract void move();

    public abstract void display(Graphics g);

    public double cos(double angle) {
        return Math.cos(Math.toRadians(angle));
    }

    public double sin(double angle) {
        return Math.sin(Math.toRadians(angle));
    }

    public boolean isTouching(GameObject gO) {
        if (Math.pow(x - gO.x, 2) + Math.pow(y - gO.y, 2) <= Math.pow(hitboxRadius + gO.hitboxRadius, 2)) {
            return true;
        }
        return false;
    }
}