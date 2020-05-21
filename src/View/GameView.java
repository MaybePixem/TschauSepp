package View;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {

    public static void main(String[] args) {
        new GameView();
    }

    public GameView() throws HeadlessException {
        setTitle("Tschau Sepp");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel playerDeckPanel = new JPanel();
        JPanel playfieldPlanel = new JPanel();
        JPanel otherPlayersPanel = new JPanel();

        getContentPane().add(mainPanel);
        setVisible(true);
    }
}
