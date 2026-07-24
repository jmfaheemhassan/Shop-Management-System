package com.shop;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ShopGUI gui = new ShopGUI();
            gui.setVisible(true);
        });
    }
}