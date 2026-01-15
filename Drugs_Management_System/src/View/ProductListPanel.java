package View;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.table.*;
import java.util.ArrayList;
import Model.Drug;
import Controller.SearchController;

public class ProductListPanel extends javax.swing.JPanel {

    private Controller.DrugController controller;
    private JLabel lblTitle;
    private JButton btnAddProduct;
    private JTable productTable;
    private JScrollPane jScrollPane1;
    private JPanel centerPanel; 
    private JTextField searchField;
    private boolean isGridView = false;

    // High-Contrast Medical Palette
    private final Color NEON_CYAN = new Color(0, 255, 213);
    private final Color DEEP_BACKGROUND = new Color(10, 12, 15); // Dark Obsidian
    private final Color OFF_WHITE_ROW = new Color(245, 245, 245); // High Contrast Row
    private final Color LIGHT_GRAY_ROW = new Color(225, 228, 230); // Zebra Row

    public ProductListPanel(Controller.DrugController controller) {
        this.controller = controller;
        initComponents();
        applyMedicalTheme();
        updateView(); // Populates data immediately on load
    }

    private void applyMedicalTheme() {
        this.setOpaque(true);
        this.setBackground(DEEP_BACKGROUND);

        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));

        // Table Header Styling
        JTableHeader header = productTable.getTableHeader();
        header.setOpaque(true);
        header.setBackground(new Color(20, 25, 30));
        header.setForeground(new Color(180, 180, 180));
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, NEON_CYAN));

        // High-Contrast Table Setup
        productTable.setRowHeight(45);
        productTable.setShowGrid(false);
        productTable.setIntercellSpacing(new Dimension(0, 0));
        productTable.setSelectionBackground(new Color(0, 255, 213, 80));
        productTable.setSelectionForeground(Color.BLACK);

        productTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (isSelected) {
                    c.setBackground(new Color(0, 255, 213));
                    c.setForeground(Color.BLACK);
                } else {
                    // Using the Off-White colors for maximum contrast against the dark background
                    c.setBackground(row % 2 == 0 ? OFF_WHITE_ROW : LIGHT_GRAY_ROW);
                    c.setForeground(new Color(30, 30, 30)); // Dark text for light rows
                }
                
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
                ((JLabel) c).setFont(new Font("Segoe UI", Font.PLAIN, 14));
                return c;
            }
        });

        jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(60, 70, 80), 1));
        jScrollPane1.getViewport().setBackground(DEEP_BACKGROUND);

        // Vibrant Neon Add Button
        btnAddProduct.setBackground(NEON_CYAN);
        btnAddProduct.setForeground(DEEP_BACKGROUND);
        btnAddProduct.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAddProduct.setFocusPainted(false);
        btnAddProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void initComponents() {
        lblTitle = new JLabel("INVENTORY MANAGEMENT");
        btnAddProduct = new JButton("+ ADD NEW PRODUCT");
        jScrollPane1 = new JScrollPane();
        productTable = new JTable();
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        // --- SEARCH BAR (Vibrant Neon Border) ---
        searchField = new JTextField(15);
        searchField.setBackground(new Color(25, 30, 35));
        searchField.setForeground(NEON_CYAN);
        searchField.setCaretColor(NEON_CYAN);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(NEON_CYAN, 1), "Search Inventory", 
                0, 0, new Font("Segoe UI", Font.BOLD, 10), Color.GRAY));
        
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateView(); // Executes Binary Search
            }
        });

        // --- SORTING & VIEW PANEL ---
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        filterPanel.setOpaque(false);

        JButton btnSortName = createVibrantButton("Name ▾");
        btnSortName.addActionListener(e -> {
            Controller.SortByName.SortByName(controller.getDrugs());
            updateView();
        });

        JButton btnSortVendor = createVibrantButton("Supplier ▾");
        btnSortVendor.addActionListener(e -> {
            Controller.SortByVendor.sort(controller.getDrugs());
            updateView();
        });

        JButton btnSortPrice = createVibrantButton("Price ▾");
        btnSortPrice.addActionListener(e -> {
            Controller.SortByPrice.sortByPrice(controller.getDrugs());
            updateView();
        });

        JButton btnViewToggle = createVibrantButton("GRID VIEW ");
        btnViewToggle.addActionListener(e -> {
            isGridView = !isGridView;
            btnViewToggle.setText(isGridView ? "LIST VIEW ≡" : "GRID VIEW ");
            updateView();
        });

        filterPanel.add(btnSortName);
        filterPanel.add(btnSortVendor);
        filterPanel.add(btnSortPrice);
        filterPanel.add(Box.createHorizontalStrut(20));
        filterPanel.add(searchField);
        filterPanel.add(btnViewToggle);

        productTable.setModel(new DefaultTableModel(
            new Object[][]{}, 
            new String[]{"Drug Name", "Stock", "Supplier", "Unit Cost"}
        ));

        // Event for Add Product
        btnAddProduct.addActionListener(e -> switchToPanel(new ProductEntryForm(this.controller)));

        // --- FINAL LAYOUT ---
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(lblTitle, BorderLayout.WEST);
        topPanel.add(btnAddProduct, BorderLayout.EAST);

        JPanel headerContainer = new JPanel(new GridLayout(2, 1, 0, 20));
        headerContainer.setOpaque(false);
        headerContainer.add(topPanel);
        headerContainer.add(filterPanel);

        add(headerContainer, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void updateView() {
        // Integrated Binary Search from your Controller
        ArrayList<Drug> filteredList = SearchController.binarySearchByName(
            controller.getDrugs(), searchField.getText());

        centerPanel.removeAll();
        if (isGridView) {
            centerPanel.add(new ProductGridPanel(controller, filteredList), BorderLayout.CENTER);
        } else {
            refreshTableData(filteredList);
            jScrollPane1.setViewportView(productTable);
            centerPanel.add(jScrollPane1, BorderLayout.CENTER);
        }
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void refreshTableData(ArrayList<Drug> list) {
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0);
        for (Drug d : list) {
            model.addRow(new Object[]{
                d.getName(), 
                d.getStock() + " units", 
                d.getVendor(), 
                "RS. " + d.getUnitCost()
            });
        }
    }

    private JButton createVibrantButton(String text) {
        JButton btn = new JButton(text);
        btn.setContentAreaFilled(false);
        btn.setForeground(NEON_CYAN);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(NEON_CYAN, 1),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        return btn;
    }

    private void switchToPanel(JPanel newPanel) {
        Container parent = this.getParent(); 
        if (parent != null) {
            parent.removeAll();
            parent.add(newPanel);
            parent.revalidate();
            parent.repaint();
        }
    }
}