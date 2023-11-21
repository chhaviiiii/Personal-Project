package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
// Reads and parses order data from a JSON file.
// The OrderReader is responsible for loading order information, including order details, products, and statuses.

public class OrderReader {
    private final String source;

// Constructs an OrderReader to read from the specified source file

    public OrderReader(String source) {
        this.source = source;
    }

    // Reads the source file and parses its content into a list of Order objects.
    // Returns an empty list if a JSONException occurs during parsing.
    public List<Order> read() throws FileNotFoundException {
        try {
            String jsonData = readFile(source);
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("orders");
            return parseOrders(jsonArray);
        } catch (JSONException e) {
            System.err.println("Error reading JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Parses a JSONArray of order data into a list of Order objects.
    // Each JSONObject in the array is converted into an Order object.

    protected String readFile(String source) throws FileNotFoundException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Scanner scanner = new Scanner(new File(source))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                contentBuilder.append(line).append("\n");
                System.out.println("Read line: " + line);
            }
        }

        return contentBuilder.toString();
    }

    // Parses a JSONArray of product data into a list of Product objects.
    // Each JSONObject in the array is converted into a Product object.
    public List<Order> parseOrders(JSONArray jsonArray) {
        List<Order> orders = new ArrayList<>();

        for (Object json : jsonArray) {
            JSONObject orderJson = (JSONObject) json;
            String productDetails = orderJson.getString("productDetails");
            String customerDetails = orderJson.getString("customerDetails");
            OrderStatus orderStatus = OrderStatus.valueOf(orderJson.getString("orderStatus"));
            JSONArray productsArray = orderJson.getJSONArray("productsToSell");
            List<Product> productsToSell = new ArrayList<>();

            for (Object productJson : productsArray) {
                JSONObject productData = (JSONObject) productJson;
                String productName = productData.getString("name");
                String productDescription = productData.getString("description");
                double productPrice = productData.getDouble("price");
                ProductType productType = ProductType.valueOf(productData.getString("productType"));
                Product product = new Product(productName, productDescription, productPrice, productType);
                productsToSell.add(product);
            }
            String orderID = OrderID.generateOrderID(productsToSell);
            Order order = new Order(orderID, productDetails, customerDetails, orderStatus, productsToSell);
            orders.add(order);
        }

        return orders;
    }

    // Add an order to the existing list of orders
    public void addOrder(Order newOrder, List<Order> existingOrders) {
        existingOrders.add(newOrder);
    }
}
