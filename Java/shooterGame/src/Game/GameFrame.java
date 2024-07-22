package Game;

import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {
    public GamePanel gamePanel;

    public GameFrame() {
        gamePanel = new GamePanel();

        this.add(gamePanel, BorderLayout.CENTER);
        this.setUndecorated(true);
        this.setSize(900, 600);
        this.setOpacity(0.8f);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}