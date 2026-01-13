    package model;

    import java.awt.*;
    import javax.swing.*;

    public class PieChart extends JPanel {

        private double[] values;
        private Color[] colors;
        private String[] labels;

        public PieChart() {

            values = new double[]{70, 30};         
            labels = new String[]{"Retail", "Wholesale"};

            colors = new Color[]{new Color(0, 255, 255), new Color(200, 200, 200)};       
            setPreferredSize(new Dimension(200, 150));
            setOpaque(false);
        }

        public void setData(double[] values, String[] labels, Color[] colors) {
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
            int diameter = Math.min(width, height) - 40;

            double total = 0;
            for (double v : values) total += v;

            double curAngle = 90; // Start from top
            for (int i = 0; i < values.length; i++) {
                double angle = values[i] / total * 360;

                g2.setColor(colors[i]);
                g2.fillArc(10, (height - diameter) / 2, diameter, diameter, (int) curAngle, (int) angle);

                // Draw Legend (Small boxes on the right)
                int legendX = diameter + 30;
                int legendY = 30 + (i * 25);
                g2.fillRect(legendX, legendY, 15, 15);

                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
                g2.drawString(labels[i], legendX + 25, legendY + 12);

                curAngle += angle;
            }
        }
    }