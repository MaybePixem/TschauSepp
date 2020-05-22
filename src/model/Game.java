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
    private int bauerColor = -1;

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

    public boolean playCard(Card c, int bauerColor) {
        if (checkValid(c)) {
            currentDeck.add(c);
            players.get(currentPlayer).getdeck().remove(c);
            if (c instanceof NumberCard) {
                nextPlayer(false, 0);
            } else {
                if (((ActionCard) c).getAction().equals("Sieben")) {
                    nextPlayer(false, 2);
                } else if (((ActionCard) c).getAction().equals("Acht")) {
                    nextPlayer(true, 0);
                } else if (((ActionCard) c).getAction().equals("Bauer")) {
                    this.bauerColor = bauerColor;
                    nextPlayer(false, 0);
                }
            }
            return true;
        }
        return false;
    }

    public boolean checkValid(Card c) {

        if (c instanceof NumberCard || ((ActionCard) c).getAction().equals("Sieben") || ((ActionCard) c).getAction().equals("Acht")) {
            if (bauerColor != -1) {
                return c.getColor() == currentDeck.get(currentDeck.size() - 1).getColor()
                        || ((NumberCard) c).getValue().equals(((NumberCard) currentDeck.get(currentDeck.size()-1)).getValue());
            } else {
                return c.getColor() == bauerColor
                        || ((NumberCard) c).getValue().equals(((NumberCard) currentDeck.get(currentDeck.size()-1)).getValue());
            }
        } else {
            return true;
        }
    }

    public void callTschau() {
        players.get(currentPlayer).setcalledtschau(true);
    }

    public void callSepp() {
        players.get(currentPlayer).setcalledsepp(true);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Card> getCurrentDeck() {
        return currentDeck;
    }

    public ArrayList<Card> getSideDeck() {
        return sideDeck;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public int getBauerColor() {
        return bauerColor;
    }
}
