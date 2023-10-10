package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private Order order;
    private List<Product> productsToSell;
    private Product product;


    @BeforeEach
    public void setup() {
        product = new Product("p1", "Good Product", 100.0);
        productsToSell = new ArrayList<>();
        productsToSell.add(product);

        order = new Order("o1", "Beautiful Product", "John Doe", OrderStatus.PLACED, productsToSell);
    }

    @Test
    public void testGetOrderID() {
        assertEquals("o1", order.getOrderID());
    }

    @Test
    public void testGetProductDetails() {
        assertEquals("Beautiful Product", order.getProductDetails());
    }

    @Test
    public void testGetCustomerDetails() {
        assertEquals("John Doe", order.getCustomerDetails());
    }

    @Test
    public void testSetProductsToSell() {
        Product newProduct = new Product("p2", "Better Product", 200.0);
        List<Product> newProductsToSell = new ArrayList<>();
        newProductsToSell.add(newProduct);

        order.setProductsToSell(newProductsToSell);

        assertEquals(newProductsToSell, order.getProductsToSell());
    }

    @Test
    public void testAddProductToSell() {
        Product newProduct = new Product("p3", "Best Product", 300.0);
        order.addProductToSell(newProduct);

        assertEquals(2, order.getProductsToSell().size());
        assertEquals(newProduct, order.getProductsToSell().get(1));
    }

    @Test
    public void testRemoveProductToSell() {
        order.removeProductToSell(product);

        assertEquals(0, order.getProductsToSell().size());
    }

    @Test
    public void testUpdateOrderStatus(){
        order.updateOrderStatus(OrderStatus.SHIPPED);
        assertEquals(OrderStatus.SHIPPED, order.getOrderStatus()); // Assuming you have a getter method getOrderStatus() in your Order class
    }

}