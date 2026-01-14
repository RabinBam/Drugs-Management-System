package View;

import Model.*;
import Controller.DrugController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class HistoryPanel extends JPanel {
    private JTable historyTable;
    private DefaultTableModel tableModel;
    private History historyModel;
    private DrugController controller;
    private Dashboard_Admin mainDashboard;

 public HistoryPanel(DrugController controller, History sharedHistory, Dashboard_Admin dashboard) {
    this.controller = controller;
    this.historyModel = sharedHistory;
    this.mainDashboard = dashboard; // Link established
    
    setLayout(new BorderLayout());
    setOpaque(false);
    initComponents();
    refreshHistoryTable();
}

    private void initComponents() {
        JLabel lblHeader = new JLabel("Admin: Sales & Transaction History");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblHeader.setForeground(new Color(0, 255, 213));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(lblHeader, BorderLayout.NORTH);

        String[] columnNames = {"Drug Name", "Unit Cost", "Category/Disease", "Vendor"};
        tableModel = new DefaultTableModel(columnNames, 0);
        historyTable = new JTable(tableModel);
        
        historyTable.setBackground(new Color(45, 45, 45));
        historyTable.setForeground(Color.WHITE);
        historyTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane, BorderLayout.CENTER);

        // --- BUTTON PANEL ---
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.setOpaque(false);
        
        // Peek Button (Show Top of Stack)
        JButton btnPeek = new JButton("Peek Latest Sale");
        btnPeek.addActionListener(e -> {
            Drug last = historyModel.getLastSoldDrug();
            if (last != null) {
                JOptionPane.showMessageDialog(this, "Most Recent Sale: " + last.getName());
            } else {
                JOptionPane.showMessageDialog(this, "History is empty.");
            }
        });

        // Undo Button (Pop from Stack)
        JButton btnUndo = new JButton("Undo Last Sale");
        btnUndo.addActionListener(e -> {
    if (!historyModel.isEmpty()) {
        historyModel.undoLastSale();
        refreshHistoryTable();
        // LINK: Update the dashboard visuals immediately
        if (mainDashboard != null) mainDashboard.updateDashboardStats();
    } else {
        JOptionPane.showMessageDialog(this, "No history to undo.");
    }
});

        // Clear Button (Reset Stack)
        JButton btnClear = new JButton("Clear History");
        btnClear.addActionListener(e -> {
    historyModel.clearHistory();
    refreshHistoryTable();
    // LINK: Reset the dashboard visuals
    if (mainDashboard != null) mainDashboard.updateDashboardStats();
});

        southPanel.add(btnPeek);
        southPanel.add(btnUndo);
        southPanel.add(btnClear);
        add(southPanel, BorderLayout.SOUTH);
    }

   public void refreshHistoryTable() {
    tableModel.setRowCount(0);
    java.util.ArrayList<Model.Drug> historyList = historyModel.getHistoryStack().getAll();

    // Map to group: Drug Name -> Quantity
    java.util.Map<String, Integer> counts = new java.util.HashMap<>();
    java.util.Map<String, Model.Drug> drugData = new java.util.HashMap<>();

    for (Model.Drug d : historyList) {
        counts.put(d.getName(), counts.getOrDefault(d.getName(), 0) + 1);
        drugData.put(d.getName(), d);
    }

    // Set Font to White as requested
    historyTable.setForeground(Color.WHITE);

    // Add grouped rows to table
    for (String name : counts.keySet()) {
        Model.Drug d = drugData.get(name);
        int qty = counts.get(name);
        tableModel.addRow(new Object[]{
            name + " (x" + qty + ")", 
            "RS. " + (d.getUnitCost() * qty), // Total cost for this group
            d.getDisease(), 
            d.getVendor()
        });
    }
}
}