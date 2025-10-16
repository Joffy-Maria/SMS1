package app.ui;

import app.dao.ProductDAO;
import app.models.Product;
import app.util.BillPrinter;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
public class CustomerFrame extends JFrame {
    private ProductDAO dao = new ProductDAO();
    private JTable table;
    private DefaultTableModel tableModel;
    private Map<Integer, Integer> cart = new HashMap<>();

    public CustomerFrame() {
        setTitle("Customer - Supermarket");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(240, 245, 250));

        // Enhanced top header with gradient
        GradientPanel top = new GradientPanel(new Color(3, 169, 244), new Color(2, 119, 189));
        top.setPreferredSize(new Dimension(1000, 80));
        top.setLayout(new BorderLayout(20, 0));
        top.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JLabel heading = new JLabel("🛒 Customer Shopping");
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        top.add(heading, BorderLayout.WEST);

        main.add(top, BorderLayout.NORTH);

        // Table panel with better styling
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(240, 245, 250));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 15));
        tablePanel.setBackground(Color.CYAN);
        String[] cols = {"ID", "Product Name", "Price", "Stock Available"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(38);
        table.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        table.getTableHeader().setFont(new Font("Sanserif", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(3, 169, 244));
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setPreferredSize(new Dimension(0, 45));
        table.setSelectionBackground(new Color(100, 181, 246));
        table.setSelectionForeground(Color.BLUE);
        table.setGridColor(new Color(230, 230, 230));
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setBackground(Color.pink);
        // Alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(248, 250, 252));
                    }
                }
                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        });

        // Center align table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        sp.getViewport().setBackground(Color.WHITE);

        tablePanel.add(sp, BorderLayout.CENTER);
        main.add(tablePanel, BorderLayout.CENTER);

        // Enhanced right panel
        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(350, 0));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(new Color(240, 245, 250));
        right.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 20));

        // Quantity input card
        JPanel qtyCard = createCard();
        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        qtyLabel.setForeground(new Color(50, 50, 50));
        qtyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField qtyF = new JTextField("1");
        qtyF.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        qtyF.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        qtyF.setBackground(new Color(248, 248, 248));
        qtyF.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        qtyCard.add(qtyLabel);
        qtyCard.add(Box.createVerticalStrut(8));
        qtyCard.add(qtyF);
        qtyCard.add(Box.createVerticalStrut(12));

        JButton addCartBtn = createStyledButton("Add to Cart", new Color(76, 175, 80));
        qtyCard.add(addCartBtn);
        right.add(qtyCard);
        right.add(Box.createVerticalStrut(20));

        // Action buttons card
        JPanel actionCard = createCard();
        JButton viewCartBtn = createStyledButton("View Cart", new Color(3, 169, 244));
        JButton buyBtn = createStyledButton("Checkout & Pay", new Color(244, 67, 54));

        actionCard.add(viewCartBtn);
        actionCard.add(Box.createVerticalStrut(10));
        actionCard.add(buyBtn);
        actionCard.add(Box.createVerticalStrut(10));

        JButton refreshBtn = createStyledButton("Refresh", new Color(156, 39, 176));
        actionCard.add(refreshBtn);

        right.add(actionCard);
        right.add(Box.createVerticalGlue());

        main.add(right, BorderLayout.EAST);
        add(main);

        loadProducts();

        addCartBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = table.getSelectedRow();
                    if (row == -1) {
                        JOptionPane.showMessageDialog(CustomerFrame.this, "Select product first", "Notice", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    int qty = Integer.parseInt(qtyF.getText().trim());
                    Product p = dao.getProductById(id);
                    if (p == null) {
                        JOptionPane.showMessageDialog(CustomerFrame.this, "Product not found", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (qty <= 0 || qty > p.getQuantity()) {
                        JOptionPane.showMessageDialog(CustomerFrame.this, "Invalid quantity", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    cart.put(id, cart.getOrDefault(id, 0) + qty);
                    JOptionPane.showMessageDialog(CustomerFrame.this, "✓ Added to cart", "Success", JOptionPane.INFORMATION_MESSAGE);
                    qtyF.setText("1");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(CustomerFrame.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        viewCartBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cart.isEmpty()) {
                    JOptionPane.showMessageDialog(CustomerFrame.this, "Cart is empty", "Notice", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                StringBuilder sb = new StringBuilder();
                double total = 0;
                for (Map.Entry<Integer, Integer> en : cart.entrySet()) {
                    try {
                        Product p = dao.getProductById(en.getKey());
                        int q = en.getValue();
                        double amt = p.getPrice() * q;
                        sb.append(String.format("%s × %d = Rs %.2f\n", p.getName(), q, amt));
                        total += amt;
                    } catch (SQLException ex) { }
                }
                sb.append(String.format("\n━━━━━━━━━━━━━━━━━\nTotal: Rs %.2f", total));
                JOptionPane.showMessageDialog(CustomerFrame.this, sb.toString(), "Shopping Cart", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cart.isEmpty()) {
                    JOptionPane.showMessageDialog(CustomerFrame.this, "Cart is empty", "Notice", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                List<String> lines = new ArrayList<>();
                double total = 0;
                try {
                    for (Map.Entry<Integer, Integer> en : cart.entrySet()) {
                        Product p = dao.getProductById(en.getKey());
                        int q = en.getValue();
                        if (q > p.getQuantity()) {
                            JOptionPane.showMessageDialog(CustomerFrame.this, "Not enough stock for " + p.getName(), "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    for (Map.Entry<Integer, Integer> en : cart.entrySet()) {
                        Product p = dao.getProductById(en.getKey());
                        int q = en.getValue();
                        double amt = p.getPrice() * q;
                        total += amt;
                        lines.add(String.format("%s x %d = Rs %.2f", p.getName(), q, amt));
                        p.setQuantity(p.getQuantity() - q);
                        dao.updateProduct(p);
                    }
                    BillPrinter.printBill(lines, total);
                    JOptionPane.showMessageDialog(CustomerFrame.this, "✓ Purchase successful. Bill generated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cart.clear();
                    loadProducts();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(CustomerFrame.this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        refreshBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadProducts();
            }
        });
    }

    private JPanel createCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));
        return card;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

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
                tableModel.addRow(new Object[]{p.getId(), p.getName(), String.format("Rs %.2f", p.getPrice()), p.getQuantity()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Gradient Panel
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
}