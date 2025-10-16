package app.util;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BillPrinter {
    public static void printBill(List<String> lines, double total) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("        *** SUPERMARKET BILL ***\n");
        sb.append("========================================\n\n");

        for (String s : lines) {
            sb.append(s + "\n");
        }

        sb.append("\n----------------------------------------\n");
        sb.append(String.format("TOTAL: $%.2f\n", total));
        sb.append("----------------------------------------\n\n");
        sb.append("     Thank you for shopping!\n");
        sb.append("========================================\n");

        // Create a new JFrame for the bill display
        JFrame billFrame = new JFrame("Purchase Receipt");
        billFrame.setSize(500, 600);
        billFrame.setLocationRelativeTo(null);
        billFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create panel with header
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(250, 250, 250));

        // Header
        JPanel header = new JPanel();
        header.setBackground(new Color(3, 169, 244));
        header.setPreferredSize(new Dimension(500, 50));
        JLabel headerLabel = new JLabel("Purchase Receipt");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        header.add(headerLabel);
        mainPanel.add(header, BorderLayout.NORTH);

        // Text area for bill content
        JTextArea billText = new JTextArea(sb.toString());
        billText.setFont(new Font("Monospaced", Font.PLAIN, 12));
        billText.setEditable(false);
        billText.setMargin(new Insets(15, 15, 15, 15));
        billText.setBackground(Color.WHITE);
        billText.setLineWrap(false);

        JScrollPane scrollPane = new JScrollPane(billText);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 248, 250));
        buttonPanel.setPreferredSize(new Dimension(500, 60));

        JButton printBtn = new JButton("Print Bill");
        printBtn.setBackground(new Color(3, 169, 244));
        printBtn.setForeground(Color.WHITE);
        printBtn.setFocusPainted(false);
        printBtn.setBorderPainted(false);
        printBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        printBtn.setPreferredSize(new Dimension(120, 35));

        JButton closeBtn = new JButton("Close");
        closeBtn.setBackground(new Color(200, 200, 200));
        closeBtn.setForeground(Color.BLACK);
        closeBtn.setFocusPainted(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        closeBtn.setPreferredSize(new Dimension(120, 35));

        printBtn.addActionListener(e -> {
            try {
                billText.print();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(billFrame, "Print error: " + ex.getMessage());
            }
        });

        closeBtn.addActionListener(e -> billFrame.dispose());

        buttonPanel.add(printBtn);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(closeBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        billFrame.add(mainPanel);
        billFrame.setVisible(true);
    }
}