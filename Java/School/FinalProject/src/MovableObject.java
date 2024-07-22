import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class MovableObject extends GameObject {
    Player player = Main.mainFrame.mainPanel.player;

    public MovableObject(int x, int y) {
        super(x, y);
        c = Color.getHSBColor(351f / 360f, 1f, 0.6f);
        try {
            icon = ImageIO.read(new File("src/BlockIcons/Movable.png")).getScaledInstance(30, 30,
                    Image.SCALE_DEFAULT);
        } catch (IOException e) {
        }
        isPlayer = false;
    }

    @Override
    public String toString() {
        return "3";
    }
}