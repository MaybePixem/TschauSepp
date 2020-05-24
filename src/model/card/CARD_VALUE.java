package model.card;

/**
 * Enum Class for the Card Value also contains the String to get the image, the points of the card and if its a action card.
 *
 * @author Tim Bucher
 */
public enum CARD_VALUE {
    SEVEN("7", 7, true),
    EIGHT("8", 8, true),
    JACK("J", 2, true),
    TWO("2", 2, false),
    THREE("3", 3, false),
    FOUR("4", 4, false),
    FIVE("5", 5, false),
    SIX("6", 6, false),
    NINE("9", 9, false),
    TEN("10", 10, false),
    ACE("A", 11, false),
    KING("K", 4, false),
    QUEEN("Q", 3, false);

    private String imageString;
    private int points;
    private boolean isActionCard;

    /**
     * Constructor
     *
     * @param imageString  The String for the image
     * @param points       The amount of points the card gives
     * @param isActionCard Flag if its an action card
     */
    CARD_VALUE(String imageString, int points, boolean isActionCard) {
        this.imageString = imageString;
        this.points = points;
        this.isActionCard = isActionCard;
    }

    /**
     * Getter for the Image String
     *
     * @return imageString
     */
    public String toImageString() {
        return imageString;
    }

    /**
     * Getter for the points
     *
     * @return points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Getter for the action card flag
     *
     * @return isActionCard
     */
    public boolean isActionCard() {
        return isActionCard;
    }
}
