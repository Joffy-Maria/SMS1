package app.ui;

import app.dao.ProductDAO;
import app.models.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class AdminFrame extends JFrame {
    private ProductDAO dao;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameF;
    private JTextField priceF;
    private JTextField qtyF;
    private JTextField descF;
    private JLabel totalProductsLabel;

    public AdminFrame() {
        setTitle("Admin Panel - Supermarket Management System");
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        dao = new ProductDAO();

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(240, 242, 247));

        // Top Header Panel
        GradientPanel headerPanel = new GradientPanel(new Color(26, 118, 255), new Color(13, 71, 161));
        headerPanel.setPreferredSize(new Dimension(1200, 80));
        headerPanel.setLayout(new BorderLayout(20, 0));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel heading = new JLabel("Admin Dashboard");
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 28));

        totalProductsLabel = new JLabel("Total Products: 0");
        totalProductsLabel.setForeground(new Color(200, 220, 255));
        totalProductsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        headerPanel.add(heading, BorderLayout.WEST);
        headerPanel.add(totalProductsLabel, BorderLayout.EAST);
        main.add(headerPanel, BorderLayout.NORTH);

        // Center - Table Panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(240, 242, 247));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        String[] cols = {"ID", "Name", "Price", "Quantity", "Description"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(33, 150, 243));
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setOpaque(true);
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        table.setSelectionBackground(new Color(100, 181, 246));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(0, 1));

        // Center table cells renderer
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // <-- FIX: create JScrollPane (sp) for the table (was previously missing)
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        sp.setBackground(Color.WHITE);

        centerPanel.add(sp, BorderLayout.CENTER);
        main.add(centerPanel, BorderLayout.CENTER);

        // Right Panel - Controls
        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(340, 0));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(new Color(245, 248, 250));
        right.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(245, 248, 250));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(33, 150, 243), 2),
                "Product Details",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 13),
                new Color(33, 150, 243)
        ));

        // Name Field
        JLabel nameL = new JLabel("Name:");
        nameL.setFont(new Font("Segoe UI", Font.BOLD, 12));
        nameL.setForeground(new Color(33, 33, 33));
        nameF = createStyledTextField();
        formPanel.add(nameL);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(nameF);
        formPanel.add(Box.createVerticalStrut(12));

        // Price Field
        JLabel priceL = new JLabel("Price:");
        priceL.setFont(new Font("Segoe UI", Font.BOLD, 12));
        priceL.setForeground(new Color(33, 33, 33));
        priceF = createStyledTextField();
        formPanel.add(priceL);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(priceF);
        formPanel.add(Box.createVerticalStrut(12));

        // Quantity Field
        JLabel qtyL = new JLabel("Quantity:");
        qtyL.setFont(new Font("Segoe UI", Font.BOLD, 12));
        qtyL.setForeground(new Color(33, 33, 33));
        qtyF = createStyledTextField();
        formPanel.add(qtyL);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(qtyF);
        formPanel.add(Box.createVerticalStrut(12));

        // Description Field
        JLabel descL = new JLabel("Description:");
        descL.setFont(new Font("Segoe UI", Font.BOLD, 12));
        descL.setForeground(new Color(33, 33, 33));
        descF = createStyledTextField();
        formPanel.add(descL);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(descF);

        right.add(formPanel);
        right.add(Box.createVerticalStrut(15));

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 10, 10));
        buttonPanel.setBackground(new Color(245, 248, 250));
        buttonPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
                "Actions",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 13),
                new Color(76, 175, 80)
        ));

        JButton addBtn = createStyledButton("Add Item", new Color(76, 175, 80));
        JButton updatePriceBtn = createStyledButton("Update Price", new Color(255, 152, 0));
        JButton updateQtyBtn = createStyledButton("Update Qty", new Color(33, 150, 243));
        JButton deleteBtn = createStyledButton("Remove Item", new Color(244, 67, 54));
        JButton refreshBtn = createStyledButton("Refresh", new Color(156, 39, 176));

        buttonPanel.add(addBtn);
        buttonPanel.add(updatePriceBtn);
        buttonPanel.add(updateQtyBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);

        right.add(buttonPanel);
        right.add(Box.createVerticalGlue());

        main.add(right, BorderLayout.EAST);

        add(main);

        loadProducts();

        // Button Actions
        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameF.getText().trim();
                    if (name.isEmpty()) { showError("Product name cannot be empty"); return; }
                    double price = Double.parseDouble(priceF.getText().trim());
                    int qty = Integer.parseInt(qtyF.getText().trim());
                    String desc = descF.getText().trim();
                    Product p = new Product(name, price, qty, desc);
                    if (dao.addProduct(p)) {
                        showSuccess("Product added successfully");
                        loadProducts();
                        clearFields();
                    }
                } catch (NumberFormatException ex) {
                    showError("Invalid price or quantity format");
                } catch (Exception ex) {
                    showError("Error: " + ex.getMessage());
                }
            }
        });

        updatePriceBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = table.getSelectedRow();
                    if (row == -1) { showError("Select a product from the table first"); return; }
                    int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    double newPrice = Double.parseDouble(priceF.getText().trim());
                    if (dao.updatePrice(id, newPrice)) {
                        showSuccess("Price updated successfully");
                        loadProducts();
                        clearFields();
                    }
                } catch (NumberFormatException ex) {
                    showError("Invalid price format");
                } catch (Exception ex) {
                    showError("Error: " + ex.getMessage());
                }
            }
        });

        updateQtyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = table.getSelectedRow();
                    if (row == -1) { showError("Select a product from the table first"); return; }
                    int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    int newQty = Integer.parseInt(qtyF.getText().trim());
                    if (newQty < 0) { showError("Quantity cannot be negative"); return; }
                    Product p = dao.getProductById(id);
                    if (p != null) {
                        p.setQuantity(newQty);
                        if (dao.updateProduct(p)) {
                            showSuccess("Quantity updated successfully");
                            loadProducts();
                            clearFields();
                        }
                    }
                } catch (NumberFormatException ex) {
                    showError("Invalid quantity format");
                } catch (Exception ex) {
                    showError("Error: " + ex.getMessage());
                }
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = table.getSelectedRow();
                    if (row == -1) { showError("Select a product to delete"); return; }
                    int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    String productName = tableModel.getValueAt(row, 1).toString();
                    int confirm = JOptionPane.showConfirmDialog(AdminFrame.this,
                            "Delete product '" + productName + "'?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION && dao.deleteProduct(id)) {
                        showSuccess("Product deleted successfully");
                        loadProducts();
                        clearFields();
                    }
                } catch (Exception ex) {
                    showError("Error: " + ex.getMessage());
                }
            }
        });

        refreshBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadProducts();
                clearFields();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int r = table.getSelectedRow();
                if (r >= 0) {
                    nameF.setText(tableModel.getValueAt(r,1).toString());
                    priceF.setText(tableModel.getValueAt(r,2).toString());
                    qtyF.setText(tableModel.getValueAt(r,3).toString());
                    descF.setText(tableModel.getValueAt(r,4).toString());
                }
            }
        });
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(darkerColor(bgColor));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });
        return btn;
    }

    private Color darkerColor(Color c) {
        return new Color(Math.max(0, c.getRed() - 30),
                Math.max(0, c.getGreen() - 30),
                Math.max(0, c.getBlue() - 30));
    }

    private void loadProducts() {
        try {
            List<Product> list = dao.getAllProducts();
            tableModel.setRowCount(0);
            for (Product p : list) {
                tableModel.addRow(new Object[]{p.getId(), p.getName(), String.format("Rs %.2f", p.getPrice()), p.getQuantity(), p.getDescription()});
            }
            totalProductsLabel.setText("Total Products: " + list.size());
        } catch (SQLException ex) {
            showError("DB Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        nameF.setText("");
        priceF.setText("");
        qtyF.setText("");
        descF.setText("");
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Gradient Panel for header
    static class GradientPanel extends JPanel {
        private Color color1, color2;

        GradientPanel(Color color1, Color color2) {
            this.color1 = color1;
            this.color2 = color2;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), 0, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }
    }

    // Optional main to run frame standalone
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminFrame f = new AdminFrame();
            f.setVisible(true);
        });
    }
}
