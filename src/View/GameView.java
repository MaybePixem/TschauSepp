package View;

import Controller.GameController;
import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class GameView extends JFrame {

    private final int MAX_CARD_SIZE = 150;
    private final int BLANK_CARD_INDEX = -1;
    private final int DECK_CARD_INDEX = -2;

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
        setMinimumSize(new Dimension(400, 350));
        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        redraw();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                redraw();
            }
        });

        getContentPane().add(mainPanel);
        setVisible(true);

    }

    private void readImages() throws IOException {
        String filePath = new File("").getAbsolutePath();
        for (int i = 0; i < GameController.COLORS.length; i++) {
            for (int j = 0; j < GameController.NUMBERCARDS.length; j++) {
                cardImagesArr.put(
                        i + GameController.NUMBERCARDS[j],
                        ImageIO.read(new File(filePath + "/src/assets/images/" + GameController.NUMBERCARDS[j] + GameController.COLORS[i] + ".png"))
                );
            }
            for (int j = 0; j < GameController.ACTIONCARDS.length; j++) {
                cardImagesArr.put(i + GameController.ACTIONCARDS[j],
                        ImageIO.read(new File(filePath + "/src/assets/images/" + GameController.ACTIONCARDS[j] + GameController.COLORS[i] + ".png"))
                );
            }
        }
        cardImagesArr.put("blankCard", ImageIO.read(new File(filePath + "/src/assets/images/blankCard.png")));
    }

    private void redraw() {
        mainPanel = new JPanel(new BorderLayout());
        playerDeckPanel = new JPanel();
        playfieldPlanel = new JPanel();
        otherPlayersPanel = new JPanel();

        playfieldPlanel.add(new CardImage(cardImagesArr.get("blankCard"), Math.min(getWidth() / 3, MAX_CARD_SIZE), BLANK_CARD_INDEX, this));
        Card currentCardOnDeck = game.getCurrentDeck().get(game.getCurrentDeck().size() - 1);
        if (currentCardOnDeck instanceof NumberCard) {
            playfieldPlanel.add(
                    new CardImage(
                            cardImagesArr.get(currentCardOnDeck.getColor()
                                    + currentCardOnDeck.getValue())
                            , Math.min(getWidth() / 3, MAX_CARD_SIZE)
                            , DECK_CARD_INDEX
                            , this
                    )
            );
        } else {
            playfieldPlanel.add(
                    new CardImage(
                            cardImagesArr.get(currentCardOnDeck.getColor()
                                    + currentCardOnDeck.getValue())
                            , Math.min(getWidth() / 3, MAX_CARD_SIZE)
                            , DECK_CARD_INDEX
                            , this
                    )
            );
        }

        int cardWidth = Math.min(getWidth() / (game.getCurrentPlayer().getdecksize() + 2), MAX_CARD_SIZE);

        for (int i = 0; i < game.getCurrentPlayer().getdeck().size(); i++) {
            Card c = game.getCurrentPlayer().getdeck().get(i);
            CardImage img;
            if (c instanceof ActionCard) {
                img = new CardImage(cardImagesArr.get(c.getColor() + c.getValue()), cardWidth, i, this);
            } else {
                img = new CardImage(cardImagesArr.get(c.getColor() + c.getValue()), cardWidth, i, this);
            }
            playerDeckPanel.add(img);
        }

        for (Player p :
                game.getPlayers()) {
            otherPlayersPanel.add(new JLabel(Integer.toString(p.getdecksize())));
        }

        mainPanel.add(otherPlayersPanel, BorderLayout.NORTH);
        mainPanel.add(playfieldPlanel, BorderLayout.CENTER);
        mainPanel.add(playerDeckPanel, BorderLayout.SOUTH);
        getContentPane().removeAll();
        getContentPane().add(mainPanel);
        getContentPane().revalidate();
    }

    void playerClickedCard(int cardIndexOnPlayerDeck) {

        switch (cardIndexOnPlayerDeck) {
            case BLANK_CARD_INDEX:
                game.getCurrentPlayer().getdeck().add(game.drawCard());
                game.nextPlayer(false, 0);
                redraw();
                break;

            case DECK_CARD_INDEX:
                //Player can't click on the card from the main deck
                break;

            default:
                boolean hasBeenPlaced = game.playCard(game.getCurrentPlayer().getdeck().get(cardIndexOnPlayerDeck), -1);
                if (hasBeenPlaced) {
                    redraw();
                }
                break;
        }
    }
}
