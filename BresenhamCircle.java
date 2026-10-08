import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

// Canvas on which pixels are plotted
class CirclePanel extends JPanel {
    private BufferedImage img;

    CirclePanel(int w, int h) {
        img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(w, h));
        clear();
    }

    // Plot one pixel (ignores points outside the canvas)
    void putPixel(int x, int y, Color col) {
        if (x >= 0 && x < img.getWidth() && y >= 0 && y < img.getHeight())
            img.setRGB(x, y, col.getRGB());
    }

    void clear() {
        Graphics2D g = img.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        g.dispose();
        repaint();
    }

    // Bresenham's circle algorithm: decision parameter p = 3 - 2r
    void drawCircle(int x1, int y1, int r, Color col) {
        int x = 0;
        int y = r;
        int p = 3 - (2 * r);

        do {
            // 8-way symmetry
            putPixel(x1 + x, y1 - y, col);
            putPixel(x1 + y, y1 - x, col);
            putPixel(x1 + y, y1 + x, col);
            putPixel(x1 + x, y1 + y, col);
            putPixel(x1 - x, y1 + y, col);
            putPixel(x1 - y, y1 + x, col);
            putPixel(x1 - y, y1 - x, col);
            putPixel(x1 - x, y1 - y, col);

            if (p < 0) {
                p = p + (4 * x) + 6;
            } else {
                p = p + (4 * (x - y)) + 10;
                y = y - 1;
            }
            x = x + 1;
        } while (x <= y);

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }
}

public class BresenhamCircle {

    static CirclePanel canvas;
    static JTextField tfX, tfY, tfR;

    static void doDraw() {
        try {
            int x1 = Integer.parseInt(tfX.getText().trim());
            int y1 = Integer.parseInt(tfY.getText().trim());
            int r  = Integer.parseInt(tfR.getText().trim());
            if (r <= 0) {
                JOptionPane.showMessageDialog(null, "Radius must be greater than 0");
                return;
            }
            canvas.drawCircle(x1, y1, r, Color.WHITE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter whole numbers only");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Bresenham Circle");
            f.setLayout(new BorderLayout());

            canvas = new CirclePanel(600, 450);

            // ---- Input + button panel (menu) ----
            tfX = new JTextField("300", 4);
            tfY = new JTextField("225", 4);
            tfR = new JTextField("100", 4);

            JButton bDraw  = new JButton("1. Draw Circle");
            JButton bClear = new JButton("2. Clear");
            JButton bExit  = new JButton("3. Exit");

            JPanel top = new JPanel(new FlowLayout());
            top.add(new JLabel("x1:")); top.add(tfX);
            top.add(new JLabel("y1:")); top.add(tfY);
            top.add(new JLabel("Radius:")); top.add(tfR);
            top.add(bDraw); top.add(bClear); top.add(bExit);

            bDraw.addActionListener(e -> doDraw());
            bClear.addActionListener(e -> canvas.clear());
            bExit.addActionListener(e -> System.exit(0));

            f.add(top, BorderLayout.NORTH);
            f.add(canvas, BorderLayout.CENTER);

            f.pack();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}