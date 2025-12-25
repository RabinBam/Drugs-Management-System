/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {

    private Image backgroundImage;

    public BackgroundPanel(Image image) {
        this.backgroundImage = image;
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        // Draw background image
        g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // Dark overlay (thin)
        g2.setColor(new Color(0, 0, 0, 70)); // opacity: 0–255
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.dispose();
    }
}