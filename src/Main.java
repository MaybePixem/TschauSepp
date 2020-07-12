import Controller.GameController;
import Controller.OnlinePlayController;
import View.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        ChoiceWindow selectGameTypeView = new ChoiceWindow("Spielart wählen", "Online", "Offline");
        if (selectGameTypeView.getSelectedOption() == 1) {
            ChoiceWindow selectClientTypeView = new ChoiceWindow("Spiel starten", "Host", "Client");
            if (selectClientTypeView.getSelectedOption() == 2) {
                ConnectionSetupView connectionSetupView = new ConnectionSetupView();
                new OnlinePlayController(
                        connectionSetupView.getIpInput(),
                        connectionSetupView.getPortInput()
                );
            } else {
                StartOnlineGameView startOnlineGameView = new StartOnlineGameView();
                new OnlinePlayController();
            }
        } else {
            StartNormalGameView startNormalGameView = new StartNormalGameView();
            GameController game = new GameController(
                    startNormalGameView.getNumPlayersSpinner(),
                    startNormalGameView.getNumAISpinner(),
                    startNormalGameView.getNumStartingCardsSpinner()
            );
            game.startGame();
        }
    }
}
