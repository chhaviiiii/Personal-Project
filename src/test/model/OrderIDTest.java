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
    void testGenerateOrderID() {
        Product product1 = new Product("1", "Book", 50.0, ProductType.CLOTHES);
        Product product2 = new Product("2","Pen", 10.0, ProductType.ELECTRONICS);
        Product product3 = new Product("3", "Pizza", 15.0, ProductType.FOOD);
        Product product4 = new Product("4", "Lipstick", 35.0, ProductType.MAKEUP);

        ArrayList<Product> products1 = new ArrayList<>();
        products1.add(product1);

        ArrayList<Product> products2 = new ArrayList<>();
        products2.add(product2);

        ArrayList<Product> products3 = new ArrayList<>();
        products3.add(product3);

        ArrayList<Product> products4 = new ArrayList<>();
        products4.add(product4);

        assertEquals("o1", OrderID.generateOrderID(products1));
        assertEquals("o2", OrderID.generateOrderID(products2));
        assertEquals("o3", OrderID.generateOrderID(products3));
        assertEquals("o4", OrderID.generateOrderID(products4));
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
    @Test
    void testGenerateOrderID_withMultipleDifferentProducts() {
        products.add(new Product("1", "Shirt", 50, ProductType.CLOTHES));
        products.add(new Product("2", "Laptop", 1000, ProductType.ELECTRONICS));
        products.add(new Product("3", "Apple", 1, ProductType.FOOD));
        assertEquals("o3", OrderID.generateOrderID(products)); // or whatever logic you have for multiple different type products
    }

    @Test
    void testGenerateOrderID_withMultipleSameProducts() {
        products.add(new Product("1", "Shirt", 50, ProductType.CLOTHES));
        products.add(new Product("2", "Pants", 40, ProductType.CLOTHES));
        assertEquals("o1", OrderID.generateOrderID(products));
    }


    @Test
    void testGenerateOrderID_withInvalidProductType() {
        products.add(new Product("1", "Shirt", 50, null));
        assertEquals("o", OrderID.generateOrderID(products));
    }
}