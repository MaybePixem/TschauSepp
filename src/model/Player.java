package model;

import java.util.ArrayList;

public class Player {

    private ArrayList<Card> deck;
    private boolean calledtschau = false;
    private boolean calledsepp = false;

    public Player(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public void addcard(Card c) {
        deck.add(c);
    }

    public void removecard(Card c) {
        deck.remove(c);
    }

    public boolean hascalledsepp() {
        return calledsepp;
    }

    public void setcalledsepp(boolean calledsepp) {
        this.calledsepp = calledsepp;
    }

    public boolean hascalledtschau() {
        return calledtschau;
    }

    public void setcalledtschau(boolean calledtschau) {
        this.calledtschau = calledtschau;
    }

    public int getdecksize() {
        return deck.size();
    }

    public ArrayList<Card> getdeck() {
        return deck;
    }
}
