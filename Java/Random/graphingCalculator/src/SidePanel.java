import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SidePanel extends JPanel implements MouseListener {
    ArrayList<JTextField> labels = new ArrayList<JTextField>();

    public SidePanel() {
        this.setLayout(new GridLayout(20, 0, 0, 0));
        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent mE) {
        JTextField j = new JTextField();
        labels.add(j);
        for (JTextField label : labels) {
            this.add(label);
        }
        this.updateUI();
    }

    @Override
    public void mousePressed(MouseEvent mE) {
    }

    @Override
    public void mouseReleased(MouseEvent mE) {
    }

    @Override
    public void mouseEntered(MouseEvent mE) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }
}
