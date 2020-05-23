package model;

/**
 * Model Class for an Card that doesn't perform an action when placed. For example 1.
 *
 * @author Tim Bucher
 * @version 1.0
 * @since 23.05.2020
 */
public class NumberCard extends Card {

    private String value;

    /**
     * Constructor
     *
     * @param c The color of the card
     * @param v The value of the card
     */
    public NumberCard(int c, String v) {
        super(c);
        value = v;
    }

    /**
     * Getter for the value
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * toString for the card
     *
     * @return color and value of the Card
     */
    public String toString() {
        return getColor() + " " + value;
    }
}
