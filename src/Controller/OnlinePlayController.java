package Controller;

import View.ConnectionSetupView;
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
import java.util.Timer;
import java.util.TimerTask;

public class OnlinePlayController implements Runnable {

    private String ip;
    private int port = 25565;
    private Thread thread;

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;

    private ServerSocket serverSocket;

    private boolean yourTurn = false;
    private boolean accepted = false;
    private boolean startTurn = false;
    private boolean gameIsAlive = true;

    private Game game;
    GameView gameView;

    public OnlinePlayController() {

        ConnectionSetupView connectionSetupView = new ConnectionSetupView();
        try {
            if (connectionSetupView.isHost()) {
                initializeServer();
                listenForServerRequest();
            } else {
                ip = connectionSetupView.getIpInput();
                port = connectionSetupView.getPortInput();
                connect();
            }
            Thread thread = new Thread(this, "bruh");
            thread.start();
        } catch (NullPointerException e) {
            System.out.println("No Data was provided");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Unable to connect to the address: " + ip + ":" + port);
        }
    }

    private void tick() {
        try {
            System.out.println("start listening");
            game = createGameObjectFromJSONObject(new JSONObject(dis.readUTF()));
            System.out.println("startet turn");
            gameView.setGame(game);
            gameView.setHideCards(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }


    public void startGame() throws IOException {
        gameView = new GameView(game, this);
    }

    private void listenForServerRequest() {
        Socket socket;
        try {
            socket = serverSocket.accept();
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            accepted = true;
            yourTurn = true;
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
        accepted = true;
        System.out.println("Successfully connected to the server.");
        JSONObject jsonObject = new JSONObject(dis.readUTF());
        game = createGameObjectFromJSONObject(jsonObject);
        startGame();
        gameView.setHideCards(true);
    }

    private void initializeServer() {
        try {
            StartOnlineGameView startGameWindow = new StartOnlineGameView();
            game = new Game(
                    startGameWindow.getNumStartingCardsSpinner()
            );

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

    public static void main(String[] args) {
        OnlinePlayController o = new OnlinePlayController();
    }

    public void endTurn() {
        try {
            yourTurn = false;
            gameView.setHideCards(true);
            dos.writeUTF(new JSONObject(game).toString());
            System.out.println("ended turn");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void endGame() {
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
