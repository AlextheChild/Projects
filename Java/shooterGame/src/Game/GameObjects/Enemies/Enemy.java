package Game.GameObjects.Enemies;

import Main.*;
import Game.GameObjects.*;

public abstract class Enemy extends GameObject{
    double gravityX, gravityY;

    double speed;

    double angle = 360 * Math.random();;
    double rotationSpeed;

    int shade = 150 + (int) (105 * Math.random());

    public double health;

    public Enemy() {
        // spawning on all sides
        switch ((int) (4 * Math.random())) {
        case (0):
            this.x = 0;
            this.y = Math.random() * 600;
            break;
        case (1):
            this.x = 900;
            this.y = Math.random() * 600;
            break;
        case (2):
            this.x = Math.random() * 900;
            this.y = 0;
            break;
        case (3):
            this.x = Math.random() * 900;
            this.y = 600;
            break;
        }

        mass = 20;

        this.hitboxRadius = 17;
    }

    @Override
    public void move() {
        BlackHole blackHole = Main.gameFrame.gamePanel.blackHole;

        gravityX = blackHole.x - x;
        gravityY = blackHole.y - y;

        double distance = Math.sqrt(Math.pow(blackHole.x - x, 2) + Math.pow(blackHole.y - y, 2));

        gravityX /= distance;
        gravityY /= distance;

        x += gravityX * (Main.G * mass * blackHole.mass) / Math.pow(distance, 2);
        y += gravityY * (Main.G * mass * blackHole.mass) / Math.pow(distance, 2);

        // rotation
        angle += rotationSpeed;
    }
}