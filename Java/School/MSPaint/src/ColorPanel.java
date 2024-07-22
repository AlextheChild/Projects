import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class ColorPanel extends JPanel implements ActionListener {
    Square square;
    Bar bar;
    JPanel preview, rgb, selectedColorPanel, sc;
    JLabel red, green, blue;
    Color selectedColor = Color.BLACK;

    Timer t;

    public ColorPanel() {
        this.setBackground(new Color(240, 240, 240));
        this.setPreferredSize(new Dimension(240, 488));
        this.setLayout(new BorderLayout());

        bar = new Bar();
        square = new Square(bar.hue);
        preview = new JPanel(new BorderLayout());

        // ! JTextField
        rgb = new JPanel(new GridLayout(0, 1));
        red = new JLabel();
        green = new JLabel();
        blue = new JLabel();

        selectedColorPanel = new JPanel();
        sc = new JPanel();
        sc.setPreferredSize(new Dimension(40, 40));
        sc.setBackground(Color.BLACK);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        sc.setBorder(border);

        rgb.add(red);
        rgb.add(green);
        rgb.add(blue);
        preview.add(rgb);
        selectedColorPanel.add(sc);
        preview.add(selectedColorPanel, BorderLayout.EAST);

        this.add(square, BorderLayout.NORTH);
        this.add(bar, BorderLayout.CENTER);
        this.add(preview, BorderLayout.SOUTH);
        t = new Timer(1, this);
        t.setActionCommand("timerFired");
        t.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("timerFired")) {
            square.update(bar.hue);
            square.repaint();
            selectedColor = square.getSelectedColor();
            updatePreview();
        }
    }

    public void updatePreview() {
        try {
            red.setText("R: " + selectedColor.getRed());
            green.setText("G: " + selectedColor.getGreen());
            blue.setText("B: " + selectedColor.getBlue());
        } catch (NullPointerException e) {
            green.setText("No color selected. ");
        }

        sc.setBackground(selectedColor);
    }
}