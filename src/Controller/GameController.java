package Controller;

import View.GameView;
import model.Game;
import org.json.JSONObject;

import java.io.IOException;

public class GameController {

    private Game game;

    public GameController(int numPlayers, int numAIPlayers, int numStartingCards) {
        game = new Game(numPlayers, numAIPlayers, numStartingCards);
    }

    public void startGame() throws IOException {
        GameView gameView = new GameView(game);
    }


    public Game getGame() {
        return game;
    }
}
