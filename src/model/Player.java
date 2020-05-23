package model;

import java.util.ArrayList;

/**
 * Model Class for the Player
 *
 * @author Tim Bucher
 * @version 1.0
 * @since 23.05.2020
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
    public void addcard(Card c) {
        deck.add(c);
    }

    /**
     * Removes a card from the deck
     *
     * @param c Card to be removed
     */
    public void removecard(Card c) {
        deck.remove(c);
    }

    /**
     * Getter for the calledSepp flag
     *
     * @return calledSepp flag
     */
    public boolean hasCalledSepp() {
        return calledSepp;
    }

    /**
     * Setter for the calledSepp flag
     *
     * @param calledSepp flag
     */
    public void setcalledSepp(boolean calledSepp) {
        this.calledSepp = calledSepp;
    }

    /**
     * Getter for the calledTschau flag
     *
     * @return calledTschau flag
     */
    public boolean hasCalledTschau() {
        return calledTschau;
    }

    /**
     * Setter for the calledTschau flag
     *
     * @param calledTschau flag
     */
    public void setcalledTschau(boolean calledTschau) {
        this.calledTschau = calledTschau;
    }

    /**
     * Getter for the deck size of the player
     *
     * @return deck size
     */
    public int getdecksize() {
        return deck.size();
    }

    /**
     * Getter for the deck
     *
     * @return deck
     */
    public ArrayList<Card> getdeck() {
        return deck;
    }
}
