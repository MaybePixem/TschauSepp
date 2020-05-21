package model;

import Controller.GameController;

import java.util.ArrayList;
import java.util.Collections;

public class Game {

    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Card> currentDeck = new ArrayList<>();
    private ArrayList<Card> sideDeck;
    private int currentPlayer = 0;

    public Game(int numberOfPlayers, int numberOfAI, int numberOfStartingCards) {
        sideDeck = createDeck();

        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = new Player(createDeckForPlayer(numberOfStartingCards));
            players.add(player);
        }
        for (int i = 0; i < numberOfAI; i++) {
            Player ai = new Player(createDeckForPlayer(numberOfStartingCards));
            players.add(ai);
        }

    }

    private ArrayList<Card> createDeckForPlayer(int numberOfStartingCards) {
        ArrayList<Card> deck = new ArrayList<>();
        for (int j = 0; j < numberOfStartingCards; j++) {
            deck.add(sideDeck.get(0));
            sideDeck.remove(0);
        }
        return deck;
    }

    private ArrayList<Card> createDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        for (int i = 0; i < GameController.COLORS.length; i++) {
            for (int j = 0; j < GameController.NUMBERCARDS.length; j++) {
                deck.add(new NumberCard(i, GameController.NUMBERCARDS[j]));
            }
            for (int j = 0; j < GameController.ACTIONCARDS.length; j++) {
                deck.add(new ActionCard(i, GameController.ACTIONCARDS[j]));
            }
        }
        Collections.shuffle(deck);
        System.out.println(deck.toString());
        return deck;
    }

    public Card drawCard() {
        return new Card(2);
    }

    public void nextPlayer(boolean skip, int pickup) {

    }

    public Player getWinningPlayer() {
        return new Player(new ArrayList<Card>());
    }

    public boolean won() {
        return true;
    }

    public boolean playCard(Card c) {

        return true;
    }

    public void doCardAction(Card c) {

    }

    public static boolean checkValid(Card c) {
        return true;
    }

    public void callTschau() {

    }

    public void callSepp() {

    }
}
