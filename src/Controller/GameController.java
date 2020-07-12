package Controller;

import View.GameView;
import model.Game;
import model.card.CARD_COLOR;
import model.card.CARD_VALUE;
import model.card.Card;
import model.player.AI;
import model.player.Player;

import java.io.IOException;
import java.util.Collections;

/**
 * Controls  the Game. Contains all the Game Logic.
 *
 * @author Tim Bucher
 */
public class GameController {

    private Game game;
    private GameView gameView;

    public GameController(int numPlayers, int numAIPlayers, int numStartingCards) {
        game = new Game(numPlayers, numAIPlayers, numStartingCards);
    }

    public GameController(Game game) {
        this.game = game;
    }

    public void startGame() throws IOException {
        gameView = new GameView(this);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Returns the first card from the side deck
     *
     * @return first card from the side deck
     */
    public Card drawCard() {
        if (game.getSideDeck().size() <= 1) {
            Card tempCard = game.getCurrentDeck().remove(game.getCurrentDeck().size() - 1);
            game.getSideDeck().addAll(game.getCurrentDeck());
            game.getCurrentDeck().clear();
            game.getCurrentDeck().add(tempCard);
            Collections.shuffle(game.getSideDeck());
        }
        return game.getSideDeck().remove(0);
    }

    /**
     * Sets currentPlayer to the next Player and performs action when a action card was placed.
     *
     * @param skip   should next player be skipped
     * @param pickup how many cards has the next player to pickup
     */
    public void nextPlayer(boolean skip, int pickup) {
        int totalPlayers = game.getPlayers().size();
        if (skip) {
            if (game.getCurrentPlayerIndex() + 2 >= totalPlayers) {
                if (game.getCurrentPlayerIndex() + 1 >= totalPlayers) {
                    game.setCurrentPlayerIndex(1);
                } else {
                    game.setCurrentPlayerIndex(0);
                }
            } else {
                game.setCurrentPlayerIndex(game.getCurrentPlayerIndex() + 2);
            }
        } else {
            if (game.getCurrentPlayerIndex() + 1 == totalPlayers) {
                game.setCurrentPlayerIndex(0);
            } else {
                game.setCurrentPlayerIndex(game.getCurrentPlayerIndex() + 1);
            }
        }
        if (game.getCurrentPlayer() instanceof AI) {
            ((AI) game.getCurrentPlayer()).makeTurn(this);
        }
    }

    /**
     * Checks if someone has won the game.
     *
     * @return Player if someone has won and null if no one has won.
     */
    public Player getWinningPlayer() {
        for (Player p :
                game.getPlayers()) {
            if (p.getDeck().size() == 0)
                return p;
        }
        return null;
    }

    /**
     * Checks if card can be placed and places it when it can.
     *
     * @param c          The card that should be placed
     * @param bauerColor The Color that the player has chosen if the placed card was a color-choosing-action-card
     * @return if the card has been placed
     */
    public boolean playCard(Card c, CARD_COLOR bauerColor) {
        if (checkValid(c)) {
            if (game.getCurrentPlayer().getDecksize() == 2) {
                if (!game.getCurrentPlayer().getCalledTschau()) {
                    game.getCurrentPlayer().addCard(drawCard());
                    game.getCurrentPlayer().addCard(drawCard());
                    nextPlayer(false, 0);
                    return true;
                }
            } else if (game.getCurrentPlayer().getDecksize() == 1) {
                if (!game.getCurrentPlayer().getCalledSepp()) {
                    for (int i = 0; i < 4; i++) {
                        game.getCurrentPlayer().addCard(drawCard());
                    }
                    nextPlayer(false, 0);
                    return true;
                }
            }

            game.getCurrentDeck().add(c);
            game.getPlayers().get(game.getCurrentPlayerIndex()).removeCard(c);

            if (!c.getValue().isActionCard()) {
                nextPlayer(false, 0);
            } else {
                if (c.getValue() == CARD_VALUE.SEVEN) {
                    nextPlayer(false, 2);
                } else if (c.getValue() == CARD_VALUE.EIGHT) {
                    nextPlayer(false, 0);
                } else if (c.getValue() == CARD_VALUE.JACK) {
                    game.setBauerColor(bauerColor);
                    nextPlayer(false, 0);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Moves a Player to the finishedPlayers List.
     *
     * @param p The Player to be moved
     */
    public void setPlayerToFinished(Player p) {
        game.getFinishedPlayers().add(p);
        game.getPlayers().remove(p);
        if (game.getCurrentPlayerIndex() >= game.getPlayers().size())
            game.setCurrentPlayerIndex(game.getPlayers().size() - 1);
    }

    /**
     * Checks if the given card can be placed on the deck.
     *
     * @param c The Card to be checked.
     * @return if it is valid.
     */
    public boolean checkValid(Card c) {
        if (c.getValue().isActionCard() && c.getValue() == CARD_VALUE.JACK) {
            return true;
        } else {
            if (game.getBauerColor() == null) {
                return c.getColor() == game.getCurrentDeck().get(game.getCurrentDeck().size() - 1).getColor()
                        || c.getValue() == game.getCurrentDeck().get(game.getCurrentDeck().size() - 1).getValue();
            } else {
                return c.getColor() == game.getBauerColor()
                        || c.getValue() == game.getCurrentDeck().get(game.getCurrentDeck().size() - 1).getValue();
            }
        }
    }

    /**
     * Sets the calledTschau to true for the current player if he has 2 cards.
     *
     * @return has the flag been set
     */
    public boolean callTschau() {
        System.out.println(game.getCurrentPlayer().getDeck().size());
        if (game.getCurrentPlayer().getDeck().size() == 2) {
            game.getPlayers().get(game.getCurrentPlayerIndex()).setCalledTschau(true);
            return true;
        }
        return false;
    }

    /**
     * Sets the calledSepp to true for the current player if he has 1 card.
     *
     * @return has the flag been set
     */
    public boolean callSepp() {
        if (game.getCurrentPlayer().getDeck().size() == 1) {
            game.getPlayers().get(game.getCurrentPlayerIndex()).setCalledSepp(true);
            return true;
        }
        return false;
    }

    /**
     * Getter for the Game View
     *
     * @return Game View
     */
    public GameView getGameView() {
        return gameView;
    }
}
