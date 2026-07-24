package com.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Product> products;

    public Inventory() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean removeProduct(String id) {
        return products.removeIf(p -> p.getId().equals(id));
    }

    public Product findById(String id) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public List<Product> getLowStockProducts(int threshold) {
        List<Product> lowStock = new ArrayList<>();
        for (Product p : products) {
            if (p.isLowStock(threshold)) {
                lowStock.add(p);
            }
        }
        return lowStock;
    }

    public double getTotalInventoryValue() {
        double total = 0;
        for (Product p : products) {
            total += p.getPrice() * p.getQuantity();
        }
        return total;
    }

    public void printAllProducts() {
        for (Product p : products) {
            System.out.println(p.getId() + " - " + p.getName() + " | " +
                    p.getCategory() + " | Price: " + p.getPrice() +
                    " | Qty: " + p.getQuantity());
        }

    
    }
        



 public Sale sellProduct(String id, int amount, SalesLog log) {
        Product p = findById(id);
        if (p == null) {
            throw new IllegalArgumentException("Product not found");
        }
        p.sell(amount);
        Sale sale = new Sale(p.getId(), p.getName(), amount, p.getPrice());
        log.recordSale(sale);
        return sale;
    }










}