package View;

import model.*;
import model.card.*;
import model.player.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

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
    private JButton callTschauBtn;
    private JButton callSeppBtn;

    public static void main(String[] args) throws IOException {
        Game game = new Game(1, 3, 6);
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

        callTschauBtn = new JButton("Tschau");
        callTschauBtn = new JButton("Tschau");
        callSeppBtn = new JButton("Sepp");
        callTschauBtn.addActionListener(actionEvent -> {
            boolean hasFlagBeenSet = game.callTschau();
            if (hasFlagBeenSet) {
                callTschauBtn.setEnabled(false);
            }
        });
        callSeppBtn.addActionListener(actionEvent -> {
            boolean hasFlagBeenSet = game.callSepp();
            if (hasFlagBeenSet) {
                callSeppBtn.setEnabled(false);
            }
        });

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
        for (int i = 0; i < CARD_COLOR.values().length; i++) {
            for (int j = 0; j < CARD_VALUE.values().length; j++) {
                cardImagesArr.put(CARD_COLOR.values()[i].toImageString() + CARD_VALUE.values()[j].toImageString(),
                        ImageIO.read(getClass().getResource("/resources/images/" + CARD_VALUE.values()[j].toImageString() + CARD_COLOR.values()[i].toImageString() + ".png"))
                );
            }
        }

        cardImagesArr.put("blankCard", ImageIO.read(
                getClass().
                        getResource("/resources/images/blankCard.png")))
        ;
    }

    private void redraw() {
        mainPanel = new JPanel(new BorderLayout());
        playerDeckPanel = new JPanel();
        playfieldPlanel = new JPanel();
        otherPlayersPanel = new JPanel();

        playfieldPlanel.add(new CardImage(cardImagesArr.get("blankCard"), Math.min(getWidth() / 4, MAX_CARD_SIZE), BLANK_CARD_INDEX, this));
        Card currentCardOnDeck = game.getCurrentDeck().get(game.getCurrentDeck().size() - 1);
        if (currentCardOnDeck instanceof NumberCard) {
            playfieldPlanel.add(
                    new CardImage(
                            cardImagesArr.get(currentCardOnDeck.getColor().toImageString()
                                    + currentCardOnDeck.getValue().toImageString())
                            , Math.min(getWidth() / 4, MAX_CARD_SIZE)
                            , DECK_CARD_INDEX
                            , this
                    )
            );
        } else {
            playfieldPlanel.add(
                    new CardImage(
                            cardImagesArr.get(currentCardOnDeck.getColor().toImageString()
                                    + currentCardOnDeck.getValue().toImageString())
                            , Math.min(getWidth() / 4, MAX_CARD_SIZE)
                            , DECK_CARD_INDEX
                            , this
                    )
            );
        }

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(callTschauBtn, BorderLayout.NORTH);
        JPanel marginPanel = new JPanel();
        marginPanel.setPreferredSize(new Dimension(1, Math.min(getWidth() / 15, MAX_CARD_SIZE / 2)));
        buttonPanel.add(marginPanel, BorderLayout.CENTER);
        buttonPanel.add(callSeppBtn, BorderLayout.SOUTH);

        playfieldPlanel.add(buttonPanel);

        int cardWidth = Math.min(getWidth() / (game.getCurrentPlayer().getdecksize() + 2), MAX_CARD_SIZE);

        for (int i = 0; i < game.getCurrentPlayer().getdeck().size(); i++) {
            Card c = game.getCurrentPlayer().getdeck().get(i);
            CardImage img;
            if (c instanceof ActionCard) {
                img = new CardImage(cardImagesArr.get(c.getColor().toImageString() + c.getValue().toImageString()), cardWidth, i, this);
            } else {
                img = new CardImage(cardImagesArr.get(c.getColor().toImageString() + c.getValue().toImageString()), cardWidth, i, this);
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

        callSeppBtn.setEnabled(true);
        callTschauBtn.setEnabled(true);

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
                boolean hasBeenPlaced = game.playCard(game.getCurrentPlayer().getdeck().get(cardIndexOnPlayerDeck), null);
                if (hasBeenPlaced) {
                    Player winner = game.getWinningPlayer();
                    if (Objects.isNull(winner))
                        redraw();
                    else {
                        System.out.println("bruh");
                        GameOverView gameOverView = new GameOverView(this, winner, game.getPlayers(), game.getFinishedPlayers());
                        if (gameOverView.isEndGame()) {
                            dispose();
                        } else {
                            game.setPlayerToFinished(winner);
                            redraw();
                        }
                    }
                }
                break;
        }
    }
}
