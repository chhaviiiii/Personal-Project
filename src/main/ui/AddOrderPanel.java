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

    // EFFECTS: Returns a JPanel that contains the form for adding orders,
    // including fields for customer name, product name, product type, and product price.
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

    // EFFECTS: Returns a JButton that, when clicked, triggers the process to create and add a product to an order.
    private JButton createAddButton() {
        JButton addButton = new JButton("Add Order");
        addButton.addActionListener(e -> createAndAddProductToOrder());
        return addButton;
    }


    // REQUIRES: Valid inputs in the customerNameField, productNameField, productTypeDropdown, and productPriceField.
    // EFFECTS: Attempts to create a product from user input, add or update an order with this product,
    // clear input fields, and refresh the UI. Displays error messages in case of invalid input.
    // MODIFIES: Modifies the state of orderManager and potentially the UI components (input fields).
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


    // REQUIRES: Valid inputs in the productNameField, productTypeDropdown, and productPriceField.
    // EFFECTS: Creates and returns a Product instance based on user inputs.
    // Throws NumberFormatException if the price input is invalid.
    private Product createProductFromInput() throws NumberFormatException {
        String productName = productNameField.getText();
        ProductType productType = ProductType.valueOf(((String)
                productTypeDropdown.getSelectedItem()).toUpperCase());
        double productPrice = Double.parseDouble(productPriceField.getText());
        return new Product(productName, "", productPrice, productType);
    }

    // REQUIRES: A non-null Product object.
    // EFFECTS: Adds the given product to an existing order for the given customer,
    // or creates a new order if one doesn't exist.
    //MODIFIES: Modifies the OrderManager's state by adding or updating orders.
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


    // EFFECTS: Clears the text in the customerNameField, productNameField, productPriceField,
    // and resets the productTypeDropdown to its default state.
    // MODIFIES: Modifies the state of the UI components (input fields).
    private void clearInputFields() {
        customerNameField.setText("");
        productNameField.setText("");
        productTypeDropdown.setSelectedIndex(0);
        productPriceField.setText("");
    }
}
