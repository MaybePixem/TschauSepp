import Controller.GameController;
import View.StartGameView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        StartGameView startGameWindow = new StartGameView();
        GameController game = new GameController(
                startGameWindow.getNumPlayersSpinner(),
                startGameWindow.getNumAISpinner(),
                startGameWindow.getNumStartingCardsSpinner()
        );
        game.startGame();
    }
}
