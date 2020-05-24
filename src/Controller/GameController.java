package Controller;

import View.GameView;
import model.Game;

import java.io.IOException;

public class GameController {

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
