import java.awt.Color;
import java.util.ArrayList;

public class Grass extends Anything {
    String cycle;
    Color gd = new Color(75, 150, 85);
    Color gn = new Color(25, 70, 37);
    ArrayList<Grass> grass;

    public Grass(ArrayList<Grass> grass) {
        grass.add(this);
    }

    @Override
    public void cycle(String cycle) {
        if (cycle.equals("day")) {
            color = gd;
        } else if (cycle.equals("night")) {
            color = gn;
        }
    }
}