package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;

// This represents an order with an ID, status and description.
public class Order implements Writable {
    private List<Product> productsToSell;
    private final String orderID;
    private final String productDetails;
    private final String customerDetails;
    private OrderStatus orderStatus;

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
        this.customerDetails = customerDetails;
        this.orderStatus = orderStatus;
        this.productsToSell = productsToSell;
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

    // REQUIRES: product must be non-null
    // MODIFIES: this
    // EFFECTS: Adds a product to the product list
    public void addProductToSell(Product product) {
        this.productsToSell.add(product);
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
        this.orderStatus = newStatus;
    }

    // EFFECTS: Returns customer details
    public String getCustomerDetails() {
        return customerDetails;
    }


    // EFFECTS: Returns product details
    public String getProductDetails() {
        return Product.getDescription();
    }


    // EFFECTS: Returns order ID
    public String getOrderID() {
        return orderID;
    }

    // EFFECTS: Returns order status
    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("orderID", orderID);
        json.put("productDetails", productDetails);
        json.put("customerDetails", customerDetails);
        json.put("orderStatus", orderStatus.toString());
        json.put("productsToSell", productsToJson());

        return json;
    }

    private JSONArray productsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Product product : productsToSell) {
            jsonArray.put(product.toJson());
        }

        return jsonArray;
    }
}







