import java.awt.*;

public class PhysicsObject {
    double x, y;
    double mass;
    Vector direction, gravity;

    Color color = new Color((int) (255 * Math.random()), (int) (255 * Math.random()), (int) (255 * Math.random()));

    public PhysicsObject(double x, double y, double mass, double directionX, double directionY) {
        this.x = x;
        this.y = y;
        this.mass = mass;
        direction = new Vector(directionX, directionY);
        gravity = new Vector(0, 0);
    }

    public void display(Graphics g) {
        g.setColor(color);
        int diameter = (int) (mass);
        g.fillOval((int) x - diameter / 2, (int) y - diameter / 2, diameter, diameter);
    }

    public void move(PhysicsObject pO) {
        //! should still move after touching
        if(this.isTouching(pO)){
            return;
        }
        // gravity
        gravity.x = pO.x - x;
        gravity.y = pO.y - y;

        double distance = Math.sqrt(Math.pow(pO.x - x, 2) + Math.pow(pO.y - y, 2));

        gravity.x /= distance;
        gravity.y /= distance;

        x += direction.x + gravity.x * (Main.G * mass) / Math.pow(distance, 2);
        y += direction.y + gravity.y * (Main.G * mass) / Math.pow(distance, 2);

        x = x >= 0 ? x : 0;
        x = x <= 900 ? x : 900;
        y = y >= 0 ? y : 0;
        y = y <= 600 ? y : 600;
    }

    public boolean isTouching(PhysicsObject pO) {
        if (Math.sqrt(Math.pow(pO.x - x, 2) + Math.pow(pO.y - y, 2)) <= mass/2 + pO.mass/2) {
            return true;
        }
        return false;
    }
}