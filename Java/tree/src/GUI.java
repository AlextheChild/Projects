import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame implements KeyListener{
    TreePanel panel;

    public GUI(String title){
        panel = new TreePanel();

        this.add(panel, BorderLayout.CENTER);
        this.setBackground(Color.WHITE);
        this.setUndecorated(true);
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle(title);

        this.addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent kE) {
        if(kE.getKeyChar() == 'p'){
            System.exit(0);
        }

        if(kE.getKeyChar() == 'n'){
            panel.newTree();
        }
    }

    @Override
    public void keyReleased(KeyEvent kE) {

    }

    @Override
    public void keyTyped(KeyEvent kE) {
    }
}