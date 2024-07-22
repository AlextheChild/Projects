package Game.GameObjects;

import Main.*;

import java.awt.*;

public class Bomb extends GameObject {
    public long milliseconds;
    public boolean exploding = false;

    public Bomb(double x, double y, long time) {
        this.x = (int) x - 2 + (int) (4 * Math.random());
        this.y = (int) y - 2 + (int) (4 * Math.random());
        this.milliseconds = time;
    }

    @Override
    public void move() {

    }

    @Override
    public void display(Graphics g) {
        if (!exploding) {
            g.setColor(Main.bombColor);
            g.fillOval((int) x - 6, (int) y - 6, 12, 12);
        } else {
            this.hitboxRadius = 50;
            g.setColor(new Color(255, 0, 0));
            g.fillOval((int) x - 50, (int) y - 50, 100, 100);
            g.setColor(new Color(255, 120, 80));
            g.fillOval((int) x - 30, (int) y - 30, 60, 60);
            g.setColor(new Color(255, 255, 255));
            g.fillOval((int) x - 10, (int) y - 10, 20, 20);
        }

    }
}