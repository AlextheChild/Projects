import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

public class Bar extends JPanel implements MouseListener, MouseMotionListener {
    BufferedImage bar;
    Graphics2D g1;
    Color hue = Color.RED;

    public Bar() {
        bar = new BufferedImage(
                200, 20, BufferedImage.TYPE_INT_RGB);
        g1 = bar.createGraphics();

        for (int i = 0; i < 360; i++) {
            g1.setColor(Color.getHSBColor((float) i / 200, 1f, 1f));
            g1.drawLine(i, 0, i, 20);
        }

        this.setPreferredSize(new Dimension(240, 30));
        JLabel ba = new JLabel(new ImageIcon(bar));
        this.add(ba);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setVisible(true);
    }

    public void mouseDragged(MouseEvent e) {
        // ! bad
        // make it go on the edges easier
        if (e.getX() - 20 >= 0 && e.getX() - 20 <= 200) {
            Color mousePixelColor = Color.getHSBColor((e.getX() - 20) / 200f, 1f, 1f);
            hue = mousePixelColor;
            updateCursor(e.getX() - 20);
        }
        repaint();
    }

    public void mousePressed(MouseEvent e) {
        Color mousePixelColor = Color.getHSBColor((e.getX() - 20) / 200f, 1f, 1f);
        hue = mousePixelColor;
        updateCursor(e.getX() - 20);
        repaint();
    }

    // draws the selection bar
    public void updateCursor(int x) {
        g1 = bar.createGraphics();

        for (int i = 0; i < 360; i++) {
            g1.setColor(Color.getHSBColor((float) i / 200, 1f, 1f));
            g1.drawLine(i, 0, i, 20);
        }

        g1.setColor(Color.WHITE);
        g1.drawLine(x, 0, x, 20);
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

}