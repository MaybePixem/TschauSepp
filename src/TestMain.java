import Controller.GameController;

import java.io.IOException;

public class TestMain {
    public static void main(String[] args) throws IOException {
        GameController game = new GameController(
                2,
                2,
                6
        );
        game.startGame();
    }
}
