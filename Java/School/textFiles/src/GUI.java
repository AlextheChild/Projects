import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame implements ActionListener {
    JPanel actionPanel, aPanel, viewingPanel;
    JScrollPane textScrolling;
    JLabel choose, word, reps, text;
    JButton selectionButton, reverseButton, sumDigitsButton, egoMeasureButton, findWordButton, wEnterButton,
            alliterationButton, aEnterButton, clearFilesButton;
    Font font = new Font("Verdana", Font.PLAIN, 11);
    JTextField wordGetter, repGetter;

    File original;
    ArrayList<File> files = new ArrayList<File>();
    boolean fileChosen;

    FileChanger filechanger = new FileChanger();
    Methods methods = new Methods();

    public GUI(String title) {
        Color gray = new Color(240, 240, 240);

        // text selection panel
        actionPanel = new JPanel();
        actionPanel.setBackground(gray);
        actionPanel.setSize(200, 900);
        actionPanel.setLayout(new BorderLayout());

        // selection button
        selectionButton = new JButton("select a file");
        selectionButton.addActionListener(this);
        selectionButton.setActionCommand("select");

        // reverse button
        reverseButton = new JButton("reverse");
        reverseButton.addActionListener(this);
        reverseButton.setActionCommand("reverse");
        reverseButton.setVisible(false);

        // sumDigits button
        sumDigitsButton = new JButton("sumDigits");
        sumDigitsButton.addActionListener(this);
        sumDigitsButton.setActionCommand("sumDigits");
        sumDigitsButton.setVisible(false);

        // egoMeasure button
        egoMeasureButton = new JButton("egoMeasure");
        egoMeasureButton.addActionListener(this);
        egoMeasureButton.setActionCommand("egoMeasure");
        egoMeasureButton.setVisible(false);

        // findWord button
        findWordButton = new JButton("findWord");
        findWordButton.addActionListener(this);
        findWordButton.setActionCommand("findWord");
        findWordButton.setVisible(false);
        word = new JLabel("<html>Enter the word <br> you'd like to find: </html>");
        word.setForeground(Color.GRAY);
        word.setFont(font);
        word.setVisible(false);
        wordGetter = new JTextField("", 11);
        wordGetter.setFont(font);
        wordGetter.setVisible(false);
        wEnterButton = new JButton("Enter");
        wEnterButton.addActionListener(this);
        wEnterButton.setActionCommand("wEnter");
        wEnterButton.setVisible(false);

        // alliteration button
        alliterationButton = new JButton("alliterate");
        alliterationButton.addActionListener(this);
        alliterationButton.setActionCommand("alliterate");
        reps = new JLabel("<html>How many alliterations <br> would you like? </html>");
        reps.setForeground(Color.GRAY);
        reps.setFont(font);
        reps.setVisible(false);
        repGetter = new JTextField("", 11);
        repGetter.setFont(font);
        repGetter.setVisible(false);
        aEnterButton = new JButton("Enter");
        aEnterButton.addActionListener(this);
        aEnterButton.setActionCommand("aEnter");
        aEnterButton.setVisible(false);

        // clear button
        clearFilesButton = new JButton("clearFiles");
        clearFilesButton.addActionListener(this);
        clearFilesButton.setActionCommand("clear");

        // put stuff on panels
        aPanel = new JPanel(new GridLayout(0, 1));
        aPanel.add(selectionButton);
        aPanel.add(new JPanel());
        choose = new JLabel("<html>    Choose an action <br> to perform: </html>");
        choose.setForeground(Color.GRAY);
        choose.setFont(font);
        choose.setVisible(false);
        aPanel.add(choose);
        aPanel.add(reverseButton);
        aPanel.add(sumDigitsButton);
        aPanel.add(egoMeasureButton);
        aPanel.add(findWordButton);
        aPanel.add(word);
        aPanel.add(wordGetter);
        aPanel.add(wEnterButton);
        aPanel.add(alliterationButton);
        aPanel.add(reps);
        aPanel.add(repGetter);
        aPanel.add(aEnterButton);
        aPanel.add(clearFilesButton);
        actionPanel.add(aPanel, BorderLayout.NORTH);

        // file viewing panel
        viewingPanel = new JPanel();
        viewingPanel.setBackground(Color.WHITE);
        viewingPanel.setSize(500, 900);
        viewingPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // text
        text = new JLabel("Changed file's text will appear here.", SwingConstants.LEFT);
        text.setForeground(Color.BLACK);
        text.setFont(font);
        viewingPanel.add(text);

        // scrolling
        textScrolling = new JScrollPane(viewingPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        textScrolling.setBorder(BorderFactory.createEmptyBorder());

        // final setup
        this.add(actionPanel, BorderLayout.WEST);
        this.add(textScrolling, BorderLayout.CENTER);
        this.setTitle(title);
        this.setSize(700, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("select")) {
            original = FileChooser.getFileObject();
            choose.setVisible(true);
            reverseButton.setVisible(true);
            sumDigitsButton.setVisible(true);
            egoMeasureButton.setVisible(true);
            findWordButton.setVisible(true);
            fileChosen = true;
        }

        try {
            // must choose a file before performing actions with it
            if (fileChosen) {
                if (e.getActionCommand().equals("reverse")) {
                    filechanger.reverseSentence(original);
                    text.setText(filechanger.getTextText(files));
                } else if (e.getActionCommand().equals("sumDigits")) {
                    filechanger.sumDigits(original);
                    text.setText(filechanger.getTextText(files));
                } else if (e.getActionCommand().equals("egoMeasure")) {
                    filechanger.measureEgo(original);
                    text.setText(filechanger.getTextText(files));
                } else if (e.getActionCommand().equals("findWord")) {
                    word.setVisible(true);
                    wordGetter.setVisible(true);
                    wEnterButton.setVisible(true);
                } else if (e.getActionCommand().equals("wEnter")) {
                    filechanger.findWord(wordGetter.getText(), original);
                    text.setText(filechanger.getTextText(files));
                }
            }
            if (e.getActionCommand().equals("alliterate")) {
                reps.setVisible(true);
                repGetter.setVisible(true);
                aEnterButton.setVisible(true);
            } else if (e.getActionCommand().equals("clear")) {
                for (File f : files) {
                    f.delete();
                }
            }
        } catch (FileNotFoundException e1) {
            text.setText("Please select a file first. ");
        }
        if (e.getActionCommand().equals("aEnter")) {
            try {
                try {
                    methods.alliterate(Integer.parseInt(repGetter.getText()),
                            new File("src/text_files/names.txt"),
                            new File("src/text_files/adjectives.txt"), new File("src/text_files/nouns.txt"));
                    text.setText(methods.getAlliteratedText());
                } catch (NumberFormatException n) {
                    text.setText(
                            "That's not a number you nimrod dimwit numbskull idiot I hate you I hate you I hate you I hate you I hate you");
                }
            } catch (FileNotFoundException e1) {
                // !
                text.setText(
                        "lol your computer doesn't have my exact files and I'm too lazy to implement a fileChooser at the moment");
            }

        }
    }
}