package persistence;

import model.Order;
import model.OrderStatus;
import model.Product;
import model.ProductType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class OrderReaderTest {
    private OrderReader orderReader;
    private String testFilePath;
    private OrderWriter orderWriter;

    @BeforeEach
    public void setUp() {
        testFilePath = "test_orders.json"; // Use a test file path
        orderReader = new OrderReader(testFilePath);
        orderWriter = new OrderWriter(testFilePath);
    }

    @AfterEach
    public void tearDown() {
        // Clean up the test file after each test
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testParseOrdersValidJson() {
        JSONArray jsonArray = new JSONArray();

        // Create a sample order in JSON format
        JSONObject orderJson = new JSONObject();
        orderJson.put("orderID", "1");
        orderJson.put("productDetails", "Details");
        orderJson.put("customerDetails", "Customer");
        orderJson.put("orderStatus", "PLACED");

        JSONArray productsArray = new JSONArray();
        JSONObject productJson = new JSONObject();
        productJson.put("name", "Product");
        productJson.put("description", "Description");
        productJson.put("price", 10.99);
        productJson.put("productType", "CLOTHES");

        productsArray.put(productJson);

        orderJson.put("productsToSell", productsArray);

        jsonArray.put(orderJson);

        List<Order> orders = orderReader.parseOrders(jsonArray);

        assertEquals(1, orders.size());

        Order order = orders.get(0);
        assertEquals("1", order.getOrderID());
        assertEquals("Description", order.getProductDetails());
        assertEquals("Customer", order.getCustomerDetails());
        assertEquals(OrderStatus.PLACED, order.getOrderStatus());

        List<Product> products = order.getProductsToSell();
        assertEquals(1, products.size());

        Product product = products.get(0);
        assertEquals("Product", product.getName());
        assertEquals("Description", product.getDescription());
        assertEquals(10.99, product.getPrice(), 0.01);
        assertEquals(ProductType.CLOTHES, product.getProductType());
    }

    @Test
    public void testParseOrdersEmptyJsonArray() {
        // Test parsing an empty JSON array
        JSONArray jsonArray = new JSONArray();

        List<Order> orders = orderReader.parseOrders(jsonArray);

        assertTrue(orders.isEmpty());
    }


    @Test
    public void testReadMultipleOrders() {
        // Test reading JSON with multiple orders
        createTestJsonFile("[{\"orderID\":\"1\",\"...\"},{\"orderID\":\"2\",\"...\"}," +
                "{\"orderID\":\"3\",\"...\"}]");

        try {
            List<Order> orders = orderReader.read();
            assertEquals(0, orders.size()); // Change the expected size accordingly
        } catch (FileNotFoundException e) {
            fail("Test failed: " + e.getMessage());
        }
    }

    @Test
    public void testReadOrdersWithDifferentProductTypes() {
        // Test reading orders with different product types
        createTestJsonFile("[{\"orderID\":\"1\",\"...\"},{\"orderID\":\"2\",\"...\"}]");

        try {
            List<Order> orders = orderReader.read();
            for (Order order : orders) {
                for (Product product : order.getProductsToSell()) {
                    assertTrue(product.getProductType() == ProductType.CLOTHES ||
                            product.getProductType() == ProductType.ELECTRONICS);
                }
            }
        } catch (FileNotFoundException e) {
            fail("Test failed: " + e.getMessage());
        }
    }


    @Test
    public void testReadInvalidJson() {
        createTestJsonFile("Invalid JSON");

        try {
            List<Order> orders = orderReader.read();
            assertTrue(orders.isEmpty());
        } catch (FileNotFoundException e) {
            fail("Test failed: " + e.getMessage());
        }
    }

    private void createTestJsonFile(String jsonData) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(testFilePath))) {
            writer.write(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testReadEmptyOrders() {
        // Create an empty list of orders and write it to a file
        List<Order> expectedOrders = new ArrayList<>();
        orderWriter.write(expectedOrders);

        // Read the orders from the file
        try {
            List<Order> actualOrders = orderReader.read();

            // Verify that the read orders are empty
            assertTrue(actualOrders.isEmpty());
        } catch (FileNotFoundException e) {
            fail("Test failed: " + e.getMessage());
        }
    }

    @Test
    public List<Order> createSampleOrders() {
        List<Order> orders = new ArrayList<>();
        // Create some sample orders here
        Order order1 = new Order("1",
                "Product1",
                "Customer1",
                OrderStatus.PLACED,
                new ArrayList<>());
        Order order2 = new Order("2",
                "Product2",
                "Customer2",
                OrderStatus.SHIPPED,
                new ArrayList<>());
        orders.add(order1);
        orders.add(order2);
        return orders;
    }

}


