package model.card;

/**
 * Model Class for a Card.
 *
 * @author Tim Bucher
 */
public class Card {
    private CARD_COLOR color;
    private CARD_VALUE value;

    /**
     * Constructor
     *
     * @param c Color of the card
     * @param v Value of the card
     */
    public Card(CARD_COLOR c, CARD_VALUE v) {
        color = c;
        value = v;
    }

    /**
     * Getter for the color
     *
     * @return color
     */
    public CARD_COLOR getColor() {
        return color;
    }

    /**
     * Getter for the Value
     *
     * @return value
     */
    public CARD_VALUE getValue() {
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

    /**
     * Getter for the points that card gives
     *
     * @return points
     */
    public int getPoints() {
        return value.getPoints();
    }
}
