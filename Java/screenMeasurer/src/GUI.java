import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    MainPanel panel;

    public GUI() {
        panel = new MainPanel();

        // final setup
        this.add(panel, BorderLayout.CENTER);
        this.setSize(1440, 900);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.setBackground(new Color(0, 0, 0, 80));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}