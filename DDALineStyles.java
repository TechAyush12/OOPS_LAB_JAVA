import java.awt.*;
import javax.swing.*;

// Java program to draw line styles using the DDA algorithm
// Solid, Dotted, Dashed, Dash-Dot and Thick

public class DDALineStyles {

    // Base class: knows how to plot a single pixel
    static class Pixel {
        protected Graphics g;

        Pixel(Graphics g) {
            this.g = g;
        }

        void putPixel(int x, int y, Color col) {
            g.setColor(col);
            g.fillRect(x, y, 1, 1);
        }
    }

    // Derived class: implements DDA with different line styles
    static class DDA extends Pixel {

        DDA(Graphics g) {
            super(g);
        }

        // style:
        // 1 = Solid
        // 2 = Dotted
        // 3 = Dashed
        // 4 = Dash-Dot
        // 5 = Thick
        void drawLine(int x1, int y1, int x2, int y2,
                      Color col, int style) {

            int dx = x2 - x1;
            int dy = y2 - y1;

            // Number of steps
            int len = Math.max(Math.abs(dx), Math.abs(dy));

            // Avoid division by zero for a single-point line
            if (len == 0) {
                putPixel(x1, y1, col);
                return;
            }

            // Increment per step
            float xInc = (float) dx / len;
            float yInc = (float) dy / len;

            float x = x1;
            float y = y1;

            for (int i = 0; i <= len; i++) {

                int px = Math.round(x);
                int py = Math.round(y);

                switch (style) {

                    case 1: // Solid
                        putPixel(px, py, col);
                        break;

                    case 2: // Dotted: 1 on, 3 off
                        if (i % 4 == 0)
                            putPixel(px, py, col);
                        break;

                    case 3: // Dashed: 8 on, 4 off
                        if (i % 12 < 8)
                            putPixel(px, py, col);
                        break;

                    case 4: // Dash-Dot
                        // 8 on, 3 off, 1 on, 3 off
                        int p = i % 15;

                        if (p < 8 || p == 11)
                            putPixel(px, py, col);
                        break;

                    case 5: // Thick line: 3 pixels wide
                        putPixel(px, py - 1, col);
                        putPixel(px, py, col);
                        putPixel(px, py + 1, col);
                        break;
                }

                x += xInc;
                y += yInc;
            }
        }
    }

    // Panel on which the lines are drawn
    static class DrawPanel extends JPanel {

        DrawPanel() {
            setBackground(Color.BLACK);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            DDA d = new DDA(g);
            Color c = Color.WHITE;

            String[] names = {
                "Solid",
                "Dotted",
                "Dashed",
                "Dash-Dot",
                "Thick"
            };

            for (int i = 0; i < 5; i++) {

                int y = 60 + i * 60;

                // Draw label
                g.setColor(Color.YELLOW);
                g.drawString(
                    (i + 1) + ". " + names[i] + " Line",
                    20,
                    y - 10
                );

                // Draw line using DDA
                d.drawLine(
                    100,
                    y,
                    500,
                    y,
                    c,
                    i + 1
                );
            }
        }
    }

    // Main method
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame f = new JFrame("DDA Line Styles");

            f.add(new DrawPanel());

            f.setSize(620, 400);

            f.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
            );

            f.setLocationRelativeTo(null);

            f.setVisible(true);
        });
    }
}

