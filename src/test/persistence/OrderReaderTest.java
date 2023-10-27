package persistence;

import model.Order;
import model.OrderStatus;
import model.Product;
import model.ProductType;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderReaderTest {

    @Test
    void testOrderReader() {
        try {
            OrderReader orderReader = new OrderReader("source");
            assertNotNull(orderReader);
        } catch (Exception e) {
            fail("Exception should not have been thrown.");
        }
    }

    @Test
    void testRead() {
        try {
            OrderReader orderReader = new OrderReader("./data/FileNotFound.json");
            assertNotNull(orderReader.read());
        } catch (FileNotFoundException e) {
            fail("File should be found");
        } catch (JSONException e) {
            fail("Parsing the JSON should not result in an exception");
        }
    }


    @Test
    void testParseOrders() {
        try {
            OrderReader orderReader = new OrderReader("source");
            JSONArray jsonArray = new JSONArray();
            assertNotNull(orderReader.parseOrders(jsonArray));
        } catch (Exception e) {
            fail("Parsing the JSON should not result in an exception");
        }
    }

    @Test
    void testAddOrder() {
        try {
            OrderReader orderReader = new OrderReader("source");
            List<Order> existingOrders = new ArrayList<>();
            List<Product> productsToSell = new ArrayList<>();
            OrderStatus orderStatus = OrderStatus.COMPLETE;
            String orderID = "ID1";
            String productDetails = "Detail1";
            String customerDetails = "Customer1";

            Order newOrder = new Order(orderID, productDetails, customerDetails, orderStatus, productsToSell);
            orderReader.addOrder(newOrder, existingOrders);

            assertTrue(existingOrders.contains(newOrder));
        } catch (Exception e) {
            fail("Adding an order should not result in an exception");
        }
    }

    @Test
    void testReadValidJSON() {
        try {
            OrderReader orderReader = new OrderReader("./data/FileFound.json"); // File with valid orders
            List<Order> orders = orderReader.read();
            assertTrue(orders.size() > 0, "Orders should have been read");
        } catch (FileNotFoundException e) {
            fail("File should be found and read");
        }
    }

    @Test
    void testReadInvalidJSON() {
        try {
            OrderReader orderReader = new OrderReader("./data/InvalidFile.json"); // File with invalid order (invalid JSON)
            List<Order> orders = orderReader.read();
            assertTrue(orders.isEmpty(), "No orders should be read from invalid JSON");
        } catch (FileNotFoundException e) {
            fail("File should be found and read");
        }
    }

    @Test
    void testReadFromFileNotFoundException() {
        assertThrows(FileNotFoundException.class, () -> {
            OrderReader orderReader = new OrderReader("non_existent_file.json");
            orderReader.read();
        }, "FileNotFoundException should be thrown for non-existent file");
    }

    @Test
    void testReadOrderJSON() {
        try {
            OrderReader orderReader = new OrderReader("./data/FileFound.json");
            // 'valid_order.json' file must include valid order and product details
            List<Order> orders = orderReader.read();

            assertFalse(orders.isEmpty());

            Order order = orders.get(0); // Check the first order and product details
            assertEquals("o1", order.getOrderID());
            assertEquals("productDetails", order.getProductDetails());
            assertEquals("customerDetails", order.getCustomerDetails());

            List<Product> productsToSell = order.getProductsToSell();
            assertFalse(productsToSell.isEmpty());

            Product product = productsToSell.get(0); // Check the first product details
            assertEquals("productName", product.getName());
            assertEquals("productDetails", product.getDescription());
            assertEquals(34.0, product.getPrice());
            assertEquals(ProductType.CLOTHES, product.getProductType());
        } catch (FileNotFoundException e) {
            fail("Exception should not have been thrown.");
        }
    }
    @Test
    void testReadFile() {
        try {
            OrderReader orderReader = new OrderReader("./data/test.json");

            String contents = orderReader.readFile("./data/test.json");

            String expectedContents = "{\"orders\": [\n"
                    + "    {\n"
                    + "        \"orderID\": \"ID1\",\n"
                    + "        \"productsToSell\": [],\n"
                    + "        \"orderStatus\": \"COMPLETE\",\n"
                    + "        \"productDetails\": \"Detail1\",\n"
                    + "        \"customerDetails\": \"Customer1\"\n"
                    + "    },\n"
                    + "    {\n"
                    + "        \"orderID\": \"ID2\",\n"
                    + "        \"productsToSell\": [],\n"
                    + "        \"orderStatus\": \"PROCESSED\",\n"
                    + "        \"productDetails\": \"Detail2\",\n"
                    + "        \"customerDetails\": \"Customer2\"\n"
                    + "    }\n"
                    + "]}\n";

            assertEquals(expectedContents, contents, "Should read the entire contents from test.json with line break");
        } catch (FileNotFoundException e) {
            fail("Exception should not have been thrown.");
        }
    }

    @Test
    void testReadFileThrowsExceptionForInvalidFileName() {
        try {
            OrderReader orderReader = new OrderReader("non_existent_file.json");
            orderReader.readFile("non_existent_file.json");

            fail("FileNotFoundException should have been thrown.");
        } catch (FileNotFoundException e) {
            // FileNotFoundException is expected here, so the test passes
        }
    }
}