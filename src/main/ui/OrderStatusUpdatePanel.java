package ui;

import model.OrderStatus;

import javax.swing.*;
import java.awt.*;

// Updates the Order Status of the selected order
public class OrderStatusUpdatePanel extends JPanel {
    private final OrderManager orderManager;  // Non-static
    private JTextField orderIdField;    // Non-static
    private JComboBox<String> statusDropdown;  // Non-static

    // REQUIRES: A non-null OrderManager object.
    // EFFECTS: Initializes an OrderStatusUpdatePanel instance with the given OrderManager.
    // Sets up components, layout, and listeners for updating order statuses.
    // MODIFIES: This object (OrderStatusUpdatePanel), modifying its state by initializing UI components
    public OrderStatusUpdatePanel(OrderManager orderManager) {
        this.orderManager = orderManager;
        initializeComponents();
        setupLayout();
        setupListeners();
    }

    // EFFECTS: Initializes the components of the panel, including the order ID field and the status dropdown.
    // MODIFIES: This object (OrderStatusUpdatePanel), modifying its orderIdField and statusDropdown fields.
    private void initializeComponents() {
        orderIdField = new JTextField(10);
        statusDropdown = new JComboBox<>(new String[]{"PLACED",
                "PROCESSED",
                "SHIPPED",
                "DELIVERED",
                "COMPLETE",
                "VOID"});
    }

    // EFFECTS: Configures the layout of the panel using GridBagLayout.
    // Arranges the order ID components, status components, and update button within the panel.
    // MODIFIES: This object (OrderStatusUpdatePanel), modifying its layout and potentially the arrangement
    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);

        addOrderIdComponents(gridBagConstraints);
        addStatusComponents(gridBagConstraints);
        addUpdateButton(gridBagConstraints);
    }

    // REQUIRES: A non-null GridBagConstraints object.
    // EFFECTS: Adds components related to the order ID to the panel, including a label and a text field.
    // MODIFIES: This object (OrderStatusUpdatePanel), modifying its layout and components related to order ID.
    private void addOrderIdComponents(GridBagConstraints gridBagConstraints) {
        JLabel orderIdLabel = createWhiteLabel("Order ID:");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(orderIdLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(orderIdField, gridBagConstraints);
    }

    // REQUIRES: A non-null GridBagConstraints object.
    // EFFECTS: Adds components related to the order status to the panel, including a label and a dropdown.
    // MODIFIES: This object (OrderStatusUpdatePanel), modifying its layout and components related to order status.
    private void addStatusComponents(GridBagConstraints gridBagConstraints) {
        JLabel statusLabel = createWhiteLabel("Status:");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(statusLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(statusDropdown, gridBagConstraints);
    }

    // REQUIRES: A non-null GridBagConstraints object.
    // EFFECTS: Adds the "Update Status" button to the panel.
    // MODIFIES: This object (OrderStatusUpdatePanel), modifying its layout and adding a new button component.
    private void addUpdateButton(GridBagConstraints gridBagConstraints) {
        JButton updateButton = new JButton("Update Status");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        add(updateButton, gridBagConstraints);
    }

    // REQUIRES: A non-null String text.
    // EFFECTS: Creates and returns a JLabel with the specified text and white color.
    private JLabel createWhiteLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }

    // REQUIRES: The components of the panel, particularly the update button, must be correctly initialized.
    // EFFECTS: Sets up action listeners for the update button to handle the order status update action.
    // MODIFIES: This object (OrderStatusUpdatePanel) by adding an action listener to it.
    private void setupListeners() {
        JButton updateButton = (JButton) getComponent(4);
        updateButton.addActionListener(e -> updateOrderStatus());
    }

    // REQUIRES: Valid and non-empty input in orderIdField and a valid selection in statusDropdown.
    // EFFECTS: Updates the status of the order with the ID entered in orderIdField to the status in statusDropdown
    // MODIFIES: Potentially modifies the order status in the OrderManager and the UI
    private void updateOrderStatus() {
        String selectedOrderId = orderIdField.getText();
        OrderStatus selectedStatus = OrderStatus.valueOf((String) statusDropdown.getSelectedItem());
        orderManager.updateOrderStatus(selectedOrderId, selectedStatus);
        orderIdField.setText("");
        JOptionPane.showMessageDialog(null, "Order status updated!");
    }
}

