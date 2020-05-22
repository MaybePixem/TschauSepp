package View;

import Controller.GameController;
import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class GameView extends JFrame {

    private final int MAX_CARD_SIZE = 120;

    private Game game;
    private HashMap<String, BufferedImage> cardImagesArr = new HashMap<>();
    private JPanel mainPanel;
    private JPanel playerDeckPanel;
    private JPanel playfieldPlanel;
    private JPanel otherPlayersPanel;

    public static void main(String[] args) throws IOException {
        Game game = new Game(1, 3, 15);
        new GameView(game);
    }

    public GameView(Game game) throws HeadlessException, IOException {
        this.game = game;

        readImages();

        setTitle("Tschau Sepp");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());
        playerDeckPanel = new JPanel();
        playfieldPlanel = new JPanel();
        otherPlayersPanel = new JPanel();

        redraw();

        mainPanel.add(otherPlayersPanel, BorderLayout.NORTH);
        mainPanel.add(playfieldPlanel, BorderLayout.CENTER);
        mainPanel.add(playerDeckPanel, BorderLayout.SOUTH);


        getContentPane().add(mainPanel);
        setVisible(true);

    }

    private void readImages() throws IOException {
        String filePath = new File("").getAbsolutePath();
        for (int i = 0; i < GameController.COLORS.length; i++) {
            for (int j = 0; j < GameController.NUMBERCARDS.length; j++) {
                cardImagesArr.put(i + GameController.NUMBERCARDS[j], ImageIO.read(new File(filePath + "/src/assets/images/" + GameController.NUMBERCARDS[j] + GameController.COLORS[i] + ".png")));
            }
            for (int j = 0; j < GameController.ACTIONCARDS.length; j++) {
                cardImagesArr.put(i + GameController.ACTIONCARDS[j], ImageIO.read(new File(filePath + "/src/assets/images/" + GameController.ACTIONCARDS[j] + GameController.COLORS[i] + ".png")));
            }
        }
    }

    private void redraw() {
        playfieldPlanel.add(
                new CardImage(
                        cardImagesArr.get(game.getCurrentDeck().get(game.getCurrentDeck().size() - 1).getColor()
                                + ((NumberCard) game.getCurrentDeck().get(game.getCurrentDeck().size() - 1)).getValue())
                        , MAX_CARD_SIZE)
        );

        int cardWidth = getWidth() / (game.getCurrentPlayer().getdecksize() + 2);
        cardWidth = cardWidth > MAX_CARD_SIZE ? MAX_CARD_SIZE : cardWidth;
        for (Card c :
                game.getCurrentPlayer().getdeck()) {
            CardImage img;
            if (c instanceof ActionCard) {
                img = new CardImage(cardImagesArr.get(c.getColor() + ((ActionCard) c).getAction()), cardWidth);
            } else {
                img = new CardImage(cardImagesArr.get(c.getColor() + ((NumberCard) c).getValue()), cardWidth);
            }
            playerDeckPanel.add(img);
        }

        for (Player p :
                game.getPlayers()) {
            otherPlayersPanel.add(new JLabel(Integer.toString(p.getdecksize())));
        }
    }

}