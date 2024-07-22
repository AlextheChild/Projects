import java.awt.Color;
import java.util.ArrayList;

public class Rain {
    int xLoc, yLoc;
    int size = 600;
    Color color = new Color(70, 100, 120);

    public Rain() {
    }

    public void move() {
        yLoc++;
    }

    public void drown(ArrayList<Anything> occupants, ArrayList<Grass> grasses, ArrayList<Rabbit> rabbits,
            ArrayList<Fox> foxes) {
        for (Grass g : grasses) {
            if (g.xLoc == xLoc && g.yLoc == yLoc) {
                grasses.remove(g);
                occupants.remove(g);
                System.out.println("eat");
                return;
            }
        }
        for (Rabbit r : rabbits) {
            if (r.xLoc == xLoc && r.yLoc == yLoc) {
                rabbits.remove(r);
                occupants.remove(r);
                System.out.println("eat");
                return;
            }
        }
        for (Fox f : foxes) {
            if (f.xLoc == xLoc && f.yLoc == yLoc) {
                foxes.remove(f);
                occupants.remove(f);
                System.out.println("eat");
                return;
            }
        }
    }
}