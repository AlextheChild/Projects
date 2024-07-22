import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
    ArrayList<PhysicsObject> physicsObjects = new ArrayList<PhysicsObject>();

    int mouseX, mouseY;

    Timer t;

    public GamePanel() {
        for(int i = 0; i<3; i++){
            physicsObjects.add(new PhysicsObject(450 + (int)(450 * Math.random()), 150 + (int)(150 * Math.random()), (int)(10 * Math.random()), (int)(2 * Math.random() - 1), (int)(2 * Math.random() - 1)));
        }

        
        physicsObjects.add(new PhysicsObject(500, 300, 70, 0, -1));

        this.setBackground(Color.WHITE);

        t = new Timer(10, this);
        t.setActionCommand("timerFired");
        t.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (PhysicsObject p : physicsObjects) {
            p.display(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("timerFired")) {
            for (int i = 0; i < physicsObjects.size(); i++) {
                PhysicsObject p = physicsObjects.get(i);

                for (int q = 0; q < physicsObjects.size(); q++) {
                    PhysicsObject pO = physicsObjects.get(q);

                    if (i == q) {
                        continue;
                    }

                    p.move(pO);

                    // if (p.isTouching(pO)) {
                    // physicsObjects.remove(p);
                    // physicsObjects.remove(pO);
                    // physicsObjects.add(new PhysicsObject((p.x + pO.x) / 2, (p.y + pO.y) / 2,
                    // (p.mass + pO.mass)));
                    // }
                }
            }

            repaint();
        }
    }
}