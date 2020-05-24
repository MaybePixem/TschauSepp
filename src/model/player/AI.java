package model.player;

import model.card.Card;

import java.util.ArrayList;

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
}
