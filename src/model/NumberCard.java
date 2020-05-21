package model;

public class NumberCard extends Card{

    private String value;

    public NumberCard(int c, String v) {
        super(c);
        value = v;
    }

    public String getValue() {
        return value;
    }


    public String toString() {
        return getColor() + " " + value;
    }
}
