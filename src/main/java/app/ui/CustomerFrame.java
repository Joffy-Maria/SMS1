package app.ui;

import app.dao.ProductDAO;
import app.models.Product;
import app.util.BillPrinter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CustomerFrame extends JFrame {
    private ProductDAO dao = new ProductDAO();
    private JTable table;
    private DefaultTableModel tableModel;
    private Map<Integer, Integer> cart = new HashMap<>(); // productId -> qty

    public CustomerFrame() {
        setTitle("Customer - Supermarket");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(250, 250, 250));

        JPanel top = new JPanel();
        top.setBackground(new Color(3, 169, 244));
        top.setPreferredSize(new Dimension(1000, 70));
        JLabel heading = new JLabel("Customer Shopping");
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        top.add(heading);
        main.add(top, BorderLayout.NORTH);

        String[] cols = {"ID", "Name", "Price", "Available"};
        tableModel = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r,int c){return false;} };
        table = new JTable(tableModel);
        JScrollPane sp = new JScrollPane(table);
        main.add(sp, BorderLayout.CENTER);

        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(340, 0));
        right.setLayout(null);
        right.setBackground(new Color(245, 248, 250));

        JLabel qtyL = new JLabel("Quantity:"); qtyL.setBounds(10,20,80,25); right.add(qtyL);
        JTextField qtyF = new JTextField("1"); qtyF.setBounds(100,20,220,25); right.add(qtyF);

        JButton addCartBtn = new JButton("Add to Cart"); addCartBtn.setBounds(10,60,310,40); styleButton(addCartBtn); right.add(addCartBtn);
        JButton viewCartBtn = new JButton("View Cart"); viewCartBtn.setBounds(10,110,150,40); styleButton(viewCartBtn); right.add(viewCartBtn);
        JButton buyBtn = new JButton("Checkout & Pay"); buyBtn.setBounds(170,110,150,40); styleButton(buyBtn); right.add(buyBtn);
        JButton refreshBtn = new JButton("Refresh"); refreshBtn.setBounds(10,170,310,40); styleButton(refreshBtn); right.add(refreshBtn);

        main.add(right, BorderLayout.EAST);

        add(main);

        loadProducts();

        addCartBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = table.getSelectedRow();
                    if (row == -1) { JOptionPane.showMessageDialog(CustomerFrame.this, "Select product first"); return; }
                    int id = Integer.parseInt(tableModel.getValueAt(row,0).toString());
                    int qty = Integer.parseInt(qtyF.getText().trim());
                    Product p = dao.getProductById(id);
                    if (p == null) { JOptionPane.showMessageDialog(CustomerFrame.this, "Product not found"); return; }
                    if (qty <= 0 || qty > p.getQuantity()) { JOptionPane.showMessageDialog(CustomerFrame.this, "Invalid quantity"); return; }
                    cart.put(id, cart.getOrDefault(id, 0) + qty);
                    JOptionPane.showMessageDialog(CustomerFrame.this, "Added to cart");
                } catch (Exception ex) { JOptionPane.showMessageDialog(CustomerFrame.this, "Error: " + ex.getMessage()); }
            }
        });

        viewCartBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cart.isEmpty()) { JOptionPane.showMessageDialog(CustomerFrame.this, "Cart is empty"); return; }
                StringBuilder sb = new StringBuilder();
                double total = 0;
                for (Map.Entry<Integer,Integer> en : cart.entrySet()) {
                    try {
                        Product p = dao.getProductById(en.getKey());
                        int q = en.getValue();
                        double amt = p.getPrice() * q;
                        sb.append(String.format("%s x %d = %.2f\n", p.getName(), q, amt));
                        total += amt;
                    } catch (SQLException ex) { }
                }
                sb.append(String.format("\nTotal = %.2f", total));
                JOptionPane.showMessageDialog(CustomerFrame.this, sb.toString(), "Cart", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cart.isEmpty()) { JOptionPane.showMessageDialog(CustomerFrame.this, "Cart empty"); return; }
                List<String> lines = new ArrayList<>();
                double total = 0;
                try {
                    for (Map.Entry<Integer,Integer> en : cart.entrySet()) {
                        Product p = dao.getProductById(en.getKey());
                        int q = en.getValue();
                        if (q > p.getQuantity()) { JOptionPane.showMessageDialog(CustomerFrame.this, "Not enough stock for " + p.getName()); return; }
                    }
                    for (Map.Entry<Integer,Integer> en : cart.entrySet()) {
                        Product p = dao.getProductById(en.getKey());
                        int q = en.getValue();
                        double amt = p.getPrice() * q;
                        total += amt;
                        lines.add(String.format("%s x %d = %.2f", p.getName(), q, amt));
                        p.setQuantity(p.getQuantity() - q);
                        dao.updateProduct(p);
                    }
                    BillPrinter.printBill(lines, total);
                    JOptionPane.showMessageDialog(CustomerFrame.this, "Purchase successful. Bill generated.");
                    cart.clear();
                    loadProducts();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(CustomerFrame.this, "Error: " + ex.getMessage());
                }
            }
        });

        refreshBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { loadProducts(); }
        });
    }

    private void loadProducts() {
        try {
            List<Product> list = dao.getAllProducts();
            tableModel.setRowCount(0);
            for (Product p : list) {
                tableModel.addRow(new Object[]{p.getId(), p.getName(), String.format("%.2f", p.getPrice()), p.getQuantity()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage());
        }
    }

    private void styleButton(JButton b) {
        b.setBackground(new Color(3, 169, 244));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
    }
}