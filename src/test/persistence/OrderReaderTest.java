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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class OrderReaderTest {
        private OrderReader orderReader;
        private String testFilePath;

        @BeforeEach
        public void setUp() {
            testFilePath = "test_orders.json"; // Use a test file path
            orderReader = new OrderReader(testFilePath);
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
        JSONArray jsonArray = new JSONArray();

        List<Order> orders = orderReader.parseOrders(jsonArray);

        assertTrue(orders.isEmpty());
    }


    @Test
        public void testReadEmptyOrders() {
            createTestJsonFile("[]");

            try {
                List<Order> orders = orderReader.read();
                assertTrue(orders.isEmpty());
            } catch (FileNotFoundException e) {
                fail("Test failed: " + e.getMessage());
            }
        }
    @Test
        public void testReadMultipleOrders() {
        createTestJsonFile("{\"orderID\":\"1\",\"...\"},{\"orderID\":\"2\",\"...\"}," +
                "{\"orderID\":\"3\",\"...\"}");

        try {
            List<Order> orders = orderReader.read();
            assertEquals(0, orders.size());
        } catch (FileNotFoundException e) {
            fail("Test failed: " + e.getMessage());
        }
    }

    @Test
        public void testReadOrdersWithDifferentProductTypes() {
        createTestJsonFile("{\"orderID\":\"1\",\"...\"},{\"orderID\":\"2\",\"...\"}");

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

    }

