package persistence;

import model.Order;
import model.OrderStatus;
import model.Product;
import model.ProductType;
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

