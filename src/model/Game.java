package model;

import Controller.GameController;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Model Class for the Game.
 * Saves all information for the Game and handles all the moves that the Player and events that take place in the game.
 *
 * @author Tim Bucher
 * @version 1.0
 * @since 23.05.2020
 */
public class Game {

    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Card> currentDeck = new ArrayList<>();
    private ArrayList<Card> sideDeck;
    private int currentPlayer = 0;
    private int totalPlayers;
    private int bauerColor = -1;

    /**
     * Constructor
     * Creates the Deck and the Players.
     * Creates the Deck for the Players by using cards from the main deck.
     *
     * @param numberOfPlayers       Number of Players that play the game
     * @param numberOfAI            Number of AIs that play the game
     * @param numberOfStartingCards Number of Cards that the Players start with
     */
    public Game(int numberOfPlayers, int numberOfAI, int numberOfStartingCards) {
        totalPlayers = numberOfPlayers + numberOfAI;

        sideDeck = createDeck();

        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = new Player(createDeckForPlayer(numberOfStartingCards));
            players.add(player);
        }
        for (int i = 0; i < numberOfAI; i++) {
            Player ai = new Player(createDeckForPlayer(numberOfStartingCards));
            players.add(ai);
        }

        currentDeck.add(drawCard());
        while (!(currentDeck.get(0) instanceof NumberCard)) {
            sideDeck.add(currentDeck.get(0));
            currentDeck.remove(0);
            currentDeck.add(drawCard());
        }

    }

    /**
     * Creates the deck for an Player by using cards that are in the main deck.
     *
     * @param numberOfStartingCards Number of Cards in the deck
     * @return The created deck
     */
    private ArrayList<Card> createDeckForPlayer(int numberOfStartingCards) {
        ArrayList<Card> deck = new ArrayList<>();
        for (int j = 0; j < numberOfStartingCards; j++) {
            deck.add(sideDeck.get(0));
            sideDeck.remove(0);
        }
        return deck;
    }

    /**
     * Creates the main deck by reading the NUMBERCARDS, ACTIONCARDS and COLORS fields in the GameController.
     *
     * @return ArrayList with the Cards inside
     */
    private ArrayList<Card> createDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        for (int numberofFullDecks = 0; numberofFullDecks < 2; numberofFullDecks++) {
            for (int i = 0; i < GameController.COLORS.length; i++) {
                for (int j = 0; j < GameController.NUMBERCARDS.length; j++) {
                    deck.add(new NumberCard(i, GameController.NUMBERCARDS[j]));
                }
                for (int j = 0; j < GameController.ACTIONCARDS.length; j++) {
                    deck.add(new ActionCard(i, GameController.ACTIONCARDS[j]));
                }
            }
        }
        Collections.shuffle(deck);
        return deck;
    }

    /**
     * Returns the first card from the side deck
     *
     * @return first card from the side deck
     */
    public Card drawCard() {
        return sideDeck.remove(0);
    }

    /**
     * Sets currentPlayer to the next Player and performs action when a action card was placed.
     *
     * @param skip   should next player be skipped
     * @param pickup how many cards has the next player to pickup
     */
    public void nextPlayer(boolean skip, int pickup) {
        if (skip) {
            if (currentPlayer + 2 >= totalPlayers) {
                if (currentPlayer + 1 >= totalPlayers) {
                    currentPlayer = 1;
                } else {
                    currentPlayer = 0;
                }
            } else {
                currentPlayer += 2;
            }
        } else {
            if (currentPlayer + 1 == totalPlayers) {
                currentPlayer = 0;
            } else {
                currentPlayer++;
            }
        }
    }

    /**
     * Checks if someone has won the game.
     *
     * @return Player if someone has won and null if no one has won.
     */
    public Player getWinningPlayer() {
        for (Player p :
                players) {
            if (p.getdeck().size() == 0)
                return p;
            break;
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
    public boolean playCard(Card c, int bauerColor) {
        if (checkValid(c)) {
            currentDeck.add(c);
            players.get(currentPlayer).getdeck().remove(c);
            if (c instanceof NumberCard) {
                nextPlayer(false, 0);
            } else {
                if (c.getValue().equals(GameController.ACTIONCARDS[0])) {
                    nextPlayer(false, 2);
                } else if (c.getValue().equals(GameController.ACTIONCARDS[1])) {
                    nextPlayer(true, 0);
                } else if (c.getValue().equals(GameController.ACTIONCARDS[2])) {
                    this.bauerColor = bauerColor;
                    nextPlayer(false, 0);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if the given card can be placed on the deck.
     *
     * @param c The Card to be checked.
     * @return if it is valid.
     */
    private boolean checkValid(Card c) {
        if (!(c instanceof ActionCard && c.getValue().equals(GameController.ACTIONCARDS[2]))) {
            if (bauerColor == -1) {
                return c.getColor() == currentDeck.get(currentDeck.size() - 1).getColor()
                        || c.getValue().equals(currentDeck.get(currentDeck.size() - 1).getValue());
            } else {
                return c.getColor() == bauerColor
                        || c.getValue().equals(currentDeck.get(currentDeck.size() - 1).getValue());
            }
        } else {
            return true;
        }
    }

    /**
     * Sets the calledTschau to true for the current player.
     */
    public void callTschau() {
        players.get(currentPlayer).setcalledTschau(true);
    }

    /**
     * Sets the calledSepp to true for the current player.
     */
    public void callSepp() {
        players.get(currentPlayer).setcalledSepp(true);
    }

    /**
     * Getter for the players ArrayList
     *
     * @return players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Getter for the currentDeck ArrayList
     *
     * @return currentDeck
     */
    public ArrayList<Card> getCurrentDeck() {
        return currentDeck;
    }

    /**
     * Getter for the sideDeck ArrayList
     *
     * @return sideDeck
     */
    public ArrayList<Card> getSideDeck() {
        return sideDeck;
    }

    /**
     * Gets the current Player from the players ArrayList
     *
     * @return the current Player
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    /**
     * Getter for the chosen BauerColor
     *
     * @return bauerColor
     */
    public int getBauerColor() {
        return bauerColor;
    }
}
