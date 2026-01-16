package model;

import java.awt.*;
import javax.swing.*;

public class PieChart extends JPanel {
    private double[] values;
    private Color[] colors;
    private String[] labels;

    public PieChart() {
        // Default starting data
        this.values = new double[]{0, 0};         
        this.labels = new String[]{"Sales", "Pending"};
        this.colors = new Color[]{new Color(0, 255, 213), new Color(150, 160, 170)};       
        setPreferredSize(new Dimension(200, 150));
        setOpaque(false);
    }

    public void setData(double[] values, String[] labels, Color[] colors) {
        this.values = values;
        this.labels = labels;
        this.colors = colors;
        this.revalidate();
        this.repaint(); 
    }

    @Override
    /**
 * Custom paint method for rendering a pie chart with legend.
 * Draws each segment proportionally based on the values array,
 * applies corresponding colors, and displays a labeled legend with counts.
 * Handles empty data by drawing a placeholder circle.
 *
 * @param g the Graphics context used for drawing
 */

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (values == null || values.length == 0) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int diameter = Math.min(width, height) - 40;

        double total = 0;
        for (double v : values) total += v;
        
        // If no data, draw an empty circle to avoid errors
        if (total == 0) {
            g2.setColor(new Color(50, 50, 50));
            g2.drawOval(10, (height - diameter) / 2, diameter, diameter);
            return;
        }

        double curAngle = 90; 
        for (int i = 0; i < values.length; i++) {
            double angle = (values[i] / total) * 360;
            g2.setColor(colors[i]);
            g2.fillArc(10, (height - diameter) / 2, diameter, diameter, (int) curAngle, (int) angle);

            int legendX = diameter + 30;
            int legendY = 30 + (i * 25);
            g2.fillRect(legendX, legendY, 15, 15);
            g2.setColor(Color.WHITE);
            g2.drawString(labels[i] + " (" + (int)values[i] + ")", legendX + 25, legendY + 12);
            
            curAngle += angle;
        }
    }
}