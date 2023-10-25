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

public class OrderReader {
    private String source;

    public OrderReader(String source) {
        this.source = source;
    }

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

    private String readFile(String source) throws FileNotFoundException {
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

    // Add an order to the existing list of orders
    public void addOrder(Order newOrder, List<Order> existingOrders) {
        existingOrders.add(newOrder);
    }
}
