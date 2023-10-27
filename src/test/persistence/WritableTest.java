package persistence;

import model.Order;
import model.OrderStatus;
import model.Product;
import model.ProductType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WritableTest {

    @Test
    public void testOrderToJson() {
        // Create an Order instance with sample data
        Order order = new Order("12345",
                "Sample Product",
                "John Doe",
                OrderStatus.PLACED, new ArrayList<>());

        JSONObject json = order.toJson();

        // Assert the expected JSON structure
        assertEquals("12345", json.getString("orderID"));
        assertEquals("Sample Product", json.getString("productDetails"));
        assertEquals("John Doe", json.getString("customerDetails"));
        assertEquals("PLACED", json.getString("orderStatus"));

    }

    @Test
    public void testProductToJson() {
        // Create a Product instance with sample data
        Product product = new Product("Sample Product", "Description", 19.99, ProductType.ELECTRONICS);

        // Call the toJson method
        JSONObject json = product.toJson();

        // Assert the expected JSON structure
        assertEquals("Sample Product", json.getString("name"));
        assertEquals("Description", json.getString("description"));
        assertEquals(19.99, json.getDouble("price"), 0.01);
        assertEquals("ELECTRONICS", json.getString("productType"));
    }

    @Test
    public void testEmptyProductsToJSONConversion() {
        List<Product> productsToSell = new ArrayList<>();

        // Convert an empty list of products to a JSON array
        JSONArray jsonArray = new JSONArray();
        for (Product product : productsToSell) {
            jsonArray.put(product.toJson());
        }

        // You can add assertions here if needed
        // For example, you can check that the JSON array is empty
        assertEquals(0, jsonArray.length());
    }

    @Test
    public void testProductsToJsonWithNonEmptyList() {
        // Create an Order instance with a non-empty list of products
        List<Product> products = new ArrayList<>();
        products.add(new Product("Product1", "Description1", 10.0, ProductType.CLOTHES));
        products.add(new Product("Product2", "Description2", 20.0, ProductType.MAKEUP));
        Order order = new Order("2", "Product2", "Customer2", OrderStatus.SHIPPED, products);

        // Convert the products list to a JSON array
        JSONArray productsJsonArray = new JSONArray();

        for (Product product : products) {
            JSONObject productJson = new JSONObject();
            productJson.put("name", product.getName());
            productJson.put("description", product.getDescription());
            productJson.put("price", product.getPrice());
            // Add any other fields as needed

            productsJsonArray.put(productJson);
        }

        // Ensure that the products JSON array contains the expected number of products
        assertEquals(2, productsJsonArray.length());

        // You can add more detailed assertions if needed, such as comparing product details.
        JSONObject product1Json = productsJsonArray.getJSONObject(0);
        JSONObject product2Json = productsJsonArray.getJSONObject(1);

        assertEquals("Product1", product1Json.getString("name"));
        assertEquals("Description2", product1Json.getString("description"));
        assertEquals(10.0, product1Json.getDouble("price"), 0.01);

        assertEquals("Product2", product2Json.getString("name"));
        assertEquals("Description2", product2Json.getString("description"));
        assertEquals(20.0, product2Json.getDouble("price"), 0.01);
    }
}