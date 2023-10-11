package ui;

import model.Order;
import model.OrderID;
import model.OrderStatus;
import model.Product;
import model.ProductType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderTracker {
    private List<Order> orders;
    private List<Product> productsToSell;

    @SuppressWarnings("methodlength")
    public OrderTracker() {
        orders = new ArrayList<>();
    }

    @SuppressWarnings({"methodlength", "checkstyle:OperatorWrap", "checkstyle:SuppressWarnings"})
    public void start() {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to the Order Tracker.");

        boolean runTracker = true;
        while (runTracker) {
            System.out.println("\n Please select an option: ");
            System.out.println("1. Create an order");
            System.out.println("2. Update an order status");
            System.out.println("3. View all active orders");
            System.out.println("4. Exit");

            if (!input.hasNextInt()) {
                System.out.println("Invalid input, Please enter a valid number!");
                input.next();
            }
            int operation = input.nextInt();

            if (operation == 1) {
                // Code to create an order
                productsToSell = new ArrayList<>();
                boolean moreProducts = true;
                while (moreProducts) {
                    input.nextLine();
                    System.out.println("Enter product name:");
                    String productName = input.nextLine();
                    System.out.println("Enter product description:");
                    String productDescription = input.nextLine();
                    System.out.println("Enter product price:");
                    double productPrice = input.nextDouble();
                    System.out.println("Enter product type # ( 1.CLOTHES, 2.ELECTRONICS, 3.FOOD, 4.MAKEUP):");
                    int productTypeIndex = input.nextInt();
                    ProductType productType = ProductType.values()[productTypeIndex - 1];
                    Product product = new Product(productName, productDescription, productPrice, productType);
                    productsToSell.add(product);
                    System.out.println("Do you want to add more product? enter (1 for Yes / 2 for No)");
                    moreProducts = input.nextInt() == 1;
                }

                System.out.println("Enter customer details:");
                input.nextLine();
                String customerDetails = input.nextLine();
                String orderID = OrderID.generateOrderID(productsToSell);
                Order newOrder = new Order(orderID,
                        "Products in Order: " +
                                productsToSell.size(),
                        customerDetails,
                        OrderStatus.PLACED,
                        productsToSell);
                orders.add(newOrder);
                System.out.println("Order created successfully with Order ID: " + orderID);

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