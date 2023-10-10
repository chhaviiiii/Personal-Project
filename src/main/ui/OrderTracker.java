package ui;

import model.Order;
import model.OrderStatus;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderTracker {
    private List<Order> orders;
    private List<Product> productsToSell;

    @SuppressWarnings("methodlength")
    public OrderTracker() {
        orders = new ArrayList<>();
        productsToSell = new ArrayList<>(); // initialize this list as well to fix the NullPointerException issue
    }

    @SuppressWarnings({"methodlength", "checkstyle:OperatorWrap", "checkstyle:SuppressWarnings"})
    public void start() {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to the Order Tracker.");

        boolean runTracker = true;
        while (runTracker) {
            System.out.println("\n(OrderID: o1) Please select an option: ");
            System.out.println("1. Create an order");
            System.out.println("2. Update an order status");
            System.out.println("3. View all active orders");
            System.out.println("4. Exit");

            while (!input.hasNextInt()) {
                System.out.println("Invalid input, Please enter a valid number!");
                input.next();
            }
            int operation = input.nextInt();

            if (operation == 1) {

                // Code to create an order
                input.nextLine();
                System.out.println("Enter product details:");
                String productDetails = input.nextLine();
                System.out.println("Enter customer details:");
                String customerDetails = input.nextLine();
                // Create a new order with a predefined ID and status of PROCESSING

                Order newOrder = new Order("o1", productDetails, customerDetails, OrderStatus.PLACED, productsToSell);
                orders.add(newOrder);

                System.out.println("Order created successfully.");

            } else if (operation == 2) {

                // Code to update an order
                // Code to update an order
                System.out.println("Enter order ID:");
                String orderId = input.next();
                Order orderToUpdate = null;
                for (Order order : orders) {
                    if (order.getOrderID().equals(orderId)) {
                        orderToUpdate = order;
                        break;
                    }
                }
                if (orderToUpdate == null) {
                    System.out.println("No order found with ID: " + orderId);
                } else {
                    System.out.println("Enter new status # " +
                            "(1.PLACED, 2.PROCESSED, 3.SHIPPED, 4.DELIVERED, 5.COMPLETE):");
                    int statusNum = input.nextInt();
                    OrderStatus newStatus = OrderStatus.values()[statusNum - 1];
                    orderToUpdate.updateOrderStatus(newStatus);

                    // Checking if New Status is COMPLETE
                    if (newStatus.equals(OrderStatus.COMPLETE)) {
                        orders.remove(orderToUpdate);
                        System.out.println("Order status updated to COMPLETE and it's deactivated now.");
                    } else {
                        System.out.println("Order updated successfully.");
                    }
                }

            } else if (operation == 3) {
                // Code to view all active orders
                System.out.println("Active orders:");
                for (Order order : orders) {
                    System.out.println("Order ID: " +
                            order.getOrderID() +
                            ", Product Details: " +
                            order.getProductDetails() +
                            ", Customer Details: " +
                            order.getCustomerDetails() +
                            ", Status: " + order.getOrderStatus());
                }

            } else if (operation == 4) {

                // Code to exit
                runTracker = false;

            } else {

                System.out.println("Invalid Option Selected!");

            }
        }

        input.close();
    }
}