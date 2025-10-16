package app.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;

    public LoginFrame() {
        setTitle("Supermarket - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);

        // Background panel (center everything)
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(new Color(230, 240, 250));
        add(background);

        // Center card
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 220, 240), 1),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        card.setPreferredSize(new Dimension(450, 380));

        // Header (gradient panel)
        JPanel header = new GradientPanel(new Color(3, 169, 244), new Color(2, 119, 189));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        header.setLayout(new GridBagLayout());
        JLabel title = new JLabel("Welcome to Supermarket");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        header.add(title);
        card.add(header);

        card.add(Box.createVerticalStrut(25));

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(userLabel);
        card.add(Box.createVerticalStrut(5));

        userField = createTextField();
        card.add(userField);
        card.add(Box.createVerticalStrut(15));

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(passLabel);
        card.add(Box.createVerticalStrut(5));

        passField = createPasswordField();
        card.add(passField);
        card.add(Box.createVerticalStrut(25));

        // Buttons row
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonRow.setBackground(Color.WHITE);
        JButton adminBtn = createButton("Admin", new Color(3, 169, 244));
        JButton customerBtn = createButton("Customer", new Color(76, 175, 80));
        buttonRow.add(adminBtn);
        buttonRow.add(customerBtn);
        card.add(buttonRow);

        // Add card to center of background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        background.add(card, gbc);

        // ===== Actions =====
        adminBtn.addActionListener((ActionEvent e) -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            if ("admin".equals(user) && "admin123".equals(pass)) {
                dispose();
                new AdminFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid admin credentials.\nUse admin / admin123 for demo.",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        customerBtn.addActionListener((ActionEvent e) -> {
            dispose();
            new CustomerFrame().setVisible(true);
        });
    }

    private JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 220), 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return tf;
    }

    private JPasswordField createPasswordField() {
        JPasswordField pf = new JPasswordField();
        pf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        pf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 220), 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return pf;
    }

    private JButton createButton(String text, Color color) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(110, 38));

        // Hover effect
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                b.setBackground(color.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                b.setBackground(color);
            }
        });
        return b;
    }

    // Gradient header background
    static class GradientPanel extends JPanel {
        private final Color c1, c2;

        GradientPanel(Color c1, Color c2) {
            this.c1 = c1;
            this.c2 = c2;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setPaint(new GradientPaint(0, 0, c1, getWidth(), 0, c2));
            g2.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
