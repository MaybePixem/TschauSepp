package model.card;

public enum CARD_COLOR {
    CLUBS("C"),
    HEARTS("H"),
    SPADES("S"),
    DIAMONDS("D");

    CARD_COLOR(String imageString) {
        this.imageString = imageString;
    }

    private String imageString;

    public String toImageString() {
        return imageString;
    }
}
