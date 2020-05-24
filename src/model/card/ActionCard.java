package model.card;

/**
 * Model Class for an Card that performs an Action when placed. For example 7.
 *
 * @author Tim Bucher
 * @version 1.0
 * @since 23.05.2020
 */
public class ActionCard extends Card {

    /**
     * Constructor
     *
     * @param c The Color of the Card
     * @param a The Action reference for the card
     */
    public ActionCard(CARD_COLOR c, CARD_VALUE a) {
        super(c, a);
    }

}
