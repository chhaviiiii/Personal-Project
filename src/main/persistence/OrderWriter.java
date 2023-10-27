package persistence;

import model.Order;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

// Represents a writer that writes JSON representation of an order tracker to a file
public class OrderWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: Constructs a writer to write to the specified destination file
    public OrderWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: Opens the writer; throws FileNotFoundException if the destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
// EFFECTS: Writes JSON representation of the list of orders to the file
    public void write(List<Order> orders) {
        try {
            open();  // Ensure the writer is opened

            JSONObject jsonObject = new JSONObject();  // Create a top-level JSON object
            JSONArray jsonArray = new JSONArray();

            for (Order order : orders) {
                JSONObject json = order.toJson();
                jsonArray.put(json);
            }

            jsonObject.put("orders", jsonArray);  // Add the "orders" key and associate it with the JSON array

            saveToFile(jsonObject.toString(TAB));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        close();  // Ensure the writer is closed after writing
    }


    // MODIFIES: this
    // EFFECTS: Closes the writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: Writes a string to the file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
