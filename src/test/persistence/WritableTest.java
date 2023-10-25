package persistence;

import model.Order;
import model.OrderStatus;
import model.Product;
import model.ProductType;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
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
}
