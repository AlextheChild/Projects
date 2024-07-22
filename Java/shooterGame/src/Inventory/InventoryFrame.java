package Inventory;

import java.awt.*;
import javax.swing.*;

public class InventoryFrame extends JFrame {
    JPanel inventoryPanel, middlePanel, sidePanel;
    public InventoryInfoPanel inventoryInfoPanel;
    public InventoryItemPanel inventoryItemPanel;
    public InventoryUpgradePanel inventoryUpgradePanel;

    public InventoryFrame() {
        this.setLayout(new GridBagLayout());

        inventoryPanel = new JPanel(new GridBagLayout());
        inventoryPanel.setPreferredSize(new Dimension(900, 600));
        inventoryPanel.setBackground(Color.BLACK);

        middlePanel = new JPanel(new BorderLayout());
        middlePanel.setPreferredSize(new Dimension(800, 500));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        inventoryPanel.add(middlePanel, c);

        inventoryUpgradePanel = new InventoryUpgradePanel();
        middlePanel.add(inventoryUpgradePanel, BorderLayout.CENTER);

        sidePanel = new JPanel(new BorderLayout());
        sidePanel.setPreferredSize(new Dimension(400, 500));

        inventoryInfoPanel = new InventoryInfoPanel();
        sidePanel.add(inventoryInfoPanel, BorderLayout.CENTER);

        inventoryItemPanel = new InventoryItemPanel();
        sidePanel.add(inventoryItemPanel, BorderLayout.SOUTH);

        middlePanel.add(sidePanel, BorderLayout.WEST);

        this.add(inventoryPanel);

        this.setUndecorated(true);
        this.setSize(900, 600);
        this.setOpacity(0.8f);
        this.setLocationRelativeTo(null);
        this.setVisible(false);
    }
}