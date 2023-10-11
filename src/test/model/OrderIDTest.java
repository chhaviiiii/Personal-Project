package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderIDTest {
    private List<Product> products;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
    }

    @Test
    void generateOrderID_forClothes() {
        products.add(new Product("Shirt", "Blue shirt", 20, ProductType.CLOTHES));
        assertEquals("o1", OrderID.generateOrderID(products));
    }

    @Test
    void generateOrderID_forElectronics() {
        products.add(new Product("Laptop", "High performance laptop", 1000, ProductType.ELECTRONICS));
        assertEquals("o2", OrderID.generateOrderID(products));
    }

    @Test
    void generateOrderID_forFood() {
        products.add(new Product("Apple", "Green apple", 1, ProductType.FOOD));
        assertEquals("o3", OrderID.generateOrderID(products));
    }

    @Test
    void generateOrderID_forMakeup() {
        products.add(new Product("Lipstick", "Red lipstick", 15, ProductType.MAKEUP));
        assertEquals("o4", OrderID.generateOrderID(products));
    }

    @Test
    void generateOrderID_forEmptyList() {
        assertEquals("o", OrderID.generateOrderID(products));
    }
}