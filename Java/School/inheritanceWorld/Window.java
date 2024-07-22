import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Window extends JFrame implements ActionListener {
    // update rate
    final int TIMER_DELAY = 50;
    Timer t;

    // GUI components:
    JButton pauseButton;
    JPanel topPanel, displayPanel;
    JTextField info;
    Display display;

    ArrayList<Anything> occupants = new ArrayList<Anything>();
    ArrayList<Grass> grasses = new ArrayList<Grass>();
    ArrayList<Rabbit> rabbits = new ArrayList<Rabbit>();
    ArrayList<Fox> foxes = new ArrayList<Fox>();

    int GRIDSIZE;
    int WIDTH;
    int HEIGHT;

    boolean moving = true;

    int time = 0;
    String cycle;

    public Window(int width, int height, int gridSize) {
        WIDTH = width;
        HEIGHT = height;
        GRIDSIZE = gridSize;

        // layout
        this.setLayout(new BorderLayout());

        // pause button
        pauseButton = new JButton("PAUSE");
        pauseButton.addActionListener(this);
        pauseButton.setActionCommand("pauseButton");

        // top panel
        topPanel = new JPanel();
        topPanel.add(pauseButton);
        Color darkGreen = new Color(50, 90, 70);
        topPanel.setBackground(darkGreen);

        // display
        display = new Display(occupants);

        // add display to JFrame:
        this.add(topPanel, BorderLayout.NORTH);
        this.add(display, BorderLayout.CENTER);

        // setup
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        // simulation related setup
        cycle = "day";
        if (cycle.equals("day")) {
            display.setBackground(new Color(100, 190, 115));
        } else if (cycle.equals("night")) {
            display.setBackground(new Color(25, 50, 35));
        }

        populate();
    }

    // creates timer
    public void startTimer() {
        t = new Timer(TIMER_DELAY, this);
        t.setActionCommand("timerFired");
        t.start();
    }

    // pause button and timer
    @Override
    public void actionPerformed(ActionEvent e) {
        // timer fired
        if (e.getActionCommand().equals("timerFired")) {
            updateAll();
        }

        // pause button
        if (e.getActionCommand().equals("pauseButton")) {
            if (moving) {
                t.stop();
                pauseButton.setText("UNPAUSE");
            } else {
                t.start();
                pauseButton.setText("PAUSE");
            }
            moving = !moving;
        }
    }

    // add stuff to the occupants arraylist
    public void populate() {
        for (int i = 0; i < 20; i++) {
            occupants.add(new Grass(grasses));
        }

        for (int i = 0; i < 1; i++) {
            occupants.add(new Rabbit(rabbits));
        }

        for (int i = 0; i < 0; i++) {
            occupants.add(new Fox(foxes));
        }
        // !tf
        // new Rain();
    }

    // updates with the timer
    public void updateAll() {
        time++;
        display.repaint();
        moreStuff();
        dayNight();
        rabbitCode();
        foxCode();
    }

    // add new stuff
    public void moreStuff() {
        // grass is cool
        if (time % 5 == 0 && cycle.equals("day")) {
            occupants.add(new Grass(grasses));
        }
        // more rabbits if they're all dead
        if (time % 30 == 0 && Math.random() > 0.7 && rabbits.size() == 0) {
            occupants.add(new Rabbit(rabbits));
        }
        // more foxes if they're all dead
        if (time % 70 == 0 && Math.random() > 0.7 && foxes.size() == 0) {
            occupants.add(new Fox(foxes));
        }
    }

    public void dayNight() {
        // animals
        for (Anything o : occupants) {
            o.cycle(cycle);
        }

        // background
        if (time % 100 == 0 && time != 0) {
            if (cycle.equals("day")) {
                cycle = "night";
                display.setBackground(new Color(25, 50, 35));
            } else if (cycle.equals("night")) {
                cycle = "day";
                display.setBackground(new Color(100, 190, 115));
            }

        }
    }

    public void rabbitCode() {
        // moving
        for (Rabbit r : rabbits) {
            r.move(cycle, grasses);
            // if offscreen
            checkOffScreen(r);
        }

        for (int i = 0; i < rabbits.size(); i++) {
            // lawn mower
            rabbits.get(i).eat(occupants, grasses);
            // die
            rabbits.get(i).die(occupants, rabbits);
        }
    }

    public void foxCode() {
        // fox moving
        if (time % 5 == 0) {
            for (Fox f : foxes) {
                f.move(cycle, rabbits);
                // if offscreen
                checkOffScreen(f);
            }
        }

        // rabbit mower
        for (int i = 0; i < foxes.size(); i++) {
            foxes.get(i).eat(occupants, rabbits);
        }
    }

    public void checkOffScreen(Anything o) {
        if (o.xLoc < -1) {
            o.xLoc += GRIDSIZE - 1;
        }
        // right
        if (o.xLoc > GRIDSIZE - 2) {
            o.xLoc -= GRIDSIZE;
        }
        // up
        if (o.yLoc < -1) {
            o.yLoc += GRIDSIZE - 1;
        }
        // down
        if (o.yLoc > GRIDSIZE - 2) {
            o.yLoc -= GRIDSIZE;
        }
    }
}