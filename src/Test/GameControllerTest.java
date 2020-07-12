package Test;

import Controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    GameController gameController;

    @BeforeEach
    void setUp() throws IOException {
        gameController = new GameController(2, 2, 6);
        gameController.startGame();
    }

    @Test
    void drawCard() {
        int initialCardsFromPlayer = gameController.getGame().getCurrentPlayer().getDecksize();
        gameController.getGame().getCurrentPlayer().addCard(gameController.drawCard());
        assertEquals(initialCardsFromPlayer + 1, gameController.getGame().getPlayers().get(gameController.getGame().getCurrentPlayerIndex()).getDecksize());
    }

    @Test
    void nextPlayer() {
        int currentPlayer = gameController.getGame().getCurrentPlayerIndex();
        gameController.nextPlayer(false, 0);
        assertEquals(currentPlayer + 1, gameController.getGame().getCurrentPlayerIndex());
    }

    @Test
    void getWinningPlayer() {
        assertNull(gameController.getWinningPlayer());
    }
}