package Game.GameObjects.Enemies;

import Main.*;

import java.awt.*;

public class SquareEnemy extends Enemy {
    public SquareEnemy() {
        speed = 0.01 + 0.01 * Math.random();

        rotationSpeed = 0.2 * Math.random();

        health = 4;
    }

    @Override
    public void display(Graphics g) {
        g.setColor(new Color(shade, 0, 0));
        int[] xPoints = new int[] { (int) (x + 20 * cos(angle)), (int) (x + 20 * cos(angle + 90)),
                (int) (x + 20 * cos(angle + 180)), (int) (x + 20 * cos(angle + 270)) };
        int[] yPoints = new int[] { (int) (y + 20 * sin(angle)), (int) (y + 20 * sin(angle + 90)),
                (int) (y + 20 * sin(angle + 180)), (int) (y + 20 * sin(angle + 270)) };
        g.fillPolygon(xPoints, yPoints, 4);

        if (Main.gameFrame.gamePanel.devTools) {
            g.setColor(new Color(0, 0, 0));
            g.fillOval((int) x - 1, (int) y - 1, 2, 2);
            g.drawOval((int) x - 17, (int) y - 17, 34, 34);
        }
    }
}