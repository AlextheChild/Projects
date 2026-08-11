import java.awt.*;

public class Main {
    public static void main(String[] args) throws Exception {
        int direction = Integer.parseInt(args[0]);

        Robot robot = new Robot();
        Point point = MouseInfo.getPointerInfo().getLocation();

        robot.mouseMove((int) point.getX() + direction, (int) point.getY());
    }
}