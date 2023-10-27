package ui;

import model.*;
import org.json.JSONException;
import persistence.OrderReader;
import persistence.OrderWriter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Application that is able to create, track and update orders.
public class OrderTracker {
    private final List<Order> orders;
    private static final String JSON_STORE = "./data/InvalidFile.json";

    private OrderWriter jsonWriter;
    private OrderReader jsonReader;

    public OrderTracker() {
        orders = new ArrayList<>();
        jsonWriter = new OrderWriter(JSON_STORE);
        jsonReader = new OrderReader(JSON_STORE);
    }

    // REQUIRES: A Scanner input object for user interaction.
    // EFFECTS: Displays a menu with options to the user, (1, 2, 3, or 4).
    // If the user enters an invalid input, it displays an error message and consumes the newline character.
    public int displayMenu(Scanner input) {
        System.out.println("\n Please select an option: ");
        System.out.println("1. Create an order");
        System.out.println("2. Update an order status");
        System.out.println("3. View all active orders");
        System.out.println("4. Save Active Orders");
        System.out.println("5. Load Orders");
        System.out.println("6. Exit");


        if (!input.hasNextInt()) {
            System.out.println("Invalid input, Please enter a valid number!");
            input.nextLine();
            return -1;
        }
        int result = input.nextInt();
        input.nextLine();  // consume the remaining newline
        return result;
    }

    // REQUIRES: A list of Product objects (productsToSell). The list can be null.
    // MODIFIES: This method can modify the value of orderID.
    // EFFECTS: Collects product and customer details to create a new order.
    public void createOrder(Scanner input) {
        List<Product> productsToSell = new ArrayList<>();

        boolean moreProducts = true;
        while (moreProducts) {
            System.out.println("Delivery Instructions(Optional): ");
            Product product = getProduct(input);
            if (product != null) {
                productsToSell.add(product);
            }

            System.out.println("Do you want to add more products?");
            moreProducts = askForMoreProducts(input);
        }

        String customerDetails = getCustomerDetails(input);

        String orderID = OrderID.generateOrderID(productsToSell);
        Order newOrder = new Order(orderID, " (in Order): "
                + productsToSell.size(),
                customerDetails,
                OrderStatus.PLACED, productsToSell);
        orders.add(newOrder);
        System.out.println("Order created successfully with Order ID: " + orderID);


    }

    // REQUIRES: A Scanner input object for user interaction.
    // EFFECTS: Collects product information from the user, including product name, product details, and product price.
    // If the user enters an invalid price (non-numeric), it displays an error message,
    // Returns a Product object with the collected details if input is valid.
    private Product getProduct(Scanner input) {
        Product product = null;

        input.nextLine(); // consume the leftover newline
        System.out.println("Enter product name: ");
        String productName = input.nextLine();

        System.out.println("Enter product details: ");
        String productDetail = input.nextLine();

        System.out.println("Enter product price: ");
        double productPrice = getDouble(input);
        if (productPrice == -1) {
            return null;
        }

        System.out.println("Enter product type # (1. CLOTHES, 2. ELECTRONICS, 3. FOOD, 4. MAKEUP): ");
        int productTypeIndex = getInt(input);
        if (productTypeIndex < 1 || productTypeIndex > 4) {
            return product;
        }

        ProductType productType = ProductType.values()[productTypeIndex - 1];
        product = new Product(productName, productDetail, productPrice, productType);

        return product;
    }

    // REQUIRES: A Scanner input object for user interaction.
    // EFFECTS: Reads a double from the input.
    // returns -1, and consumes the input. Otherwise, it returns the read double.
    private double getDouble(Scanner input) {
        if (!input.hasNextDouble()) {
            System.out.println("Invalid input, Please enter a valid number!");
            input.next();
            return -1;
        }
        return input.nextDouble();
    }

    // REQUIRES: A Scanner input object for user interaction.
    // EFFECTS: Reads an integer from the input. If the user enters an invalid input (non-integer),
    // returns -1, and consumes the input. If the integer is not within the range [1, 4],
    // Otherwise, it returns the read integer.
    private int getInt(Scanner input) {
        if (!input.hasNextInt()) {
            System.out.println("Invalid input, Please enter a valid number!");
            input.next();
            return -1;
        }
        int number = input.nextInt();
        if (!(number >= 1 && number <= 6)) {
            System.out.println("Invalid option selected!");
            return -1;
        }
        return number;
    }


    // REQUIRES: A Scanner input object for user interaction.
    // EFFECTS: Asks the user if they want to add more products. Reads the user's choice (1 for Yes, 2 for No)
    private boolean askForMoreProducts(Scanner input) {
        System.out.println("Do you want to add more product? enter (1 for Yes / 2 for No)");
        int addMoreProductsNum = getInt(input);
        input.nextLine(); // consume the remaining newline
        return addMoreProductsNum == 1;
    }

    // REQUIRES: A Scanner input object for user interaction.
    // EFFECTS: Prompts the user to enter customer details and returns the entered details as a String.
    private String getCustomerDetails(Scanner input) {
        System.out.println("Enter customer details:");
        return input.nextLine();
    }


    // EFFECTS: Displays information about all active orders in the orders list, including order ID, product details
    public void viewAllActiveOrders() {
        System.out.println("Active orders:");
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getOrderID() + ","
                    + "Product Details: "
                    + order.getProductDetails()
                    + ", Customer Details: "
                    + order.getCustomerDetails()
                    + ", Status: " + order.getOrderStatus());
        }
    }

    // REQUIRES: A Scanner input object for user interaction.
    // MODIFIES: The status of an order in the orders list.
    // EFFECTS: Prompts the user to enter an order ID. Updates the status of the order with the given ID.
    // Removes the order from the list if the new status is set to COMPLETE.
    public void updateOrderStatus(Scanner input) {
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
            return;
        }

        System.out.println("Enter new status # (1.PLACED, 2.PROCESSED, 3.SHIPPED, 4.DELIVERED, 5.COMPLETE):");
        int statusNum = input.nextInt();
        OrderStatus newStatus = OrderStatus.values()[statusNum - 1];
        orderToUpdate.updateOrderStatus(newStatus);

        if (newStatus.equals(OrderStatus.COMPLETE)) {
            orders.remove(orderToUpdate);
            System.out.println("Order status updated to COMPLETE and it's deactivated now.");
        } else {
            System.out.println("Order updated successfully.");
        }
    }

    // EFFECTS: Saves the list of orders to a JSON file
    public void saveActiveOrders() {
        try {
            jsonWriter.open();
            jsonWriter.write(orders);
            jsonWriter.close();
            System.out.println("Saved active orders successfully into" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Error saving active orders to the file.");
        }
    }


    // EFFECTS: Loads active orders from a JSON file
    public void loadActiveOrders() {
        try {
            List<Order> loadedOrders = jsonReader.read();

            if (loadedOrders != null) {
                orders.clear(); // Clear existing orders
                orders.addAll(loadedOrders); // Add the loaded orders to the existing list
                System.out.println("Loaded active orders successfully from " + JSON_STORE);
            } else {
                System.out.println("No valid orders found in the file.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found. " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error: Invalid JSON format. " + e.getMessage());
        }
    }


    // REQUIRES: A Scanner input object for user interaction.
    // MODIFIES: Closes the provided Scanner input.
    // EFFECTS: Displays an exit message and closes the Scanner input.
    public void exit(Scanner input) {
        System.out.println("Exiting the program...");
        input.close();
    }

    // EFFECTS: Initiates the main functionality of the application, including displaying the welcome message and menu.
    public void start() {
        boolean runTracker = true;

        Scanner input = new Scanner(System.in);

        while (runTracker) {
            System.out.println("Welcome to the Order Tracker.");
            int operation = displayMenu(input);

            if (operation == 1) {
                createOrder(input);
            } else if (operation == 2) {
                updateOrderStatus(input);
            } else if (operation == 3) {
                viewAllActiveOrders();
            } else if (operation == 4) {
                saveActiveOrders();
            } else if (operation == 5) {
                loadActiveOrders();
            } else if (operation == 6) {
                exit(input);
                runTracker = false;
            } else {
                System.out.println("Invalid Option Selected!");
            }
        }

    }
}




