package persistence;

import model.Order;
import model.OrderStatus;
import model.Product;
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
            OrderReader orderReader = new OrderReader("./data/InvalidFIle.json");
            assertNotNull(orderReader.read());
        } catch (FileNotFoundException e) {
            fail("File should be found");
        } catch (JSONException e) {
            fail("Parsing the JSON should not result in an exception");
        }
    }

    @Test
    void testReadFile() {
        try {
            OrderReader orderReader = new OrderReader("./data/ValidFile.json");
            assertNotNull(orderReader.readFile("./data/InvalidFile.json"));
        } catch (FileNotFoundException e) {
            fail("File should be found");
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
    void testReadInvalidJSON() {
        try {
            OrderReader orderReader = new OrderReader("./data/InvalidFile.json");
            assertEquals(0, orderReader.read().size(), "List should be empty for invalid JSON");
        } catch (FileNotFoundException e) {
            fail("File should be found");
        }


    }

}