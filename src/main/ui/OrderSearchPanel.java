package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import model.Order;

public class OrderSearchPanel extends JPanel {
    private final OrderManager orderManager;

    public OrderSearchPanel(OrderManager orderManager) {
        this.orderManager = orderManager;
        setLayout(new BorderLayout());
        add(createSearchComponents(), BorderLayout.CENTER);
    }

    private JPanel createSearchComponents() {
        JPanel searchPanel = new JPanel(new FlowLayout());

        JTextField searchField = new JTextField(20); // Set a preferred size
        JButton searchButton = new JButton("Search");

        searchPanel.add(new JLabel("Order ID:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Add action listener to search field and button
        ActionListener searchAction = e -> searchOrder(searchField.getText());
        searchField.addActionListener(searchAction);
        searchButton.addActionListener(searchAction);

        return searchPanel;
    }

    private void searchOrder(String orderId) {
        Order order = orderManager.searchOrderById(orderId);
        if (order != null) {
            // Display the found order
            // You can update a table model or show a dialog with order details
            JOptionPane.showMessageDialog(this, "Order found: \n" + order,
                    "Order Search", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Order not found.", "Order Search", JOptionPane.ERROR_MESSAGE);
        }
    }
}
