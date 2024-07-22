package Game.GameObjects;

import Main.*;

import java.awt.*;

public class BlackHole extends GameObject {
    double tick = 0;

    public BlackHole() {
        x = 450;
        y = 300;
        mass = 40;
        hitboxRadius = 40;
    }

    @Override
    public void move() {
        tick += 0.1;
        x = 450 + 7 * Math.cos(Math.toRadians(tick));
        y = 300 + 7 * Math.sin(Math.toRadians(tick));
    }

    @Override
    public void display(Graphics g) {
        g.setColor(Main.holeColor);
        g.fillOval((int) (x - mass), (int) (y - mass), (int) (2 * mass), (int) (2 * mass));
    }
}