package Start;

import java.awt.*;
import javax.swing.*;

public class Uneditable extends JTextArea{
    public Uneditable() {
        this.setEditable(false);
        Font font = new Font("Times New Roman", Font.PLAIN, 12);
        this.setFont(font);
        this.setForeground(Color.WHITE);
        this.setBackground(Color.BLACK);
    }
}