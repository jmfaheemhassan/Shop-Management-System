package com.shop.model;

import java.time.LocalDateTime;

public class Sale {
    private String productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
    private LocalDateTime dateTime;

    public Sale(String productId, String productName, int quantity, double unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = quantity * unitPrice;
        this.dateTime = LocalDateTime.now();
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public double getTotalPrice() { return totalPrice; }
    public LocalDateTime getDateTime() { return dateTime; }

    @Override
    public String toString() {
        return dateTime + " | " + productName + " x" + quantity +
                " | Unit: " + unitPrice + " | Total: " + totalPrice;
    }
}
