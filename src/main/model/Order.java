package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;

// This represents an order with an ID, status and description.
public class Order implements Writable {
    private final String orderID;
    private final String customerName;
    private OrderStatus orderStatus;
    private final String productDetails;
    private List<Product> productsToSell;

    // REQUIRES: orderID, productDetails, customerDetails, orderStatus, productsToSell must be non-null
    // MODIFIES: this
    // EFFECTS: Initializes the order
    public Order(String orderID,
                 String productDetails,
                 String customerDetails,
                 OrderStatus orderStatus,
                 List<Product> productsToSell) {
        this.orderID = orderID;
        this.productDetails = productDetails;
        this.customerName = customerDetails;
        this.orderStatus = orderStatus;
        this.productsToSell = productsToSell;
    }

    // EFFECTS: Returns customer details
    public String getCustomerName() {
        return customerName;
    }

    // EFFECTS: Returns order ID
    public String getOrderID() {
        return orderID;
    }

    // EFFECTS: Returns order status
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    // EFFECTS: Returns list of products to sell
    public List<Product> getProductsToSell() {
        return productsToSell;
    }

    // REQUIRES: productsToSell must be non-null
    // MODIFIES: this
    // EFFECTS: Sets the product list to the given list
    public void setProductsToSell(List<Product> productsToSell) {
        this.productsToSell = productsToSell;
    }

    // REQUIRES: OrderID must be non-null
    // MODIFIES: this
    // EFFECTS: Sets the orderID
    public static void searchOrderID(String orderID, boolean isOrderFound) {
        String logMessage = isOrderFound ? "Order found for ID: " : "Order not found for ID: ";
        EventLog.getInstance().logEvent(new Event(logMessage + orderID));
    }


    // REQUIRES: product must be non-null
    // MODIFIES: this
    // EFFECTS: Adds a product to the product list
    public void addProductToSell(Product product) {
        this.productsToSell.add(product);
        EventLog.getInstance().logEvent(new Event("Products added to Order: " + productsToSell));
    }

    // REQUIRES: product must be non-null
    // MODIFIES: this
    // EFFECTS: Removes a product from the product list
    public void removeProductToSell(Product product) {
        this.productsToSell.remove(product);
    }

    // REQUIRES: newStatus must be non-null
    // MODIFIES: this
    // EFFECTS: Updates the order's status
    public void updateOrderStatus(OrderStatus newStatus) {
        orderStatus = newStatus;
        EventLog.getInstance().logEvent(new Event("Order Status updated: " + newStatus));
    }

    // EFFECTS: Returns product details
    public String getProductDetails() {
        StringBuilder sb = new StringBuilder();
        for (Product product : productsToSell) {
            sb.append(product.getName()).append(" - ").append(Product.getDescription()).append("; ");
        }
        return sb.toString();
    }

    // EFFECTS: Returns a string representation of the order with relevant details
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(getOrderID())
                .append(", Customer Details: ").append(getCustomerName())
                .append(", Order Status: ").append(getOrderStatus());

        for (Product product : getProductsToSell()) {
            sb.append("\n\tProduct Name: ").append(product.getName())
                    .append(", Type: ").append(Product.getProductType())
                    .append(", Price: ").append(product.getPrice());
        }
        return sb.toString();
    }

    // Converts this order to a JSON object for data persistence.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("orderID", orderID);
        json.put("productDetails", productDetails);
        json.put("customerDetails", customerName);
        json.put("orderStatus", orderStatus.toString());
        json.put("productsToSell", productsToJson());

        return json;
    }
    // The JSON representation of the order.

    private JSONArray productsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Product product : productsToSell) {
            jsonArray.put(product.toJson());
        }

        return jsonArray;
    }
}







