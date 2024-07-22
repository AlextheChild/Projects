package Inventory;

import Main.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class InventoryItemPanel extends JPanel implements ActionListener, KeyListener {
    String[] itemNames = { "bullet damage", "bomb", "black hole shield"};
    int[] itemCosts = { 0, 0, 0 };
    JButton[] itemButtons = new JButton[itemNames.length];

    public InventoryItemPanel() {
        this.setPreferredSize(new Dimension(400, 300));
        this.setBackground(new Color(255, 0, 0));
        this.setLayout(new GridLayout(0, 2));

        for (int i = 0; i < itemButtons.length; i++) {
            itemButtons[i] = new JButton(itemNames[i] + ", cost " + itemCosts[i]);
            itemButtons[i].addActionListener(this);
            itemButtons[i].setActionCommand(itemNames[i]);
            this.add(itemButtons[i]);
        }

        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int playerMoney = Main.gameFrame.gamePanel.player.money;

        for (int i = 0; i < itemButtons.length; i++) {
            if (e.getActionCommand().equals(itemNames[i])) {
                if(playerMoney >= itemCosts[i]){
                    buy(itemNames[i]);
                }
            }
        }

        this.requestFocusInWindow();
    }

    public void buy(String itemName){
        if(itemName.equals("bullet damage")){
            Main.gameFrame.gamePanel.bulletDamage++;
            Main.inventoryFrame.inventoryInfoPanel.bulletDamageLabel.setText("bullet damage: " + Main.gameFrame.gamePanel.bulletDamage);
        }
        if(itemName.equals("bomb")){
            Main.gameFrame.gamePanel.numBombs++;
            Main.inventoryFrame.inventoryInfoPanel.numBombsLabel.setText("num bombs: " + Main.gameFrame.gamePanel.numBombs);
        }
        else if(itemName.equals("black hole shield")){

        }
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