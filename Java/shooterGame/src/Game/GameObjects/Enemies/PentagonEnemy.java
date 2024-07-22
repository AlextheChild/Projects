package Game.GameObjects.Enemies;

import Main.*;

import java.awt.*;

public class PentagonEnemy extends Enemy {
    public PentagonEnemy() {
        speed = 0.03 + 0.03 * Math.random();

        rotationSpeed = 0.25 * Math.random();

        health = 7;
    }

    @Override
    public void display(Graphics g) {
        g.setColor(new Color(0, 0, shade));
        int[] xPoints = new int[] { (int) (x + 20 * cos(angle)), (int) (x + 20 * cos(angle + 72)),
                (int) (x + 20 * cos(angle + 144)), (int) (x + 20 * cos(angle + 216)),
                (int) (x + 20 * cos(angle + 288)) };
        int[] yPoints = new int[] { (int) (y + 20 * sin(angle)), (int) (y + 20 * sin(angle + 72)),
                (int) (y + 20 * sin(angle + 144)), (int) (y + 20 * sin(angle + 216)),
                (int) (y + 20 * sin(angle + 288)) };
        g.fillPolygon(xPoints, yPoints, 5);

        if (Main.gameFrame.gamePanel.devTools) {
            g.setColor(new Color(0, 0, 0));
            g.fillOval((int) x - 1, (int) y - 1, 2, 2);
            g.drawOval((int) x - 17, (int) y - 17, 34, 34);
        }
    }
}