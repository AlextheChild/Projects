import java.awt.*;

public class Main {
    static int mode;
    static int width, height;

    /**
     * mode 0: screenshot of whole screen, no save
     * mode 1: user defined screenshot, no save
     * mode 2: user defined screenshot, save
     */

    public static void main(String[] args) throws Exception {
        mode = Integer.parseInt(args[0]);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) screenSize.getWidth();
        height = (int) screenSize.getHeight();

        switch (mode) {
            case 0:
                new Screenshotter(0, 0, width, height, false);
                break;
            case 1:
                new GUI();
                break;
            case 2:
                new GUI();
                break;
        }
    }
}