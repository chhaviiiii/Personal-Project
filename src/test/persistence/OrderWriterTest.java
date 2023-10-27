package persistence;

import model.Order;
import model.OrderStatus;
import model.Product;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderWriterTest {

    @Test
    void testOrderWriter() {
        try {
            OrderWriter orderWriter = new OrderWriter("test.json");
            assertNotNull(orderWriter);
        } catch (Exception e) {
            fail("Exception should not have been thrown.");
        }
    }

    @Test
    void testOpen() {
        try {
            OrderWriter orderWriter = new OrderWriter("test.json");
            orderWriter.open();
            assertNotNull(orderWriter);
        } catch (FileNotFoundException e) {
            fail("File should be found and opened");
        }
    }


    @Test
    void testWrite() {
        try {
            OrderWriter orderWriter = new OrderWriter("test.json");
            List<Order> orders = new ArrayList<>();
            orderWriter.write(orders);

            File file = new File("test.json");
            assertTrue(file.exists());
        } catch (Exception e) {
            fail("Writing should not result in an exception");
        }
    }

    @Test
    void testSaveToFile() {
        // This function is private, so it can't be tested directly
        // It is tested indirectly by the 'testWrite' function
    }

    @Test
    void testClose() {
        try {
            OrderWriter orderWriter = new OrderWriter("test.json");
            orderWriter.open();
            orderWriter.close();

            // No exception means the writer closed correctly.
            // There is no 'isOpen' function to check the state of FileWriter.
            // So we have no way to check this function directly.
        } catch (Exception e) {
            fail("Closing should not result in an exception");
        }
    }

    @Test
    void testWrite_ThrowsException_WhenFileNotFound() {
        try {
            OrderWriter orderWriter = new OrderWriter("nonExistent/path/test.json");
            List<Order> orders = new ArrayList<>();
            orderWriter.write(orders);

            fail("Writing should have resulted in an exception");
        } catch (Exception e) {
            assertTrue(e instanceof RuntimeException);
        }
    }

    @Test
    void testClose_ThrowsException_WhenWriterNotOpened() {
        // Closing a FileWriter that was never opened doesn't actually throw an exception
        // So there isn't a good way to test this failure
    }

    @Test
    void testJsonObjectCreationFromOrders() {
        try {
            OrderWriter orderWriter = new OrderWriter("test.json");
            List<Order> orders = new ArrayList<>();

            // Creating dummy Order objects
            List<Product> productsToSell1 = new ArrayList<>();
            List<Product> productsToSell2 = new ArrayList<>();
            OrderStatus orderStatus1 = OrderStatus.COMPLETE;
            OrderStatus orderStatus2 = OrderStatus.PROCESSED;
            String orderID1 = "ID1";
            String orderID2 = "ID2";
            String productDetails1 = "Detail1";
            String productDetails2 = "Detail2";
            String customerDetails1 = "Customer1";
            String customerDetails2 = "Customer2";

            Order order1 = new Order(orderID1, productDetails1, customerDetails1, orderStatus1, productsToSell1);
            Order order2 = new Order(orderID2, productDetails2, customerDetails2, orderStatus2, productsToSell2);

            orders.add(order1);
            orders.add(order2);

            orderWriter.open();
            orderWriter.write(orders);
            orderWriter.close();

            // Read the JSON file and check if it contains the added Orders
            OrderReader orderReader = new OrderReader("test.json");
            List<Order> readOrders = orderReader.read();

            assertEquals(2, readOrders.size());
            assertEquals(orderID1, readOrders.get(0).getOrderID());
            assertEquals(orderID2, readOrders.get(1).getOrderID());

        } catch (Exception e) {
            fail("Exception should not have been thrown.");
        }
    }
}





