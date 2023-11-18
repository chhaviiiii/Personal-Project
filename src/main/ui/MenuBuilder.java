package ui;

import javax.swing.*;



public class MenuBuilder {
    private final OrderManager orderManager;

    public MenuBuilder(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

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
