import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener {
    JPanel sidebar, buttonPanel;
    JButton addButtonButton;
    JTextField buttonName;
    JScrollPane textPanel;
    JEditorPane text;
    Font font = new Font("Verdana", Font.PLAIN, 11);

    Actions actions = new Actions();
    ArrayList<String> code = new ArrayList<String>();
    int timesAdded = 0;

    public GUI(String title) {
        // ========== sidebar ========= //

        sidebar = new JPanel();
        sidebar.setBackground(new Color(240, 240, 240));
        sidebar.setPreferredSize(new Dimension(100, 728));
        sidebar.setLayout(new BorderLayout());

        // formatting
        buttonPanel = new JPanel(new GridLayout(0, 1));
        sidebar.add(buttonPanel, BorderLayout.NORTH);

        // --------- buttons --------- //

        addButtonButton = new JButton("addButton");
        addButtonButton.addActionListener(this);
        addButtonButton.setActionCommand("addButton");
        buttonPanel.add(addButtonButton);
        buttonName = new JTextField("");
        buttonPanel.add(buttonName);

        // ========== code text panel ========= //

        // text on panel
        text = new JEditorPane();
        text.setPreferredSize(new Dimension(800, 728));
        text.setForeground(Color.BLACK);
        text.setFont(font);

        // scrolling
        textPanel = new JScrollPane(text, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        textPanel.setPreferredSize(new Dimension(800, 728));
        textPanel.setBorder(BorderFactory.createEmptyBorder());

        // ========== final setup ========= //

        this.add(sidebar, BorderLayout.WEST);
        this.add(textPanel, BorderLayout.CENTER);
        this.setTitle(title);
        this.setSize(900, 728);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        try {
            actions.setup(code);
            text.setText(actions.getTextText());
        } catch (FileNotFoundException e1) {
            text.setText("uh oh");
        }
    }

    // actionPerformed
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getActionCommand().equals("addButton")) {
                actions.addButton(code, buttonName.getText(), timesAdded);
                text.setText(actions.getTextText());
                timesAdded++;
            }
        } catch (FileNotFoundException e1) {
            text.setText("uh oh");
        }
    }
}