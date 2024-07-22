package Game.GameObjects.Enemies;

import Main.*;
import Game.GameObjects.*;

import java.awt.*;

public class EnemyBullet extends GameObject{
    public double angle;
    double speed;

    public EnemyBullet(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.speed = 0.6;
        
        this.hitboxRadius = 1;
    }

    public void move() {
        x += speed * cos(angle);
        y += speed * sin(angle);
    }

    public void display(Graphics g) {
        g.setColor(Main.bulletColor);
        g.drawOval((int) x - 3, (int) y - 3, 6, 6);
    }
}