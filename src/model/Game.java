package model;

import java.util.ArrayList;

public class Game {

    private static final String[] COLORS = {"Eichel", "Rosen", "Schellen", "Schilten"};

    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Card> currentDeck = new ArrayList<>();
    private ArrayList<Card> sideDeck = new ArrayList<>();
    private int currentPlayer = 0;

    public Game(int numberOfPlayers, int numberOfAI, int numberOfStartingCards) {
        createPlayers(numberOfPlayers, numberOfAI, numberOfStartingCards);
        sideDeck = createDeck();
    }

    public void createPlayers(int numberOfPlayers, int numberOfAI, int numberOfStartingCards) {
        for (int i = 0; i < numberOfPlayers; i++) {
            Player p = new Player(new ArrayList<Card>());
            for (int j = 0; j < numberOfStartingCards; j++) {

            }
            players.add(p);
        }

        for (int i = 0; i < numberOfAI; i++) {
            Player ai = new AI(new ArrayList<Card>());
            for (int j = 0; j < numberOfStartingCards; j++) {

            }
            players.add(ai);
        }
    }

    private ArrayList<Card> createDeck() {
        return new ArrayList<>();
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
