package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CardImage extends JPanel {

    private BufferedImage img;
    private int width;
    private int height;


    CardImage(BufferedImage img, int width) {
        this.img = img;

        float imgWidth = img.getWidth();
        float imgHeight = img.getHeight();
        float ratio = imgHeight / imgWidth;

        this.width = width;
        this.height = (int) (width * ratio);
        setPreferredSize(new Dimension(width, height));

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2draw = (Graphics2D) g;

        g2draw.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2draw.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2draw.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);

        g2draw.drawImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, this);
    }
}
