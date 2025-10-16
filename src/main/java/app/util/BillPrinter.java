package app.util;

import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.util.List;

public class BillPrinter {
    public static void printBill(List<String> lines, double total) {
        StringBuilder sb = new StringBuilder();
        sb.append("*** Supermarket Bill ***\n\n");
        for (String s : lines) sb.append(s + "\n");
        sb.append(String.format("\nTotal: %.2f\n", total));
        sb.append("\nThank you for shopping!");

        JTextArea ta = new JTextArea(sb.toString());
        ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
        try {
            boolean done = ta.print();
            if (!done) {
                JOptionPane.showMessageDialog(null, "Printing canceled");
            }
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(null, "Print error: " + e.getMessage());
        }
    }
}