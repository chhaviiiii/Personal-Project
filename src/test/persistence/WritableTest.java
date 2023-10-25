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

        // Call the toJson method
        JSONObject json = order.toJson();

        // Assert the expected JSON structure
        assertEquals("12345", json.getString("orderID"));
        assertEquals("Sample Product", json.getString("productDetails"));
        assertEquals("John Doe", json.getString("customerDetails"));
        assertEquals("PLACED", json.getString("orderStatus"));

        // If productsToJson is tested separately, you can add more assertions here
    }

    @Test
    public void testProductToJson() {
        // Create a Product instance with sample data
        Product product = new Product("Sample Product",
                "Description",
                19.99,
                ProductType.ELECTRONICS);

        // Call the toJson method
        JSONObject json = product.toJson();

        // Assert the expected JSON structure
        assertEquals("Sample Product", json.getString("name"));
        assertEquals("Description", json.getString("description"));
        assertEquals(19.99, json.getDouble("price"), 0.001);
        assertEquals("ELECTRONICS", json.getString("productType"));
    }

    @Test
    public void testAddProductToJSONArray() {
        // Create a sample product
        Product product1 = new Product("Product1", "Description1", 10.99, ProductType.CLOTHES);

        // Create an empty JSON array
        JSONArray jsonArray = new JSONArray();

        // Add the product to the JSON array
        jsonArray.put(product1.toJson());

        // Ensure that the JSON array contains the expected product JSON
        assertEquals(1, jsonArray.length());

        // Verify the JSON object in the array
        assertEquals("Product1", jsonArray.getJSONObject(0).getString("name"));
        assertEquals("Description1", jsonArray.getJSONObject(0).getString("description"));
        assertEquals(10.99, jsonArray.getJSONObject(0).getDouble("price"), 0.01);
        assertEquals("CLOTHES", jsonArray.getJSONObject(0).getString("productType"));
    }


    @Test
    public void testEmptyProductsToJSONConversion() {
        List<Product> productsToSell = new ArrayList<>();

        // Convert an empty list of products to a JSON array
        JSONArray jsonArray = new JSONArray();
        for (Product product : productsToSell) {
            jsonArray.put(product.toJson());
        }
    }
}
