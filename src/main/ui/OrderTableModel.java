package ui;

import model.Order;
import model.Product;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class OrderTableModel extends AbstractTableModel {
    private final String[] columnNames = {
            "Order ID",
            "Customer Name",
            "Order Status",
            "Products",
            "Total Price"
    };

    private List<Order> orders;

    public OrderTableModel(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        fireTableDataChanged(); // Notify the table that data has changed
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        fireTableDataChanged(); // Notify the table that data has changed
    }

    @Override
    public int getRowCount() {
        return orders.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order order = orders.get(rowIndex);
        switch (columnIndex) {
            case 0: return order.getOrderID();
            case 1: return order.getCustomerDetails();
            case 2: return order.getOrderStatus().toString();
            case 3: return getProductNames(order.getProductsToSell());
            case 4: return calculateTotalPrice(order.getProductsToSell());
            default: return null;
        }
    }

    private String getProductNames(List<Product> products) {
        StringBuilder productNames = new StringBuilder();
        for (Product product : products) {
            if (productNames.length() > 0) {
                productNames.append(", ");
            }
            productNames.append(product.getName());
        }
        return productNames.toString();
    }


    private double calculateTotalPrice(List<Product> products) {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }
}
