import java.awt.Graphics;
import java.awt.Color;

public class Anything {
    int xLoc, yLoc;
    int size = 15;
    Color color;

    public Anything() {
        xLoc = (int) (Math.random() * 40.0) - 1;
        yLoc = (int) (Math.random() * 40.0) - 1;
    }

    public void cycle(String cycle) {
    }

    public void draw(Graphics g, int pad) {
        // sets the color before drawing
        g.setColor(color);

        // location of the animal on grid:
        int x = (int) (size * xLoc) + pad;
        int y = (int) (size * yLoc) + pad;

        // draw the animal:
        g.fillRect(x, y, size, size);
    }
}