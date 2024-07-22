import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;

public class ArtMethods {
    int center = ArtDisplay.HEIGHT / 2;
    int y = 0;
    int blue = 255;
    int tick = 0;

    // cube
    public void cube(Graphics g) {
        int midPointX = 400;
        int midPointY = 350;
        int red = (int) (Math.random() * 200) + 55;
        int green = (int) (Math.random() * 200) + 55;
        int blue = (int) (Math.random() * 200) + 55;

        g.setColor(new Color(red, green, blue));
        g.fillRect(midPointX, midPointY, 100, 100);

        g.setColor(new Color(red - 20, green - 20, blue - 20));
        g.fillPolygon(new int[] { midPointX, midPointX + 100, midPointX + 40, midPointX - 40 },
                new int[] { midPointY, midPointY, midPointY - 40, midPointY - 40 }, 4);

        g.setColor(new Color(red - 40, green - 40, blue - 40));
        g.fillPolygon(new int[] { midPointX, midPointX, midPointX - 40, midPointX - 40 },
                new int[] { midPointY, midPointY + 100, midPointY + 40, midPointY - 40 }, 4);

    }

    // ! only works once
    // sunset scene
    public void sunset(Graphics g, JPanel artDisplay) {
        // sky
        artDisplay.setBackground(new Color(25, 25, blue));
        if (blue > 25) {
            blue--;
        }
        // draw sun
        int x = 400;
        g.setColor(Color.YELLOW);
        g.fillOval(x - 100, y, 200, 200);
        if (y < 700) {
            y += 1;
        }
        // ! I am not making this fully realistic
        // draw ground
        for (int i = 0; i < 10; i++) {
            g.setColor(new Color(70 - i * 6 + (int) (y / 5), 255 - i * 20, 120 - i * 10));
            g.fillRect(0, 600 + i * 10, 800, 10);
        }
    }

    // concentric rings
    public void rings(Graphics g, int numRings) {
        int x = 400;
        int y = 350;
        for (int i = numRings - 1; i >= 0; i--) {
            Color randomColor = new Color((int) (Math.random() * 255), (int) (Math.random() * 255),
                    (int) (Math.random() * 255));
            g.setColor(randomColor);
            int factor = (int) (Math.random() * 10);

            g.fillOval(x - i * factor / 2, y - i * factor / 2, i * factor, i * factor);
        }
    }

    // draws a whale
    public void emoji(Graphics g) {
        try {
            BufferedImage whale = ImageIO.read(new File("src/whale.png"));
            g.drawImage(whale, 240, 190, null);
        } catch (Exception e) {
            System.out.println("bad");
        }
    }

    // circles and stuff
    public void circle(Graphics g) {
        tick++;
        int centerX = 400;
        int centerY = 350;

        // lines
        for (int i = 0; i < 72; i++) {
            int x1 = (int) (100000000 * Math.cos(Math.toRadians(i * 5 + tick / 6)));
            int y1 = (int) (100000000 * Math.sin(Math.toRadians(i * 5 + tick / 6)));
            int x2 = (int) (100000000 * Math.cos(Math.toRadians(i * 5 - tick / 4)));
            int y2 = (int) (100000000 * Math.sin(Math.toRadians(i * 5 - tick / 4)));

            g.setColor(Color.getHSBColor((float) i / 72, 1f, 0.75f));
            g.drawLine(centerX + 2 * x1 / 1000000, centerY + 2 * y1 / 1000000, centerX + 3 * x1 / 1000000,
                    centerY + 3 * y1 / 1000000);
            g.setColor(Color.getHSBColor((float) i / 72, 1f, 1f));
            g.drawLine(centerX + x2 / 2000000, centerY + y2 / 2000000, centerX + x2 / 1000000,
                    centerY + y2 / 1000000);
        }
        // points
        for (int i = 0; i < 72; i++) {
            int x3 = (int) (100000000 * Math.cos(Math.toRadians(i * 5 + tick / 3)));
            int y3 = (int) (100000000 * Math.sin(Math.toRadians(i * 5 + tick / 3)));
            int x4 = (int) (100000000 * Math.cos(Math.toRadians(i * 5 - tick / 3 + 180)));
            int y4 = (int) (100000000 * Math.sin(Math.toRadians(i * 5 - tick / 3 + 180)));

            g.setColor(Color.getHSBColor((float) i / 36, 1f, 1f));
            g.drawLine(centerX + x3 / 400000, centerY + y3 / 400000, centerX + x3 / 400000,
                    centerY + y3 / 400000);

            g.setColor(Color.getHSBColor((float) i / 36, 1f, 1f));
            g.drawLine(centerX + x4 / 400000, centerY + y4 / 400000, centerX + x4 / 400000,
                    centerY + y4 / 400000);
        }
    }

    // ! needs ray-tracing and VFX
    // perspective
    public void perspective(Graphics g) {
        int centerX = 400;
        int centerY = 350;

        // background
        g.setColor(new Color(8, 2, 10));
        g.fillRect(0, 0, 800, 350);
        g.setColor(Color.WHITE);
        for (int i = 0; i < 100; i++) {
            int x = (int) (Math.random() * 800);
            int y = (int) (Math.random() * 350);
            g.fillOval(x, y, 2, 2);
        }
        g.setColor(Color.YELLOW);
        g.fillOval(200, 150, 400, 400);
        g.setColor(Color.getHSBColor((float) 290 / 360, 0.9f, 1f));
        g.fillRect(0, 350, 800, 350);

        g.setColor(Color.WHITE);
        // horizontal
        for (int i = 0; i < 7; i++) {
            g.drawLine(0, 350 + (int) ((7 - i) * 50 / Math.pow(Math.sqrt(2), i)), 800,
                    350 + (int) ((7 - i) * 50 / Math.pow(Math.sqrt(2), i)));
        }

        // left
        for (int i = 0; i < 6; i++) {
            int x1 = -(int) (10000000 * Math.cos(Math.toRadians((5 - i) * 18 / Math.pow(Math.sqrt(2), i))));
            int y1 = (int) (10000000 * Math.sin(Math.toRadians((5 - i) * 18 / Math.pow(Math.sqrt(2), i))));
            g.drawLine(centerX, centerY, centerX + 5 * x1 / 100000, centerY + 5 * y1 / 100000);
        }

        // right
        for (int i = 0; i < 6; i++) {
            int x1 = (int) (10000000 * Math.cos(Math.toRadians((5 - i) * 18 / Math.pow(1.4, i))));
            int y1 = (int) (10000000 * Math.sin(Math.toRadians((5 - i) * 18 / Math.pow(1.4, i))));
            g.drawLine(centerX, centerY, centerX + 5 * x1 / 100000, centerY + 5 * y1 / 100000);
        }
    }
}