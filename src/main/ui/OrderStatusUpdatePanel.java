package ui;

import model.OrderStatus;

import javax.swing.*;
import java.awt.*;

// Updates the Order Status of the selected order
public class OrderStatusUpdatePanel extends JPanel {
    private final OrderManager orderManager;  // Non-static
    private JTextField orderIdField;    // Non-static
    private JComboBox<String> statusDropdown;  // Non-static

    // Allows Order Manager to update the status of an existing order
    public OrderStatusUpdatePanel(OrderManager orderManager) {
        this.orderManager = orderManager;
        initializeComponents();
        setupLayout();
        setupListeners();
    }

    // Elements of the Panel
    private void initializeComponents() {
        orderIdField = new JTextField(10);
        statusDropdown = new JComboBox<>(new String[]{"PLACED", "PROCESSED", "SHIPPED", "DELIVERED", "COMPLETE"});
    }

    // Panel Layout
    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);

        addOrderIdComponents(gridBagConstraints);
        addStatusComponents(gridBagConstraints);
        addUpdateButton(gridBagConstraints);
    }

    // Order ID components
    private void addOrderIdComponents(GridBagConstraints gridBagConstraints) {
        JLabel orderIdLabel = createWhiteLabel("Order ID:");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(orderIdLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(orderIdField, gridBagConstraints);
    }

    // Status Components
    private void addStatusComponents(GridBagConstraints gridBagConstraints) {
        JLabel statusLabel = createWhiteLabel("Status:");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(statusLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(statusDropdown, gridBagConstraints);
    }

    // Update Status Button
    private void addUpdateButton(GridBagConstraints gridBagConstraints) {
        JButton updateButton = new JButton("Update Status");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        add(updateButton, gridBagConstraints);
    }

    // Customization of the Label
    private JLabel createWhiteLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }

    // Action Button Listener
    private void setupListeners() {
        JButton updateButton = (JButton) getComponent(4);
        updateButton.addActionListener(e -> updateOrderStatus());
    }

    // Method to update the order status and display message
    private void updateOrderStatus() {
        String selectedOrderId = orderIdField.getText();
        OrderStatus selectedStatus = OrderStatus.valueOf((String) statusDropdown.getSelectedItem());
        orderManager.updateOrderStatus(selectedOrderId, selectedStatus);
        orderIdField.setText("");
        JOptionPane.showMessageDialog(null, "Order status updated!");
    }
}

