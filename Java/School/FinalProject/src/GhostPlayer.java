import java.awt.*;

public class GhostPlayer {
    public void display(Graphics g) {
        // drawing
        g.setColor(new Color(0, 0, 150));
        g.drawRect(Main.mainFrame.mainPanel.player.onPanelX * 30, Main.mainFrame.mainPanel.player.onPanelY * 30, 30,
                30);
    }
}