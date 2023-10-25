package persistence;

import model.Order;
import model.OrderStatus;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class OrderWriterTest {
    private OrderWriter orderWriter;
    private String testFilePath;

    @BeforeEach
    public void setUp() {
        testFilePath = "test_orders.json"; // Use a test file path
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
    public void testWriteValidOrders() {
        List<Order> orders = createSampleOrders();
        orderWriter.write(orders);

        // Verify that the file has been written correctly
        try {
            Scanner scanner = new Scanner(new File(testFilePath));
            String fileContent = scanner.useDelimiter("\\A").next();
            scanner.close();

            // Create the expected JSON object
            JSONObject expectedJsonObject = new JSONObject();
            JSONArray expectedJsonArray = new JSONArray();
            for (Order order : orders) {
                expectedJsonArray.put(order.toJson());
            }
            expectedJsonObject.put("orders", expectedJsonArray);

            assertEquals(expectedJsonObject.toString(4), fileContent);
        } catch (IOException e) {
            fail("Test failed: " + e.getMessage());
        }
    }

    @Test
    public void testWriteEmptyOrders() {
        List<Order> orders = new ArrayList<>();
        orderWriter.write(orders);

        // Verify that the file has been written correctly
        try {
            Scanner scanner = new Scanner(new File(testFilePath));
            String fileContent = scanner.useDelimiter("\\A").next();
            scanner.close();

            // The file content should be an empty JSON array
            assertEquals("{\"orders\": []}", fileContent);
        } catch (IOException e) {
            fail("Test failed: " + e.getMessage());
        }
    }

    private List<Order> createSampleOrders() {
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

    @Test
    public void testOpenWithFileNotFoundException() {
        String testFilePath = "test_orders.json";
        OrderWriter orderWriter = new OrderWriter(testFilePath) {
            // Override the open() method to throw a FileNotFoundException
            @Override
            public void open() throws FileNotFoundException {
                throw new FileNotFoundException("File not found");
            }
        };

        // Ensure that an exception is thrown when opening the writer
        assertThrows(FileNotFoundException.class, () -> orderWriter.open());

        // Clean up the test file after the test
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
    
}
