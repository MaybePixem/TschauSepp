package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CardImage extends JPanel {

    private BufferedImage img;
    private int width;
    private int height;


    public CardImage(BufferedImage img, int width) {
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
        g.drawImage(img.getScaledInstance(width, height, Image.SCALE_DEFAULT), 0, 0, this);
    }
}
