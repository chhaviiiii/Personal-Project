package persistence;

import model.Order;
import model.OrderStatus;
import model.Product;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderWriterTest {
    private static final String TEST_FILE = "./data/test.json";
    private OrderWriter writer;
    private List<Order> orders;
    private List<Product> products;


    @BeforeEach
    void setup() throws FileNotFoundException {
        writer = new OrderWriter(TEST_FILE);
        orders = new ArrayList<>();
        products = new ArrayList<>();
        // Populate 'orders' with some dummy data
    }

    @AfterEach
    void tearDown() {
        new File(TEST_FILE).delete(); // Clean up the test file after each test
    }

    @Test
    void testWriterInvalidFile() {
        try {
            OrderWriter writer = new OrderWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyOrders() {
        try {
            writer.open();
            writer.write(orders);
            writer.close();

            OrderReader reader = new  OrderReader(TEST_FILE);
            orders = reader.read();
            assertEquals(0, orders.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralOrders() {
        try {
            // Assuming Order class has a constructor and methods to add details
            Order order1 = new Order("o1", "productDetails", "customerDetails", OrderStatus.PROCESSED, products);
            Order order2 = new Order("orderID", "productDetails", "customerDetails",OrderStatus.PLACED, products);
            orders.add(order1);
            orders.add(order2);

            writer.open();
            writer.write(orders);
            writer.close();

            OrderReader reader = new OrderReader(TEST_FILE);
            orders = reader.read();
            assertEquals(2, orders.size());
            // Additional assertions to check the contents of the orders

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
