package model.card;

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
    private int value;
    private boolean isActionCard;

    CARD_VALUE(String imageString, int value, boolean isActionCard) {
        this.imageString = imageString;
        this.value = value;
        this.isActionCard = isActionCard;
    }

    public String toImageString() {
        return imageString;
    }

    public int getValue() {
        return value;
    }

    public boolean isActionCard() {
        return isActionCard;
    }
}
