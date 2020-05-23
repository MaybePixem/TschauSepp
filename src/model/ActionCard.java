package model;

/**
 * Model Class for an Card that performs an Action when placed. For example 7.
 *
 * @author Tim Bucher
 * @version 1.0
 * @since 23.05.2020
 */
public class ActionCard extends Card {

    private String action;

    /**
     * Constructor
     *
     * @param c The Color of the Card
     * @param a The Action reference for the card
     */
    public ActionCard(int c, String a) {
        super(c);
        action = a;
    }

    /**
     * Getter for the Action
     *
     * @return action
     */
    public String getAction() {
        return action;
    }

    /**
     * toString for the Card
     *
     * @return Color and Action of the Card
     */
    public String toString() {
        return getColor() + " " + action;
    }
}
