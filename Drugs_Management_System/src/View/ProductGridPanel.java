package View;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import Model.Drug;

public class ProductGridPanel extends JPanel {

    private Controller.DrugController controller;
    private ArrayList<Drug> displayList;

    /**
     * Constructor accepts the list of drugs to display.
     * This allows the parent Panel to handle the Search/Sort logic.
     */
    public ProductGridPanel(Controller.DrugController controller, ArrayList<Drug> list) {
        this.controller = controller;
        this.displayList = list;
        
        initComponents();
    }

    private void initComponents() {
        this.setOpaque(false); // Transparent to show dashboard background
        this.setLayout(new BorderLayout());

        // Grid layout: 0 rows (dynamic), 3 columns, 20px gaps
        JPanel gridContainer = new JPanel(new GridLayout(0, 3, 20, 20));
        gridContainer.setOpaque(false);
        gridContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a card for every drug in the provided list
        for (Drug drug : displayList) {
            gridContainer.add(createProductCard(drug));
        }

        // Wrap in a ScrollPane for long lists
        JScrollPane scrollPane = new JScrollPane(gridContainer);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createProductCard(Drug d) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(0, 10));
        card.setBackground(new Color(30, 38, 48)); // Dark Card Background
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 255, 213, 60), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // 1. Image Section
        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(JLabel.CENTER);
        imgLabel.setPreferredSize(new Dimension(180, 160));
        
        try {
            // Loading image from your source directory structure
            String path = d.getPicture();
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            Image img = icon.getImage().getScaledInstance(180, 150, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imgLabel.setText("IMAGE NOT FOUND");
            imgLabel.setForeground(Color.GRAY);
        }

        // 2. Info Section (Name and Price)
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(d.getName().toUpperCase());
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel priceLabel = new JLabel("RS. " + d.getUnitCost());
        priceLabel.setForeground(new Color(0, 255, 213)); // Medical Cyan
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);

        // 3. Action Button
        JButton btnDetail = new JButton("View More Detail");
        styleDetailButton(btnDetail);
        
        btnDetail.addActionListener(e -> {
            navigateToDetails(d);
        });

        card.add(imgLabel, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(btnDetail, BorderLayout.SOUTH);

        return card;
    }

    private void styleDetailButton(JButton btn) {
        btn.setBackground(new Color(0, 255, 213));
        btn.setForeground(new Color(15, 20, 25));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
    }

    private void navigateToDetails(Drug drug) {
        // Navigates to the ItemDetail page
        Container parent = this.getParent().getParent(); // Reaching the centerPanel of ProductListPanel
        if (parent != null) {
            parent.removeAll();
            parent.add(new ItemDetail(drug, controller), BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }
}