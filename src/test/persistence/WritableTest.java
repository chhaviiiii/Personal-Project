package persistence;

import model.Order;
import model.OrderStatus;
import model.Product;
import model.ProductType;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WritableTest {

    @Test
    void testToJson() {
        List<Product> productsToSell = new ArrayList<>();
        Product product = new Product("name", "description", 10.0, ProductType.CLOTHES);
        productsToSell.add(product);

        Order order = new Order("orderID",
                "productDetails",
                "customerDetails",
                OrderStatus.COMPLETE,
                productsToSell);

        JSONObject json = order.toJson();

        assertEquals("orderID", json.getString("orderID"));
        assertEquals("productDetails", json.getString("productDetails"));
        assertEquals("customerDetails", json.getString("customerDetails"));
        assertEquals("COMPLETE", json.getString("orderStatus"));
        assertEquals(1, json.getJSONArray("productsToSell").length());
        assertEquals("name",
                json.getJSONArray("productsToSell").getJSONObject(0).getString("name"));
        assertEquals("description",
                json.getJSONArray("productsToSell").getJSONObject(0).getString("description"));
        assertEquals(10.0,
                json.getJSONArray("productsToSell").getJSONObject(0).getDouble("price"));
        assertEquals("CLOTHES",
                json.getJSONArray("productsToSell").getJSONObject(0).getString("productType"));
    }

    @Test
    void testProductsToJson() {
        Product product = new Product("name", "description", 10.0, ProductType.CLOTHES);

        JSONObject json = product.toJson();

        assertEquals("name", json.getString("name"));
        assertEquals("description", json.getString("description"));
        assertEquals(10.0, json.getDouble("price"));
        assertEquals("CLOTHES", json.getString("productType"));
    }
}
