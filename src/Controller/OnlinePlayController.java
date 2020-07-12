package Controller;

import View.GameOverView;
import View.GameView;
import View.StartOnlineGameView;
import model.Game;
import model.card.CARD_COLOR;
import model.card.CARD_VALUE;
import model.card.Card;
import model.player.Player;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Contains all the logic needed for Online Play. Uses the GameController class to control the game.
 *
 * @author Tim Bucher
 */
public class OnlinePlayController implements Runnable {

    private String ip;
    private int port = 25565;

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;

    private ServerSocket serverSocket;

    private boolean startTurn = false;
    private boolean gameIsAlive = true;

    private Game game;
    private GameController gameController;
    GameView gameView;

    public OnlinePlayController(String ip, int port) {

        try {
            this.ip = ip;
            this.port = port;
            connect();
            Thread thread = new Thread(this, "bruh");
            thread.start();

        } catch (IOException e) {
            System.out.println("Unable to connect to the address: " + ip + ":" + port);
        }
    }

    public OnlinePlayController() {
        try {
            initializeServer();
            listenForServerRequest();
            Thread thread = new Thread(this, "bruh");
            thread.start();

        } catch (NullPointerException e) {
            System.out.println("No Data was provided");
            e.printStackTrace();
        }
    }

    private void tick() {
        try {
            System.out.println("start listening");
            game = createGameObjectFromJSONObject(new JSONObject(dis.readUTF()));
            gameController.setGame(game);
            if (gameController.getWinningPlayer() != null) {
                new GameOverView(gameView, gameController.getWinningPlayer(), game.getPlayers(), game.getFinishedPlayers());
                gameView.dispose();
                gameIsAlive = false;
            } else {
                System.out.println("startet turn");
                gameView.setGame(game);
                gameView.setHideCards(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }


    public void startGame() throws IOException {
        gameView = new GameView(this, gameController);
    }

    private void listenForServerRequest() {
        Socket socket;
        try {
            socket = serverSocket.accept();
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            System.out.println("Client connected");
            startGame();
            JSONObject jsonObject = new JSONObject(game);
            dos.writeUTF(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connect() throws IOException {
        socket = new Socket(ip, port);
        dos = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());
        System.out.println("Successfully connected to the server.");
        JSONObject jsonObject = new JSONObject(dis.readUTF());
        game = createGameObjectFromJSONObject(jsonObject);
        gameController = new GameController(game);
        startGame();
        gameView.setHideCards(true);
    }

    private void initializeServer() {
        try {
            StartOnlineGameView startGameWindow = new StartOnlineGameView();
            game = new Game(
                    startGameWindow.getNumStartingCardsSpinner()
            );
            gameController = new GameController(game);

            serverSocket = new ServerSocket(port, 8, InetAddress.getLocalHost());
            System.out.println("Server was created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (gameIsAlive) {
            tick();
        }
    }

    public void endTurn() {
        try {
            gameView.setHideCards(true);
            dos.writeUTF(new JSONObject(game).toString());
            System.out.println("ended turn");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void endGame() {
        try {
            dos.writeUTF(new JSONObject(game).toString());
            new GameOverView(gameView, gameController.getWinningPlayer(), game.getPlayers(), game.getFinishedPlayers());
            gameView.dispose();
            gameIsAlive = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Game createGameObjectFromJSONObject(JSONObject jsonObject) {
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Player> finishedPlayers = new ArrayList<>();
        ArrayList<Card> currentDeck = new ArrayList<>();
        ArrayList<Card> sideDeck = new ArrayList<>();
        int currentPlayer = Integer.parseInt(jsonObject.get("currentPlayerIndex").toString());

        CARD_COLOR bauerColor;
        try {
            bauerColor = CARD_COLOR.valueOf(jsonObject.get("bauerColor").toString());
        } catch (JSONException e) {
            bauerColor = null;
        }

        JSONArray playersArr = new JSONArray(jsonObject.get("players").toString());
        for (int i = 0; i < playersArr.length(); i++) {
            JSONObject player = new JSONObject(playersArr.get(i).toString());
            ArrayList<Card> deck = new ArrayList<>();
            JSONArray deckArr = new JSONArray(player.get("deck").toString());
            for (int j = 0; j < deckArr.length(); j++) {
                JSONObject card = new JSONObject(deckArr.get(j).toString());
                deck.add(new Card(
                        CARD_COLOR.valueOf(card.get("color").toString()),
                        CARD_VALUE.valueOf(card.get("value").toString())
                ));
            }
            players.add(new Player(deck));
        }

        JSONArray sideDeckArr = new JSONArray(jsonObject.get("sideDeck").toString());
        for (int i = 0; i < sideDeckArr.length(); i++) {
            JSONObject card = new JSONObject(sideDeckArr.get(i).toString());
            sideDeck.add(new Card(
                    CARD_COLOR.valueOf(card.get("color").toString()),
                    CARD_VALUE.valueOf(card.get("value").toString())
            ));
        }

        JSONArray currentDeckArr = new JSONArray(jsonObject.get("currentDeck").toString());
        for (int i = 0; i < currentDeckArr.length(); i++) {
            JSONObject card = new JSONObject(currentDeckArr.get(i).toString());
            currentDeck.add(new Card(
                    CARD_COLOR.valueOf(card.get("color").toString()),
                    CARD_VALUE.valueOf(card.get("value").toString())
            ));
        }

        return new Game(players, finishedPlayers, currentDeck, sideDeck, currentPlayer, bauerColor);
    }
}
