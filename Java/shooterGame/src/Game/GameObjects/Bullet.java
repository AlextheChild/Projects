package Game.GameObjects;

import Main.*;

import java.awt.*;

public class Bullet extends GameObject {
    public double angle;
    double speed;

    public int bounces = 5;

    public Bullet(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.speed = 0.8;

        this.hitboxRadius = 1;
    }

    public void move() {
        x += speed * cos(angle);
        y += speed * sin(angle);
    }

    public void display(Graphics g) {
        g.setColor(Main.bulletColor);
        g.fillOval((int) x - 3, (int) y - 3, 6, 6);
    }
}