import java.awt.*;
import java.io.*;

// ! level design

// ! simplify mainpanel
// ! movingobject teleportation

public class Main {
    public static Dimension screenSize = new Dimension(1440, 900);
    public static Dimension frameSize = new Dimension(720, 450);
    public static int frameX = (screenSize.width - frameSize.width) / 2,
            frameY = (screenSize.height - frameSize.height) / 2 + 15;

    public static MainFrame mainFrame;
    public static DragFrame dragFrame;
    public static SideFrame sideFrame;

    public static File level = new File("src/Levels/level0.txt");

    public static void main(String[] args) throws Exception {
        mainFrame = new MainFrame();
        dragFrame = new DragFrame();
        sideFrame = new SideFrame();

        mainFrame.toFront();
        try {
            Main.mainFrame.mainPanel.updateLevel();
        } catch (FileNotFoundException e1) {
        }
    }
}