package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


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
        order = new Order("1", "Books and pens", "John Doe", OrderStatus.PROCESSED, products);
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

    @Test
    void testAddingMultipleProducts() {
        Product product3 = new Product("3", "highlighter", 20.0, ProductType.MAKEUP);
        Product product4 = new Product("4", "apple", 5.0, ProductType.FOOD);
        order.addProductToSell(product3);
        order.addProductToSell(product4);
        List<Product> updatedProducts = order.getProductsToSell();
        assertTrue(updatedProducts.contains(product3));
        assertTrue(updatedProducts.contains(product4));
    }

    @Test
    void testRemovingProductThatDoesntExist() {
        Product product3 = new Product("3", "blush", 30.0, ProductType.MAKEUP);
        order.removeProductToSell(product3);
        List<Product> updatedProducts = order.getProductsToSell();
        assertFalse(updatedProducts.contains(product3));
        // as product3 was never added, it should not be there
    }

    @Test
    void testOrderStatusTransition() {
        order.updateOrderStatus(OrderStatus.SHIPPED);
        assertEquals(OrderStatus.SHIPPED, order.getOrderStatus());

        order.updateOrderStatus(OrderStatus.DELIVERED);
        assertEquals(OrderStatus.DELIVERED, order.getOrderStatus());

        order.updateOrderStatus(OrderStatus.COMPLETE);
        assertEquals(OrderStatus.COMPLETE, order.getOrderStatus());
    }

    @Test
    void testOrderIDUniqueness() {
        Order order2 = new Order("2",
                "Pens and Notebooks",
                "Jane Doe",
                OrderStatus.PROCESSED,
                new ArrayList<>(List.of(new Product("5", "Notebook", 30.0, ProductType.MAKEUP))));
        assertNotEquals(order.getOrderID(), order2.getOrderID());
    }

    @Test
    void testGetProductDetailsForEmptyOrder() {
        Order emptyOrder = new Order("3", "", "Jane Doe", OrderStatus.DELIVERED, new ArrayList<>());
        String productDetails = emptyOrder.getProductDetails();
        assertEquals("", productDetails);
        // as there are no products, product details should be empty
    }

    @Test
    void testGetCustomerDetailsForUnassignedOrder() {
        Order unassignedOrder = new Order("4", "Books", "", OrderStatus.PROCESSED, new ArrayList<>(List.of(product1)));
        String customerDetails = unassignedOrder.getCustomerDetails();
        assertEquals("", customerDetails);
        // as there is no customer, customer details should be empty
    }

    @Test
    public void testToString() {
        // Setup
        List<Product> products = new ArrayList<>();
        products.add(new Product("Book", "Fiction book", 12.99, ProductType.FOOD));
        products.add(new Product("Pen", "Blue pen", 1.99, ProductType.MAKEUP));

        Order order = new Order("123", "Order for John Doe", "John Doe", OrderStatus.PLACED, products);

        // Expected string
        String expected = "Order ID: 123, Customer Details: John Doe, Order Status: PLACED\n\t" +
                "Product Name: Book, Type: BOOK, Price: 12.99\n\t" +
                "Product Name: Pen, Type: STATIONARY, Price: 1.99";

        // Assert
        assertEquals(expected, order.toString());
    }
}
