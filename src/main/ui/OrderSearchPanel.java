package ui;

import model.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static model.Order.searchOrderID;


// Panel that allows the user to search for an existing order
public class OrderSearchPanel extends JPanel {
    private final OrderManager orderManager;

    // REQUIRES: A non-null OrderManager object.
    // EFFECTS: Initializes an OrderSearchPanel with the given OrderManager.
    // Sets up the layout and adds search components.
    // MODIFIES: This object (OrderSearchPanel), modifying its layout and components.
    public OrderSearchPanel(OrderManager orderManager) {
        this.orderManager = orderManager;
        setLayout(new BorderLayout());
        add(createSearchComponents(), BorderLayout.CENTER);
    }

    // EFFECTS: Creates and returns a JPanel containing components for order searching, including a label,
    // a text field for inputting the order ID, and a search button.
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

    // REQUIRES: orderId should be a non-null string.
    // EFFECTS: Searches for an order with the given ID using OrderManager.
    // Displays a message dialog showing the order details if found, or an error message if not found.
    // MODIFIES: Potentially modifies the UI by displaying a message dialog.
    private void searchOrder(String orderId) {
        Order order = orderManager.searchOrderById(orderId);
        if (order != null) {
            JOptionPane.showMessageDialog(this, "Order found: \n" + order,
                    "Order Search", JOptionPane.INFORMATION_MESSAGE);
            searchOrderID(orderId, true); // Order found
        } else {
            JOptionPane.showMessageDialog(this,
                    "Order not found.",
                    "Order Search", JOptionPane.ERROR_MESSAGE);
            searchOrderID(orderId, false); // Order not found
        }
    }
}
