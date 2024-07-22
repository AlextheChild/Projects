package Start;

import Main.*;
import Game.*;
import Inventory.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class StartFrame extends JFrame implements ActionListener {
    JPanel startPanel;
    JLabel gameText;
    JButton startButton, tutorialButton;

    public StartFrame() {
        startPanel = new JPanel(new GridBagLayout());
        startPanel.setPreferredSize(new Dimension(900, 600));
        startPanel.setBackground(Color.BLACK);
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0;
        c.weighty = 0.5;

        gameText = new JLabel("Game", SwingConstants.CENTER);
        gameText.setForeground(Color.WHITE);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        c.insets = new Insets(100, 0, 0, 0);
        startPanel.add(gameText, c);

        startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(200, 100));
        startButton.addActionListener(this);
        startButton.setActionCommand("start");
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(200, 350, 0, 350);
        startPanel.add(startButton, c);

        tutorialButton = new JButton("Tutorial");
        tutorialButton.setPreferredSize(new Dimension(80, 80));
        tutorialButton.addActionListener(this);
        tutorialButton.setActionCommand("tutorial");
        c.gridx = 1;
        c.gridy = 2;
        c.insets = new Insets(0, 350, 0, 0);
        c.anchor = GridBagConstraints.LAST_LINE_END;
        startPanel.add(tutorialButton, c);

        this.add(startPanel);

        this.setUndecorated(true);
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("start")) {
            Main.gameFrame = new GameFrame();
            Main.inventoryFrame = new InventoryFrame();
            this.setVisible(false);
        }
        if (e.getActionCommand().equals("tutorial")) {
            try {
                new TutorialFrame();
            } catch (IOException iE) {
                System.out.println("Problem with I/O");
            }
        }
    }
}