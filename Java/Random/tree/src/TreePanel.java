import java.util.*;
import java.awt.*;
import javax.swing.*;

// randomize depth while making new branch to get rid of circle effect

public class TreePanel extends JPanel {
    int maxLevels = 9;
    int length = 100;
    int angle = new Random().nextInt(-10, 10 + 1);

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.BLACK);
        g.drawLine(0, 605, 1000, 605);
        generateBranch(500, 600, new Random().nextInt(-10, 10 + 1), 0, g);

    }

    public void newTree() {
        this.repaint();
    }

    public void generateBranch(int baseX, int baseY, int angle, int depth, Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(maxLevels - depth));
        int range = 78;

        int angleAddon = 0;
        if (depth > 0) {
            angleAddon = new Random().nextInt(-range, range + 1) / depth;
            angle += angleAddon;
        }

        // draw the branches
        double cos = Math.cos(-Math.toRadians(angle + 90));
        double sin = Math.sin(-Math.toRadians(angle + 90));
        int branchX = (int) (Math.abs(length - depth * 9) * cos) + baseX;
        int branchY = (int) (Math.abs(length - depth * 9) * sin) + baseY;

        g.setColor(new Color(35, 30, 26));
        g.drawLine(baseX, baseY, branchX, branchY);
        // g.drawString("" + angle, branchX + 10, branchY + 10);
        // g.drawString("" + angleAddon, branchX + 20, branchY + 15);

        // new branch
        if (depth < maxLevels) {
            for (int i = 0; i < new Random().nextInt(2, 5); i++) {
                if (depth + 1 < maxLevels) {
                    if ((int) (Math.random() * 10) > 3) {
                        generateBranch(branchX, branchY, angle, new Random().nextInt(depth + 1, maxLevels), g);
                    } else {
                        generateBranch(branchX, branchY, angle, depth + 1, g);
                    }
                } else {
                    generateBranch(branchX, branchY, angle, depth + 1, g);
                }
            }
        }
        // leaves
        else {
            g2.setStroke(new BasicStroke(10));
            g.setColor(new Color(40, 90, 50));
            g.drawLine(branchX, branchY, branchX, branchY);
        }
    }
}