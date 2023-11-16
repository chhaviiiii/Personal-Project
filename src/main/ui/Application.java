package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static ui.OrderTracker.JSON_STORE;

public class Application extends JFrame {
    private javax.swing.JFrame jframe;
    private JTable productTable;
    private OrderTableModel orderTableModel;
    private OrderManager orderManager;
    private OrderCreationPanel orderCreationPanel;
    private OrderSearchPanel orderSearchPanel;
    private OrderStatusUpdatePanel orderStatusUpdatePanel;

    public Application() {
        // Initialization
        this.orderTableModel = new OrderTableModel(new ArrayList<>());
        this.orderManager = new OrderManager(JSON_STORE, orderTableModel, jframe);
        this.orderCreationPanel = new OrderCreationPanel(orderManager);
        this.orderSearchPanel = new OrderSearchPanel(orderManager);
        this.orderStatusUpdatePanel = new OrderStatusUpdatePanel(orderManager);

        initializeUI();
        setTitle("Order Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center on screen
    }

    private void initializeUI() {
        // Layout setup
        setJMenuBar(new MenuBuilder(orderManager).createMenuBar());

        // Create the main panel with a border layout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Add the product table to the center
        this.productTable = new JTable(orderTableModel);
        mainPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);

        // Add the order creation panel to the north
        mainPanel.add(orderCreationPanel, BorderLayout.NORTH);

        // Add the order search panel to the west
        mainPanel.add(orderSearchPanel, BorderLayout.SOUTH);

        // Add the order status update panel to the east
        mainPanel.add(orderStatusUpdatePanel, BorderLayout.EAST);

        // Add the main panel to the frame
        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Application().setVisible(true));
    }
}
