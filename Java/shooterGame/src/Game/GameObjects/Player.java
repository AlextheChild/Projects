package Game.GameObjects;

import Main.*;

import java.awt.*;

public class Player extends GameObject {
    public boolean left = false, right = false;
    public double angle = 0;
    public boolean cruising;
    public int cruiseDirection;

    public double speed = 0.17;
    double turningSpeed = 0.17;

    public int money = 0;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;

        this.hitboxRadius = 17;
    }

    @Override
    public void move() {
        if(!cruising) {
            if (left) {
                angle -= turningSpeed;
            }
            if (right) {
                angle += turningSpeed;
            }

            x += speed * Math.cos(Math.toRadians(angle));
            y += speed * Math.sin(Math.toRadians(angle));
        }else{
            double theta = Math.toDegrees(Math.atan2(y - 300, x - 450));

            switch (cruiseDirection) {
            case (0):
                // clockwise
                angle = theta + 90;
                break;
            case (1):
                // counterclockwise
                angle = theta - 90;
                break;
            }

            x += speed * Math.cos(Math.toRadians(angle));
            y += speed * Math.sin(Math.toRadians(angle));
        }
    }

    public void setCruiseDirection() {
        double theta = Math.toDegrees(Math.atan2(y - 300, x - 450));
        if (Math.abs(theta + 90 - (angle % 360)) > Math.abs(theta - 90 - (angle % 360))) {
            // counterclockwise
            cruiseDirection = 1;
        } else {
            // clockwise
            cruiseDirection = 0;
        }
    }

    @Override
    public void display(Graphics g) {
        g.setColor(Main.playerColor);
        int[] xPoints = new int[] { (int) (x + 15 * cos(angle)), (int) (x + 9 * cos(angle + 127)), (int) x,
                (int) (x + 9 * cos(angle - 127)) };
        int[] yPoints = new int[] { (int) (y + 15 * sin(angle)), (int) (y + 9 * sin(angle + 127)), (int) y,
                (int) (y + 9 * sin(angle - 127)) };
        g.fillPolygon(xPoints, yPoints, 4);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));
        g.drawOval((int) x - 17, (int) y - 17, 34, 34);
    }
}