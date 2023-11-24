package ui;

import model.Order;
import model.Product;

import javax.swing.table.AbstractTableModel;
import java.util.List;

// Represents a table model for displaying orders in a JTable.
public class OrderTableModel extends AbstractTableModel {
    private final String[] columnNames = {
            "Order ID", "Customer Name", "Order Status", "Products (Name - Price)"
    };

    private static List<Order> orders;

    // REQUIRES: orders should be a non-null List<Order>.
    // EFFECTS: Initializes an OrderTableModel with the given list of orders.
    // MODIFIES: Static field orders in OrderTableModel.
    public OrderTableModel(List<Order> orders) {
        OrderTableModel.orders = orders;
    }


    // REQUIRES: orders should be a non-null List<Order>.
    // EFFECTS: Sets the list of orders to the provided list and notifies the table that the data has changed.
    // MODIFIES: Static field orders in OrderTableModel and potentially the UI
    public void setOrders(List<Order> orders) {
        OrderTableModel.orders = orders;
        fireTableDataChanged(); // Notify the table that data has changed
    }


    // EFFECTS: Returns the number of rows in the table, equivalent to the number of orders in the list.
    @Override
    public int getRowCount() {
        return orders.size();
    }

    // EFFECTS: Returns the number of columns in the table, equivalent to the length of columnNames.
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    // REQUIRES: column index should be within the range of the columnNames array.
    // EFFECTS: Returns the name of the column at the specified index.
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    // REQUIRES: rowIndex should be within the range of the orders list
    // EFFECTS: Returns the value
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order order = orders.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return order.getOrderID();
            case 1:
                return order.getCustomerName();
            case 2:
                return order.getOrderStatus().toString();
            case 3:
                return getProductNamesWithPrices(order.getProductsToSell());
            default:
                return null;
        }
    }

    // REQUIRES: products should be a non-null List<Product>.
    // EFFECTS: Constructs and returns a string containing the names and prices of the products in the provided list,
    // formatted as "Name - $Price".
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

}


