package app.ui;

import app.dao.ProductDAO;
import app.models.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class AdminFrame extends JFrame {
    private ProductDAO dao = new ProductDAO();
    private JTable table;
    private DefaultTableModel tableModel;

    public AdminFrame() {
        setTitle("Admin Panel - Supermarket");
        setSize(900, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(250, 250, 250));

        // top bar (gradient feel)
        JPanel top = new JPanel();
        top.setBackground(new Color(3, 169, 244));
        top.setPreferredSize(new Dimension(900, 70));
        JLabel heading = new JLabel("Admin Dashboard");
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        top.add(heading);
        main.add(top, BorderLayout.NORTH);

        String[] cols = {"ID", "Name", "Price", "Quantity", "Description"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        JScrollPane sp = new JScrollPane(table);
        main.add(sp, BorderLayout.CENTER);

        // right - controls
        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(320, 0));
        right.setLayout(null);
        right.setBackground(new Color(245, 248, 250));

        JLabel nameL = new JLabel("Name:"); nameL.setBounds(10,10,80,25); right.add(nameL);
        JTextField nameF = new JTextField(); nameF.setBounds(100,10,200,25); right.add(nameF);

        JLabel priceL = new JLabel("Price:"); priceL.setBounds(10,50,80,25); right.add(priceL);
        JTextField priceF = new JTextField(); priceF.setBounds(100,50,200,25); right.add(priceF);

        JLabel qtyL = new JLabel("Quantity:"); qtyL.setBounds(10,90,80,25); right.add(qtyL);
        JTextField qtyF = new JTextField(); qtyF.setBounds(100,90,200,25); right.add(qtyF);

        JLabel descL = new JLabel("Description:"); descL.setBounds(10,130,80,25); right.add(descL);
        JTextField descF = new JTextField(); descF.setBounds(100,130,200,25); right.add(descF);

        JButton addBtn = new JButton("Add Item"); addBtn.setBounds(20, 180, 130, 35); styleButton(addBtn); right.add(addBtn);
        JButton updateBtn = new JButton("Update Price"); updateBtn.setBounds(160,180,130,35); styleButton(updateBtn); right.add(updateBtn);
        JButton deleteBtn = new JButton("Remove Item"); deleteBtn.setBounds(20,230,130,35); styleButton(deleteBtn); right.add(deleteBtn);
        JButton refreshBtn = new JButton("Refresh"); refreshBtn.setBounds(160,230,130,35); styleButton(refreshBtn); right.add(refreshBtn);

        main.add(right, BorderLayout.EAST);

        add(main);

        loadProducts();

        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameF.getText().trim();
                    double price = Double.parseDouble(priceF.getText().trim());
                    int qty = Integer.parseInt(qtyF.getText().trim());
                    String desc = descF.getText().trim();
                    Product p = new Product(name, price, qty, desc);
                    if (dao.addProduct(p)) {
                        JOptionPane.showMessageDialog(AdminFrame.this, "Added successfully");
                        loadProducts();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AdminFrame.this, "Error: " + ex.getMessage());
                }
            }
        });

        updateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = table.getSelectedRow();
                    if (row == -1) { JOptionPane.showMessageDialog(AdminFrame.this, "Select a product from the table first"); return; }
                    int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    double newPrice = Double.parseDouble(priceF.getText().trim());
                    if (dao.updatePrice(id, newPrice)) {
                        JOptionPane.showMessageDialog(AdminFrame.this, "Price updated");
                        loadProducts();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AdminFrame.this, "Error: " + ex.getMessage());
                }
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = table.getSelectedRow();
                    if (row == -1) { JOptionPane.showMessageDialog(AdminFrame.this, "Select a product to delete"); return; }
                    int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    int confirm = JOptionPane.showConfirmDialog(AdminFrame.this, "Delete product ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION && dao.deleteProduct(id)) {
                        JOptionPane.showMessageDialog(AdminFrame.this, "Deleted");
                        loadProducts();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AdminFrame.this, "Error: " + ex.getMessage());
                }
            }
        });

        refreshBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { loadProducts(); }
        });

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
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

    private void loadProducts() {
        try {
            List<Product> list = dao.getAllProducts();
            tableModel.setRowCount(0);
            for (Product p : list) {
                tableModel.addRow(new Object[]{p.getId(), p.getName(), String.format("%.2f", p.getPrice()), p.getQuantity(), p.getDescription()});
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