import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame implements KeyListener, MouseListener, ActionListener {
    MainPanel mainPanel;

    ArrayList<int[]> previousLocs = new ArrayList<int[]>();

    Timer t;

    // f
    int numTimesFPressed = 0;
    int fValue = (int) (Math.random() * 5);

    public MainFrame() {
        mainPanel = new MainPanel();
        this.add(mainPanel, BorderLayout.CENTER);

        // ========== setup ========= //

        this.setUndecorated(true);
        this.setSize(Main.frameSize);
        this.setLocation(Main.frameX, Main.frameY);
        previousLocs.add(new int[] { this.getX(), this.getY() });
        this.setVisible(true);
        this.addKeyListener(this);
        this.addMouseListener(this);

        t = new Timer(1, this);
        t.setActionCommand("timerFired");
        t.start();
    }

    @Override
    public void keyPressed(KeyEvent kE) {
        // movement
        if (kE.getKeyChar() == 'a' || kE.getKeyCode() == KeyEvent.VK_LEFT) {
            if (mainPanel.player.onPanelX < 23) {
                updatePos(this.getX() - 30, this.getY());
            }
        }
        if (kE.getKeyChar() == 'd' || kE.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (mainPanel.player.onPanelX > 0) {
                updatePos(this.getX() + 30, this.getY());
            }
        }
        if (kE.getKeyChar() == 'w' || kE.getKeyCode() == KeyEvent.VK_UP) {
            if (mainPanel.player.onPanelY < 14) {
                updatePos(this.getX(), this.getY() - 30);
            }
        }
        if (kE.getKeyChar() == 's' || kE.getKeyCode() == KeyEvent.VK_DOWN) {
            if (mainPanel.player.onPanelY > 0) {
                updatePos(this.getX(), this.getY() + 30);
            }
        }

        // cannon
        if (kE.getKeyChar() == 'A') {
            if (mainPanel.player.onPanelX < 23) {
                updatePos(this.getX() - 30, this.getY());
            }
        }
        if (kE.getKeyChar() == 'D') {
            if (mainPanel.player.onPanelX > 0) {
                updatePos(this.getX() + 30, this.getY());
            }
        }
        if (kE.getKeyChar() == 'W') {
            if (mainPanel.player.onPanelY < 14) {
                updatePos(this.getX(), this.getY() - 30);
            }
        }
        if (kE.getKeyChar() == 'S') {
            if (mainPanel.player.onPanelY > 0) {
                updatePos(this.getX(), this.getY() + 30);
            }
        }

        // quitting
        if (kE.getKeyChar() == 'p') {
            System.exit(0);
        }

        // every other key tells the user how to quit
        if (kE.getKeyChar() != 'p' && kE.getKeyChar() != 'w' && kE.getKeyChar() != 'a' && kE.getKeyChar() != 's'
                && kE.getKeyChar() != 'd' && kE.getKeyCode() != KeyEvent.VK_LEFT && kE.getKeyCode() != KeyEvent.VK_RIGHT
                && kE.getKeyCode() != KeyEvent.VK_UP && kE.getKeyCode() != KeyEvent.VK_DOWN && kE.getKeyChar() != 'f') {
            mainPanel.fTick = 1000;
            mainPanel.fText = "Press \"P\" to close";
        }

        // frustration button
        if (kE.getKeyChar() == 'f') {
            switch (fValue) {
                case (0):
                    switch (numTimesFPressed % 11) {
                        case (0):
                            mainPanel.fText = "you scream.";
                            mainPanel.fTick = 1000;
                            break;
                        case (1):
                            mainPanel.fText = "you kick the chair back and get up. ";
                            mainPanel.fTick = 1000;
                            break;
                        case (2):
                            mainPanel.fText = "you slam the table. ";
                            mainPanel.fTick = 1000;
                            break;
                        case (3):
                            mainPanel.fText = "the table is broken. ";
                            mainPanel.fTick = 1000;
                            break;
                        case (4):
                            mainPanel.fText = "you breathe a sigh of relief. ";
                            mainPanel.fTick = 1000;
                            break;
                        case (5):
                            mainPanel.fText = "the table is broken. ";
                            mainPanel.fTick = 1000;
                            break;
                        case (6):
                            mainPanel.fText = "the table is broken. ";
                            mainPanel.fTick = 1000;
                            break;
                        case (7):
                            mainPanel.fText = "why is the table broken";
                            mainPanel.fTick = 1000;
                            break;
                        case (8):
                            mainPanel.fText = "you tell the table to stop being broken";
                            break;
                        case (9):
                            mainPanel.fText = "there is no response. ";
                            break;
                        case (10):
                            mainPanel.fText = "there is no response. ";
                            break;
                    }
                    numTimesFPressed++;
                    break;
                case (1):
                    mainPanel.fText = "why";
                    break;
                case (2):
                    mainPanel.fText = "press h for help";
                    break;
                case (3):
                    mainPanel.fText = "*frustration*";
                    break;
                case (4):
                    switch (numTimesFPressed % 2) {
                        case (0):
                            mainPanel.fText = "quasedilla";
                            break;
                        case (1):
                            mainPanel.fText = "tortilla";
                            break;
                    }
                    break;
            }
        }
    }

    // out of bounds check
    @Override
    public void keyReleased(KeyEvent kE) {
        // holding shift keeps it from correcting out of bounds
        if (kE.getKeyChar() == 'A' && kE.getKeyChar() == 'S' && kE.getKeyChar() == 'W' && kE.getKeyChar() == 'S') {
            return;
        }

        correctOutOfBounds();
        Main.dragFrame.correctOutOfBounds();
    }

    // move dragFrame
    @Override
    public void keyTyped(KeyEvent kE) {
        Main.dragFrame.updatePos(this.getX(), this.getY());
    }

    @Override
    public void mousePressed(MouseEvent mE) {
        Main.dragFrame.toFront();
    }

    public void correctOutOfBounds() {
        // out of bounds
        if (this.getX() < 0) {
            updatePos(0, this.getY());
        }
        if (this.getX() > 720) {
            updatePos(720, this.getY());
        }
        if (this.getY() < 0) {
            updatePos(this.getX(), 0);
        }
        if (this.getY() > 450) {
            updatePos(this.getX(), 450);
        }

        for (ImmovableObject iO : mainPanel.immovableObjects) {
            if (iO.onPanelX > 0 && iO.onPanelX < 24 && iO.onPanelY > 0 && iO.onPanelY < 15) {
                iO.updatePos();
            }
        }
    }

    public void updatePos(int x, int y) {
        this.setLocation(x, y);
        // update previousLocs
        previousLocs.add(new int[] { Main.mainFrame.getX(), Main.mainFrame.getY() });
        if (previousLocs.size() > 3) {
            previousLocs.remove(0);
        }

        // update player
        mainPanel.player.frameX = Main.mainFrame.getX();
        mainPanel.player.frameY = Main.mainFrame.getY();

        // gameObjects
        for (GameObject gO : mainPanel.thisLevelObjects) {
            gO.frameX = this.getX();
            gO.frameY = this.getY();
        }
    }

    // update grid numbers
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("timerFired")) {
            // grid numbers
            mainPanel.frameX = this.getX();
            mainPanel.frameY = this.getY();
            Main.dragFrame.dragPanel.frameX = Main.dragFrame.getX();
            Main.dragFrame.dragPanel.frameY = Main.dragFrame.getY();
        }
    }

    // hell
    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }
}