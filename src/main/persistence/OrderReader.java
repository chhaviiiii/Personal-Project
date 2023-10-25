package persistence;

import model.Order;
import model.OrderStatus;
import model.Product;
import model.ProductType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Represents a reader that loads JSON representation of an order tracker to a file
public class OrderReader {
    private String source;

    // REQUIRES: A valid file path or source to read order data from.
    // EFFECTS: Initializes an OrderReader object with the provided source.
    public OrderReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads orders from the file and returns them
    public List<Order> read() throws FileNotFoundException {
        try {
            String jsonData = readFile(source);
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("orders");
            return parseOrders(jsonArray);
        } catch (JSONException e) {
            // Handle the exception here (e.g., log an error message)
            System.err.println("Error reading JSON: " + e.getMessage());
            // You might return an empty list or null to indicate an error
            return new ArrayList<>();
        }
    }

    // REQUIRES: A valid file path or source.
    // EFFECTS : Reads the content of the file specified by the source and returns it as a single string.
    private String readFile(String source) throws FileNotFoundException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Scanner scanner = new Scanner(new File(source))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                contentBuilder.append(line).append("\n"); // Add a newline character at the end of each line
                // Debug print statement to check each line as it's read
                System.out.println("Read line: " + line);
            }
        }

        return contentBuilder.toString();
    }


    public List<Order> parseOrders(JSONArray jsonArray) {
        List<Order> orders = new ArrayList<>();

        for (Object json : jsonArray) {
            JSONObject orderJson = (JSONObject) json;
            String orderID = orderJson.getString("orderID");
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

            Order order = new Order(orderID, productDetails, customerDetails, orderStatus, productsToSell);
            orders.add(order);
        }

        return orders;
    }
}