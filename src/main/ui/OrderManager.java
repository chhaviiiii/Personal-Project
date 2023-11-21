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

    // Helps with persistence and focuses on user stories
    public OrderManager(String jsonStorePath, OrderTableModel orderTableModel, JFrame parentFrame) {
        this.orders = new ArrayList<>();
        this.jsonWriter = new OrderWriter(jsonStorePath);
        this.jsonReader = new OrderReader(jsonStorePath);
        this.orderTableModel = orderTableModel;
        this.parentFrame = parentFrame;

    }


    // Adds new orders
    public void addNewOrder(Order order) {
        orders.add(order);
        refreshUI();
    }


    // Pick an order ID then use the dropdown to update the status
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


    // Input the order ID and the order will be displayed
    public Order searchOrderById(String orderId) {
        for (Order order : orders) {
            if (order.getOrderID().equals(orderId)) {
                return order;
            }
        }
        return null; // If no order is found with the given ID
    }


    // Implementation of saving orders
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

    // Implementation of loading orders
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

    // Finds the order details through order ID
    public Optional<Order> findOrderForCustomer(String customerName) {
        return orders.stream()
                .filter(order -> order.getCustomerName().equals(customerName))
                .findFirst();
    }

    // Incorporate changes to the Order Table Model.
    public void refreshUI() {
        orderTableModel.setOrders(orders);
        orderTableModel.fireTableDataChanged();

    }

}


