import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

public class Square extends JPanel implements MouseListener, MouseMotionListener {
        BufferedImage square;
        Color selectedColor;
        Graphics2D g;

        int mouseX;
        int mouseY;

        public Square(Color hue) {
                square = new BufferedImage(
                                200, 200, BufferedImage.TYPE_INT_RGB);

                JLabel squ = new JLabel(new ImageIcon(square));
                this.add(squ);

                this.addMouseListener(this);
                this.addMouseMotionListener(this);
                this.setVisible(true);
        }

        public void mouseDragged(MouseEvent e) {
                mouseX = e.getX() - 20;
                mouseY = e.getY() - 5;

                // ! hmmmmmm
                try {
                        int mouseColor = square.getRGB(Math.min(Math.max(0, mouseX), 199),
                                        Math.min(Math.max(0, mouseY), 199));
                        int mouseRed = (mouseColor & 0x00ff0000) >> 16;
                        int mouseGreen = (mouseColor & 0x0000ff00) >> 8;
                        int mouseBlue = mouseColor & 0x000000ff;

                        selectedColor = new Color(mouseRed, mouseGreen, mouseBlue);
                } catch (ArrayIndexOutOfBoundsException e1) {

                }

                repaint();
        }

        public void mousePressed(MouseEvent e) {
                mouseX = e.getX() - 20;
                mouseY = e.getY() - 5;
                try {
                        int mouseColor = square.getRGB(Math.min(Math.max(0, mouseX), 199),
                                        Math.min(Math.max(0, mouseY), 199));
                        int mouseRed = (mouseColor & 0x00ff0000) >> 16;
                        int mouseGreen = (mouseColor & 0x0000ff00) >> 8;
                        int mouseBlue = mouseColor & 0x000000ff;

                        selectedColor = new Color(mouseRed, mouseGreen, mouseBlue);
                } catch (ArrayIndexOutOfBoundsException e1) {

                }

                repaint();
        }

        public void update(Color hue) {
                updateHue(hue);
                // ! separate panel for mouse
                // updateCursor(mouseX, mouseY);
        }

        public void updateHue(Color hue) {
                g = square.createGraphics();
                GradientPaint primary = new GradientPaint(0f, 0f, Color.WHITE, 199f, 0f, hue);
                GradientPaint shade = new GradientPaint(0f, 0f, new Color(0, 0, 0, 0), 0f, 199f,
                                new Color(0, 0, 0, 255));
                g.setPaint(primary);
                g.fillRect(0, 0, 200, 200);
                g.setPaint(shade);
                g.fillRect(0, 0, 200, 200);
        }

        public void updateCursor(int x, int y) {
                g = square.createGraphics();
                if (x >= 0 && x <= 199 && y >= 0 && y <= 199) {
                        g.setColor(Color.WHITE);
                        g.drawLine(x - 5, y, x + 5, y);
                        g.drawLine(x, y - 5, x, y + 5);
                }
        }

        public Color getSelectedColor() {
                return selectedColor;
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