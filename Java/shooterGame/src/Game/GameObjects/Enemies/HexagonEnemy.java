package Game.GameObjects.Enemies;

import Main.*;

import java.awt.*;

public class HexagonEnemy extends Enemy {
    public HexagonEnemy() {
        speed = 0.06 + 0.06 * Math.random();

        rotationSpeed = 0.5 * Math.random();

        health = 12;
    }

    @Override
    public void display(Graphics g) {
        g.setColor(new Color(0, shade, 0));
        int[] xPoints = new int[] { (int) (x + 20 * cos(angle)), (int) (x + 20 * cos(angle + 60)),
                (int) (x + 20 * cos(angle + 120)), (int) (x + 20 * cos(angle + 180)), (int) (x + 20 * cos(angle + 240)),
                (int) (x + 20 * cos(angle + 300)) };
        int[] yPoints = new int[] { (int) (y + 20 * sin(angle)), (int) (y + 20 * sin(angle + 60)),
                (int) (y + 20 * sin(angle + 120)), (int) (y + 20 * sin(angle + 180)), (int) (y + 20 * sin(angle + 240)),
                (int) (y + 20 * sin(angle + 300)) };
        g.fillPolygon(xPoints, yPoints, 6);

        if (Main.gameFrame.gamePanel.devTools) {
            g.setColor(new Color(0, 0, 0));
            g.fillOval((int) x - 1, (int) y - 1, 2, 2);
            g.drawOval((int) x - 17, (int) y - 17, 34, 34);
        }
    }
}