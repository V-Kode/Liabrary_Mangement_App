package org.example.ui;

import org.example.model.Book;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Modal dialog to add or edit a Book.
 * - When opened for adding, pass only owner.
 * - When opened for editing, pass an existing Book instance.
 *
 * After the dialog closes, call isSaved() to check whether to read getBook().
 */
public class BookDialog extends JDialog {

    private final JTextField idField = new JTextField(8);
    private final JTextField titleField = new JTextField(30);
    private final JTextField authorField = new JTextField(25);
    private final JSpinner copiesSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
    private final JCheckBox issuedCheck = new JCheckBox("Issued");

    private boolean saved = false;

    public BookDialog(Window owner) {
        this(owner, null);
    }

    public BookDialog(Window owner, Book existing) {
        super(owner, "Book", ModalityType.APPLICATION_MODAL);
        initComponents();
        if (existing != null) {
            setBook(existing);
            idField.setEnabled(false); // don't allow editing id
        }
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        JPanel content = new JPanel(new GridBagLayout());
        content.setBorder(new EmptyBorder(12, 12, 12, 12));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        int y = 0;

        // ID
        gbc.gridx = 0; gbc.gridy = y; content.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; content.add(idField, gbc);
        y++;

        // Title
        gbc.gridx = 0; gbc.gridy = y; content.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1; content.add(titleField, gbc);
        y++;

        // Author
        gbc.gridx = 0; gbc.gridy = y; content.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1; content.add(authorField, gbc);
        y++;

        // Copies
        gbc.gridx = 0; gbc.gridy = y; content.add(new JLabel("Copies:"), gbc);
        gbc.gridx = 1; content.add(copiesSpinner, gbc);
        y++;

        // Issued checkbox
        gbc.gridx = 0; gbc.gridy = y; content.add(new JLabel(""), gbc);
        gbc.gridx = 1; content.add(issuedCheck, gbc);
        y++;

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton cancelBtn = new JButton("Cancel");
        JButton saveBtn = new JButton("Save");

        // Styling (keeps consistent with modern dashboard)
        saveBtn.setBackground(new Color(21,101,192));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setBorder(BorderFactory.createEmptyBorder(6,12,6,12));
        cancelBtn.setBorder(BorderFactory.createEmptyBorder(6,12,6,12));

        buttons.add(cancelBtn);
        buttons.add(saveBtn);

        // Action listeners
        cancelBtn.addActionListener(e -> {
            saved = false;
            setVisible(false);
        });

        saveBtn.addActionListener(e -> {
            if (titleField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title is required", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (authorField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Author is required", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (idField.getText().trim().isEmpty()) {
                // If id empty when adding, allow DB to auto-generate (autoincrement), so we allow empty id here.
                // If you want manual id, uncomment the validation below:
                // JOptionPane.showMessageDialog(this, "ID is required", "Validation", JOptionPane.WARNING_MESSAGE);
                // return;
            } else {
                // ensure numeric id
                try {
                    Integer.parseInt(idField.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID must be an integer", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            saved = true;
            setVisible(false);
        });

        // Put everything together
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(content, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.SOUTH);
    }

    private void setBook(Book b) {
        idField.setText(String.valueOf(b.getId()));
        titleField.setText(b.getTitle());
        authorField.setText(b.getAuthor());
        copiesSpinner.setValue(b.getCopies());
        issuedCheck.setSelected(b.isIssued());
    }

    public boolean isSaved() {
        return saved;
    }

    /**
     * Build a Book object from the dialog fields.
     * If id field is blank, id=0 is returned and DAO should let DB assign id (if using AUTOINCREMENT).
     */
    public Book getBook() {
        Book b = new Book();
        String idText = idField.getText().trim();
        if (!idText.isEmpty()) {
            try { b.setId(Integer.parseInt(idText)); }
            catch (NumberFormatException ignored) { b.setId(0); }
        } else {
            b.setId(0); // 0 means "let DB autoincrement" in DAO logic
        }
        b.setTitle(titleField.getText().trim());
        b.setAuthor(authorField.getText().trim());
        b.setCopies((Integer) copiesSpinner.getValue());
        b.setIssued(issuedCheck.isSelected());
        return b;
    }
}
