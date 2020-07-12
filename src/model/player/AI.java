package model.player;

import Controller.GameController;
import model.card.Card;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Model Class for the AI.
 *
 * @author Tim Bucher
 */
public class AI extends Player {

    /**
     * Constructor
     *
     * @param deck Deck of the AI
     */
    public AI(ArrayList<Card> deck) {
        super(deck);
    }

    /**
     * Hardcoded "Brain" of the AI. Decides what the AI does.
     *
     * @param gameController GameController
     */
    public void makeTurn(GameController gameController) {
        for (Card c : getDeck()) {
            if (gameController.checkValid(c)) {
                if (getDecksize() == 2) {
                    setCalledTschau(true);
                } else if (getDecksize() == 1) {
                    setCalledSepp(true);
                }
                gameController.playCard(c, null);
                System.out.println(c.getValue());
                return;
            }
        }
        getDeck().add(gameController.drawCard());
        Player winner = gameController.getWinningPlayer();
        if (Objects.isNull(winner)) {
            gameController.nextPlayer(false, 0);
        } else {
            gameController.getGameView().setGameOver(winner);
        }

    }
}
