package Game.GameObjects.Enemies;

import Main.*;

import java.awt.*;

public class TriangleEnemy extends Enemy {
    int trianglePlayerAngle;

    long timeLastFired;

    public TriangleEnemy() {
        speed = 0.05 + 0.1 * Math.random();

        rotationSpeed = 0;

        health = 6;
    }

    @Override
    public void move() {
        trianglePlayerAngle = (int) Math.toDegrees(Math.atan(
                (float) (y - Main.gameFrame.gamePanel.player.y) / (float) (x - Main.gameFrame.gamePanel.player.x)));

        if (x > Main.gameFrame.gamePanel.player.x) {
            trianglePlayerAngle += 180;
        }

        x += speed * Math.cos(Math.toRadians(trianglePlayerAngle));
        y += speed * Math.sin(Math.toRadians(trianglePlayerAngle));

        shootBullet();
    }

    @Override
    public void display(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        int[] xPoints = new int[] { (int) (x + 20 * cos(trianglePlayerAngle)),
                (int) (x + 12 * cos(trianglePlayerAngle + 120)), (int) x,
                (int) (x + 12 * cos(trianglePlayerAngle - 120)) };
        int[] yPoints = new int[] { (int) (y + 20 * sin(trianglePlayerAngle)),
                (int) (y + 12 * sin(trianglePlayerAngle + 120)), (int) y,
                (int) (y + 12 * sin(trianglePlayerAngle - 120)) };
        g.drawPolygon(xPoints, yPoints, 4);

        if (Main.gameFrame.gamePanel.devTools) {
            g.setColor(new Color(0, 0, 0));
            g.fillOval((int) x - 1, (int) y - 1, 2, 2);
            g.drawOval((int) x - 17, (int) y - 17, 34, 34);
        }
    }

    public void shootBullet() {
        if (System.currentTimeMillis() - timeLastFired >= 2000) {
            Main.gameFrame.gamePanel.enemyBullets.add(new EnemyBullet(x, y, trianglePlayerAngle));
            timeLastFired = System.currentTimeMillis();
        }
    }
}