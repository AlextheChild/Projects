import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DragPanel extends JPanel implements ActionListener {
    int frameX = Main.frameX, frameY = Main.frameY;

    GhostPlayer ghostPlayer;

    Timer t;

    public DragPanel() {
        this.setBackground(new Color(255, 255, 255));

        ghostPlayer = new GhostPlayer();

        t = new Timer(1, this);
        t.setActionCommand("timerFired");
        t.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // grid lines
        g.setColor(new Color(200, 200, 200));
        for (int i = 30; i < 720; i += 30) {
            g.drawLine(i, 0, i, 450);
        }
        for (int i = 30; i < 450; i += 30) {
            g.drawLine(0, i, 720, i);
        }

        // grid numbers
        g.setColor(new Color(200, 200, 200));
        for (int i = 30; i < 450; i += 30) {
            g.drawString("" + (frameY + i + 30) / 30, 5, i + 25);
        }
        for (int i = 30; i < 720; i += 30) {
            g.drawString("" + (frameX + i + 30) / 30, i + 5, 25);
        }

        // board preview
        displayObjects(g);
        ghostPlayer.display(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("timerFired")) {
            repaint();
        }
    }

    public void displayObjects(Graphics g) {
        for (GameObject gO : Main.mainFrame.mainPanel.thisLevelObjects) {
            if (gO instanceof GoalObject) {
                g.setColor(Color.BLACK);
            } else if (gO instanceof MovingObject) {
                g.setColor(new Color(0, 40, 60));
            } else if (gO instanceof MovableObject) {
                g.setColor(new Color(10, 120, 40));
            } else if (gO instanceof ImmovableObject) {
                g.setColor(new Color(120, 10, 10));
            } else {
                g.setColor(Color.BLACK);
            }

            g.drawRect(gO.realX * 30 - frameX, gO.realY * 30 - frameY, 30, 30);
        }
    }
}