import Controller.GameController;
import View.StartGameView;

public class Main {
    public static void main(String[] args) {
        StartGameView startGameWindow = new StartGameView();
        GameController game = new GameController(
                startGameWindow.getNumPlayersSpinner(),
                startGameWindow.getNumAISpinner(),
                startGameWindow.getNumStartingCardsSpinner()
        );
        game.startGame();
    }
}
