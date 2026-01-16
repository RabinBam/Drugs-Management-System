package View;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import Model.Drug;
import java.net.URL;

/**
 * ProductGridPanel displays drug inventory as a grid of cards.
 * Updated to perform a master-list lookup for images to ensure consistency.
 */
public class ProductGridPanel extends JPanel {

    private Controller.DrugController controller;
    private ArrayList<Drug> displayList;

    public ProductGridPanel(Controller.DrugController controller, ArrayList<Drug> list) {
        this.controller = controller;
        this.displayList = list; 
        initComponents();
    }

    private void initComponents() {
        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        // Container for cards: 3 columns, dynamic rows
        JPanel gridContainer = new JPanel(new GridLayout(0, 3, 20, 20));
        gridContainer.setOpaque(false);
        gridContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a card for every drug in the filtered list
        for (Drug drug : displayList) {
            gridContainer.add(createProductCard(drug));
        }

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
        card.setBackground(new Color(30, 38, 48)); 
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 255, 213, 60), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // --- IMAGE SECTION: MASTER LOOKUP LOGIC ---
        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(JLabel.CENTER);
        imgLabel.setPreferredSize(new Dimension(180, 160));
        
        // REFRESH REFERENCE: Look up the drug in the Master List by Name
        // This ensures that even if 'd' lost its picture path, we find it from the source
        String imagePath = null;
        for (Drug masterDrug : controller.getDrugs()) {
            if (masterDrug.getName().equalsIgnoreCase(d.getName())) {
                imagePath = masterDrug.getPicture();
                break;
            }
        }

        if (imagePath != null && !imagePath.isEmpty()) {
            // Standard Resource Loading
            URL imgURL = getClass().getResource(imagePath);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(180, 150, Image.SCALE_SMOOTH);
                imgLabel.setIcon(new ImageIcon(img));
            } else {
                imgLabel.setText("IMAGE NOT FOUND");
                imgLabel.setForeground(Color.GRAY);
                System.err.println("Resource missing: " + imagePath);
            }
        } else {
            imgLabel.setText("NO IMAGE DEFINED");
            imgLabel.setForeground(Color.GRAY);
        }

        // --- INFO SECTION ---
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(d.getName().toUpperCase());
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel priceLabel = new JLabel("RS. " + d.getUnitCost());
        priceLabel.setForeground(new Color(0, 255, 213)); 
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);

        // --- ACTION BUTTON ---
        JButton btnDetail = new JButton("View More Detail");
        styleDetailButton(btnDetail);
        
        btnDetail.addActionListener(e -> navigateToDetails(d));

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
/**
 * Navigates from the product list to the detailed view of a selected drug.
 * Replaces the center panel content with an ItemDetail panel, passing
 * the selected drug, the controller, and a reference to the parent panel.
 *
 * @param drug the Drug object whose details are to be displayed
 */

   private void navigateToDetails(Drug drug) {
    Container parent = this.getParent(); // This is 'centerPanel'
    if (parent != null) {
        Container mainListPanel = parent.getParent(); // This is 'ProductListPanel'
        if (mainListPanel instanceof ProductListPanel) {
            parent.removeAll();
            // Pass 'drug', 'controller', and the 'mainListPanel' reference
            parent.add(new ItemDetail(drug, controller, (ProductListPanel)mainListPanel), BorderLayout.CENTER);
            parent.revalidate();
            parent.repaint();
        }
    }
}
}