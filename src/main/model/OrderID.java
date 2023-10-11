package model;

import java.util.List;
import model.ProductType;

public class OrderID {
    public static String generateOrderID(List<Product> products) {
        String orderID = "o";

        for (Product product : products) {
            if (product.getProductType() == ProductType.CLOTHES) {
                orderID = "o1";
            } else if (product.getProductType() == ProductType.ELECTRONICS) {
                orderID = "o2";
            } else if (product.getProductType() == ProductType.FOOD) {
                orderID = "o3";
            } else if (product.getProductType() == ProductType.MAKEUP) {
                orderID = "o4";
            }
        }
        return orderID;
    }
}