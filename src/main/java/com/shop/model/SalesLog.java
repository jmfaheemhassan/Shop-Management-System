package com.shop.model;

import java.util.ArrayList;
import java.util.List;

public class SalesLog {
    private List<Sale> sales;

    public SalesLog() {
        this.sales = new ArrayList<>();
    }

    public void recordSale(Sale sale) {
        sales.add(sale);
    }

    public List<Sale> getAllSales() {
        return sales;
    }

    public double getTotalRevenue() {
        double total = 0;
        for (Sale s : sales) {
            total += s.getTotalPrice();
        }
        return total;
    }

    public int getTotalItemsSold() {
        int count = 0;
        for (Sale s : sales) {
            count += s.getQuantity();
        }
        return count;
    }

    public void printAllSales() {
        if (sales.isEmpty()) {
            System.out.println("No sales recorded yet.");
            return;
        }
        for (Sale s : sales) {
            System.out.println(s);
        }
    }
}