package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OrderCreationPanel extends JPanel {
    private final OrderManager orderManager;
    private JTextField customerNameField;
    private JTextField productNameField;
    private JTextField productTypeField;
    private JTextField productPriceField;
    private JTextField orderDetailsField;
    private final List<Product> products;

    public OrderCreationPanel(OrderManager orderManager) {
        this.orderManager = orderManager;
        this.products = new ArrayList<>();
        setLayout(new BorderLayout());
        add(createOrderForm(), BorderLayout.CENTER);
        add(createAddButton(), BorderLayout.SOUTH);
    }

    private JPanel createOrderForm() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        customerNameField = new JTextField(10);
        productNameField = new JTextField(10);
        productTypeField = new JTextField(10);
        productPriceField = new JTextField(10);
        orderDetailsField = new JTextField(10);

        formPanel.add(new JLabel("Customer Name:"));
        formPanel.add(customerNameField);

        formPanel.add(new JLabel("Product Name:"));
        formPanel.add(productNameField);

        formPanel.add(new JLabel("Product Type:"));
        formPanel.add(productTypeField);

        formPanel.add(new JLabel("Product Price:"));
        formPanel.add(productPriceField);

        formPanel.add(new JLabel("Order Details:"));
        formPanel.add(orderDetailsField);

        return formPanel;
    }

    private JButton createAddButton() {
        JButton addButton = new JButton("Add Order");
        addButton.addActionListener(e -> createAndDisplayOrder());
        return addButton;
    }

    private void createAndDisplayOrder() {
        try {
            String customerName = customerNameField.getText();
            String productName = productNameField.getText();
            ProductType productType = ProductType.valueOf(productTypeField.getText().toUpperCase());
            double productPrice = Double.parseDouble(productPriceField.getText());
            String orderDetails = orderDetailsField.getText();

            Product product = new Product(productName, orderDetails, productPrice, productType);
            products.add(product);

            Order newOrder = new Order(OrderID.generateOrderID(products),
                    orderDetails, customerName,
                    OrderStatus.PLACED,
                    products);
            orderManager.addNewOrder(newOrder);

            // Update UI components here, e.g., refreshing a table model or clearing input fields
            clearInputFields();
            orderManager.refreshUI(); // Assuming OrderManager has a method to refresh the UI

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format in price field.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid product type.");
        }
    }

    private void clearInputFields() {
        customerNameField.setText("");
        productNameField.setText("");
        productTypeField.setText("");
        productPriceField.setText("");
        orderDetailsField.setText("");
    }
}