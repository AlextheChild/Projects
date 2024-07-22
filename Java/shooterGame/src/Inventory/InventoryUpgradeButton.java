package Inventory;

import Main.*;

import javax.swing.*;

public class InventoryUpgradeButton extends JButton {
    String upgradeName;
    int cost;
    boolean purchased = false;
    boolean upgradeActivated = false;

    public InventoryUpgradeButton(String upgradeName, int cost) {
        this.upgradeName = upgradeName;
        this.cost = cost;
        this.setText(upgradeName + ", cost " + cost + " (not purchased)");
        this.setActionCommand(upgradeName);
    }

    public void clicked(int playerMoney) {
        if (!purchased) {
            if (playerMoney >= cost) {
                purchased = true;
                Main.gameFrame.gamePanel.player.money -= cost;
                Main.inventoryFrame.inventoryInfoPanel.moneyLabel.setText("money: " + Main.gameFrame.gamePanel.player.money);
                this.setText(upgradeName + ", cost " + cost + " (purchased, activated)");
                upgradeActivated = true;
                Main.gameFrame.gamePanel.upgrades.put(upgradeName, upgradeActivated);
            }
        }
        // toggling
        else{
            upgradeActivated = !upgradeActivated;
            Main.gameFrame.gamePanel.upgrades.put(upgradeName, upgradeActivated);

            String activation = upgradeActivated ? "activated" : "unactivated";
            this.setText(upgradeName + ", cost " + cost + " (purchased, " + activation + ")");
        }
    }
}