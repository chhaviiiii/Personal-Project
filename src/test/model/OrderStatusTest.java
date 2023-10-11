package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

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
            Order order = new Order(
                    "ID",
                    "productsDetails",
                    "customerDetails",
                    OrderStatus.PLACED,
                    new ArrayList<Product>()
            );
            assertEquals(OrderStatus.PLACED, order.getOrderStatus());

            order.updateOrderStatus(OrderStatus.PROCESSED);
            assertEquals(OrderStatus.PROCESSED, order.getOrderStatus());

            order.updateOrderStatus(OrderStatus.SHIPPED);
            assertEquals(OrderStatus.SHIPPED, order.getOrderStatus());

            order.updateOrderStatus(OrderStatus.DELIVERED);
            assertEquals(OrderStatus.DELIVERED, order.getOrderStatus());

            order.updateOrderStatus(OrderStatus.COMPLETE);
            assertEquals(OrderStatus.COMPLETE, order.getOrderStatus());
        }
    }
