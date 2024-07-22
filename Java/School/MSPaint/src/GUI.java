import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener {
    JPanel leftPanel, actionPanel, toolPanel, buttonPanel, rightPanel, variousPanel;
    ColorPanel colorPanel;
    PaintDisplay paintPanel;
    JButton clearAllButton, undoButton, paintButton, eraserButton, fillButton;
    ImageIcon paint, eraser, fill;

    Timer t;

    public GUI(String title) {
        // ========== leftPanel ========= //

        leftPanel = new JPanel();
        leftPanel.setBackground(new Color(240, 240, 240));
        leftPanel.setPreferredSize(new Dimension(120, 728));
        leftPanel.setLayout(new BorderLayout());

        // --------- actionPanel --------- //

        actionPanel = new JPanel(new GridLayout(0, 1));
        leftPanel.add(actionPanel, BorderLayout.NORTH);

        clearAllButton = new JButton("clear all");
        clearAllButton.addActionListener(this);
        clearAllButton.setActionCommand("clearAll");
        actionPanel.add(clearAllButton);

        undoButton = new JButton("undo");
        undoButton.addActionListener(this);
        undoButton.setActionCommand("undo");
        actionPanel.add(undoButton);

        // --------- toolPanel --------- //

        toolPanel = new JPanel();
        leftPanel.add(toolPanel, BorderLayout.CENTER);

        // buttonPanel
        buttonPanel = new JPanel(new GridLayout(0, 2));
        toolPanel.add(buttonPanel);

        paint = new ImageIcon("src/icons/paint.png");
        eraser = new ImageIcon("src/icons/eraser.png");
        fill = new ImageIcon("src/icons/fill.png");

        paintButton = new JButton("");
        paintButton.addActionListener(this);
        paintButton.setActionCommand("paintButton");
        paintButton.setBorderPainted(false);
        paintButton.setBorder(null);
        paintButton.setMargin(new Insets(0, 0, 0, 0));
        paintButton.setContentAreaFilled(false);
        paintButton.setDisabledIcon(paint);
        paintButton.setIcon(paint);
        paintButton.setRolloverIcon(paint);
        paintButton.setPressedIcon(paint);
        buttonPanel.add(paintButton);

        eraserButton = new JButton("");
        eraserButton.addActionListener(this);
        eraserButton.setActionCommand("eraserButton");
        eraserButton.setBorderPainted(false);
        eraserButton.setBorder(null);
        eraserButton.setMargin(new Insets(0, 0, 0, 0));
        eraserButton.setContentAreaFilled(false);
        eraserButton.setDisabledIcon(eraser);
        eraserButton.setIcon(eraser);
        eraserButton.setRolloverIcon(eraser);
        eraserButton.setPressedIcon(eraser);
        buttonPanel.add(eraserButton);

        fillButton = new JButton("");
        fillButton.addActionListener(this);
        fillButton.setActionCommand("fillButton");
        fillButton.setBorderPainted(false);
        fillButton.setBorder(null);
        fillButton.setMargin(new Insets(0, 0, 0, 0));
        fillButton.setContentAreaFilled(false);
        fillButton.setDisabledIcon(fill);
        fillButton.setIcon(fill);
        fillButton.setRolloverIcon(fill);
        fillButton.setPressedIcon(fill);
        buttonPanel.add(fillButton);

        // ========= art panel ========= //

        paintPanel = new PaintDisplay();
        paintPanel.setPreferredSize(new Dimension(780, 728));
        paintPanel.setBackground(Color.WHITE);

        // ========== rightPanel ========= //

        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(240, 728));
        rightPanel.setLayout(new BorderLayout());

        // --------- colorPanel --------- //

        colorPanel = new ColorPanel();

        rightPanel.add(colorPanel, BorderLayout.CENTER);

        // --------- variousPanel --------- //

        variousPanel = new JPanel();
        variousPanel.setBackground(new Color(220, 220, 220));
        variousPanel.setPreferredSize(new Dimension(240, 240));
        variousPanel.setLayout(new BorderLayout());
        rightPanel.add(variousPanel, BorderLayout.SOUTH);

        // ========== final setup ========= //

        this.add(leftPanel, BorderLayout.WEST);
        this.add(paintPanel, BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.EAST);
        this.setTitle(title);
        this.setSize(1060, 728);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        t = new Timer(1, this);
        t.setActionCommand("timerFired");
        t.start();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("timerFired")) {
            paintPanel.color = colorPanel.selectedColor;
        } else if (e.getActionCommand().equals("clearAll")) {
            paintPanel.clearAll();
        } else if (e.getActionCommand().equals("undo")) {
            paintPanel.mode = Constants.UNDO;
        } else if (e.getActionCommand().equals("paintButton")) {
            paintPanel.mode = Constants.PAINT;
        } else if (e.getActionCommand().equals("eraserButton")) {
            paintPanel.mode = Constants.ERASE;
        } else if (e.getActionCommand().equals("fillButton")) {
            paintPanel.mode = Constants.FILL;
        }
    }
}