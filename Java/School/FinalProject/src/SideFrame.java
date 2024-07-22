import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SideFrame extends JFrame implements MouseListener, MouseMotionListener {
    SidePanel sidePanel;
    int normalX = Main.screenSize.width - 30;
    int normalY = 0;

    int numTimesClicked;
    boolean activated = false;

    public SideFrame() {
        sidePanel = new SidePanel();
        this.add(sidePanel, BorderLayout.CENTER);

        // --------- setup ---------- //

        this.setUndecorated(true);
        this.setSize(480, Main.screenSize.height);
        this.setLocation(normalX, normalY);
        this.setVisible(true);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    public void mousePressed(MouseEvent mE) {
        numTimesClicked++;
        if (numTimesClicked % 2 == 1) {
            activated = true;
            this.setLocation(normalX - 450, normalY);
        } else {
            activated = false;
            this.setLocation(normalX, 0);
        }
    }

    // prompting
    @Override
    public void mouseEntered(MouseEvent mE) {
        if (!activated) {
            this.setLocation(normalX - 30, normalY);
        }
    }

    @Override
    public void mouseExited(MouseEvent mE) {
        if (!activated) {
            this.setLocation(normalX, 0);
        }
    }

    // ======= hell ======= //
    @Override
    public void mouseDragged(MouseEvent mE) {
    }

    @Override
    public void mouseMoved(MouseEvent mE) {
    }

    @Override
    public void mouseClicked(MouseEvent mE) {
    }

    @Override
    public void mouseReleased(MouseEvent mE) {
    }

}