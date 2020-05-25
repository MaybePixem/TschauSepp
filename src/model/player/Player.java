package model.player;

import model.card.Card;

import java.util.ArrayList;

/**
 * Model Class for the Player
 *
 * @author Tim Bucher
 */
public class Player {

    private ArrayList<Card> deck;
    private boolean calledTschau = false;
    private boolean calledSepp = false;

    /**
     * Constructor
     *
     * @param deck Deck of the Player
     */
    public Player(ArrayList<Card> deck) {
        this.deck = deck;
    }

    /**
     * Adds a card to the deck
     *
     * @param c Card to be added
     */
    public void addCard(Card c) {
        deck.add(c);
    }

    /**
     * Removes a card from the deck
     *
     * @param c Card to be removed
     */
    public void removeCard(Card c) {
        deck.remove(c);
    }

    /**
     * Getter for the calledSepp flag
     *
     * @return calledSepp flag
     */
    public boolean getCalledSepp() {
        return calledSepp;
    }

    /**
     * Setter for the calledSepp flag
     *
     * @param calledSepp flag
     */
    public void setCalledSepp(boolean calledSepp) {
        this.calledSepp = calledSepp;
    }

    /**
     * Getter for the calledTschau flag
     *
     * @return calledTschau flag
     */
    public boolean getCalledTschau() {
        return calledTschau;
    }

    /**
     * Setter for the calledTschau flag
     *
     * @param calledTschau flag
     */
    public void setCalledTschau(boolean calledTschau) {
        this.calledTschau = calledTschau;
    }

    /**
     * Getter for the deck size of the player
     *
     * @return deck size
     */
    public int getDecksize() {
        return deck.size();
    }

    /**
     * Getter for the deck
     *
     * @return deck
     */
    public ArrayList<Card> getDeck() {
        return deck;
    }
}
