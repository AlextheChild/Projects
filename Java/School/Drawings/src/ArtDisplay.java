import java.awt.*;
import javax.swing.*;

public class ArtDisplay extends JPanel {
    public int mode;
    ArtMethods art = new ArtMethods();

    public ArtDisplay() {
        setPreferredSize(new Dimension(600, 600));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        setBackground(Color.WHITE);
        switch (mode) {
            case (Constants.CUBE):
                art.cube(g);
                break;
            case (Constants.SUNSET):
                art.sunset(g, this);
                break;
            case (Constants.RINGS):
                art.rings(g, 50);
                break;
            case (Constants.EMOJI):
                art.emoji(g);
                break;
            case (Constants.CIRCLE):
                g2.setStroke(new BasicStroke(3));
                art.circle(g);
                break;
            case (Constants.PERSPECTIVE):
                g2.setStroke(new BasicStroke(1));
                art.perspective(g);
                break;
        }
    }
}