package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.ProductType;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product("Test Product", "A product for testing", 10.00, ProductType.ELECTRONICS);
    }

    @Test
    void testConstructor() {
        assertEquals("Test Product", testProduct.getName());
        assertEquals("A product for testing", testProduct.getDescription());
        assertEquals(10.00, testProduct.getPrice());
        assertEquals(ProductType.ELECTRONICS, testProduct.getProductType());
    }

    @Test
    void testGetName() {
        assertEquals("Test Product", testProduct.getName());
    }

    @Test
    void testGetDescription() {
        assertEquals("A product for testing", testProduct.getDescription());
    }

    @Test
    void testGetPrice() {
        assertEquals(10.00, testProduct.getPrice());
    }

}