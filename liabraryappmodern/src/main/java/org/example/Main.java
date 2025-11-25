package org.example;

import com.formdev.flatlaf.FlatLightLaf;
import org.example.ui.ModernDashboardFrame;
import org.example.util.DbUtil;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // Initialize SQLite DB
        DbUtil.initDatabase();

        // Apply modern UI look
        try {
            FlatLightLaf.setup();
            UIManager.put("Component.arc", 12);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Launch UI
        SwingUtilities.invokeLater(() -> {
            ModernDashboardFrame frame = new ModernDashboardFrame();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
