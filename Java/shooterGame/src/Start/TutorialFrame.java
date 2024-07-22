package Start;

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;

public class TutorialFrame extends JFrame implements KeyListener {
    JPanel tutorialPanel, imagePanel;
    Uneditable textArea;

    int numPages = 2;
    String[] text = new String[numPages];
    ArrayList<ArrayList<ImageIcon>> images = new ArrayList<ArrayList<ImageIcon>>();
    int currentPage = 0;

    public TutorialFrame() throws IOException {
        fillImageArray();
        fillTextArray();

        tutorialPanel = new JPanel(new BorderLayout());
        imagePanel = new JPanel();
        imagePanel.setPreferredSize(new Dimension(100, 500));
        imagePanel.setLayout(new GridLayout(5, 1));
        imagePanel.setBackground(Color.BLACK);
        textArea = new Uneditable();

        this.add(tutorialPanel);

        tutorialPanel.add(imagePanel, BorderLayout.WEST);
        tutorialPanel.add(textArea, BorderLayout.CENTER);

        this.setBackground(new Color(0, 0, 0));
        this.setUndecorated(true);
        this.setSize(450, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.setFocusable(true);
        this.addKeyListener(this);

        nextPage(1);
    }

    public void fillImageArray() throws IOException {
        // initialize the internal arraylists
        for (int i = 0; i < numPages; i++) {
            images.add(new ArrayList<ImageIcon>());
        }

        // read all the images from the folder and put them into the arrayList
        File allPageImages = new File("src/Start/TutorialImages");
        int page = 0;
        for (File pageImages : allPageImages.listFiles()) {
            // loops through all the images in each page folder
            for (File image : pageImages.listFiles()) {
                images.get(page)
                        .add(new ImageIcon(ImageIO.read(image).getScaledInstance(100, 100, Image.SCALE_DEFAULT)));

            }
            page++;
        }
    }

    public void fillTextArray() throws IOException {
        Scanner s = new Scanner(new File("src/Start/TutorialPages.txt"));

        for (int i = 0; i < numPages; i++) {
            String wholeText = "";
            boolean hyphenReached = false;
            while (!hyphenReached && s.hasNextLine()) {
                String line = s.nextLine();
                if (line.equals("-")) {
                    hyphenReached = true;
                }

                wholeText += line + "\n";
            }
            text[i] = wholeText;
        }

        s.close();
    }

    public void nextPage(int direction) {
        currentPage += direction;
        if (currentPage > numPages || currentPage < 0) {
            this.dispose();
            return;
        }

        // update image
        imagePanel.removeAll();
        for (ImageIcon image : images.get(currentPage - 1)) {
            imagePanel.add(new JLabel(image));

        }
        imagePanel.revalidate();
        imagePanel.repaint();

        // update text
        textArea.setText(text[currentPage - 1]);
    }

    @Override
    public void keyPressed(KeyEvent kE) {
        if (kE.getKeyCode() == KeyEvent.VK_LEFT) {
            nextPage(-1);
        }
        if (kE.getKeyCode() == KeyEvent.VK_RIGHT) {
            nextPage(1);
        }
    }

    @Override
    public void keyReleased(KeyEvent kE) {
    }

    @Override
    public void keyTyped(KeyEvent kE) {
    }
}