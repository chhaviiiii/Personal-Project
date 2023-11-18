package ui;

import model.OrderStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderStatusUpdatePanel extends JPanel {
    private final OrderManager orderManager;

    public OrderStatusUpdatePanel(OrderManager orderManager) {
        this.orderManager = orderManager;
        setLayout(new BorderLayout());
        add(createStatusUpdateControls(), BorderLayout.CENTER);
    }

    private JPanel createOrderInputAndStatusPanel() {
        JPanel inputAndStatusPanel = new JPanel(new GridLayout(0, 1)); // Grid layout for organized rows

        // Label and TextField for Order ID
        JLabel orderIdLabel = new JLabel("Order ID:");
        JTextField orderIdField = new JTextField(10);
        JPanel orderIdPanel = new JPanel();
        orderIdPanel.add(orderIdLabel);
        orderIdPanel.add(orderIdField);
        inputAndStatusPanel.add(orderIdPanel);

        // Dropdown for Status
        JLabel statusLabel = new JLabel("Status:");
        String[] statuses = {"PLACED", "PROCESSED", "SHIPPED", "DELIVERED", "COMPLETE"}; // Example statuses
        JComboBox<String> statusDropdown = new JComboBox<>(statuses);
        JPanel statusPanel = new JPanel();
        statusPanel.add(statusLabel);
        statusPanel.add(statusDropdown);
        inputAndStatusPanel.add(statusPanel);

        return inputAndStatusPanel;
    }

    private JButton createUpdateButton(JTextField orderIdField, JComboBox<String> statusDropdown) {
        JButton updateButton = new JButton("Update Status");
        updateButton.addActionListener(e -> {
            String selectedOrderId = orderIdField.getText();
            String selectedStatus = (String) statusDropdown.getSelectedItem();
            orderManager.updateOrderStatus(selectedOrderId, OrderStatus.valueOf(selectedStatus));
            orderIdField.setText("");
            JOptionPane.showMessageDialog(null, "Order status updated!");
        });

        return updateButton;
    }

    private JPanel createStatusUpdateControls() {
        JPanel updatePanel = new JPanel(new GridLayout(0, 1)); // Grid layout for organized rows

        // Create Order Input and Status Panel
        JPanel orderInputAndStatusPanel = createOrderInputAndStatusPanel();
        updatePanel.add(orderInputAndStatusPanel);

        // Extracting order ID field and status dropdown for use in the button creation
        JTextField orderIdField = (JTextField) ((JPanel) orderInputAndStatusPanel.getComponent(0)).getComponent(1);
        JComboBox<String> statusDropdown = (JComboBox<String>)
                ((JPanel) orderInputAndStatusPanel.getComponent(1)).getComponent(1);

        // Create Update Button
        JButton updateButton = createUpdateButton(orderIdField, statusDropdown);
        updatePanel.add(updateButton);

        return updatePanel;
    }
}


