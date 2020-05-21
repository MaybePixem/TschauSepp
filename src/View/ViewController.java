package View;

import model.Game;

import javax.swing.*;

public class ViewController extends JFrame {


    public static void main(String[] args) {
        StartGameView startGameWindow = new StartGameView();
        Game game = new Game(
                startGameWindow.getNumPlayersSpinner(),
                startGameWindow.getNumAISpinner(),
                startGameWindow.getNumStartingCardsSpinner()
        );
        GameView gameView = new GameView();
    }

}
