package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private Product testProduct;
    private Order order;
    private Product product1;
    private Product product2;


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

    @Test
    void removeProductNotInList() {
        Product product3 = new Product("3", "lipstick", 30.0, ProductType.MAKEUP);
        order.removeProductToSell(product3);

        List<Product> updatedProducts = order.getProductsToSell();
        assertTrue(updatedProducts.contains(product1));
        assertTrue(updatedProducts.contains(product2));
    }


    @Test
    void testSetProductType() {
        testProduct.setProductType(ProductType.ELECTRONICS);
        assertEquals(ProductType.ELECTRONICS, testProduct.getProductType());
    }

}