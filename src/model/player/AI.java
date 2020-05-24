package model.player;

import model.card.Card;

import java.util.ArrayList;

/**
 * Model Class for the AI.
 *
 * @author Tim Bucher
 * @version 1.0
 * @since 23.05.2020
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
