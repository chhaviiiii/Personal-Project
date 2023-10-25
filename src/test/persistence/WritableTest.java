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
    }
}
