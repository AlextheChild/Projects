package Inventory;

import Main.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class InventoryUpgradePanel extends JPanel implements ActionListener, KeyListener {
    String[] upgradeNames = Main.gameFrame.gamePanel.upgradeNames;
    int[] costs = { 0, 0, 0, 0, 0, 0, 0 };
    InventoryUpgradeButton[] upgradeButtons = new InventoryUpgradeButton[upgradeNames.length];

    public InventoryUpgradePanel() {
        this.setBackground(new Color(0, 0, 0));
        this.setLayout(new GridLayout(0, 1));

        for (int i = 0; i < upgradeButtons.length; i++) {
            upgradeButtons[i] = new InventoryUpgradeButton(upgradeNames[i], costs[i]);
            upgradeButtons[i].addActionListener(this);
            this.add(upgradeButtons[i]);
        }

        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int playerMoney = Main.gameFrame.gamePanel.player.money;

        for (int i = 0; i < upgradeButtons.length; i++) {
            if (e.getActionCommand().equals(upgradeNames[i])) {
                upgradeButtons[i].clicked(playerMoney);
            }
        }

        this.requestFocusInWindow();
    }

    @Override
    public void keyPressed(KeyEvent kE) {
        if (kE.getKeyCode() == KeyEvent.VK_P) {
            System.exit(0);
        }

        if (kE.getKeyCode() == KeyEvent.VK_E) {
            Main.gameFrame.gamePanel.pause();
            Main.inventoryFrame.setVisible(false);
            Main.gameFrame.gamePanel.time = System.currentTimeMillis();
        }
    }

    @Override
    public void keyTyped(KeyEvent kE) {
    }

    @Override
    public void keyReleased(KeyEvent kE) {
    }
}