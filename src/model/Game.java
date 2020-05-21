package model;

import Controller.GameController;

import java.util.ArrayList;
import java.util.Collections;

public class Game {

    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Card> currentDeck = new ArrayList<>();
    private ArrayList<Card> sideDeck;
    private int currentPlayer = 0;
    private int totalPlayers;


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
        return deck;
    }

    public Card drawCard() {
        return sideDeck.remove(0);
    }

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

    public Player getWinningPlayer() {
        for (Player p :
                players) {
            if (p.getdeck().size() == 0)
                return p;
            break;
        }
        return null;
    }

    public boolean playCard(Card c) {
        if (checkValid(c)) {
            if(c instanceof  ActionCard){
                doCardAction(c);
            }
            currentDeck.add(c);
            players.get(currentPlayer).getdeck().remove(c);
            return true;
        }
        return false;
    }

    public void doCardAction(Card c) {

    }

    public boolean checkValid(Card c) {
        if (c instanceof NumberCard || ((ActionCard) c).getAction().equals("Sieben") || ((ActionCard) c).getAction().equals("Acht")) {
            return c.getColor() == currentDeck.get(currentDeck.size()).getColor()
                    || ((NumberCard) c).getValue().equals(((NumberCard) currentDeck.get(currentDeck.size())).getValue());

        } else {
            return true;
        }
    }

    public void callTschau() {

    }

    public void callSepp() {

    }
}
