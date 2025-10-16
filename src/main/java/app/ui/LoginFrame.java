package app.ui;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;

    public LoginFrame() {
        setTitle("Supermarket - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);

        // Background panel with gradient
        JPanel background = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(15, 76, 129),
                        getWidth(), getHeight(), new Color(25, 118, 210));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        background.setOpaque(false);
        add(background);

        // Center card with shadow
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Shadow
                g2.setColor(new Color(0, 0, 0, 30));
                g2.fillRoundRect(3, 3, getWidth() - 6, getHeight() - 6, 15, 15);

                // Card background
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 3, getHeight() - 3, 15, 15);
                super.paintComponent(g);
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(450, 380));
        card.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Header with gradient
        JPanel header = new GradientPanel(new Color(3, 169, 244), new Color(2, 119, 189));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        header.setLayout(new GridBagLayout());
        JLabel title = new JLabel("Welcome to Supermarket");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        header.add(title);
        card.add(header);

        card.add(Box.createVerticalStrut(25));

        // Username
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setBackground(Color.WHITE);
        userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        userLabel.setForeground(new Color(50, 50, 50));
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        userField = createTextField();
        userPanel.add(userLabel);
        userPanel.add(Box.createVerticalStrut(5));
        userPanel.add(userField);
        card.add(userPanel);
        card.add(Box.createVerticalStrut(15));

        // Password
        JPanel passPanel = new JPanel();
        passPanel.setLayout(new BoxLayout(passPanel, BoxLayout.Y_AXIS));
        passPanel.setBackground(Color.WHITE);
        passPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        passLabel.setForeground(new Color(50, 50, 50));
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passField = createPasswordField();
        passPanel.add(passLabel);
        passPanel.add(Box.createVerticalStrut(5));
        passPanel.add(passField);
        card.add(passPanel);
        card.add(Box.createVerticalStrut(25));

        // Buttons row
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonRow.setBackground(Color.WHITE);
        buttonRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

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
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        tf.setBackground(new Color(248, 248, 248));
        tf.setCaretColor(new Color(3, 169, 244));
        tf.setBorder(new RoundBorder(new Color(200, 200, 200), 6, 1));
        return tf;
    }

    private JPasswordField createPasswordField() {
        JPasswordField pf = new JPasswordField();
        pf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        pf.setBackground(new Color(248, 248, 248));
        pf.setCaretColor(new Color(3, 169, 244));
        pf.setBorder(new RoundBorder(new Color(200, 200, 200), 6, 1));
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
        b.setPreferredSize(new Dimension(130, 42));

        // Hover effect
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                b.setBackground(color.darker());
            }

            public void mouseExited(MouseEvent e) {
                b.setBackground(color);
            }
        });
        return b;
    }

    // Custom rounded border
    static class RoundBorder extends AbstractBorder {
        private Color color;
        private int radius;
        private int thickness;

        RoundBorder(Color color, int radius, int thickness) {
            this.color = color;
            this.radius = radius;
            this.thickness = thickness;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(8, 12, 8, 12);
        }
    }

    // Gradient Panel for header
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
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), 0, c2);
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}