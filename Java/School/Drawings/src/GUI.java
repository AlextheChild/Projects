import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener {
    JPanel sidebar, buttonPanel, artPanel;
    ArtDisplay artDisplay;
    JButton cubeButton, sunsetButton, ringsButton, whaleEmojiButton, circleButton, perspectiveButton;

    Timer t;

    public GUI(String title) {
        // =========== sidebar ========== //
        sidebar = new JPanel();
        sidebar.setBackground(new Color(240, 240, 240));
        sidebar.setLayout(new BorderLayout());

        // buttons
        cubeButton = new JButton("cube");
        cubeButton.addActionListener(this);
        cubeButton.setActionCommand("cube");
        // ! let the user input a shade or random or random every frame for epilepsy
        // ! starts with this on

        sunsetButton = new JButton("sunset");
        sunsetButton.addActionListener(this);
        sunsetButton.setActionCommand("sunset");

        ringsButton = new JButton("rings");
        ringsButton.addActionListener(this);
        ringsButton.setActionCommand("rings");

        whaleEmojiButton = new JButton("emoji");
        whaleEmojiButton.addActionListener(this);
        whaleEmojiButton.setActionCommand("emoji");

        circleButton = new JButton("circle");
        circleButton.addActionListener(this);
        circleButton.setActionCommand("circle");

        perspectiveButton = new JButton("perspective");
        perspectiveButton.addActionListener(this);
        perspectiveButton.setActionCommand("perspective");

        buttonPanel = new JPanel(new GridLayout(0, 1));
        buttonPanel.add(cubeButton);
        buttonPanel.add(sunsetButton);
        buttonPanel.add(ringsButton);
        buttonPanel.add(whaleEmojiButton);
        buttonPanel.add(circleButton);
        buttonPanel.add(perspectiveButton);

        sidebar.add(buttonPanel, BorderLayout.NORTH);
        sidebar.setPreferredSize(new Dimension(100, 728));

        // =========== artDisplay ========== //
        artDisplay = new ArtDisplay();
        artDisplay.setBackground(Color.WHITE);

        // =========== finalization =========== //
        this.add(sidebar, BorderLayout.WEST);
        this.add(artDisplay, BorderLayout.CENTER);
        this.setTitle(title);
        this.setSize(900, 728);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        startTimer();
    }

    public void startTimer() {
        t = new Timer(5, this);
        t.setActionCommand("timerFired");
        t.start();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("timerFired")) {
            artDisplay.repaint();
        }

        if (e.getActionCommand().equals("cube")) {
            artDisplay.mode = Constants.CUBE;
            artDisplay.repaint();
        } else if (e.getActionCommand().equals("sunset")) {
            artDisplay.mode = Constants.SUNSET;
            artDisplay.repaint();
        } else if (e.getActionCommand().equals("rings")) {
            artDisplay.mode = Constants.RINGS;
            artDisplay.repaint();
        } else if (e.getActionCommand().equals("emoji")) {
            artDisplay.mode = Constants.EMOJI;
            artDisplay.repaint();
        } else if (e.getActionCommand().equals("circle")) {
            artDisplay.mode = Constants.CIRCLE;
            artDisplay.repaint();
        } else if (e.getActionCommand().equals("perspective")) {
            artDisplay.mode = Constants.PERSPECTIVE;
            artDisplay.repaint();
        }
    }
}