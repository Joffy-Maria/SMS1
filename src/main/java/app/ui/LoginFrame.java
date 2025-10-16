package app.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;

    public LoginFrame() {
        setTitle("Supermarket - Login");
        setSize(420, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 248, 255));
        panel.setLayout(null);

        // gradient-like header
        JPanel header = new JPanel();
        header.setBackground(new Color(3, 169, 244));
        header.setBounds(0,0,420,70);
        header.setLayout(null);
        JLabel title = new JLabel("Welcome to Supermarket");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBounds(70,15,300,30);
        header.add(title);
        panel.add(header);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(new Color(3, 82, 139));
        userLabel.setBounds(50, 100, 100, 25);
        panel.add(userLabel);

        userField = new JTextField();
        userField.setBounds(150, 100, 200, 25);
        panel.add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(new Color(3, 82, 139));
        passLabel.setBounds(50, 140, 100, 25);
        panel.add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(150, 140, 200, 25);
        panel.add(passField);

        JButton adminBtn = new JButton("Admin");
        adminBtn.setBounds(80, 200, 100, 35);
        styleButton(adminBtn);
        panel.add(adminBtn);

        JButton customerBtn = new JButton("Customer");
        customerBtn.setBounds(230, 200, 100, 35);
        styleButton(customerBtn);
        panel.add(customerBtn);

        add(panel);

        adminBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = userField.getText();
                String pass = new String(passField.getPassword());
                if ("admin".equals(user) && "admin123".equals(pass)) {
                    dispose();
                    new AdminFrame().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Invalid admin credentials. Use admin/admin123 for demo.");
                }
            }
        });

        customerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CustomerFrame().setVisible(true);
            }
        });
    }

    private void styleButton(JButton b) {
        b.setBackground(new Color(3, 169, 244));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
    }
}