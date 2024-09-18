import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    int beforeDraggedX, beforeDraggedY;
    int xOffset = 0, yOffset = 0;
    int dragXOffset = 0, dragYOffset = 0;
    double zoom = 1;

    ArrayList<String> equations = new ArrayList<String>();

    public GraphPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AffineTransform at = g2.getTransform();
        at.scale(1, -1);
        at.translate(400, -300);
        at.translate(xOffset + dragXOffset, -yOffset - dragYOffset);
        at.scale(zoom, zoom);
        g2.setTransform(at);

        drawEquation(g);
    }

    public void getEquations(ArrayList<JTextField> labels) {
        for (JTextField label : labels) {
            if (!label.getText().equals("")) {
                equations.add(label.getText());
            }
        }
    }

    public void drawEquation(Graphics g) {
        // grid
        g.drawLine((int) ((-400 - xOffset) / zoom), 0, (int) ((400 - xOffset) / zoom), 0);
        g.drawLine(0, (int) ((-300 + yOffset) / zoom), 0, (int) ((300 + yOffset) / zoom));

        for (int y = 0; y < 300 / zoom; y += 1) {
            // y = x^2
            int x = (int) Math.sqrt(y);
            g.drawLine(x, y, x, y);
            g.drawLine(-x, y, -x, y);
        }
    }

    // dragging
    @Override
    public void mousePressed(MouseEvent mE) {
        beforeDraggedX = mE.getX();
        beforeDraggedY = mE.getY();
    }

    @Override
    public void mouseDragged(MouseEvent mE) {
        dragXOffset = mE.getX() - beforeDraggedX;
        dragYOffset = mE.getY() - beforeDraggedY;
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mE) {
        xOffset += mE.getX() - beforeDraggedX;
        yOffset += mE.getY() - beforeDraggedY;
        dragXOffset = 0;
        dragYOffset = 0;
        repaint();
    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // zooming
    @Override
    public void mouseWheelMoved(MouseWheelEvent mE) {
        zoom += mE.getPreciseWheelRotation() / 100;
        repaint();
    }

    // hell
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
    public void mouseMoved(MouseEvent mE) {
    }
}