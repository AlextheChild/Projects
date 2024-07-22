import java.awt.*;

public class Player extends GameObject {
    public Player(int x, int y) {
        super(x, y);
        c = Color.BLACK;
        isPlayer = true;
    }

    @Override
    public String toString() {
        return "p";
    }
}