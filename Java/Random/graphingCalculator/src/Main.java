import java.awt.*;

// ! infinite lines with dragging
// ! draw equations

public class Main {

    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int windowWidth = screenSize.width * 2 / 3, windowHeight = screenSize.height * 2 / 3;

    
    public static void main(String[] args) {
        new GUI("Desmos");
    }
}