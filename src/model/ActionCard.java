package model;

public class ActionCard extends Card {

    private String action;

    public ActionCard(int c, String a) {
        super(c);
        action = a;
    }

    public String getAction() {
        return action;
    }


    public String toString() {
        return getColor() + " " + action;
    }
}
