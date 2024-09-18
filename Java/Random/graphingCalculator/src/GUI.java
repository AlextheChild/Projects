import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.Timer;

public class GUI extends JFrame implements ActionListener {
    GraphPanel graphPanel;
    SidePanel sidePanel;

    ArrayList<String> Equations = new ArrayList<String>();

    Timer t;

    public GUI(String title) {
        sidePanel = new SidePanel();
        sidePanel.setBackground(new Color(240, 240, 240));
        sidePanel.setPreferredSize(new Dimension(200, 600));

        graphPanel = new GraphPanel();
        graphPanel.setBackground(new Color(255, 255, 255));
        graphPanel.setPreferredSize(new Dimension(800, 600));

        // ========== final setup ========= //
        this.add(sidePanel, BorderLayout.WEST);
        this.add(graphPanel, BorderLayout.CENTER);
        this.setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
        this.setTitle(title);
        this.setSize(1000, 628);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        t = new Timer(1, this);
        t.setActionCommand("timerFired");
        t.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("timerFired")){
            graphPanel.getEquations(sidePanel.labels);
        }
    }
}