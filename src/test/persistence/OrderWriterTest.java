package persistence;

import model.Order;
import model.OrderStatus;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
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
    public void testOpen() {
        try {
            orderWriter.open();
            // If no exception is thrown, consider the test successful
        } catch (FileNotFoundException e) {
            fail("Test failed: " + e.getMessage());
        }
    }

    @Test
    void testOpenWithFileNotFoundException() {
        assertThrows(FileNotFoundException.class, () -> {
            OrderWriter orderWriter = new OrderWriter("non_existent_directory/test_orders.json");
            orderWriter.open();
        });
    }


    @Test
    void testWriteValidOrders() {
        List<Order> orders = createSampleOrders();

        orderWriter.write(orders);

        String fileContent = readFileContent(testFilePath);
        JSONObject expectedJsonObject = new JSONObject();
        JSONArray expectedJsonArray = new JSONArray();
        for (Order order : orders) {
            expectedJsonArray.put(order.toJson());
        }
        expectedJsonObject.put("orders", expectedJsonArray);

        assertEquals(expectedJsonObject.toString(4), fileContent);
    }

    @Test
    public void testWriteEmptyOrders() throws FileNotFoundException {
        List<Order> orders = new ArrayList<>();
        orderWriter.write(orders);

        // Read the content of the file and check if it's an empty JSON array
        String fileContent = readFileContent(testFilePath);
        assertEquals("{\"orders\": []}", fileContent);
    }

    private String readFileContent(String filePath) {
        try {
            Scanner scanner = new Scanner(new File(filePath));
            String fileContent = scanner.useDelimiter("\\A").next();
            scanner.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Test
    void testWriteWithSingleOrder() {
        List<Order> orders = new ArrayList<>();
        Order order = new Order("1",
                "Product1",
                "Customer1",
                OrderStatus.PLACED,
                new ArrayList<>());
        orders.add(order);
        orderWriter.write(orders);

        String fileContent = readFileContent(testFilePath);

        JSONObject expectedJsonObject = new JSONObject();
        JSONArray expectedJsonArray = new JSONArray();
        expectedJsonArray.put(order.toJson());
        expectedJsonObject.put("orders", expectedJsonArray);

        assertEquals(expectedJsonObject.toString(4), fileContent);
    }


    @Test
    void testWriteWithFileNotFoundException() {
        assertThrows(RuntimeException.class, () -> {
            OrderWriter orderWriter = new OrderWriter("nonExistentFile.txt");
            List<Order> orders = new ArrayList<>();
            Order order1 = new Order("1", "Product1", "Customer1", OrderStatus.PLACED, new ArrayList<>());
            orders.add(order1);
            orderWriter.write(orders);
        });
    }
}





