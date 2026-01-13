package View;

import Model.Drug;
import Model.SalesModel;
import Model.History;
import Controller.DrugController;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class SalesPanel extends JPanel {

    private SalesModel salesModel;
    private DrugController controller;
    private History historyModel; // Integration with History
    private JTable queueTable;
    private DefaultTableModel tableModel;
    private JLabel lblTotal;

    // The constructor now requires History to ensure persistence
    public SalesPanel(SalesModel salesModel, DrugController controller, History historyModel) {
        this.salesModel = salesModel;
        this.controller = controller;
        this.historyModel = historyModel;
        initComponents();
        refreshTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout(20, 20));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- HEADER ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel lblTitle = new JLabel("SALES CHECKOUT QUEUE");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(0, 255, 213)); // Neon Cyan

        lblTotal = new JLabel("Total Queue Value: RS. 0.00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotal.setForeground(Color.WHITE); 

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(lblTotal, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // --- CENTER: TABLE ---
        String[] columns = {"Drug Name", "Vendor", "Unit Price", "Quantity", "Subtotal"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        queueTable = new JTable(tableModel);
        styleTable(queueTable);
        
        JScrollPane scrollPane = new JScrollPane(queueTable);
        scrollPane.getViewport().setBackground(new Color(20, 25, 30));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 213, 50)));
        add(scrollPane, BorderLayout.CENTER);

        // --- RIGHT: SIDEBAR ---
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setOpaque(false);
        sidebar.setPreferredSize(new Dimension(180, 0));

        JButton btnProcess = createSmallButton("Process Next", new Color(0, 180, 150));
        JButton btnPeek = createSmallButton("Peek Next", new Color(50, 100, 180));
        JButton btnClear = createSmallButton("Clear All", new Color(200, 50, 50));
        JButton btnBack = createSmallButton("Back to Products", Color.DARK_GRAY);

        btnProcess.addActionListener(e -> handleProcess());
        btnPeek.addActionListener(e -> handlePeek());
        btnClear.addActionListener(e -> handleClear());
        btnBack.addActionListener(e -> navigateBack());

        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnProcess);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnPeek);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnClear);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnBack);

        add(sidebar, BorderLayout.EAST);
    }

    private void handleProcess() {
        if (!salesModel.isSalesQueueEmpty()) {
            // Process the FIFO queue
            Drug processed = salesModel.processNextSale();
            
            // CRITICAL: Push the processed drug to the history model
            historyModel.addSoldDrug(processed); 
            
            JOptionPane.showMessageDialog(this, "Success: " + processed.getName() + " processed.\nTransaction moved to History.");
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "The queue is empty.");
        }
    }

    private void handlePeek() {
        // Look at the first item without removing it
        Drug next = salesModel.viewNextSale();
        if (next != null) {
            JOptionPane.showMessageDialog(this, "Next in Queue: " + next.getName() + "\nVendor: " + next.getVendor());
        } else {
            JOptionPane.showMessageDialog(this, "No items in queue.");
        }
    }

    private void handleClear() {
        salesModel.clearSales();
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        double grandTotal = 0;
        Map<String, Integer> counts = new LinkedHashMap<>();
        Map<String, Drug> drugDetails = new LinkedHashMap<>();

        for (Drug d : salesModel.getAllSales()) {
            counts.put(d.getName(), counts.getOrDefault(d.getName(), 0) + 1);
            drugDetails.put(d.getName(), d);
        }

        for (String name : counts.keySet()) {
            Drug d = drugDetails.get(name);
            int qty = counts.get(name);
            double subtotal = d.getUnitCost() * qty;
            grandTotal += subtotal;
            tableModel.addRow(new Object[]{name, d.getVendor(), "RS. " + d.getUnitCost(), qty, "RS. " + subtotal});
        }
        lblTotal.setText("Total Queue Value: RS. " + String.format("%.2f", grandTotal));
    }

    private void navigateBack() {
        Container parent = this.getParent();
        if (parent != null) {
            parent.removeAll();
            parent.add(new Products(this.controller, this.salesModel, this.historyModel), BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }

    private void styleTable(JTable table) {
        table.setBackground(new Color(30, 35, 45));
        table.setForeground(Color.WHITE);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(0, 255, 213, 80));
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(15, 20, 25));
        header.setForeground(new Color(0, 255, 213));
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    private JButton createSmallButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(170, 40));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}