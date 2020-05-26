package View;

import model.player.AI;
import model.Game;
import model.player.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameOverView extends JDialog {

    private boolean endGame;

    public static void main(String[] args) {
        Game game = new Game(1, 1, 6);
        new GameOverView(null, game.getPlayers().get(0), game.getPlayers(), game.getFinishedPlayers());
    }

    public GameOverView(Frame owner, Player winner, ArrayList<Player> allPlayers, ArrayList<Player> finishedPlayers) {
        super(owner);

        int winnerIndex = allPlayers.lastIndexOf(winner);
        String winnerString =
                getPlayerTypeString(winner)
                        + (winnerIndex + 1)
                        + (finishedPlayers.size() >= 1 ? " ist Fertig!" : " hat gewonnen!");

        setTitle(winnerString);
        setSize(new Dimension(640, 360));
        setMinimumSize(new Dimension(360, 250));
        setLocationRelativeTo(null);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel otherPlayersInfoPanel = new JPanel();
        JPanel otherPlayersInfoPanelContainer = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel winnerStringPanel = new JPanel();

        JLabel winnerStringLabel = new JLabel(winnerString);
        winnerStringLabel.setFont(new Font("Arial", Font.PLAIN, 25));

        JButton continueGameBtn = new JButton("Weiter spielen");
        JButton endGameBtn = new JButton("Spiel beenden");

        otherPlayersInfoPanel.setLayout(new BoxLayout(otherPlayersInfoPanel, BoxLayout.PAGE_AXIS));

        for (int i = 0; i < allPlayers.size(); i++) {
            Player p = allPlayers.get(i);
            if (p.equals(winner))
                continue;
            JLabel label = new JLabel(getPlayerTypeString(p) + (i + 1) + " hat noch " + p.getDecksize() + " Karte(n)");
            label.setFont(new Font("Arial", Font.PLAIN, 15));
            label.setAlignmentX(0.5f);
            otherPlayersInfoPanel.add(label);
        }

        otherPlayersInfoPanelContainer.setLayout(new BoxLayout(otherPlayersInfoPanelContainer, BoxLayout.X_AXIS));
        otherPlayersInfoPanelContainer.add(Box.createHorizontalGlue());
        otherPlayersInfoPanelContainer.add(otherPlayersInfoPanel);
        otherPlayersInfoPanelContainer.add(Box.createHorizontalGlue());

        winnerStringPanel.add(winnerStringLabel);

        if (allPlayers.size() > 2)
            buttonPanel.add(continueGameBtn);
        buttonPanel.add(endGameBtn);

        mainPanel.add(winnerStringPanel, BorderLayout.NORTH);
        mainPanel.add(otherPlayersInfoPanelContainer, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        continueGameBtn.addActionListener(actionEvent -> {
            endGame = false;
            dispose();
        });

        endGameBtn.addActionListener(actionEvent -> {
            endGame = true;
            dispose();
        });

        getContentPane().add(mainPanel);

        setVisible(true);

    }

    private String getPlayerTypeString(Player p) {
        return p instanceof AI ? "Roboter " : "Spieler ";
    }

    boolean isEndGame() {
        return endGame;
    }
}
