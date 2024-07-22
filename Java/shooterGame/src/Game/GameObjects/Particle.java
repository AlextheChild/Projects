package Game.GameObjects;

import java.awt.*;

public class Particle extends GameObject{
    double angle;

    double speed = 0.05;

    int shade = (int) (100 * Math.random());
    int size = (int) (4 * Math.random());

    public Particle(double x, double y, double angle) {
        this.x = x - 2 + 4 * Math.random();
        this.y = y - 2 + 4 * Math.random();
        this.angle = angle - 20 + 40 * Math.random() + 180;
    }

    @Override
    public void move() {
        x += speed * Math.cos(Math.toRadians(angle));
        y += speed * Math.sin(Math.toRadians(angle));
    }

    @Override
    public void display(Graphics g) {
        g.setColor(new Color(155 + shade, shade, 0));
        g.drawRect((int) (x - size / 2), (int) (y - size / 2), size, size);
    }
}