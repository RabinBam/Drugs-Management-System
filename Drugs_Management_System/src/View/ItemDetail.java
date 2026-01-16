package View;

import java.awt.*;
import javax.swing.*;

/**
 * Professional Medical Item Detail View
 */
public class ItemDetail extends javax.swing.JPanel {

    private Controller.DrugController controller;
    private Model.Drug selectedDrug;
    private ProductListPanel parentInventory; // Reference to the existing inventory panel

    /**
     * Updated Constructor
     * @param drug The drug to display
     * @param controller Shared controller
     * @param parent The existing ProductListPanel instance
     */
    public ItemDetail(Model.Drug drug, Controller.DrugController controller, ProductListPanel parent) {
        this.selectedDrug = drug;
        this.controller = controller;
        this.parentInventory = parent; // Store the reference
        
        // Basic Setup
        this.setOpaque(true);
        this.setBackground(new Color(18, 22, 25)); 
        this.setLayout(new BorderLayout(20, 20));
        
        initComponents();
        renderCustomUI();
    }
/**
 * Renders the custom UI layout for the panel.
 * Clears existing components, adds a header section at the top,
 * creates a two-column content area with an image display and technical info,
 * applies padding and spacing, and refreshes the panel display.
 */

    private void renderCustomUI() {
        this.removeAll();
        add(buildHeaderSection(), BorderLayout.NORTH);
        
        JPanel contentArea = new JPanel(new GridLayout(1, 2, 40, 0));
        contentArea.setOpaque(false);
        contentArea.setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50));
        
        contentArea.add(buildImageDisplay());
        contentArea.add(buildTechnicalInfo());
        
        add(contentArea, BorderLayout.CENTER);
        
        this.revalidate();
        this.repaint();
    }

    private JPanel buildHeaderSection() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));

        JLabel title = new JLabel("PHARMACEUTICAL SPECIFICATIONS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);

        // --- BACK BUTTON ---
        JButton btnBack = new JButton("← BACK TO INVENTORY");
        styleSecondaryButton(btnBack);
        btnBack.addActionListener(e -> navigateBack());

        header.add(title, BorderLayout.WEST);
        header.add(btnBack, BorderLayout.EAST);
        return header;
    }

    /**
     * Logic to refresh the existing panel instead of recreating it
     */
    private void navigateBack() {
        if (parentInventory != null) {
            // Simply call the updateView() of the panel that is already in memory
            parentInventory.updateView();
        }
    }

    private JPanel buildImageDisplay() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        try {
            String path = selectedDrug.getPicture();
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            Image scaled = icon.getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            imgLabel.setText("IMAGE NOT LOADED");
            imgLabel.setForeground(Color.GRAY);
        }

        imgLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 213, 40), 2));
        panel.add(imgLabel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildTechnicalInfo() {
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);

        JLabel nameHeader = new JLabel(selectedDrug.getName().toUpperCase());
        nameHeader.setFont(new Font("Segoe UI", Font.BOLD, 48));
        nameHeader.setForeground(new Color(0, 255, 213));

        info.add(nameHeader);
        info.add(Box.createVerticalStrut(30));
        info.add(createDetailRow("Current Price:", "RS. " + selectedDrug.getUnitCost()));
        info.add(createDetailRow("Stock Level:", selectedDrug.getStock() + " Units Available"));
        info.add(createDetailRow("Manufacturer:", selectedDrug.getVendor()));
        info.add(Box.createVerticalStrut(40));
        
        JLabel descHeader = new JLabel("Dosage & Description");
        descHeader.setForeground(Color.WHITE);
        descHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        JTextArea descBody = new JTextArea("Authorized medical data for " + selectedDrug.getName() + ". "
                + "Ensure proper storage temperature as per standards.");
        descBody.setLineWrap(true);
        descBody.setWrapStyleWord(true);
        descBody.setEditable(false);
        descBody.setOpaque(false);
        descBody.setForeground(new Color(170, 180, 190));
        descBody.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        info.add(descHeader);
        info.add(Box.createVerticalStrut(10));
        info.add(descBody);

        return info;
    }

    private JPanel createDetailRow(String label, String value) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        row.setOpaque(false);
        JLabel l = new JLabel(label + "  ");
        l.setForeground(new Color(140, 150, 160));
        l.setFont(new Font("Segoe UI", Font.BOLD, 17));
        JLabel v = new JLabel(value);
        v.setForeground(Color.WHITE);
        v.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        row.add(l); row.add(v);
        return row;
    }

    private void styleSecondaryButton(JButton b) {
        b.setBackground(new Color(30, 38, 48));
        b.setForeground(new Color(0, 255, 213));
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 255, 213, 80), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
    }

    private void initComponents() {} // NetBeans placeholder
}