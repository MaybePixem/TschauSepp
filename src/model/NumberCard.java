package model;

/**
 * Model Class for an Card that doesn't perform an action when placed. For example 1.
 *
 * @author Tim Bucher
 * @version 1.0
 * @since 23.05.2020
 */
public class NumberCard extends Card {

    /**
     * Constructor
     *
     * @param c The color of the card
     * @param v The value of the card
     */
    NumberCard(int c, String v) {
        super(c, v);
    }

}
