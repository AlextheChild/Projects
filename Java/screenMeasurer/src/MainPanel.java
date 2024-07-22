import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.List;

public class MainPanel extends JPanel implements MouseListener, MouseMotionListener {
    final int width = 1440, height = 900;
    final int rightMargin = 70, bottomMargin = 30;
    final String saveFolderPath = "C:/Users/Alex/Desktop/";

    JLabel coordLabel;

    boolean dragging = false;
    int beforeDraggedX, beforeDraggedY;
    int dragOffsetX, dragOffsetY;
    int selectX, selectY, selectW, selectH;

    public MainPanel() {
        coordLabel = new JLabel("", SwingConstants.CENTER);
        coordLabel.setForeground(new Color(255, 255, 255, 200));
        coordLabel.setFont(new Font("Monospace", Font.BOLD, 12));

        this.setLayout(null);
        this.add(coordLabel);
        this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

        this.setOpaque(false);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setFocusable(true);
        this.requestFocus();
        this.requestFocusInWindow();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (dragging) {
            int x1 = beforeDraggedX;
            int y1 = beforeDraggedY;
            int x2 = beforeDraggedX + dragOffsetX;
            int y2 = beforeDraggedY + dragOffsetY;

            int minX = Math.min(x1, x2);
            int minY = Math.min(y1, y2);
            int maxX = Math.max(x1, x2);
            int maxY = Math.max(y1, y2);

            selectX = minX;
            selectY = minY;
            selectW = maxX - minX;
            selectH = maxY - minY;

            g.setColor(new Color(255, 255, 255, 150));
            g.fillRect(selectX, selectY, selectW, selectH);
            g.setColor(new Color(255, 255, 255, 200));
            g.drawRect(selectX, selectY, selectW, selectH);
        }
    }

    // ————— screenshot ————— //

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            dragging = true;
            beforeDraggedX = e.getX();
            beforeDraggedY = e.getY();
        }
        if (e.getButton() == MouseEvent.BUTTON3 && dragging) {
            System.exit(0);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        dragOffsetX = x - beforeDraggedX;
        dragOffsetY = y - beforeDraggedY;

        displayNums(x, y, Math.abs(dragOffsetX), Math.abs(dragOffsetY));
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragging = false;
        repaint();

        try {
            takeScreenshot(selectX, selectY, selectW, selectH);
        } catch (Exception ex) {
            System.out.println("Error taking screenshot");
        }

        System.exit(0);
    }

    public void takeScreenshot(int x, int y, int w, int h) throws AWTException, IOException {
        JFrame GUI = (JFrame) SwingUtilities.getWindowAncestor(this);
        GUI.dispose();

        // take screenshot
        Robot r = new Robot();
        BufferedImage image;
        MultiResolutionImage mrImage = r.createMultiResolutionScreenCapture(new Rectangle(x, y, w, h));
        List<Image> resolutionVariants = mrImage.getResolutionVariants();
        if (resolutionVariants.size() > 1) {
            image = (BufferedImage) resolutionVariants.get(1);
        } else {
            image = (BufferedImage) resolutionVariants.get(0);
        }

        // save
        ImageIO.write(image, "png", new File(saveFolderPath + "screenshot" + System.currentTimeMillis() + ".png"));

        // copy to clipboard
        new ImageCopier(image);
    }

    // ————— coord display ————— //

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        displayNums(x, y, x, y);
    }

    public void displayNums(int x, int y, int num1, int num2) {
        coordLabel.setText(num1 + ", " + num2);

        // marginalization
        if (x > width - rightMargin) {
            x -= rightMargin;
        }
        if (y > height - bottomMargin) {
            y -= bottomMargin;
        }
        coordLabel.setBounds(x, y, 70, 30);
    }

    // ————— exiting ————— //

    @Override
    public void mouseClicked(MouseEvent e) {
        System.exit(0);
    }

    // ————— hell ————— //

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}