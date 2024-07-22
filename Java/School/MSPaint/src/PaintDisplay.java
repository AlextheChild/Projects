import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

public class PaintDisplay extends JPanel implements MouseListener, MouseMotionListener {
    // zoom
    int mode;
    Color color = Color.BLACK;
    int mouseX;
    int mouseY;

    boolean paintedLast = false;
    boolean filledLast = false;

    ArrayList<ArrayList<DrawnPoint>> interpolatedPointCoords = new ArrayList<ArrayList<DrawnPoint>>();
    ArrayList<ArrayList<DrawnPoint>> allBaseLineCoords = new ArrayList<ArrayList<DrawnPoint>>();
    ArrayList<DrawnPoint> points;

    ArrayList<ArrayList<Coordinate>> allPolyCoords = new ArrayList<ArrayList<Coordinate>>();
    ArrayList<Coordinate> polyCoords;

    public PaintDisplay() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setBackground(Color.WHITE);
        this.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (mode) {
            case (Constants.PAINT):
                points = new ArrayList<DrawnPoint>();
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        switch (mode) {
            case (Constants.PAINT):
                if (color == null) {
                    color = Color.BLACK;
                }
                DrawnPoint c = new DrawnPoint(mouseX, mouseY, color);
                points.add(c);
                allBaseLineCoords.add(points);
                break;
            case (Constants.ERASE):
                break;
            case (Constants.FILL):
                break;
        }
        repaint();
    }

    public void clearAll() {
        allBaseLineCoords.clear();
        allPolyCoords.clear();
        repaint();
    }

    // !
    public void undo(Graphics g) {
        System.out.println(paintedLast + ", " + filledLast);
        if (paintedLast) {
            allBaseLineCoords.remove(allBaseLineCoords.size() - 1);
        } else if (filledLast) {
            allPolyCoords.remove(allBaseLineCoords.size() - 1);
        }
        repaint();
    }

    public void painting(Graphics g) {
        // renderInterpolatedPoints(g);
        renderBasicLines(g);
    }

    public void renderBasicLines(Graphics g) {
        for (ArrayList<DrawnPoint> l : allBaseLineCoords) {
            for (int i = 0; i < l.size() - 1; i++) {
                g.setColor(l.get(i).pointColor);
                g.drawLine(l.get(i).x, l.get(i).y, l.get(i + 1).x, l.get(i + 1).y);
            }
        }
    }

    public void renderInterpolatedPoints(Graphics g) {
        for (ArrayList<DrawnPoint> l : allBaseLineCoords) {
            for (int i = 0; i < l.size() - 1; i++) {
                int x1 = l.get(i).x;
                int x2 = l.get(i + 1).x;
                int y1 = l.get(i).y;
                int y2 = l.get(i + 1).y;
                int changeX = x1 - x2;
                int changeY = y1 - y2;
                double slope;

                if (changeX == 0) {
                    for (int q = Math.min(y1, y2); q < Math.max(y1, y2); q++) {
                        g.drawLine(x1, q, x1, q);
                    }
                }

                slope = (double) changeY / (double) changeX;
                double b = y1 - slope * x1;

                // make sure it draws more pixels
                if (changeX > changeY) {
                    for (int q = Math.min(x1, x2); q < Math.max(x1, x2); q++) {
                        g.drawLine(q, (int) (slope * q + b), q, (int) (slope * q + b));
                    }
                } else {
                    for (int q = Math.min(y1, y2); q < Math.max(y1, y2); q++) {
                        g.drawLine((int) ((q - b) / slope), q, (int) ((q - b) / slope), q);
                    }
                }
            }
        }
    }

    public void erase(Graphics g) {
        // ! delete the point and make seperate lines
        renderBasicLines(g);
        // checks if the mouse is over any drawn point and deletes it
        int index = 0;
        for (int i = 0; i < allBaseLineCoords.size(); i++) {
            for (int q = 0; q < allBaseLineCoords.get(i).size() - 1; q++) {
                if (Math.pow(allBaseLineCoords.get(i).get(q).x - mouseX, 2)
                        + Math.pow(allBaseLineCoords.get(i).get(q).y - mouseY, 2) <= Math.pow(10, 2)) {
                    allBaseLineCoords.get(i).remove(q);
                    q--;
                    // make new line 1
                    ArrayList<DrawnPoint> line1 = new ArrayList<DrawnPoint>();
                    for (int w = 0; w < w; w++) {
                        // ! is it really l.get(i)
                        line1.add(allBaseLineCoords.get(i).get(w));
                    }
                    i++;
                    allBaseLineCoords.add(index, line1);
                    // make new line 2
                    ArrayList<DrawnPoint> line2 = new ArrayList<DrawnPoint>();
                    for (int w = 0; w < w; w++) {
                        // ! is it really l.get`(i)
                        line1.add(allBaseLineCoords.get(i).get(w));
                    }
                    allBaseLineCoords.add(index, line2);
                    i++;
                }

            }

            index++;
        }
    }

    public void fill(Graphics g) {
        // ! set to the color of where the mouse is clicked
        Color mousePixelColor;
        Color pointPixelColor;

        for (ArrayList<DrawnPoint> l : allBaseLineCoords) {
            for (int i = 0; i < l.size() - 1; i++) {
                g.drawLine(l.get(i).x, l.get(i).y, l.get(i + 1).x, l.get(i + 1).y);
            }
        }

        // add polygon coordinates to allPolyCoords
        ArrayDeque<Coordinate> coords = new ArrayDeque<Coordinate>();
        BufferedImage image;
        try {
            image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            // ! does screensize include the bar
        } catch (HeadlessException | AWTException e) {
            image = null;
            e.printStackTrace();
        }

        // get color of pixel the mouse is on
        int mouseColor = image.getRGB(mouseX + 120, mouseY);
        int mouseRed = (mouseColor & 0x00ff0000) >> 16;
        int mouseGreen = (mouseColor & 0x0000ff00) >> 8;
        int mouseBlue = mouseColor & 0x000000ff;
        mousePixelColor = new Color(mouseRed, mouseGreen, mouseBlue);

        coords.add(new Coordinate(mouseX, mouseY));
        while (!coords.isEmpty()) {
            Coordinate coord = coords.poll();

            int pointColor = image.getRGB(mouseX + 120, mouseY);
            int pointRed = (pointColor & 0x00ff0000) >> 16;
            int pointGreen = (pointColor & 0x0000ff00) >> 8;
            int pointBlue = pointColor & 0x000000ff;
            pointPixelColor = new Color(pointRed, pointGreen, pointBlue);

            polyCoords = new ArrayList<Coordinate>();
            if (!pointPixelColor.equals(mousePixelColor)) {
                polyCoords.add(new Coordinate(coord.x, coord.y));
            } else {
                coords.add(new Coordinate(coord.x - 1, coord.y));
                coords.add(new Coordinate(coord.x + 1, coord.y));
                coords.add(new Coordinate(coord.x, coord.y - 1));
                coords.add(new Coordinate(coord.x, coord.y + 1));
            }
            allPolyCoords.add(polyCoords);
        }

        // draw the polygon
        ArrayList<Integer> xsArrayList = new ArrayList<Integer>();
        ArrayList<Integer> ysArrayList = new ArrayList<Integer>();

        int[] xsArray, ysArray;

        // loop through each polygon
        for (ArrayList<Coordinate> polygon : allPolyCoords) {
            // fill separate arraylists with all vertice x coordinates and y coordinates
            for (Coordinate verticeCoord : polygon) {
                xsArrayList.add(verticeCoord.x);
                ysArrayList.add(verticeCoord.y);
            }

            // convert arraylist to array
            xsArray = new int[xsArrayList.size()];
            for (int i = 0; i < xsArrayList.size(); i++) {
                xsArray[i] = xsArrayList.get(i);
            }
            ysArray = new int[ysArrayList.size()];
            for (int i = 0; i < ysArrayList.size(); i++) {
                ysArray[i] = ysArrayList.get(i);
            }

            g.fillPolygon(xsArray, ysArray, polygon.size());

            for (int i = 0; i < xsArray.length; i++) {
                System.out.println(xsArray[i] + ", " + ysArray[i]);
            }

            xsArrayList.clear();
            ysArrayList.clear();
        }
    }

    public void text(Graphics g) {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        switch (mode) {
            case (Constants.UNDO):
                undo(g);
                System.out.println("hi");
                break;
            case (Constants.PAINT):
                painting(g);
                paintedLast = true;
                filledLast = false;
                break;
            case (Constants.ERASE):
                erase(g);
                break;
            case (Constants.FILL):
                fill(g);
                paintedLast = false;
                filledLast = true;
                break;
        }
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