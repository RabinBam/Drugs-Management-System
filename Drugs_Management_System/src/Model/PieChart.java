package model;

import java.awt.*;
import javax.swing.*;

public class PieChart extends JPanel {

    private double[] values;    // The values for the pie slices
    private Color[] colors;     // The colors for each slice
    private String[] labels;    // Labels for each slice

    public PieChart() {
        // Dummy data for showcase (values only, labels empty for now)
        values = new double[]{40, 30, 20, 10};         
        labels = new String[values.length];            // empty labels
        colors = new Color[]{Color.BLUE, Color.GREEN, Color.ORANGE, Color.RED};       
        setPreferredSize(new Dimension(400, 300));
    }

    // Allow setting values dynamically
    public void setData(double[] values, String[] labels, Color[] colors) {
        if (values.length != labels.length || values.length != colors.length) {
            throw new IllegalArgumentException("Arrays must have the same length");
        }
        this.values = values;
        this.labels = labels;
        this.colors = colors;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (values == null || values.length == 0) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int diameter = Math.min(width, height) - 50;

        // 3D effect offset
        int offsetY = 20;

        double total = 0;
        for (double v : values) total += v;

        double curAngle = 0;
        for (int i = 0; i < values.length; i++) {
            double angle = values[i] / total * 360;

            // Draw 3D-like "side"
            g2.setColor(colors[i].darker());
            g2.fillArc((width - diameter) / 2, (height - diameter) / 2 + offsetY, diameter, diameter, (int) curAngle, (int) angle);

            // Draw top
            g2.setColor(colors[i]);
            g2.fillArc((width - diameter) / 2, (height - diameter) / 2, diameter, diameter, (int) curAngle, (int) angle);

            curAngle += angle;
        }

        // Draw labels (only if they are not empty)
        curAngle = 0;
        for (int i = 0; i < values.length; i++) {
            double angle = values[i] / total * 360;
            if (labels[i] != null && !labels[i].isEmpty()) {
                double rad = Math.toRadians(curAngle + angle / 2);
                int x = (int) (width / 2 + Math.cos(rad) * diameter / 2.5);
                int y = (int) (height / 2 + Math.sin(rad) * diameter / 2.5);
                g2.setColor(Color.BLACK);
                g2.drawString(labels[i], x - 20, y);
            }
            curAngle += angle;
        }
    }
}
