package model.card;

/**
 * Enum Class for the Card Color also contains the String to get the image for the card.
 *
 * @author Tim Bucher
 */
public enum CARD_COLOR {
    CLUBS("C"),
    HEARTS("H"),
    SPADES("S"),
    DIAMONDS("D");

    /**
     * Constructor
     *
     * @param imageString The String for the image
     */
    CARD_COLOR(String imageString) {
        this.imageString = imageString;
    }

    private String imageString;

    /**
     * Getter for the Image String
     *
     * @return imageString
     */
    public String toImageString() {
        return imageString;
    }
}
