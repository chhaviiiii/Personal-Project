package ui;

import model.Order;
import model.OrderID;
import model.OrderStatus;
import model.Product;
import persistence.OrderReader;
import persistence.OrderWriter;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private List<Order> orders;
    private final OrderWriter jsonWriter;
    private final OrderReader jsonReader;
    private final OrderTableModel orderTableModel;
    private final JFrame parentFrame;
    private final List<Product> products;

    public OrderManager(String jsonStorePath, OrderTableModel orderTableModel, JFrame parentFrame) {
        this.orders = new ArrayList<>();
        this.jsonWriter = new OrderWriter(jsonStorePath);
        this.jsonReader = new OrderReader(jsonStorePath);
        this.orderTableModel = orderTableModel;
        this.parentFrame = parentFrame;
        this.products = new ArrayList<>();
    }


    public void addNewOrder(Order order) {
        orders.add(order);
        orderTableModel.addOrder(order);
    }


    public void updateOrderStatus(String orderId, OrderStatus newStatus) {
        for (Order order : orders) {
            if (OrderID.generateOrderID(products).equals(orderId)) {
                order.updateOrderStatus(newStatus);
                orderTableModel.fireTableDataChanged();
                break;
            }
        }
    }


    public Order searchOrderById(String orderId) {
        for (Order order : orders) {
            if (Order.getOrderID().equals(orderId)) {
                return order;
            }
        }
        return null; // If no order is found with the given ID
    }


    void saveActiveOrders() {
        // Implementation of saving orders
        try {
            jsonWriter.open();
            jsonWriter.write(orders);
            jsonWriter.close();
            JOptionPane.showMessageDialog(parentFrame, "Saved active orders successfully.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parentFrame, "Error saving active orders: " + ex.getMessage());
        }
    }

    void loadActiveOrders() {
        // Implementation of loading orders
        try {
            orders = jsonReader.read();
            // Update your application state with the loaded orders
            JOptionPane.showMessageDialog(parentFrame, "Loaded active orders successfully.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parentFrame, "Error loading active orders: " + ex.getMessage());
        }
    }

    public void refreshUI() {
        orderTableModel.setOrders(orders);
        orderTableModel.fireTableDataChanged();
        // If there are other UI components that need to be refreshed, update them here.
        // For example, you might need to update a status label or refresh a panel.
    }

}


