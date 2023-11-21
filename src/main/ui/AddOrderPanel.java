package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Panel to add new orders or add products onto the OrderTableModel
public class AddOrderPanel extends JPanel {
    private final OrderManager orderManager;
    private JTextField customerNameField;
    private JTextField productNameField;
    private JComboBox<String> productTypeDropdown;
    private JTextField productPriceField;

    public AddOrderPanel(OrderManager orderManager) {
        this.orderManager = orderManager;
        setLayout(new BorderLayout());
        add(createOrderForm(), BorderLayout.CENTER);
        add(createAddButton(), BorderLayout.SOUTH);
    }

    // Creates the form for the user to put the information in
    private JPanel createOrderForm() {
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        customerNameField = new JTextField(5);
        productNameField = new JTextField(5);
        productTypeDropdown = new JComboBox<>(new String[]{"CLOTHES", "FOOD", "MAKEUP", "ELECTRONICS"});
        productPriceField = new JTextField(10);

        formPanel.add(new JLabel("Customer Name:"));
        formPanel.add(customerNameField);

        formPanel.add(new JLabel("Product Name:"));
        formPanel.add(productNameField);

        formPanel.add(new JLabel("Product Type:"));
        formPanel.add(productTypeDropdown);

        formPanel.add(new JLabel("Product Price:"));
        formPanel.add(productPriceField);


        return formPanel;
    }

    // Creates Add order button
    private JButton createAddButton() {
        JButton addButton = new JButton("Add Order");
        addButton.addActionListener(e -> createAndAddProductToOrder());
        return addButton;
    }


    // Adds and updates order list in  the order table model
    private void createAndAddProductToOrder() {
        try {
            Product product = createProductFromInput();
            addOrUpdateOrder(product);
            clearInputFields();
            orderManager.refreshUI();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format in price field.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid product type.");
        }
    }


    // Focuses on the product details such as product type, name, price
    private Product createProductFromInput() throws NumberFormatException {
        String productName = productNameField.getText();
        ProductType productType = ProductType.valueOf(((String)
                productTypeDropdown.getSelectedItem()).toUpperCase());
        double productPrice = Double.parseDouble(productPriceField.getText());
        return new Product(productName, "", productPrice, productType);
    }

    // If the customer string is the same as an existing order then it updates the order by adding products

    private void addOrUpdateOrder(Product product) {
        String customerName = customerNameField.getText();
        Optional<Order> existingOrder = orderManager.findOrderForCustomer(customerName);
        if (existingOrder.isPresent()) {
            existingOrder.get().addProductToSell(product);
        } else {
            List<Product> newProductsList = new ArrayList<>();
            newProductsList.add(product);
            Order newOrder = new Order(OrderID.generateOrderID(newProductsList),
                    "", customerName, OrderStatus.PLACED, newProductsList);
            orderManager.addNewOrder(newOrder);
        }
    }


    // Lowkey do not need it but clears the fields after an order is added
    private void clearInputFields() {
        customerNameField.setText("");
        productNameField.setText("");
        productTypeDropdown.setSelectedIndex(0);
        productPriceField.setText("");
    }
}
