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
    private History historyModel; // Shared instance
    private DrugController controller;

    public HistoryPanel(DrugController controller, History sharedHistory) {
        this.controller = controller;
        this.historyModel = sharedHistory; // Use the passed history
        
        setLayout(new BorderLayout());
        setOpaque(false);
        initComponents();
        refreshHistoryTable();
    }

    private void initComponents() {
        // UI Header
        JLabel lblHeader = new JLabel("Admin: Sales & Transaction History");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblHeader.setForeground(new Color(0, 255, 213)); // Teal color
        lblHeader.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(lblHeader, BorderLayout.NORTH);

        // Table Columns: Name, Price, Category (from SimilarDrugs), Vendor
        String[] columnNames = {"Drug Name", "Unit Cost", "Category/Disease", "Vendor"};
        tableModel = new DefaultTableModel(columnNames, 0);
        historyTable = new JTable(tableModel);
        
        // Styling
        historyTable.setBackground(new Color(45, 45, 45));
        historyTable.setForeground(Color.WHITE);
        historyTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.setOpaque(false);
        
        JButton btnUndo = new JButton("Undo Last Sale");
        btnUndo.addActionListener(e -> {
            if (!historyModel.isEmpty()) {
                historyModel.undoLastSale();
                refreshHistoryTable();
            } else {
                JOptionPane.showMessageDialog(this, "No history to undo.");
            }
        });

        southPanel.add(btnUndo);
        add(southPanel, BorderLayout.SOUTH);
    }

    public void refreshHistoryTable() {
    tableModel.setRowCount(0);
    // Directly access the persistent stack from your shared model
    ArrayList<Drug> historyList = historyModel.getHistoryStack().getAll(); 
    
    for (int i = historyList.size() - 1; i >= 0; i--) {
        Drug d = historyList.get(i);
        tableModel.addRow(new Object[]{
            d.getName(), d.getUnitCost(), d.getDisease(), d.getVendor()
        });
    }
}
}