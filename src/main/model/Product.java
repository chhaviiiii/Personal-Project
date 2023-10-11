package model;

// This represents a Product with a name and a description
public class Product {
    private final String name;
    private final String description;
    private final double price;
    private ProductType productType;

    // Constructor
    public Product(String name, String description, double price, ProductType productType) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.productType = productType;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}