package Controller;

import View.GameView;
import View.StartGameView;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Game;
import model.Player;

import java.io.IOException;
import java.util.ArrayList;

public class GameController {

    public static final String[] NUMBERCARDS = {
            "2", "3", "4", "5", "6", "9", "10", "A", "K", "Q"
    };

    public static final int[] NUMBERECARDS_VALUES = {
            2, 3, 4, 5, 6, 9, 10, 11, 4, 3
    };

    public static final String[] ACTIONCARDS = {
            "7", "8", "J"
    };

    public static final int[] ACTIONCARDS_VALUES = {
            7, 8, 2
    };

    public static final String[] COLORS = {
            "C", "H", "S", "D"
    };

    private int numPlayers;
    private int numAIPlayers;
    private int numStartingCards;
    private Game game;

    public GameController(int numPlayers, int numAIPlayers, int numStartingCards) {
        this.numPlayers = numPlayers;
        this.numAIPlayers = numAIPlayers;
        this.numStartingCards = numStartingCards;
    }

    public void startGame() throws IOException {
        game = new Game(numPlayers, numAIPlayers, numStartingCards);

        GameView gameView = new GameView(game);
    }
}
