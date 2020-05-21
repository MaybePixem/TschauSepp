package model;

public class NumberCard extends Card{

    private int value;

    public NumberCard(int c, int v) {
        super(c);
        value = v;
    }

    public int getValue() {
        return value;
    }


    public String toString() {
        return getColor() + " " + value;
    }
}
