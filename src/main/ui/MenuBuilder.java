package ui;

import javax.swing.*;

import static model.Order.getOrderID;


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

        JMenuItem searchItem = new JMenuItem("Find Orders");
        saveItem.addActionListener(e -> orderManager.searchOrderById(getOrderID()));

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(searchItem);

        menuBar.add(fileMenu);
        return menuBar;
    }
}
