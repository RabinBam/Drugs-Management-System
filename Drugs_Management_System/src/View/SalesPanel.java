package View;

import Model.Drug;
import Model.SalesModel;
import Model.History;
import Controller.DrugController;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class SalesPanel extends JPanel {

    private SalesModel salesModel;
    private DrugController controller;
    private History historyModel;
    private JTable queueTable;
    private DefaultTableModel tableModel;
    private JLabel lblTotal;
    private Dashboard_Admin mainDashboard;

public SalesPanel(SalesModel salesModel, DrugController controller, History historyModel, Dashboard_Admin dashboard) {
    this.salesModel = salesModel;
    this.controller = controller;
    this.historyModel = historyModel;
    this.mainDashboard = dashboard; 
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
        lblTitle.setForeground(new Color(0, 255, 213));

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
        JButton btnProcessAll = createSmallButton("Process All", new Color(0, 120, 215)); // New Button
        JButton btnPeek = createSmallButton("Peek Next", new Color(50, 100, 180));
        JButton btnClear = createSmallButton("Clear All", new Color(200, 50, 50));
        JButton btnBack = createSmallButton("Back to Products", Color.DARK_GRAY);

        btnProcess.addActionListener(e -> handleProcess());
        btnProcessAll.addActionListener(e -> handleProcessAll()); // New Action
        btnPeek.addActionListener(e -> handlePeek());
        btnClear.addActionListener(e -> handleClear());
        btnBack.addActionListener(e -> navigateBack());

        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnProcess);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnProcessAll); // Added to UI
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnPeek);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnClear);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnBack);

        add(sidebar, BorderLayout.EAST);
    }
/**
 * Processes the next sale in the sales queue.
 * Moves the drug from the queue to the sales history, updates the
 * main dashboard statistics if available, and refreshes the sales table.
 */

private void handleProcess() {
    if (!salesModel.isSalesQueueEmpty()) {
        Drug processed = salesModel.processNextSale();
        historyModel.addSoldDrug(processed); 
        
        // This is the bridge that triggers the refresh
        if (mainDashboard != null) {
            mainDashboard.updateDashboardStats(); 
        }
        
        refreshTable();
    }
}
/**
 * Processes all pending sales in the sales queue.
 * Transfers all drugs to the sales history, clears the queue,
 * updates the main dashboard statistics if available, refreshes the table,
 * and notifies the user of the action. Displays a message if the queue is empty.
 */

private void handleProcessAll() {
    if (!salesModel.isSalesQueueEmpty()) {
        List<Drug> allPending = salesModel.getAllSales();
        for (Drug d : allPending) {
            historyModel.addSoldDrug(d);
        }
        salesModel.clearSales();
        
        // DYNAMIC UPDATE: Refresh the dashboard stats in the background
        if (mainDashboard != null) {
            mainDashboard.updateDashboardStats();
        }
        
        JOptionPane.showMessageDialog(this, "All items moved to History.");
        refreshTable();
    } else {
        JOptionPane.showMessageDialog(this, "Nothing to process.");
    }
}
    private void handlePeek() {
        Drug next = salesModel.viewNextSale();
        if (next != null) {
            JOptionPane.showMessageDialog(this, "Next: " + next.getName());
        } else {
            JOptionPane.showMessageDialog(this, "No items in queue.");
        }
    }

    private void handleClear() {
        salesModel.clearSales();
        refreshTable();
    }
/**
 * Refreshes the sales table with the latest data from the sales model.
 * Aggregates quantities of each drug, calculates subtotals and the grand total,
 * updates the table rows, and displays the total queue value.
 */

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
        Products productsPanel = new Products(this.controller, this.salesModel, this.historyModel, this.mainDashboard);
        parent.add(productsPanel, BorderLayout.CENTER);
        parent.revalidate();
        parent.repaint();
    }
}
private void styleTable(JTable table) {
    // Table Body Styling
    table.setBackground(new Color(30, 35, 45));
    table.setForeground(new Color(255, 255, 255, 180)); // Faded white text
    table.setRowHeight(35);
    table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    table.setGridColor(new Color(60, 70, 80)); // Subtle grid lines
    JTableHeader header = table.getTableHeader();
    header.setBackground(new Color(15, 20, 25)); // Darker background for contrast
    header.setForeground(Color.WHITE); 
    header.setFont(new Font("Segoe UI", Font.BOLD, 14));
    header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 255, 213, 100)));
}

    private JButton createSmallButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(170, 40));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}