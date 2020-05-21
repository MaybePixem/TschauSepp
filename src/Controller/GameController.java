package Controller;

import View.GameView;
import View.StartGameView;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Game;
import model.Player;

import java.util.ArrayList;

public class GameController {

    public static final String[] NUMBERCARDS = {
            "Ass", "König", "Ober","Under", "Banner", "Neun", "Sechs"
    };

    public static final String[] ACTIONCARDS = {
            "Sieben", "Acht", "Bauer"
    };

    public static final String[] COLORS = {"Eichel", "Rosen", "Schellen", "Schilten"};

    private int numPlayers;
    private int numAIPlayers;
    private int numStartingCards;
    private Game game;

    public GameController(int numPlayers, int numAIPlayers, int numStartingCards) {
        this.numPlayers = numPlayers;
        this.numAIPlayers = numAIPlayers;
        this.numStartingCards = numStartingCards;
    }

    public void startGame() {
        GameView gameView = new GameView();

        game = new Game(numPlayers, numAIPlayers, numStartingCards);
    }
}
