package Controller;

import View.ConnectionSetupView;
import View.StartGameView;
import model.Game;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class OnlinePlayController implements Runnable {

    private String ip;
    private int port = 25565;
    private Scanner scanner = new Scanner(System.in);
    private Thread thread;

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;

    private ServerSocket serverSocket;

    private boolean yourTurn = false;
    private boolean accepted = false;
    private boolean unableToCommunicateWithOpponent = false;

    private GameController gameController;

    private int errors = 0;

    public OnlinePlayController() {

        ConnectionSetupView connectionSetupView = new ConnectionSetupView();
        try {
            if (connectionSetupView.isHost()) {
                initializeServer();
            } else {
                ip = connectionSetupView.getIpInput();
                port = connectionSetupView.getPortInput();
                connect();
            }
            thread = new Thread(this, "TschauSeppServer");
            thread.start();
        } catch (NullPointerException e) {
            System.out.println("No Data was provided");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Unable to connect to the address: " + ip + ":" + port);
        }
    }

    public void run() {
        while (true) {
            if (accepted) {
                tick();
            } else {
                listenForServerRequest();
            }
        }
    }

    private void render() {
        if (unableToCommunicateWithOpponent) {

        }

        if (accepted) {
        }

    }

    private void tick() {
        if (errors >= 10) unableToCommunicateWithOpponent = true;

        if (!yourTurn && !unableToCommunicateWithOpponent) {
            try {
                String game = dis.readUTF();
                System.out.println(game);
                yourTurn = true;

            } catch (IOException e) {
                e.printStackTrace();
                errors++;
            }
        } else {
            try {
                System.out.println("You can type:");
                String bruh = scanner.nextLine();
                dos.writeUTF(bruh);
                yourTurn = false;
                System.out.println("Other person is typing..");
            } catch (IOException e) {
                e.printStackTrace();
                errors++;
            }
        }
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
            gameController.startGame();
            JSONObject jsonObject = new JSONObject(gameController.getGame());
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
        gameController = new GameController(jsonObject);
        gameController.startGame();
    }

    private void initializeServer() {
        try {
            StartGameView startGameWindow = new StartGameView();
            gameController = new GameController(
                    startGameWindow.getNumPlayersSpinner(),
                    startGameWindow.getNumAISpinner(),
                    startGameWindow.getNumStartingCardsSpinner()
            );

            serverSocket = new ServerSocket(port, 8, InetAddress.getLocalHost());
            System.out.println("Server was created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        OnlinePlayController ticTacToe = new OnlinePlayController();
    }

}
