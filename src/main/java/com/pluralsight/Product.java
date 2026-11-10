package com.pluralsight;

public class Product {
    // Product's Properties
    private String productId;
    private String productName;
    private double productPrice;
    private String productLocation;

    // Constructor
    public Product(String productId, String productName, double productPrice, String productLocation) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productLocation = productLocation;
    }

    // Getters
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getProductLocation() {
        return productLocation;
    }

    // Setters
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductLocation(String productLocation) {
        this.productLocation = productLocation;
    }

    // toString

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productLocation='" + productLocation + '\'' +
                '}';
    }
}
