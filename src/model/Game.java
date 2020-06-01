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

        sideDeck = createDeck();

        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = new Player(createDeckForPlayer(numberOfStartingCards));
            players.add(player);
        }
        for (int i = 0; i < numberOfAI; i++) {
            Player ai = new AI(createDeckForPlayer(numberOfStartingCards));
            players.add(ai);
        }

        currentDeck.add(sideDeck.remove(0));
        while (currentDeck.get(0).getValue().isActionCard()) {
            sideDeck.add(currentDeck.get(0));
            currentDeck.remove(0);
            currentDeck.add(sideDeck.remove(0));
        }

    }

    /**
     * Constructor when theres only 2 Players
     *
     * @param numberOfStartingCards Number of Cards that the Players start with
     */
    public Game(int numberOfStartingCards) {
        this(2, 0, numberOfStartingCards);
    }

    /**
     * Constructor for Online Play
     *
     * @param players         players
     * @param finishedPlayers finishedPlayers
     * @param currentDeck     currentDeck
     * @param sideDeck        sideDeck
     * @param currentPlayer   currentPlayer
     * @param bauerColor      bauerColor
     */
    public Game(ArrayList<Player> players, ArrayList<Player> finishedPlayers, ArrayList<Card> currentDeck, ArrayList<Card> sideDeck, int currentPlayer, CARD_COLOR bauerColor) {
        System.out.println(currentPlayer);
        this.players = players;
        this.finishedPlayers = finishedPlayers;
        this.currentDeck = currentDeck;
        this.sideDeck = sideDeck;
        this.currentPlayer = currentPlayer;
        this.bauerColor = bauerColor;
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
     * Getter for the index of the current player
     *
     * @return index of the current Player
     */
    public int getCurrentPlayerIndex() {
        return currentPlayer;
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

    /**
     * Setter for the currentPlayer
     *
     * @param currentPlayer index of the currentPlayer in the players array
     */
    public void setCurrentPlayerIndex(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Setter für die Farbe die der Spieler gewählt hat als er die Bauer Karte gelegt hat
     *
     * @param bauerColor Farbe die gewählt wurde
     */
    public void setBauerColor(CARD_COLOR bauerColor) {
        this.bauerColor = bauerColor;
    }
}
