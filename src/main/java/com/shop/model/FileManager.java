package com.shop.model;

import java.io.*;


public class FileManager {
    private static final String PRODUCTS_FILE = "products.txt";
    private static final String SALES_FILE = "sales.txt";

    public static void saveProducts(Inventory inventory) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PRODUCTS_FILE))) {
            for (Product p : inventory.getAllProducts()) {
                writer.println(p.getId() + "," + p.getName() + "," + p.getCategory() +
                        "," + p.getPrice() + "," + p.getQuantity());
            }
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }

    public static void loadProducts(Inventory inventory) {
        File file = new File(PRODUCTS_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Product p = new Product(parts[0], parts[1], parts[2],
                            Double.parseDouble(parts[3]), Integer.parseInt(parts[4]));
                    inventory.addProduct(p);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }

    public static void saveSales(SalesLog salesLog) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SALES_FILE))) {
            for (Sale s : salesLog.getAllSales()) {
                writer.println(s.getProductId() + "," + s.getProductName() + "," +
                        s.getQuantity() + "," + s.getUnitPrice() + "," +
                        s.getTotalPrice() + "," + s.getDateTime());
            }
        } catch (IOException e) {
            System.out.println("Error saving sales: " + e.getMessage());
        }
    }
}
