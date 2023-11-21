package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import model.Order;


// Panel that allows the user to search for an existing order
public class OrderSearchPanel extends JPanel {
    private final OrderManager orderManager;

    // Search for an order ID and display the found order
    public OrderSearchPanel(OrderManager orderManager) {
        this.orderManager = orderManager;
        setLayout(new BorderLayout());
        add(createSearchComponents(), BorderLayout.CENTER);
    }

    // Elements of the Order Search Panel
    private JPanel createSearchComponents() {
        JPanel searchPanel = new JPanel(new FlowLayout());

        JTextField searchField = new JTextField(20); // Set a preferred size
        JButton searchButton = new JButton("Search");

        searchPanel.add(new JLabel("Order ID:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        ActionListener searchAction = e -> searchOrder(searchField.getText());
        searchField.addActionListener(searchAction);
        searchButton.addActionListener(searchAction);

        return searchPanel;
    }

    // Search for an order ID and display the found order
    private void searchOrder(String orderId) {
        Order order = orderManager.searchOrderById(orderId);
        if (order != null) {


            JOptionPane.showMessageDialog(this, "Order found: \n" + order,
                    "Order Search", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Order not found.", "Order Search", JOptionPane.ERROR_MESSAGE);
        }
    }
}
