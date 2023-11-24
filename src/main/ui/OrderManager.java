package ui;

import model.Order;
import model.OrderStatus;
import persistence.OrderReader;
import persistence.OrderWriter;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Functions of the application and how the order is managed or searched for
public class OrderManager {
    private List<Order> orders;
    private final OrderWriter jsonWriter;
    private final OrderReader jsonReader;
    private final OrderTableModel orderTableModel;
    private final JFrame parentFrame;

    // REQUIRES: jsonStorePath should be a valid file path
    // EFFECTS: Initializes an OrderManager with a list of orders, JSON writer and reader for order persistence
    // MODIFIES: This object (OrderManager), initializing its fields.
    public OrderManager(String jsonStorePath, OrderTableModel orderTableModel, JFrame parentFrame) {
        this.orders = new ArrayList<>();
        this.jsonWriter = new OrderWriter(jsonStorePath);
        this.jsonReader = new OrderReader(jsonStorePath);
        this.orderTableModel = orderTableModel;
        this.parentFrame = parentFrame;

    }


    // REQUIRES: order should be a non-null Order object.
    // EFFECTS: Adds a new order to the list of orders and refreshes the UI to reflect this change.
    // MODIFIES: The list of orders and potentially the UI through refreshUI().
    public void addNewOrder(Order order) {
        orders.add(order);
        refreshUI();
    }


    // REQUIRES: orderId should be a non-empty string; newStatus should be a valid OrderStatus.
    // EFFECTS: Updates the status of the order with the given ID to newStatus.
    // If the order is found, the UI is refreshed. If the order is not found, displays an error message.
    // MODIFIES: The order with the specified ID and potentially the UI.
    public void updateOrderStatus(String orderId, OrderStatus newStatus) {
        for (Order order : orders) {
            if (order.getOrderID().equals(orderId)) {
                order.updateOrderStatus(newStatus);
                refreshUI();
                return;
            }
        }
        JOptionPane.showMessageDialog(parentFrame, "Order ID not found.");
    }


    // REQUIRES: orderId should be a non-empty string.
    // EFFECTS: Returns the order with the given ID. Returns null if no order is found.
    public Order searchOrderById(String orderId) {
        for (Order order : orders) {
            if (order.getOrderID().equals(orderId)) {
                return order;
            }
        }
        return null; // If no order is found with the given ID
    }


    // EFFECTS: Saves the current list of orders to a JSON file.
    // Displays a message upon successful save or an error message upon failure.
    // MODIFIES: The JSON file where orders are saved.
    void saveActiveOrders() {
        try {
            jsonWriter.open();
            jsonWriter.write(orders);
            jsonWriter.close();
            JOptionPane.showMessageDialog(parentFrame, "Saved active orders successfully.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parentFrame, "Error saving active orders: " + ex.getMessage());
        }
    }

    // EFFECTS: Loads orders from a JSON file into the orders list.
    // Displays a message upon successful load or an error message upon failure.
    // Refreshes the UI regardless of success or failure.
    // MODIFIES: The orders list and potentially the UI.
    void loadActiveOrders() {
        try {
            orders = jsonReader.read();
            System.out.println("Loaded orders: " + orders); // Debug statement
            // Update your application state with the loaded orders
            JOptionPane.showMessageDialog(parentFrame, "Loaded active orders successfully.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parentFrame, "Error loading active orders: " + ex.getMessage());
            System.err.println("Error loading orders: " + ex); // Debug statement
        }
        refreshUI(); // Refresh UI regardless of success or failure
    }

    // REQUIRES: customerName should be a non-empty string.
    // EFFECTS: Returns an Optional<Order> representing the first order found for the given customer name.
    // Returns an empty Optional if no such order is found.
    public Optional<Order> findOrderForCustomer(String customerName) {
        return orders.stream()
                .filter(order -> order.getCustomerName().equals(customerName))
                .findFirst();
    }

    // EFFECTS: Updates the order table model with the current list of orders and notifies it that its data has changed.
    // MODIFIES: The state of orderTableModel and potentially the UI.
    public void refreshUI() {
        orderTableModel.setOrders(orders);
        orderTableModel.fireTableDataChanged();

    }

}


