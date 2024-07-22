import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class Display extends JPanel {
    ArrayList<Anything> occupants;

    public Display(ArrayList<Anything> occupants) {
        this.occupants = occupants;
        setPreferredSize(new Dimension(600, 600)); // set size of display
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        final int PAD = 45;

        // border rectangle
        g.setColor(Color.BLACK);
        g.drawRect(30, 30, 600, 600);

        // draw occupants
        for (Anything o : occupants) {
            o.draw(g, PAD);
        }
    }
}