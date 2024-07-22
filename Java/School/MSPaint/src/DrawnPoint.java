import java.awt.*;

public class DrawnPoint extends Coordinate {
    Color pointColor;

    public DrawnPoint(int x, int y, Color color) {
        super(x, y);
        pointColor = color;
    }
}