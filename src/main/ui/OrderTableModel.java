package ui;

import model.Order;
import model.OrderStatus;
import model.Product;

import javax.swing.table.AbstractTableModel;
import java.util.List;

// Represents a table model for displaying orders in a JTable.

public class OrderTableModel extends AbstractTableModel {
    private final String[] columnNames = {
            "Order ID", "Customer Name", "Order Status", "Products (Name - Price)"
    };

    private List<Order> orders;

    // Constructs an OrderTableModel with a given list of orders.

    public OrderTableModel(List<Order> orders) {
        this.orders = orders;
    }

    // Adds an order to the table model and refreshes the table data.
    public void addOrder(Order order) {
        this.orders.add(order);
        fireTableDataChanged(); // Notify the table that data has changed
    }

    // Sets the list of orders in the model and refreshes the table data.
    public void setOrders(List<Order> orders) {
        this.orders = orders;
        fireTableDataChanged(); // Notify the table that data has changed
    }


    // Counts the Row
    @Override
    public int getRowCount() {
        return orders.size();
    }

    // Counts the column
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    // Produces column Name
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    // Interconnects the creation and production of the table
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order order = orders.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return Order.getOrderID();
            case 1:
                return Order.getCustomerDetails();
            case 2:
                return Order.getOrderStatus().toString();
            case 3:
                return getProductNamesWithPrices(order.getProductsToSell());
            default:
                return null;
        }
    }

    // Constructs a string containing product names and prices

    private String getProductNamesWithPrices(List<Product> products) {
        StringBuilder productDetails = new StringBuilder();
        for (Product product : products) {
            if (productDetails.length() > 0) {
                productDetails.append(", ");
            }
            productDetails.append(product.getName()).append(" - $").append(product.getPrice());
        }
        return productDetails.toString();
    }

    // Updates the status of an order by order ID and refreshes the table data.

    public void updateOrderStatus(String orderId, OrderStatus newStatus) {
        for (Order order : orders) {
            if (Order.getOrderID().equals(orderId)) {
                order.updateOrderStatus(newStatus);
                fireTableDataChanged(); // Refresh table data
                break;
            }
        }
    }
}
