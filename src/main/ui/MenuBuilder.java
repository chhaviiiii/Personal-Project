package ui;

import javax.swing.*;


// File Menu Builder to save and load files
public class MenuBuilder {
    private final OrderManager orderManager;

    // Data Persistence
    public MenuBuilder(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    // Adds different components from the Order Manager to the menu bar
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("Save Orders");
        saveItem.addActionListener(e -> orderManager.saveActiveOrders());

        JMenuItem loadItem = new JMenuItem("Load Orders");
        loadItem.addActionListener(e -> orderManager.loadActiveOrders());

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);

        menuBar.add(fileMenu);
        return menuBar;
    }
}
