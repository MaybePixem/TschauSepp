import Controller.GameController;
import View.StartNormalGameView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        StartNormalGameView startGameWindow = new StartNormalGameView();
        GameController game = new GameController(
                startGameWindow.getNumPlayersSpinner(),
                startGameWindow.getNumAISpinner(),
                startGameWindow.getNumStartingCardsSpinner()
        );
        game.startGame();
    }

}
