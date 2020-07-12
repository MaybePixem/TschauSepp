package View;

import Controller.GameController;
import Controller.OnlinePlayController;
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
    private GameController gameController;
    private HashMap<String, BufferedImage> cardImagesArr = new HashMap<>();

    private boolean hideCards = false;
    private OnlinePlayController onlinePlayController = null;

    private JPanel mainPanel;
    private JPanel playerDeckPanel;
    private JPanel playfieldPlanel;
    private JPanel otherPlayersPanel;
    private JButton callTschauBtn;
    private JButton callSeppBtn;

    public static void main(String[] args) throws IOException {
        GameController c = new GameController(1, 3, 6);
        new GameView(c);
    }

    public GameView(GameController gameController) throws HeadlessException, IOException {
        this.game = gameController.getGame();
        this.gameController = gameController;
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

    public GameView(OnlinePlayController onlinePlayController, GameController gameController) throws HeadlessException, IOException {
        this(gameController);
        this.onlinePlayController = onlinePlayController;
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

        otherPlayersPanel.setLayout(new BoxLayout(otherPlayersPanel, BoxLayout.PAGE_AXIS));
        otherPlayersPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));

        callTschauBtn = new JButton("Tschau");
        callSeppBtn = new JButton("Sepp");
        callTschauBtn.addActionListener(actionEvent -> {
            boolean hasFlagBeenSet = gameController.callTschau();
            if (hasFlagBeenSet) {
                callTschauBtn.setEnabled(false);
            }
        });
        callSeppBtn.addActionListener(actionEvent -> {
            boolean hasFlagBeenSet = gameController.callSepp();
            if (hasFlagBeenSet) {
                callSeppBtn.setEnabled(false);
            }
        });

        playfieldPlanel.add(new CardImage(cardImagesArr.get("blankCard"), Math.min(getWidth() / 4, MAX_CARD_SIZE), BLANK_CARD_INDEX, this));
        Card currentCardOnDeck = game.getCurrentDeck().get(game.getCurrentDeck().size() - 1);
        playfieldPlanel.add(
                new CardImage(
                        cardImagesArr.get(currentCardOnDeck.getColor().toImageString()
                                + currentCardOnDeck.getValue().toImageString())
                        , Math.min(getWidth() / 4, MAX_CARD_SIZE)
                        , DECK_CARD_INDEX
                        , this
                )
        );


        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(callTschauBtn, BorderLayout.NORTH);
        JPanel marginPanel = new JPanel();
        marginPanel.setPreferredSize(new Dimension(1, Math.min(getWidth() / 15, MAX_CARD_SIZE / 2)));
        buttonPanel.add(marginPanel, BorderLayout.CENTER);
        buttonPanel.add(callSeppBtn, BorderLayout.SOUTH);

        playfieldPlanel.add(buttonPanel);

        int cardWidth = Math.min(getWidth() / (game.getCurrentPlayer().getDecksize() + 2), MAX_CARD_SIZE);

        for (int i = 0; i < game.getCurrentPlayer().getDeck().size(); i++) {
            Card c = game.getCurrentPlayer().getDeck().get(i);
            CardImage img;
            img = new CardImage(cardImagesArr.get(c.getColor().toImageString() + c.getValue().toImageString()), cardWidth, i, this);
            playerDeckPanel.add(img);
        }

        for (Player p :
                game.getPlayers()) {
            String text =
                    "Spieler " +
                            (game.getPlayers().indexOf(p) + 1) +
                            " hat " +
                            p.getDecksize() +
                            (p.getDecksize() == 1 ? " Karte" : " Karten");

            JLabel jLabel = new JLabel(text);
            
            otherPlayersPanel.add(jLabel);
        }

        mainPanel.add(otherPlayersPanel, BorderLayout.WEST);
        mainPanel.add(playfieldPlanel, BorderLayout.CENTER);

        if (!hideCards) {
            mainPanel.add(playerDeckPanel, BorderLayout.SOUTH);
        } else {
            callTschauBtn.setEnabled(false);
            callSeppBtn.setEnabled(false);
        }
        getContentPane().removeAll();
        getContentPane().add(mainPanel);
        getContentPane().revalidate();
    }

    public void setHideCards(boolean hideCards) {
        this.hideCards = hideCards;
        redraw();
    }

    void playerClickedCard(int cardIndexOnPlayerDeck) {

        callSeppBtn.setEnabled(true);
        callTschauBtn.setEnabled(true);

        switch (cardIndexOnPlayerDeck) {
            case BLANK_CARD_INDEX:
                if (!hideCards) {
                    game.getCurrentPlayer().addCard(gameController.drawCard());
                    gameController.nextPlayer(false, 0);
                    if (onlinePlayController == null) {
                        redraw();
                    } else {
                        onlinePlayController.endTurn();
                    }
                }
                break;

            case DECK_CARD_INDEX:
                //Player can't click on the card from the main deck
                break;

            default:
                boolean hasBeenPlaced = gameController.playCard(game.getCurrentPlayer().getDeck().get(cardIndexOnPlayerDeck), null);
                if (hasBeenPlaced) {
                    Player winner = gameController.getWinningPlayer();
                    if (onlinePlayController == null) {
                        if (Objects.isNull(winner)) {
                            redraw();
                        } else {
                            GameOverView gameOverView = new GameOverView(this, winner, game.getPlayers(), game.getFinishedPlayers());
                            if (gameOverView.isEndGame()) {
                                dispose();
                            } else {
                                gameController.setPlayerToFinished(winner);
                                redraw();
                            }
                        }
                    } else {
                        if (Objects.isNull(winner)) {
                            onlinePlayController.endTurn();
                        } else {
                            onlinePlayController.endGame();
                        }
                    }
                }
                break;
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
