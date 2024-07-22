package Inventory;

import java.awt.*;
import javax.swing.*;

public class InventoryInfoPanel extends JPanel {
    public JLabel moneyLabel, killCountLabel, bulletDamageLabel, numBombsLabel;

    public InventoryInfoPanel() {
        this.setBackground(new Color(0, 0, 0));
        this.setLayout(new GridLayout(0,1));
        
        Font font = new Font("Times New Roman", Font.PLAIN, 14);

        moneyLabel = new JLabel();
        moneyLabel.setForeground(Color.WHITE);
        moneyLabel.setFont(font);
        moneyLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        this.add(moneyLabel);

        killCountLabel = new JLabel();
        killCountLabel.setForeground(Color.WHITE);
        killCountLabel.setFont(font);
        killCountLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        this.add(killCountLabel);

        bulletDamageLabel = new JLabel();
        bulletDamageLabel.setForeground(Color.WHITE);
        bulletDamageLabel.setFont(font);
        bulletDamageLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        this.add(bulletDamageLabel);

        numBombsLabel = new JLabel();
        numBombsLabel.setForeground(Color.WHITE);
        numBombsLabel.setFont(font);
        numBombsLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        this.add(numBombsLabel);
    }
}