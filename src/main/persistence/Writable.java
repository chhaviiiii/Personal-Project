package persistence;

import org.json.JSONObject;

// Interface to return product and orders as JSON Objects
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
