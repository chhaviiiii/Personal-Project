package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import model.Product;
import model.Order;


import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private Order order;
    private List<Product> productsToSell;
    private Product product;
    private OrderID orderID;

    @BeforeEach
    public void setup() {
        product = new Product("p1", "Nice Product", 150.0, ProductType.ELECTRONICS);
        productsToSell = new ArrayList<>();
        productsToSell.add(product);
        String orderID = OrderID.generateOrderID(productsToSell);
        order = new Order(orderID, "black phone", "John Doe", OrderStatus.PLACED, productsToSell);
    }

    @Test
    public void testGetOrderID() {
        String orderID = OrderID.generateOrderID(productsToSell);
        assertEquals(orderID, order.getOrderID());
    }

    @Test
    public void testGetProductDetails() {
        assertEquals("Nice product", order.getProductDetails());
    }

    @Test
    public void testGetCustomerDetails() {
        assertEquals("John Doe", order.getCustomerDetails());
    }

    @Test
    public void testSetProductsToSell() {
        Product newProduct = new Product("P2", "Great Product", 200.0, ProductType.MAKEUP);
        List<Product> newProductsToSell = new ArrayList<>();
        newProductsToSell.add(newProduct);

        order.setProductsToSell(newProductsToSell);
        assertEquals("o4", order.getOrderID()); // Also check that the OrderID has been updated.

        assertEquals(newProductsToSell, order.getProductsToSell());
    }

    @Test
    public void testAddProductToSell() {
        Product newProduct = new Product("P3", "Perfect Product", 300.0, ProductType.FOOD);
        order.addProductToSell(newProduct);

        assertEquals(2, order.getProductsToSell().size());
        assertEquals(newProduct, order.getProductsToSell().get(1));
        assertEquals("o3", order.getOrderID()); // Also check that the OrderID has been updated.
    }

    @Test
    public void testRemoveProductToSell() {
        order.removeProductToSell(product);
        assertEquals(0, order.getProductsToSell().size());
    }

    @Test
    public void testUpdateOrderStatus() {
        order.updateOrderStatus(OrderStatus.SHIPPED);
        assertEquals(OrderStatus.SHIPPED, order.getOrderStatus());
    }

    @Test
    public void testGetName() {
        assertEquals("p1", product.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Nice Product", product.getDescription());
    }

    @Test
    public void testGetPrice() {
        assertEquals(150.0, product.getPrice());
    }

    @Test
    public void testGetProductType() {
        assertEquals(ProductType.ELECTRONICS, product.getProductType());
    }

    @Test
    public void testSetProductType() {
        product.setProductType(ProductType.CLOTHES);
        assertEquals(ProductType.CLOTHES, product.getProductType());
    }
    @Test
    public void testEnumValues() {
        assertEquals(5, OrderStatus.values().length);
        assertEquals(OrderStatus.PLACED, OrderStatus.values()[0]);
        assertEquals(OrderStatus.PROCESSED, OrderStatus.values()[1]);
        assertEquals(OrderStatus.SHIPPED, OrderStatus.values()[2]);
        assertEquals(OrderStatus.DELIVERED, OrderStatus.values()[3]);
        assertEquals(OrderStatus.COMPLETE, OrderStatus.values()[4]);
    }
}