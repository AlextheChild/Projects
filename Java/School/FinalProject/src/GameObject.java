import java.awt.*;

public class GameObject {
    int realX, realY;
    int frameX, frameY;
    int onPanelX, onPanelY;

    int width = 30, height = 30;
    Color c;
    Image icon;

    boolean isPlayer;

    public GameObject(int x, int y) {
        realX = x;
        realY = y;
        frameX = Main.frameX;
        frameY = Main.frameY;
    }

    public void display(Graphics g) {
        // calculate position on board
        onPanelX = realX - frameX / 30;
        onPanelY = realY - frameY / 30;

        // drawing
        if (icon == null) {
            if (c == null) {
                c = Color.BLUE;
            }
            g.setColor(c);
            g.drawRect((int) (onPanelX * 30), (int) (onPanelY * 30), width, height);
        } else {
            g.drawImage(icon, (int) (onPanelX * 30), (int) (onPanelY * 30), null);
        }
    }

    public void setLocation(int x, int y) {
        realX = x;
        realY = y;
    }

    // updates location based on onPanel position
    public void updatePos() {
        realX = frameX / 30 + onPanelX;
        realY = frameY / 30 + onPanelY;
    }

    public void correctOutOfBounds() {
        // out of bounds
        if (realX < 0) {
            realX = 0;
        }
        if (realX > 47) {
            realX = 47;
        }
        if (realY < 0) {
            realY = 0;
        }
        if (realY > 29) {
            realY = 29;
        }
    }

    // prevents the player from going into blocks
    public void kick() {
        Player player = Main.mainFrame.mainPanel.player;

        // so that player doesn't kick itself
        if (isPlayer) {
            return;
        }

        if (this.realX == player.realX && this.realY == player.realY) {
            // remove the bad location
            Main.mainFrame.previousLocs.remove(Main.mainFrame.previousLocs.size() - 1);

            int[] previousLoc = Main.mainFrame.previousLocs.get(Main.mainFrame.previousLocs.size() - 1);

            // updating framePos
            Main.mainFrame.setLocation(previousLoc[0], previousLoc[1]);
            Main.mainFrame.updatePos(Main.mainFrame.getX(), Main.mainFrame.getY());
            Main.dragFrame.setLocation(previousLoc[0], previousLoc[1]);

            // updating playerPos
            player.updatePos();
        }
    }
}