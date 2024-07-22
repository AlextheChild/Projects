import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class MainPanel extends JPanel implements ActionListener {
    int frameX = Main.frameX, frameY = Main.frameY;

    Player player;
    GameObject[][] level = new GameObject[48][30];
    ArrayList<GameObject> thisLevelObjects = new ArrayList<GameObject>();
    ArrayList<GoalObject> goalObjects = new ArrayList<GoalObject>();
    ArrayList<MovingObject> movingObjects = new ArrayList<MovingObject>();
    ArrayList<MovableObject> movableObjects = new ArrayList<MovableObject>();
    ArrayList<ImmovableObject> immovableObjects = new ArrayList<ImmovableObject>();

    String fText = "";
    Font fFont = new Font("Verdana", Font.PLAIN, 18);

    Timer t;
    int tick = 0;
    int fTick = 0;

    public MainPanel() {
        this.setBackground(new Color(255, 255, 255));
        player = new Player(24, 15);

        t = new Timer(1, this);
        t.setActionCommand("timerFired");
        t.start();
    }

    // gets level text file and updates level array
    public void updateLevel() throws FileNotFoundException {
        thisLevelObjects.clear();
        goalObjects.clear();
        movingObjects.clear();
        movableObjects.clear();
        immovableObjects.clear();

        Scanner levelLineScanner = new Scanner(Main.level);

        String textLine;
        String tileLine = "";
        int tileValue = 0;

        int lineIndex = 0;
        while (levelLineScanner.hasNext()) {
            textLine = levelLineScanner.nextLine();

            // getting rid of spaces
            for (char c : textLine.toCharArray()) {
                if (c != ' ') {
                    tileLine += c;
                }
            }

            for (int i = 0; i < tileLine.length() - 1; i++) {
                // get singular tile
                tileValue = tileLine.charAt(i) - '0';

                // goal
                if (tileValue == 1) {
                    GoalObject gO = new GoalObject(i, lineIndex);
                    goalObjects.add(gO);
                    thisLevelObjects.add(gO);
                } else if (tileValue == 2) {
                    MovingObject mO = new MovingObject(i, lineIndex);
                    level[i][lineIndex] = mO;
                    movingObjects.add(mO);
                    thisLevelObjects.add(mO);
                } else if (tileValue == 3) {
                    MovableObject mO = new MovableObject(i, lineIndex);
                    level[i][lineIndex] = mO;
                    movableObjects.add(mO);
                    thisLevelObjects.add(mO);
                } else if (tileValue == 4) {
                    ImmovableObject iO = new ImmovableObject(i, lineIndex);
                    level[i][lineIndex] = iO;
                    immovableObjects.add(iO);
                    thisLevelObjects.add(iO);
                } else {
                    level[i][lineIndex] = null;
                }
            }
            lineIndex++;
            tileLine = "";
        }
        levelLineScanner.close();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // grid lines
        g.setColor(new Color(210, 210, 210));
        for (int i = 30; i < 720; i += 30) {
            g.drawLine(i, 0, i, 450);
        }
        for (int i = 30; i < 450; i += 30) {
            g.drawLine(0, i, 720, i);
        }

        // grid numbers
        g.setColor(new Color(150, 150, 150));
        for (int i = 30; i < 450; i += 30) {
            g.drawString("" + (frameY + i + 30) / 30, 5, i + 25);
        }
        for (int i = 30; i < 720; i += 30) {
            g.drawString("" + (frameX + i + 30) / 30, i + 5, 25);
        }

        // display objects
        for (GameObject gO : thisLevelObjects) {
            gO.display(g);
        }

        player.display(g);

        // fText
        FontMetrics metrics = g.getFontMetrics(fFont);
        int x = (720 - metrics.stringWidth(fText)) / 2;

        g.setColor(Color.RED);
        g.setFont(fFont);
        g.drawString(fText, x, 67);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("timerFired")) {
            tick++;

            repaint();

            for (GameObject gO : thisLevelObjects) {
                gO.kick();
            }

            getPlayerPos();
            // move things
            if (tick % 250 == 0) {
                updateGrid();
                // for (int i = 0; i < 30; i++) {
                // for (int q = 0; q < 48; q++) {
                // if (level[q][i] == null) {
                // System.out.print(0 + " ");
                // } else {
                // System.out.print(level[q][i] + " ");
                // }
                // }
                // System.out.println("");
                // }
            }

            // f
            fTick--;
            if (fTick <= 0) {
                fText = "";
            }
        }
    }

    public void updateGrid() {
        // get supposed positions of everything
        for (int i = 0; i < 48; i++) {
            for (int q = 0; q < 30; q++) {
                GameObject gO = level[i][q];
                if (gO == null) {
                    continue;
                }

                // goal checking
                if (gO instanceof MovingObject || gO instanceof MovableObject) {
                    for (GoalObject gLO : goalObjects) {
                        if (gO.realX == gLO.realX && gO.realY == gLO.realY) {
                            if (gO instanceof MovingObject) {
                                thisLevelObjects.remove(gO);
                                movingObjects.remove(gO);
                            }
                            if (gO instanceof MovableObject) {
                                thisLevelObjects.remove(gO);
                                movableObjects.remove(gO);
                            }
                            gLO.icon = gLO.litIcon;
                            level[gLO.realX][gLO.realY] = new ImmovableObject(gLO.realX, gLO.realY);
                        }
                    }
                }

                if (gO instanceof MovingObject) {
                    MovingObject mO = (MovingObject) gO;

                    // move
                    mO.getDirection();
                    try {
                        if (mO.onPanelX >= 0 && mO.onPanelX < 24 && mO.onPanelY >= 0 && mO.onPanelY < 15) {
                            push(i, q, mO.direction, mO);
                        }

                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }

            }
        }
    }

    public void getPlayerPos() {
        level[player.realX][player.realY] = player;

        // get rid of where the player previously was
        for (int i = 0; i < 48; i++) {
            for (int q = 0; q < 30; q++) {
                if (level[i][q] == player && (i != player.realX || q != player.realY)) {
                    level[i][q] = null;
                }
            }
        }
    }

    public void push(int x, int y, int direction, GameObject gO) {
        int changeX = 0;
        int changeY = 0;
        switch (direction) {
            case (4):
                changeX = -1;
                break;
            case (6):
                changeX = 1;
                break;
            case (8):
                changeY = -1;
                break;
            case (2):
                changeY = 1;
                break;
            case (7):
                changeX = -1;
                changeY = -1;
                break;
            case (1):
                changeX = -1;
                changeY = +1;
                break;
            case (9):
                changeX = +1;
                changeY = -1;
                break;
            case (3):
                changeX = +1;
                changeY = +1;
                break;
        }

        // push
        if (level[x + changeX][y + changeY] instanceof MovableObject) {
            push(x + changeX, y + changeY, direction, level[x - 1][y]);
        }
        // move left
        if (level[x + changeX][y + changeY] == null) {
            swap(x, y, changeX, changeY, level[x][y]);
        }
    }

    public void swap(int x, int y, int changeX, int changeY, GameObject gO) {
        // swap
        level[x + changeX][y + changeY] = level[x][y];
        level[x][y] = null;
        gO.setLocation(x + changeX, y + changeY);

        // checks if there's something behind it that needs to be swapped as well
        if (level[x - changeX][y - changeY] instanceof MovableObject
                && !(level[x + changeX][y + changeY] instanceof MovingObject)) {
            swap(x - changeX, y - changeY, changeX, changeY, level[x - changeX][y - changeY]);
        }
    }
}