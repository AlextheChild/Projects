import java.awt.Color;
import java.util.ArrayList;

public class Fox extends Anything {
    ArrayList<Fox> foxes;
    Color fd = new Color(255, 160, 50);
    Color fn = new Color(150, 80, 0);
    int hunger = 20;

    public Fox(ArrayList<Fox> foxes) {
        this.foxes = foxes;
        foxes.add(this);
    }

    // if they reproduce
    public Fox(int xLoc, int yLoc, ArrayList<Fox> foxes) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.foxes = foxes;
        foxes.add(this);
    }

    public void move(String cycle, ArrayList<Rabbit> rabbits) {
        hunger--;
        // sleep
        if (cycle.equals("night")) {
            return;
        }

        // if rabbit exists
        if (rabbits.size() > 0 && Math.random() > 0.5) {
            findRabbit(rabbits);
        }

        // normal move code
        else {
            int move = (int) (Math.random() * 8.0);
            switch (move) {
                case (0):
                    xLoc -= 3;
                    break;
                case (1):
                    xLoc += 3;
                    break;
                case (2):
                    yLoc += 3;
                    break;
                case (3):
                    yLoc -= 3;
                    break;
                case (4):
                    xLoc -= 3;
                    yLoc -= 3;
                    break;
                case (5):
                    xLoc += 3;
                    yLoc -= 3;
                    break;
                case (6):
                    xLoc -= 3;
                    yLoc += 3;
                    break;
                case (7):
                    xLoc += 3;
                    yLoc += 3;
                    break;
            }
        }
    }

    // find rabbits
    public void findRabbit(ArrayList<Rabbit> rabbits) {
        // find closest rabbit's index
        int rabbitIndex = 0;
        int q = Integer.MAX_VALUE;
        for (int i = 0; i < rabbits.size(); i++) {
            int dist = Math.max(Math.abs(xLoc - rabbits.get(i).xLoc), Math.abs(yLoc -
                    rabbits.get(i).yLoc));
            if (q < dist) {
                q = dist;
                rabbitIndex = i;
            }
        }
        // move towards it thrice
        for (int i = 0; i < 3; i++) {
            if (xLoc > rabbits.get(rabbitIndex).xLoc) {
                xLoc--;
            } else if (xLoc < rabbits.get(rabbitIndex).xLoc) {
                xLoc++;
            }
            if (yLoc > rabbits.get(rabbitIndex).yLoc) {
                yLoc--;
            } else if (yLoc < rabbits.get(rabbitIndex).yLoc) {
                yLoc++;
            }
        }
    }

    // rabbit mower
    public void eat(ArrayList<Anything> occupants, ArrayList<Rabbit> rabbits) {
        for (Rabbit r : rabbits) {
            if (r.xLoc == xLoc && r.yLoc == yLoc) {
                rabbits.remove(r);
                occupants.remove(r);
                if (Math.random() > 0.95) {
                    occupants.add(new Fox(xLoc, yLoc, foxes));
                }
                return;
            }
        }
    }

    // fox dies if too hungry
    public void die(ArrayList<Anything> occupants, ArrayList<Fox> foxes) {
        if (hunger <= 0) {
            foxes.remove(this);
            occupants.remove(this);
            System.out.println("yeowch");
        }
    }

    // cycle
    @Override
    public void cycle(String cycle) {
        if (cycle.equals("day")) {
            color = fd;
        } else if (cycle.equals("night")) {
            color = fn;
        }
    }
}