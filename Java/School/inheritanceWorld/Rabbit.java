import java.awt.Color;
import java.util.ArrayList;

public class Rabbit extends Anything {
    ArrayList<Rabbit> rabbits;
    Color rd, rn;
    int hunger = 20;

    public Rabbit(ArrayList<Rabbit> rabbits) {
        this.rabbits = rabbits;
        rabbits.add(this);
        // rabbit gets random color
        int c = (int) (Math.random() * 2.5);
        switch (c) {
            case (0):
                rd = new Color(255, 255, 255);
                rn = new Color(100, 100, 100);
                break;
            case (1):
                rd = new Color(130, 130, 130);
                rn = new Color(80, 80, 80);
                break;
            case (2):
                rd = new Color(0, 0, 0);
                rn = new Color(0, 0, 0);
                break;
        }
    }

    // when they reproduce
    public Rabbit(int xLoc, int yLoc, ArrayList<Rabbit> rabbits) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.rabbits = rabbits;
        rabbits.add(this);
        // rabbit gets random color
        int c = (int) (Math.random() * 2.5);
        switch (c) {
            case (0):
                rd = new Color(255, 255, 255);
                rn = new Color(100, 100, 100);
                break;
            case (1):
                rd = new Color(130, 130, 130);
                rn = new Color(80, 80, 80);
                break;
            case (2):
                rd = new Color(0, 0, 0);
                rn = new Color(0, 0, 0);
                break;
        }
    }

    public void move(String cycle, ArrayList<Grass> grasses) {
        // sleep
        if (cycle.equals("night")) {
            return;
        }

        // hunger
        if (Math.random() > 0.3) {
            hunger--;
        }

        // if rabbit is hungry and grass exists;
        if (hunger < 10 && grasses.size() > 0) {
            findGrass(grasses);
        }

        // idle move code
        else {
            int move = (int) (Math.random() * 8.0);
            switch (move) {
                case (0):
                    xLoc -= 2;
                    break;
                case (1):
                    xLoc += 2;
                    break;
                case (2):
                    yLoc += 2;
                    break;
                case (3):
                    yLoc -= 2;
                    break;
                case (4):
                    xLoc--;
                    yLoc--;
                    break;
                case (5):
                    xLoc++;
                    yLoc--;
                    break;
                case (6):
                    xLoc--;
                    yLoc++;
                    break;
                case (7):
                    xLoc++;
                    yLoc++;
                    break;
            }
        }
    }

    // move towards nearest grass
    public void findGrass(ArrayList<Grass> grasses) {
        // find closest grass' index
        int grassIndex = 0;
        int q = Integer.MAX_VALUE;
        for (int i = 0; i < grasses.size(); i++) {
            int dist = Math.max(Math.abs(xLoc - grasses.get(i).xLoc), Math.abs(yLoc -
                    grasses.get(i).yLoc));
            if (q > dist) {
                q = dist;
                grassIndex = i;
            }
        }
        // move towards it
        if (xLoc > grasses.get(grassIndex).xLoc) {
            xLoc--;
        } else if (xLoc < grasses.get(grassIndex).xLoc) {
            xLoc++;
        }
        if (yLoc > grasses.get(grassIndex).yLoc) {
            yLoc--;
        } else if (yLoc < grasses.get(grassIndex).yLoc) {
            yLoc++;
        }
    }

    // lawn mower
    public void eat(ArrayList<Anything> occupants, ArrayList<Grass> grasses) {
        for (int i = 0; i < grasses.size(); i++) {
            if (grasses.get(i).xLoc == xLoc && grasses.get(i).yLoc == yLoc) {
                hunger++;
                // !hm
                grasses.remove(grasses.get(i));
                occupants.remove(grasses.get(i));
                i--;

                // reproduce
                if (Math.random() > 0.7) {
                    occupants.add(new Rabbit(xLoc, yLoc, rabbits));
                }
            }
        }
    }

    // rabbit dies if too hungry
    public void die(ArrayList<Anything> occupants, ArrayList<Rabbit> rabbits) {
        if (hunger <= 0) {
            rabbits.remove(this);
            occupants.remove(this);
            System.out.println("yeowch");
        }
    }

    // cycle
    @Override
    public void cycle(String cycle) {
        if (cycle.equals("day")) {
            color = rd;
        } else if (cycle.equals("night")) {
            color = rn;
        }
    }
}