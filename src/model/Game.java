package model;

import model.card.*;
import model.player.AI;
import model.player.Player;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Model Class for the Game.
 * Saves all information for the Game and handles all the moves that the Player and events that take place in the game.
 *
 * @author Tim Bucher
 */
public class Game {

    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> finishedPlayers = new ArrayList<>();
    private ArrayList<Card> currentDeck = new ArrayList<>();
    private ArrayList<Card> sideDeck;
    private int currentPlayer = 0;
    private CARD_COLOR bauerColor = null;

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
        int totalPlayers = numberOfPlayers + numberOfAI;

        sideDeck = createDeck();

        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = new Player(createDeckForPlayer(numberOfStartingCards));
            players.add(player);
        }
        for (int i = 0; i < numberOfAI; i++) {
            Player ai = new AI(createDeckForPlayer(numberOfStartingCards));
            players.add(ai);
        }

        currentDeck.add(drawCard());
        while (currentDeck.get(0).getValue().isActionCard()) {
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
        for (int numberOfFullDecks = 0; numberOfFullDecks < 2; numberOfFullDecks++) {
            for (int i = 0; i < CARD_COLOR.values().length; i++) {
                for (int j = 0; j < CARD_VALUE.values().length; j++) {
                    if (CARD_VALUE.values()[j].isActionCard()) {
                        deck.add(new Card(CARD_COLOR.values()[i], CARD_VALUE.values()[j]));
                    } else {
                        deck.add(new Card(CARD_COLOR.values()[i], CARD_VALUE.values()[j]));
                    }
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
        if (sideDeck.size() <= 1) {
            Card tempCard = currentDeck.remove(currentDeck.size() - 1);
            sideDeck.addAll(currentDeck);
            currentDeck.clear();
            currentDeck.add(tempCard);
            Collections.shuffle(sideDeck);
        }
        return sideDeck.remove(0);
    }

    /**
     * Sets currentPlayer to the next Player and performs action when a action card was placed.
     *
     * @param skip   should next player be skipped
     * @param pickup how many cards has the next player to pickup
     */
    public void nextPlayer(boolean skip, int pickup) {
        int totalPlayers = players.size();
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
            if (getCurrentPlayer().getdeck().size() == 2) {
                if (!getCurrentPlayer().hasCalledTschau()) {
                    getCurrentPlayer().getdeck().add(drawCard());
                    getCurrentPlayer().getdeck().add(drawCard());
                    nextPlayer(false, 0);

                    return true;
                }
            } else if (getCurrentPlayer().getdeck().size() == 1) {
                if (!getCurrentPlayer().hasCalledSepp()) {
                    for (int i = 0; i < 4; i++) {
                        getCurrentPlayer().getdeck().add(drawCard());
                    }
                    nextPlayer(false, 0);
                    return true;
                }
            }

            currentDeck.add(c);
            players.get(currentPlayer).getdeck().remove(c);

            if (!c.getValue().isActionCard()) {
                nextPlayer(false, 0);
            } else {
                if (c.getValue() == CARD_VALUE.SEVEN) {
                    nextPlayer(false, 2);
                } else if (c.getValue() == CARD_VALUE.EIGHT) {
                    nextPlayer(true, 0);
                } else if (c.getValue() == CARD_VALUE.JACK) {
                    this.bauerColor = bauerColor;
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
        finishedPlayers.add(p);
        players.remove(p);
        if (currentPlayer >= players.size())
            currentPlayer = players.size() - 1;
    }

    /**
     * Checks if the given card can be placed on the deck.
     *
     * @param c The Card to be checked.
     * @return if it is valid.
     */
    private boolean checkValid(Card c) {
        if (c.getValue().isActionCard() && c.getValue() == CARD_VALUE.JACK) {
            return true;
        } else {
            if (bauerColor == null) {
                return c.getColor() == currentDeck.get(currentDeck.size() - 1).getColor()
                        || c.getValue() == currentDeck.get(currentDeck.size() - 1).getValue();
            } else {
                return c.getColor() == bauerColor
                        || c.getValue() == currentDeck.get(currentDeck.size() - 1).getValue();
            }
        }
    }

    /**
     * Sets the calledTschau to true for the current player if he has 2 cards.
     *
     * @return has the flag been set
     */
    public boolean callTschau() {
        if (getCurrentPlayer().getdeck().size() == 2) {
            players.get(currentPlayer).setcalledTschau(true);
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
        if (getCurrentPlayer().getdeck().size() == 1) {
            players.get(currentPlayer).setcalledSepp(true);
            return true;
        }
        return false;
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
    public CARD_COLOR getBauerColor() {
        return bauerColor;
    }

    /**
     * Getter for the finished Players
     *
     * @return finishedPlayers
     */
    public ArrayList<Player> getFinishedPlayers() {
        return finishedPlayers;
    }
}
