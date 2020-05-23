package model;

/**
 * Model Class for a Card. Only saves the Color.
 *
 * @author Tim Bucher
 * @version 1.0
 * @since 23.05.2020
 */
public abstract class Card {
    private int color;

    /**
     * Constructor
     *
     * @param c Color of the card
     */
    public Card(int c) {
        color = c;
    }

    /**
     * Getter for the color
     *
     * @return color
     */
    public int getColor() {
        return color;
    }
}
