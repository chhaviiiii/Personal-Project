package model;

// Represents all possible statuses an order can take
// These are the steps in an order's lifecycle and determine its current state in the fulfilment process
public enum OrderStatus {
    PLACED,     // The order has been placed by the customer
    PROCESSED,  // The order has been processed by the business
    SHIPPED,    // The order has been dispatched for delivery
    DELIVERED,  // The order has been delivered to the customer
    COMPLETE    // The order process has been completed successfully
}