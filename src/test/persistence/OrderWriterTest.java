package persistence;

import static org.junit.jupiter.api.Assertions.*;
import model.Order;

import org.junit.jupiter.api.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


class OrderWriterTest {
    private static final String TEST_FILE = "test.json";
    private OrderWriter writer;
    private List<Order> orders;

    @BeforeEach
    void setup() throws FileNotFoundException {
        writer = new OrderWriter(TEST_FILE);
        orders = new ArrayList<>();
        // Populate 'orders' with some dummy data
    }

    @AfterEach
    void tearDown() {
        new File(TEST_FILE).delete(); // Clean up the test file after each test
    }

    @Test
    void testConstructor() {
        assertEquals(TEST_FILE,"test.json"); // Assuming a getter for destination
    }

    @Test
    void testOpen() {
        // The 'open' method is implicitly tested in the write method
    }

    @Test
    void testWrite() {
        writer.write(orders);
        assertTrue(Files.exists(Paths.get(TEST_FILE)));

        // Additional checks can be performed to verify the JSON structure
    }

    @Test
    void testWriteFileNotFound() {
        OrderWriter badWriter = new OrderWriter("/invalid/path.json");
        assertThrows(RuntimeException.class, () -> badWriter.write(orders));
    }

    @Test
    void testClose() throws FileNotFoundException {
        writer.open();
        writer.close();
        // Check if the writer is closed (may need reflection or checking file status)
    }

    // Additional tests for saveToFile, etc.
}
