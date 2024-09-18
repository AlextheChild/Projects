

import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {
    public GamePanel gamePanel;

    public GameFrame() {
        gamePanel = new GamePanel();

        this.add(gamePanel, BorderLayout.CENTER);
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}