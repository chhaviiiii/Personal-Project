package model;

// This represents a Product with a name and a description
public class Product {
    private String name;
    private String description;

    // Constructor
    public Product(String name, String description, double v) {
        this.name = name;
        this.description = description;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}