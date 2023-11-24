package ui;

import javax.swing.*;


// File Menu Builder to save and load files
public class MenuBuilder {
    private final OrderManager orderManager;

    // REQUIRES: A non-null OrderManager object.
    // EFFECTS: Initializes a MenuBuilder instance with the given OrderManager.
    // MODIFIES: Modifies this MenuBuilder instance by setting its orderManager field.
    public MenuBuilder(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    // EFFECTS: Creates a JMenuBar containing a "File" menu with options to save and load orders.
    // Attaches action listeners to these menu items to save and load orders using the OrderManager.
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
