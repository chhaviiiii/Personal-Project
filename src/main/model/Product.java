package model;

import org.json.JSONObject;
import persistence.Writable;

// This represents a product with a name and description
public class Product implements Writable {
    private final String name;
    private static String description;
    private final double price;
    private ProductType productType;

    // REQUIRES: name, description are non-null strings, price is a non-negative double, productType is non-null
    // MODIFIES: this
    // EFFECTS: Initializes a new Product with the given name, description, price, and product type
    public Product(String name, String description, double price, ProductType productType) {
        this.name = name;
        Product.description = description;
        this.price = price;
        this.productType = productType;
    }


    // EFFECTS: Returns the product's name
    public String getName() {
        return name;
    }


    // EFFECTS: Returns the product's description
    public static String getDescription() {
        return description;
    }


    // EFFECTS: Returns the product's price
    public double getPrice() {
        return price;
    }


    // EFFECTS: Returns the product's type
    public ProductType getProductType() {
        return productType;
    }

    // REQUIRES: productType is non-null
    // MODIFIES: this
    // EFFECTS: Sets the product's type to the given productType
    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("description", description);
        json.put("price", price);
        json.put("productType", productType.toString());
        return json;
    }
}
