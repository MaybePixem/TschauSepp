package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Displays an image for a card.
 *
 * @author Tim Bucher
 */
public class CardImage extends JPanel {

    private BufferedImage img;
    private int width;
    private int height;
    private int cardIndexOnPlayerDeck;
    private GameView gameViewRef;

    CardImage(BufferedImage img, int width, int cardIndexOnPlayerDeck, GameView gameViewRef) {
        this.img = img;
        this.cardIndexOnPlayerDeck = cardIndexOnPlayerDeck;
        this.gameViewRef = gameViewRef;

        float imgWidth = img.getWidth();
        float imgHeight = img.getHeight();
        float ratio = imgHeight / imgWidth;

        this.width = width;
        this.height = (int) (width * ratio);
        setPreferredSize(new Dimension(width, height));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                gameViewRef.playerClickedCard(cardIndexOnPlayerDeck);
            }
        });

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
