package model;

import java.util.List;



// REQUIRES: A list of Product objects. The list can be null.
// MODIFIES: This can modify the value of orderID.
// EFFECTS: Given a list of products, generates a unique orderID string. If the list is null, returns just "o".
// If the product in the list is of ProductType.CLOTHES, orderID is set to "o1"; if the product type is
// ProductType.ELECTRONICS, orderID is set to "o2"; if product type is ProductType.FOOD, orderID is "o3";
// If it is ProductType.MAKEUP, orderID is "o4". Returns the final orderID string.
public class OrderID {

    // Sets the orderID
    public String orderID() {
        return "o";
    }

    public static String generateOrderID(List<Product> products) {
        String orderID = "o";
        if (products == null) {
            return "o";
        }
        for (Product product : products) {
            if (Product.getProductType() == ProductType.CLOTHES) {
                orderID = "C" + (int)(Math.random() * 99);
            } else if (Product.getProductType() == ProductType.ELECTRONICS) {
                orderID = "E" + (int)(Math.random() * 99);
            } else if (Product.getProductType() == ProductType.FOOD) {
                orderID = "F" + (int)(Math.random() * 99);
            } else if (Product.getProductType() == ProductType.MAKEUP) {
                orderID = "M" + (int)(Math.random() * 99);
            }
        }
        return orderID;
    }
}