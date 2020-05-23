package model;

/**
 * Model Class for a Card.
 *
 * @author Tim Bucher
 * @version 1.0
 * @since 23.05.2020
 */
public abstract class Card {
    private int color;
    private String value;

    /**
     * Constructor
     *
     * @param c Color of the card
     * @param v Value of the card
     */
    Card(int c, String v) {
        color = c;
        value = v;
    }

    /**
     * Getter for the color
     *
     * @return color
     */
    public int getColor() {
        return color;
    }

    /**
     * Getter for the Value
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * toString for the Card
     *
     * @return Color and Value of the Card
     */
    public String toString() {
        return getColor() + " " + value;
    }
}
