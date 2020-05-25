package View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StartNormalGameView extends StandardInputView {
    private JSpinner numPlayersSpinner;
    private JSpinner numAISpinner;
    private JSpinner numStartingCardsSpinner;
    private JButton submitBtn;


    public StartNormalGameView() throws HeadlessException {
        setTitle("Spiel starten");
        setSize(300, 160);
        setLocationRelativeTo(null);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        Border border = mainPanel.getBorder();
        Border margin = new EmptyBorder(10, 10, -10, 10);
        mainPanel.setBorder(new CompoundBorder(border, margin));

        GridBagLayout panelGridBagLayout = new GridBagLayout();
        panelGridBagLayout.columnWidths = new int[]{86, 86, 0};
        panelGridBagLayout.rowHeights = new int[]{20, 20, 20, 20, 20, 0};
        panelGridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        panelGridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE};
        mainPanel.setLayout(panelGridBagLayout);

        SpinnerModel model1 = new SpinnerNumberModel(1, 0, 4, 1);
        SpinnerModel model2 = new SpinnerNumberModel(0, 0, 4, 1);
        SpinnerModel model3 = new SpinnerNumberModel(0, 0, 12, 1);


        JLabel numPlayersLabel = new JLabel("Anzahl Spieler:");
        addLabelToLayout(numPlayersLabel, 0, mainPanel);

        numPlayersSpinner = new JSpinner(model1);
        makeDigitsOnlySpinnerUsingDocumentFilter(numPlayersSpinner);
        addComponentToLayout(numPlayersSpinner, 0, mainPanel);

        JLabel numAILabel = new JLabel("Anzahl Roboter:");
        addLabelToLayout(numAILabel, 1, mainPanel);

        numAISpinner = new JSpinner(model2);
        makeDigitsOnlySpinnerUsingDocumentFilter(numAISpinner);
        addComponentToLayout(numAISpinner, 1, mainPanel);

        JLabel numStartingCardsLabel = new JLabel("Anzahl Startkarten:");
        addLabelToLayout(numStartingCardsLabel, 2, mainPanel);

        numStartingCardsSpinner = new JSpinner(model3);
        makeDigitsOnlySpinnerUsingDocumentFilter(numStartingCardsSpinner);
        addComponentToLayout(numStartingCardsSpinner, 2, mainPanel);

        submitBtn = new JButton("Starten");
        GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
        gridBagConstraintForLabel.fill = GridBagConstraints.BOTH;
        gridBagConstraintForLabel.insets = new Insets(0, 0, 0, 0);
        gridBagConstraintForLabel.gridx = 1;
        gridBagConstraintForLabel.gridy = 3;
        submitBtn.addActionListener(actionEvent -> dispose());
        mainPanel.add(submitBtn, gridBagConstraintForLabel);

        numAISpinner.setValue(3);
        numAISpinner.setValue(3);
        numStartingCardsSpinner.setValue(6);

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    public int getNumPlayersSpinner() {
        return (int) numPlayersSpinner.getValue();
    }

    public int getNumAISpinner() {
        return (int) numAISpinner.getValue();
    }

    public int getNumStartingCardsSpinner() {
        return (int) numStartingCardsSpinner.getValue();
    }
}
