/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
/**
 *
 * @author ASUS
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class ProductListPanel extends javax.swing.JPanel {
    private Controller.DrugController controller;

    public ProductListPanel(Controller.DrugController controller) {
        this.controller = controller;
        initComponents();
        applyGlassTheme();
    }

    private void applyGlassTheme() {
        setOpaque(false);
        Color cyan = new Color(0, 255, 255);
        
        // Style Header
        lblTitle.setForeground(cyan);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        
        // Style the Table
        productTable.setBackground(new Color(20, 40, 50, 150));
        productTable.setForeground(Color.WHITE);
        productTable.setGridColor(new Color(0, 255, 255, 50));
        productTable.setRowHeight(35);
        productTable.getTableHeader().setBackground(new Color(10, 25, 30));
        productTable.getTableHeader().setForeground(cyan);
        productTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        
        // Style Buttons
        btnAddProduct.setBackground(cyan);
        btnAddProduct.setForeground(new Color(15, 25, 30));
        
        // Make ScrollPane transparent
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255, 100)));
    }

    private void initComponents() {
        lblTitle = new JLabel("Products");
        btnAddProduct = new JButton("+ Add Product");
        jScrollPane1 = new JScrollPane();
        productTable = new JTable();
        
        // Filter placeholders (Matching your image)
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setOpaque(false);
        String[] filters = {"Type", "Vendor", "Material", "Color"};
        for (String f : filters) {
            JButton btn = new JButton(f + " ▾");
            btn.setContentAreaFilled(false);
            btn.setForeground(Color.LIGHT_GRAY);
            filterPanel.add(btn);
        }

        // Table Model (Matching your image columns)
        productTable.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] { "#", "Name", "Type", "Vendor", "In Stock", "Unit Cost", "Material", "Color" }
        ));

        btnAddProduct.addActionListener(e -> {
            // Logic to open Entry Form
            openEntryForm();
        });

        // Layout logic
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(lblTitle, BorderLayout.WEST);
        topPanel.add(btnAddProduct, BorderLayout.EAST);
        
        JPanel headerContainer = new JPanel(new GridLayout(2, 1));
        headerContainer.setOpaque(false);
        headerContainer.add(topPanel);
        headerContainer.add(filterPanel);

        add(headerContainer, BorderLayout.NORTH);
        jScrollPane1.setViewportView(productTable);
        add(jScrollPane1, BorderLayout.CENTER);
    }

    private void openEntryForm() {
        Container parent = this.getParent();
        parent.removeAll();
        parent.add(new ProductEntryForm(this.controller), BorderLayout.CENTER);
        parent.revalidate();
        parent.repaint();
    }

    private JLabel lblTitle;
    private JButton btnAddProduct;
    private JScrollPane jScrollPane1;
    private JTable productTable;
}
