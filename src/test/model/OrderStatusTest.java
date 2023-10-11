package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

    class OrderStatusTest {
        private Order order;


        @Test
        void testOrderStatus() {
            assertEquals(0, OrderStatus.PLACED.ordinal());
            assertEquals(1, OrderStatus.PROCESSED.ordinal());
            assertEquals(2, OrderStatus.SHIPPED.ordinal());
            assertEquals(3, OrderStatus.DELIVERED.ordinal());
            assertEquals(4, OrderStatus.COMPLETE.ordinal());
        }

        @Test
        void checkAllOrderStatus() {
            for (OrderStatus status : OrderStatus.values()) {
                order.updateOrderStatus(status);
                assertEquals(status, order.getOrderStatus());
            }
        }
    }
