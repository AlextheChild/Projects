import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class Uneditable extends JTextArea implements KeyListener {
    public Uneditable() {
        super();
        this.setEditable(false);
        this.setPreferredSize(new Dimension(288, 588));
        this.setFont(new Font("Verdana", Font.PLAIN, 14));
        this.setForeground(new Color(50, 50, 50));
        this.setBackground(new Color(240, 240, 240));
        this.addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent kE) {
        if (kE.getKeyChar() == 'n') {
            updateGUI();
        }
        if (kE.getKeyChar() == 'p') {

            System.exit(0);
        }
    }

    public void updateGUI() {
        Methods m = new Methods();

        // keysLeft text
        if (Main.allKeysLeft.size() == 0) {
            new Methods().reset(Main.allKeysLeft, Main.GUI.level.getText());
        }
        Main.GUI.keysLeft.setText("");
        for (int i = 0; i < Main.allKeysLeft.size(); i++) {
            if (Main.allKeysLeft.get(i).size() == 0) {
                new Methods().reset(Main.allKeysLeft, Main.GUI.level.getText());
            }
            for (int q = 0; q < Main.allKeysLeft.get(i).size(); q++) {
                Main.GUI.keysLeft.setText(Main.GUI.keysLeft.getText() + Main.allKeysLeft.get(i).get(q) + ", ");
            }
            Main.GUI.keysLeft
                    .setText(Main.GUI.keysLeft.getText().substring(0, Main.GUI.keysLeft.getText().length() - 2) + "\n");
        }

        m.getRandomKeys();
        try {
            m.createTable();
            getTable();
        } catch (FileNotFoundException ex) {
        }
    }

    public void getTable() throws FileNotFoundException {
        this.setText("");
        Scanner tableScanner = new Scanner(new File("src/table.txt"));
        String line;
        while (tableScanner.hasNext()) {
            line = tableScanner.nextLine();
            this.setText(this.getText() + line + "\n");
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }
}