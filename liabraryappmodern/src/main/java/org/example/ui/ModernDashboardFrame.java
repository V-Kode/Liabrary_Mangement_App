package org.example.ui;

import org.example.dao.BookDAO;
import org.example.model.Book;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ModernDashboardFrame extends JFrame {

    private final BookDAO dao = new BookDAO();
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"ID", "Title", "Author", "Copies", "Issued"}, 0
    );
    private final JTable table = new JTable(tableModel);
    private final JTextField searchField = new JTextField(20);

    // MAIN COLOR PALETTE (Blue Theme)
    private final Color PRIMARY_BLUE = new Color(21, 101, 192);     // Button blue
    private final Color LIGHT_BLUE = new Color(227, 242, 253);      // Table selection
    private final Color HEADER_BLUE = new Color(13, 71, 161);       // Header background
    private final Color BACKGROUND_BLUE = new Color(232, 244, 253); // Page background

    public ModernDashboardFrame() {
        super("Library — Dashboard (Blue Theme)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 700);
        initUI();
        loadData();
    }

    private void initUI() {

        // Whole window background
        getContentPane().setBackground(BACKGROUND_BLUE);
        getContentPane().setLayout(new BorderLayout());

        // HEADER TOP
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(new EmptyBorder(12, 16, 12, 16));
        header.setBackground(HEADER_BLUE);

        JLabel title = new JLabel("Library Management ");
        title.setFont(new Font("Segoe UI Semibold", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);

        // RIGHT SIDE: Search + Buttons
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        right.setOpaque(false);

        searchField.setPreferredSize(new Dimension(260, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        right.add(searchField);

        JButton refreshBtn = createActionButton("Refresh", "\u21bb");
        JButton addBtn = createActionButton("Add", "+");
        JButton editBtn = createActionButton("Edit", "\u270E");
        JButton deleteBtn = createActionButton("Delete", "\u2716");
        JButton issueBtn = createActionButton("Issue", "\u25B6");
        JButton returnBtn = createActionButton("Return", "\u21BA");

        right.add(refreshBtn);
        right.add(addBtn);
        right.add(editBtn);
        right.add(deleteBtn);
        right.add(issueBtn);
        right.add(returnBtn);

        header.add(right, BorderLayout.EAST);

        getContentPane().add(header, BorderLayout.NORTH);

        // TABLE DESIGN — Blue Style
        table.setRowHeight(32);
        table.setFillsViewportHeight(true);
        table.setShowGrid(true);
        table.setGridColor(new Color(200, 220, 240));
        table.setSelectionBackground(LIGHT_BLUE);
        table.setSelectionForeground(Color.BLACK);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        table.getTableHeader().setBackground(new Color(187, 222, 251));
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
        table.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(center);
        table.getColumnModel().getColumn(3).setCellRenderer(center);
        table.getColumnModel().getColumn(4).setCellRenderer(center);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new EmptyBorder(16, 16, 16, 16));
        getContentPane().add(scroll, BorderLayout.CENTER);

        // Listeners
        refreshBtn.addActionListener(e -> loadData());
        addBtn.addActionListener(e -> openAddDialog());
        editBtn.addActionListener(e -> openEditDialog());
        deleteBtn.addActionListener(e -> deleteSelected());
        issueBtn.addActionListener(e -> issueSelected());
        returnBtn.addActionListener(e -> returnSelected());

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filter(); }
            public void removeUpdate(DocumentEvent e) { filter(); }
            public void changedUpdate(DocumentEvent e) { filter(); }
        });
    }

    private JButton createActionButton(String text, String iconText) {
        JButton b = new JButton(iconText + " " + text);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        b.setForeground(Color.WHITE);
        b.setBackground(PRIMARY_BLUE);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        b.putClientProperty("JButton.buttonType", "roundRect");
        return b;
    }

    private void filter() {
        String q = searchField.getText().trim().toLowerCase();
        try {
            List<Book> list = dao.getAll();
            tableModel.setRowCount(0);

            for (Book b : list) {
                if (q.isEmpty()
                        || String.valueOf(b.getId()).contains(q)
                        || b.getTitle().toLowerCase().contains(q)
                        || b.getAuthor().toLowerCase().contains(q)) {
                    tableModel.addRow(new Object[]{
                            b.getId(),
                            b.getTitle(),
                            b.getAuthor(),
                            b.getCopies(),
                            b.isIssued() ? "Yes" : "No"
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Search error: " + ex.getMessage());
        }
    }

    private void loadData() {
        SwingUtilities.invokeLater(() -> {
            try {
                List<Book> list = dao.getAll();
                tableModel.setRowCount(0);

                for (Book b : list) {
                    tableModel.addRow(new Object[]{
                            b.getId(),
                            b.getTitle(),
                            b.getAuthor(),
                            b.getCopies(),
                            b.isIssued() ? "Yes" : "No"
                    });
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Load error: " + ex.getMessage());
            }
        });
    }

    private void openAddDialog() {
        BookDialog dlg = new BookDialog(this);
        dlg.setVisible(true);

        if (!dlg.isSaved()) return;

        try {
            dao.add(dlg.getBook());
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Add failed: " + ex.getMessage());
        }
    }

    private void openEditDialog() {
        int r = table.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Select a row to edit.");
            return;
        }

        int id = (int) tableModel.getValueAt(r, 0);
        String t = (String) tableModel.getValueAt(r, 1);
        String a = (String) tableModel.getValueAt(r, 2);
        int c = (int) tableModel.getValueAt(r, 3);
        boolean issued = "Yes".equals(tableModel.getValueAt(r, 4));

        Book existing = new Book(id, t, a, c, issued);
        BookDialog dlg = new BookDialog(this, existing);
        dlg.setVisible(true);

        if (!dlg.isSaved()) return;

        try {
            dao.update(dlg.getBook());
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Update failed: " + ex.getMessage());
        }
    }

    private void deleteSelected() {
        int r = table.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Select a row to delete.");
            return;
        }

        int id = (int) tableModel.getValueAt(r, 0);

        if (JOptionPane.showConfirmDialog(this, "Delete book ID " + id + "?") != JOptionPane.YES_OPTION)
            return;

        try {
            dao.delete(id);
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Delete failed: " + ex.getMessage());
        }
    }

    private void issueSelected() {
        int r = table.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Select a row to issue.");
            return;
        }

        int id = (int) tableModel.getValueAt(r, 0);

        try {
            if (!dao.issueBook(id)) {
                JOptionPane.showMessageDialog(this, "Issue failed (no copies?)");
            }
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Issue failed: " + ex.getMessage());
        }
    }

    private void returnSelected() {
        int r = table.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Select a row to return.");
            return;
        }

        int id = (int) tableModel.getValueAt(r, 0);

        try {
            if (!dao.returnBook(id)) {
                JOptionPane.showMessageDialog(this, "Return failed.");
            }
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Return failed: " + ex.getMessage());
        }
    }
}
