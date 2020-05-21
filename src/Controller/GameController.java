package Controller;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Game;
import model.Player;

import java.util.ArrayList;

public class GameController {

    private static final String[] ALLCARDS = {
            "Eichel Ass", "Eichel König", "Eichel Ober", "Eichel Under", "Eichel Banner", "Eichel Neun", "Eichel Acht", "Eichel Sieben", "Eichel Sechs",
            "Rosen Ass", "Rosen König", "Rosen Ober", "Rosen Under", "Rosen Banner", "Rosen Neun", "Rosen Acht", "Rosen Sieben", "Rosen Sechs", "Schellen Ass", "Schellen König", "Schellen Ober",
            "Schellen Under", "Schellen Banner", "Schellen Neun", "Schellen Acht", "Schellen Sieben", "Schellen Sechs", "Schilten Ass", "Schilten König", "Schilten Ober", "Schilten Under",
            "Schilten Banner", "Schilten Neun", "Schilten Acht", "Schilten Sieben", "Schilten Sechs"
    };

    private static final String[] SPECIALCARDS = {
            "Eichel Sieben", "Rosen Sieben", "Schellen Sieben", "Schilten Sieben",
            "Eichel Acht", "Rosen Acht", "Schellen Acht", "Schilten Acht",
            "Eichel Under", "Rosen Under", "Schellen Under", "Schilten Under"
    };

    private int numPlayers;
    private int numAIPlayers;
    private int numStartingCards;

    public GameController(int numPlayers, int numAIPlayers, int numStartingCards) {
        this.numPlayers = numPlayers;
        this.numAIPlayers = numAIPlayers;
        this.numStartingCards = numStartingCards;
    }
}
