package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import model.Product;
import model.Order;


import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private List<Product> products;
    private Order order;
    private Product product1;
    private Product product2;



    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
        product1 = new Product("1", "Book", 50.0, ProductType.CLOTHES);
        product2 = new Product("2","Pen", 10.0, ProductType.ELECTRONICS);

        products.add(product1);
        products.add(product2);

        Order order = new Order("1",
                "Books and pens",
                "John Doe", OrderStatus.PROCESSED, products);
    }

    @Test
    void getProductsToSellTest() {
        List<Product> fetchedProducts = order.getProductsToSell();
        assertTrue(fetchedProducts.contains(product1));
        assertTrue(fetchedProducts.contains(product2));
    }

    @Test
    void setProductToSellTest() {
        Product product3 = new Product("3", "poutine", 30.0,ProductType.FOOD);
        products.add(product3);
        order.setProductsToSell(products);

        List<Product> updatedProducts = order.getProductsToSell();
        assertTrue(updatedProducts.contains(product3));
    }

    @Test
    void addProductToSellTest() {
        Product product3 = new Product("3", "eyeliner", 30.0, ProductType.MAKEUP);
        order.addProductToSell(product3);
        List<Product> updatedProducts = order.getProductsToSell();
        assertTrue(updatedProducts.contains(product3));
    }

    @Test
    void removeProductToSellTest() {
        order.removeProductToSell(product1);
        List<Product> updatedProducts = order.getProductsToSell();
        assertFalse(updatedProducts.contains(product1));
    }

    @Test
    void updateOrderStatusTest() {
        order.updateOrderStatus(OrderStatus.SHIPPED);
        assertEquals(OrderStatus.SHIPPED, order.getOrderStatus());
    }

    @Test
    void getCustomerDetailsTest() {
        String customerDetails = order.getCustomerDetails();
        assertEquals("John Doe", customerDetails);
    }

    @Test
    void getProductDetailsTest() {
        String productDetails = order.getProductDetails();
        assertEquals("Books and pens", productDetails);
    }

    @Test
    void getOrderIDTest() {
        String orderID = order.getOrderID();
        assertEquals("1", orderID);
    }

    @Test
    void getOrderStatusTest() {
        OrderStatus orderStatus = order.getOrderStatus();
        assertEquals(OrderStatus.PROCESSED, orderStatus);
    }
    }