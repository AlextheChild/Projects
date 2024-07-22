import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class SidePanel extends JPanel implements ActionListener {
    JButton button;
    Image one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, oneD, twoD, threeD, fourD, fiveD,
            sixD, sevenD, eightD, nineD, tenD, elevenD, twelveD;
    ImageIcon oneIcon, twoIcon, threeIcon, fourIcon, fiveIcon, sixIcon, sevenIcon, eightIcon, nineIcon, tenIcon,
            elevenIcon, twelveIcon, oneDIcon, twoDIcon, threeDIcon, fourDIcon, fiveDIcon, sixDIcon, sevenDIcon,
            eightDIcon, nineDIcon, tenDIcon, elevenDIcon, twelveDIcon;

    public SidePanel() {
        this.setBackground(Color.BLACK);
        this.setLayout(new GridLayout(15, 8));
        try {
            one = ImageIO.read(new File("src/LevelIcons/1.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            two = ImageIO.read(new File("src/LevelIcons/2.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            three = ImageIO.read(new File("src/LevelIcons/3.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            four = ImageIO.read(new File("src/LevelIcons/4.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            five = ImageIO.read(new File("src/LevelIcons/5.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            six = ImageIO.read(new File("src/LevelIcons/6.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            seven = ImageIO.read(new File("src/LevelIcons/7.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            eight = ImageIO.read(new File("src/LevelIcons/8.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            nine = ImageIO.read(new File("src/LevelIcons/9.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            ten = ImageIO.read(new File("src/LevelIcons/10.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            eleven = ImageIO.read(new File("src/LevelIcons/11.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            twelve = ImageIO.read(new File("src/LevelIcons/12.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            oneD = ImageIO.read(new File("src/LevelIcons/1d.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            twoD = ImageIO.read(new File("src/LevelIcons/2d.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            threeD = ImageIO.read(new File("src/LevelIcons/3d.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            fourD = ImageIO.read(new File("src/LevelIcons/4d.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            fiveD = ImageIO.read(new File("src/LevelIcons/5d.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            sixD = ImageIO.read(new File("src/LevelIcons/6d.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            sevenD = ImageIO.read(new File("src/LevelIcons/7d.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            eightD = ImageIO.read(new File("src/LevelIcons/8d.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            nineD = ImageIO.read(new File("src/LevelIcons/9d.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            tenD = ImageIO.read(new File("src/LevelIcons/10d.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            elevenD = ImageIO.read(new File("src/LevelIcons/11d.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
            twelveD = ImageIO.read(new File("src/LevelIcons/12d.png")).getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        } catch (IOException e) {
        }

        oneIcon = new ImageIcon(one);
        twoIcon = new ImageIcon(two);
        threeIcon = new ImageIcon(three);
        fourIcon = new ImageIcon(four);
        fiveIcon = new ImageIcon(five);
        sixIcon = new ImageIcon(six);
        sevenIcon = new ImageIcon(seven);
        eightIcon = new ImageIcon(eight);
        nineIcon = new ImageIcon(nine);
        tenIcon = new ImageIcon(ten);
        elevenIcon = new ImageIcon(eleven);
        twelveIcon = new ImageIcon(twelve);

        oneDIcon = new ImageIcon(oneD);
        twoDIcon = new ImageIcon(twoD);
        threeDIcon = new ImageIcon(threeD);
        fourDIcon = new ImageIcon(fourD);
        fiveDIcon = new ImageIcon(fiveD);
        sixDIcon = new ImageIcon(sixD);
        sevenDIcon = new ImageIcon(sevenD);
        eightDIcon = new ImageIcon(eightD);
        nineDIcon = new ImageIcon(nineD);
        tenDIcon = new ImageIcon(tenD);
        elevenDIcon = new ImageIcon(elevenD);
        twelveDIcon = new ImageIcon(twelveD);

        for (int i = 0; i < 41; i++) {
            JPanel fillerPanel = new JPanel();
            fillerPanel.setBackground(new Color(0, 0, 0, 0));
            this.add(fillerPanel);
        }

        for (int i = 41; i < 47; i++) {
            button = new JButton("" + (i - 40));
            button.setActionCommand("button" + (i - 40));
            button.addActionListener(this);
            button.setBorderPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(0, 17, 0, 0));
            if (i - 40 == 1) {
                button.setDisabledIcon(oneIcon);
                button.setIcon(oneIcon);
                button.setPressedIcon(oneDIcon);
            }
            if (i - 40 == 2) {
                button.setDisabledIcon(twoIcon);
                button.setIcon(twoIcon);
                button.setPressedIcon(twoDIcon);
            }
            if (i - 40 == 3) {
                button.setDisabledIcon(threeIcon);
                button.setIcon(threeIcon);
                button.setPressedIcon(threeDIcon);
            }
            if (i - 40 == 4) {
                button.setDisabledIcon(fourIcon);
                button.setIcon(fourIcon);
                button.setPressedIcon(fourDIcon);
            }
            if (i - 40 == 5) {
                button.setDisabledIcon(fiveIcon);
                button.setIcon(fiveIcon);
                button.setPressedIcon(fiveDIcon);
            }
            if (i - 40 == 6) {
                button.setDisabledIcon(sixIcon);
                button.setIcon(sixIcon);
                button.setPressedIcon(sixDIcon);
            }

            this.add(button);
        }

        for (int i = 47; i < 49; i++) {
            JPanel fillerPanel = new JPanel();
            fillerPanel.setBackground(new Color(0, 0, 0, 0));
            this.add(fillerPanel);
        }

        for (int i = 49; i < 55; i++) {
            button = new JButton("" + (i - 42));
            button.setActionCommand("button" + (i - 42));
            button.addActionListener(this);
            button.setBorderPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(0, 17, 0, 0));
            if (i - 42 == 7) {
                button.setDisabledIcon(sevenIcon);
                button.setIcon(sevenIcon);
                button.setPressedIcon(sevenDIcon);
            }
            if (i - 42 == 8) {
                button.setDisabledIcon(eightIcon);
                button.setIcon(eightIcon);
                button.setPressedIcon(eightDIcon);
            }
            if (i - 42 == 9) {
                button.setDisabledIcon(nineIcon);
                button.setIcon(nineIcon);
                button.setPressedIcon(nineDIcon);
            }
            if (i - 42 == 10) {
                button.setDisabledIcon(tenIcon);
                button.setIcon(tenIcon);
                button.setPressedIcon(tenDIcon);
            }
            if (i - 42 == 11) {
                button.setDisabledIcon(elevenIcon);
                button.setIcon(elevenIcon);
                button.setPressedIcon(elevenDIcon);
            }
            if (i - 42 == 12) {
                button.setDisabledIcon(twelveIcon);
                button.setIcon(twelveIcon);
                button.setPressedIcon(twelveDIcon);
            }
            this.add(button);
        }

        for (int i = 55; i < 120; i++) {
            JPanel fillerPanel = new JPanel();
            fillerPanel.setBackground(new Color(0, 0, 0, 0));
            this.add(fillerPanel);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.WHITE);
        g.drawLine(30, 0, 30, 900);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("button1")) {
            Main.level = new File("src/Levels/level1.txt");
        } else if (e.getActionCommand().equals("button2")) {
            Main.level = new File("src/Levels/level2.txt");
        } else if (e.getActionCommand().equals("button3")) {
            Main.level = new File("src/Levels/level3.txt");
        } else if (e.getActionCommand().equals("button4")) {
            Main.level = new File("src/Levels/level4.txt");
        } else if (e.getActionCommand().equals("button5")) {
            Main.level = new File("src/Levels/level5.txt");
        } else if (e.getActionCommand().equals("button6")) {
            Main.level = new File("src/Levels/level6.txt");
        } else if (e.getActionCommand().equals("button7")) {
            Main.level = new File("src/Levels/level7.txt");
        } else if (e.getActionCommand().equals("button8")) {
            Main.level = new File("src/Levels/level8.txt");
        } else if (e.getActionCommand().equals("button9")) {
            Main.level = new File("src/Levels/level9.txt");
        } else if (e.getActionCommand().equals("button10")) {
            Main.level = new File("src/Levels/level10.txt");
        } else if (e.getActionCommand().equals("button11")) {
            Main.level = new File("src/Levels/level11.txt");
        } else if (e.getActionCommand().equals("button12")) {
            Main.level = new File("src/Levels/level12.txt");
        }

        // change the level
        try {
            Main.mainFrame.mainPanel.updateLevel();
        } catch (FileNotFoundException e1) {
        }

        // reset things
        Main.mainFrame.updatePos(Main.frameX, Main.frameY);
        Main.dragFrame.setLocation(Main.frameX, Main.frameY);
        Player player = Main.mainFrame.mainPanel.player;
        player.realX = 24;
        player.realY = 15;
    }
}