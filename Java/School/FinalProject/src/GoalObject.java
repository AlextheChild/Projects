import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class GoalObject extends GameObject {
    Image litIcon;

    public GoalObject(int x, int y) {
        super(x, y);
        try {
            icon = ImageIO.read(new File("src/BlockIcons/Goal.png")).getScaledInstance(30, 30,
                    Image.SCALE_DEFAULT);
        } catch (IOException e) {
        }
        // lit icon
        try {
            // feesh
            if (Math.random() * 1000 == 0) {
                litIcon = ImageIO.read(new File("src/BlockIcons/GoalHappy.png")).getScaledInstance(30, 30,
                        Image.SCALE_DEFAULT);
            } else {
                litIcon = ImageIO.read(new File("src/BlockIcons/GoalLit.png")).getScaledInstance(30, 30,
                        Image.SCALE_DEFAULT);
            }
        } catch (IOException e) {
        }
        isPlayer = false;
    }

    @Override
    public String toString() {
        return "1";
    }
}