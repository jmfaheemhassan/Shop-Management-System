package com.shop;

import com.shop.model.Inventory;
import com.shop.model.Product;
import com.shop.model.SalesLog;
import com.shop.model.FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SalesPanel extends JPanel {
    private Inventory inventory;
    private SalesLog salesLog;
    private DefaultTableModel tableModel;
    private JTable productTable;
    private JTextArea salesLogArea;
    private JLabel revenueLabel;

    public SalesPanel(Inventory inventory, SalesLog salesLog) {
        this.inventory = inventory;
        this.salesLog = salesLog;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"ID", "Name", "Category", "Price", "Quantity"};
        tableModel = new DefaultTableModel(columns, 0);
        productTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(productTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Inventory"));

        // Sell panel
        JPanel sellPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField idField = new JTextField(6);
        JTextField amountField = new JTextField(4);
        JButton sellButton = new JButton("Sell");

        sellPanel.add(new JLabel("Product ID:"));
        sellPanel.add(idField);
        sellPanel.add(new JLabel("Amount:"));
        sellPanel.add(amountField);
        sellPanel.add(sellButton);

        // Add product panel
        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField newIdField = new JTextField(5);
        JTextField newNameField = new JTextField(8);
        JTextField newCategoryField = new JTextField(8);
        JTextField newPriceField = new JTextField(5);
        JTextField newQuantityField = new JTextField(5);
        JButton addButton = new JButton("Add Product");

        addPanel.add(new JLabel("ID:"));
        addPanel.add(newIdField);
        addPanel.add(new JLabel("Name:"));
        addPanel.add(newNameField);
        addPanel.add(new JLabel("Category:"));
        addPanel.add(newCategoryField);
        addPanel.add(new JLabel("Price:"));
        addPanel.add(newPriceField);
        addPanel.add(new JLabel("Qty:"));
        addPanel.add(newQuantityField);
        addPanel.add(addButton);

        salesLogArea = new JTextArea(8, 40);
        salesLogArea.setEditable(false);
        JScrollPane logScroll = new JScrollPane(salesLogArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("Sales Log"));

        revenueLabel = new JLabel("Total Revenue: 0.0");
        revenueLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        controlsPanel.add(addPanel);
        controlsPanel.add(sellPanel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(tableScroll, BorderLayout.CENTER);
        topPanel.add(controlsPanel, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(logScroll, BorderLayout.CENTER);
        bottomPanel.add(revenueLabel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        sellButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String amountText = amountField.getText().trim();
            if (id.isEmpty() || amountText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter Product ID and Amount.");
                return;
            }
            try {
                int amount = Integer.parseInt(amountText);
                inventory.sellProduct(id, amount, salesLog);
                refreshTable();
                refreshSalesLog();
                FileManager.saveProducts(inventory);
                FileManager.saveSales(salesLog);
                idField.setText("");
                amountField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Amount must be a number.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        addButton.addActionListener(e -> {
            String id = newIdField.getText().trim();
            String name = newNameField.getText().trim();
            String category = newCategoryField.getText().trim();
            String priceText = newPriceField.getText().trim();
            String qtyText = newQuantityField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || category.isEmpty() ||
                    priceText.isEmpty() || qtyText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill all fields.");
                return;
            }
            try {
                double price = Double.parseDouble(priceText);
                int qty = Integer.parseInt(qtyText);
                Product newProduct = new Product(id, name, category, price, qty);
                inventory.addProduct(newProduct);
                refreshTable();
                FileManager.saveProducts(inventory);

                newIdField.setText("");
                newNameField.setText("");
                newCategoryField.setText("");
                newPriceField.setText("");
                newQuantityField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Price/Quantity must be numbers.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        refreshTable();
        refreshSalesLog();
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Product p : inventory.getAllProducts()) {
            tableModel.addRow(new Object[]{
                    p.getId(), p.getName(), p.getCategory(), p.getPrice(), p.getQuantity()
            });
        }
    }

    public void refreshSalesLog() {
        StringBuilder sb = new StringBuilder();
        salesLog.getAllSales().forEach(s -> sb.append(s.toString()).append("\n"));
        salesLogArea.setText(sb.toString());
        revenueLabel.setText("Total Revenue: " + salesLog.getTotalRevenue());
    }
}