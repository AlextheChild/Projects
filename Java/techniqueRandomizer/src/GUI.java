import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame implements MouseListener, MouseMotionListener {
    JPanel tablePanel, rightPanel, keysLeftPanel, levelPanel;
    Uneditable text;
    JTextArea keysLeft, level;

    public GUI(String title) {
        tablePanel = new JPanel();
        tablePanel.setPreferredSize(new Dimension(300, 600));
        tablePanel.setBackground(Color.WHITE);
        text = new Uneditable();
        tablePanel.add(text);

        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(600, 600));
        keysLeftPanel = new JPanel();
        keysLeftPanel.setBackground(new Color(150, 150, 150));
        keysLeftPanel.setPreferredSize(new Dimension(600, 400));
        keysLeft = new JTextArea();
        keysLeft.setPreferredSize(new Dimension(588, 388));
        keysLeft.setForeground(new Color(50, 50, 50));
        keysLeft.setBackground(new Color(180, 180, 180));
        keysLeft.setFont(new Font("Verdana", Font.PLAIN, 14));
        keysLeftPanel.add(keysLeft);
        rightPanel.add(keysLeftPanel, BorderLayout.NORTH);
        levelPanel = new JPanel();
        levelPanel.setBackground(new Color(200, 200, 200));
        levelPanel.setPreferredSize(new Dimension(600, 200));
        level = new JTextArea("all");
        level.setPreferredSize(new Dimension(588, 188));
        level.setForeground(new Color(50, 50, 50));
        level.setBackground(new Color(240, 240, 240));
        level.setFont(new Font("Verdana", Font.PLAIN, 14));
        levelPanel.add(level);
        rightPanel.add(levelPanel, BorderLayout.CENTER);

        // final setup
        this.add(tablePanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.CENTER);
        this.setTitle(title);
        this.setUndecorated(true);
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    int beforeDraggedX, beforeDraggedY;

    // dragging
    @Override
    public void mousePressed(MouseEvent mE) {
        beforeDraggedX = mE.getX();
        beforeDraggedY = mE.getY();
    }

    @Override
    public void mouseDragged(MouseEvent mME) {
        int changeX = mME.getX() - beforeDraggedX;
        int changeY = mME.getY() - beforeDraggedY;

        int newX = this.getX() + changeX;
        int newY = this.getY() + changeY;

        //this.setLocation((newX - (newX % 10)), (newY - (newY % 10)));
        this.setLocation(newX, newY);
    }

    // hell
    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mouseReleased(MouseEvent mE) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
    }
}