import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DragFrame extends JFrame implements MouseListener, MouseMotionListener, KeyListener {
    DragPanel dragPanel;

    int beforeDraggedX, beforeDraggedY;

    public DragFrame() {
        dragPanel = new DragPanel();
        this.add(dragPanel, BorderLayout.CENTER);

        // ========== setup ========= //

        this.setUndecorated(true);
        this.setSize(Main.frameSize);
        this.setOpacity(0.8f);
        this.setLocation(Main.frameX, Main.frameY);
        this.setVisible(true);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
    }

    // dragging
    @Override
    public void mousePressed(MouseEvent mE) {
        beforeDraggedX = mE.getX();
        beforeDraggedY = mE.getY();
    }

    @Override
    public void mouseDragged(MouseEvent mME) {
        int changeX = mME.getX() - beforeDraggedX;
        int changeY = mME.getY() - beforeDraggedY;

        int newX = this.getX() + changeX;
        int newY = this.getY() + changeY;

        this.setLocation(30 * ((newX + 15) / 30), 30 * ((newY + 15) / 30));
    }

    @Override
    public void mouseReleased(MouseEvent mE) {
        correctOutOfBounds();
        // move the frame
        Main.mainFrame.updatePos(this.getX(), this.getY());
        Main.mainFrame.toFront();

        Main.mainFrame.mainPanel.player.updatePos();
    }

    // bring mainPanel forward when the player wants to use arrow keyes
    @Override
    public void keyPressed(KeyEvent kE) {
        Main.mainFrame.requestFocus();
        Main.mainFrame.toFront();
    }

    public void correctOutOfBounds() {
        // out of bounds
        if (this.getX() < 0) {
            this.setLocation(0, this.getY());
        }
        if (this.getX() > 720) {
            this.setLocation(720, this.getY());
        }
        if (this.getY() < 0) {
            this.setLocation(this.getX(), 0);
        }
        if (this.getY() > 450) {
            this.setLocation(this.getX(), 450);
        }
    }

    public void updatePos(int x, int y) {
        this.setLocation(x, y);
    }

    // hell
    @Override
    public void mouseMoved(MouseEvent mME) {
    }

    @Override
    public void mouseClicked(MouseEvent mE) {
    }

    @Override
    public void mouseEntered(MouseEvent mE) {
    }

    @Override
    public void mouseExited(MouseEvent mE) {
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }
}