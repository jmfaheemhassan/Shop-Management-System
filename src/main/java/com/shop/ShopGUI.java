package com.shop;

import com.shop.model.Inventory;
import com.shop.model.SalesLog;
import com.shop.model.FileManager;

import javax.swing.*;

public class ShopGUI extends JFrame {

    public ShopGUI() {
        setTitle("Shop Management System");
        setSize(750, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Inventory inventory = new Inventory();
        SalesLog salesLog = new SalesLog();

        FileManager.loadProducts(inventory);

        if (inventory.getAllProducts().isEmpty()) {
            inventory.addProduct(new com.shop.model.Product("P001", "Rice", "Grocery", 60.0, 100));
            inventory.addProduct(new com.shop.model.Product("P002", "Soap", "Toiletries", 30.0, 10));
            inventory.addProduct(new com.shop.model.Product("P003", "Oil", "Grocery", 150.0, 5));
            FileManager.saveProducts(inventory);
        }

        SalesPanel salesPanel = new SalesPanel(inventory, salesLog);
        add(salesPanel);
    }
}