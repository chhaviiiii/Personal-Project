package model;

import java.util.List;

// Represents an Order which includes orderID, productDetails, customerDetails, orderStatus.
// The product list to be sold can be defined and updated.
public class Order {
    private List<Product> productsToSell; // List of Products to be sold.
    private final String orderID; // Unique Identifier of the Order.
    private final String productDetails; // Details of the Product.
    private final String customerDetails; // Details of the Customer who placed the order.
    private OrderStatus orderStatus; // Status of the order (eg. "Processed", "Shipped", "Delivered", "Complete")

    // Constructor method
    public Order(String orderID,
                 String productDetails,
                 String customerDetails,
                 OrderStatus orderStatus,
                 List<Product> productsToSell) {
        this.orderID = orderID;
        this.productDetails = productDetails;
        this.customerDetails = customerDetails;
        this.orderStatus = orderStatus;
        this.productsToSell = productsToSell;
    }


    // Returns the list of products to be sold.
    public List<Product> getProductsToSell() {
        return productsToSell;
    }

    // Sets list of products to be sold.
    public void setProductsToSell(List<Product> productsToSell) {
        this.productsToSell = productsToSell;
    }

    // Adds a product to the list of products to be sold.
    public void addProductToSell(Product product) {
        this.productsToSell.add(product);
    }

    // Removes a product from the list of products to be sold.
    public void removeProductToSell(Product product) {
        this.productsToSell.remove(product);
    }

    // Updates the status of the order.
    public void updateOrderStatus(OrderStatus newStatus) {
        this.orderStatus = newStatus;
    }

    // Returns Customer Details
    public String getCustomerDetails() {
        return customerDetails;
    }

    // Returns Product Details
    public String getProductDetails() {
        return productDetails;
    }

    // Returns the order ID.
    public String getOrderID() {
        return orderID;
    }

    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }
}
