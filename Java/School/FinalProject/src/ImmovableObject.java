import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class ImmovableObject extends GameObject {
    public ImmovableObject(int x, int y) {
        super(x, y);
        try {
            icon = ImageIO.read(new File("src/BlockIcons/Immovable.png")).getScaledInstance(30, 30,
                    Image.SCALE_DEFAULT);
        } catch (IOException e) {
        }
        isPlayer = false;
    }

    @Override
    public String toString() {
        return "4";
    }
}