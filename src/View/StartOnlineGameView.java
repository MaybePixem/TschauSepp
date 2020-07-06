package View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StartOnlineGameView extends JDialog {

    private JSpinner numStartingCardsSpinner;
    private JButton submitBtn;


    public StartOnlineGameView() throws HeadlessException {
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

        SpinnerModel model1 = new SpinnerNumberModel(6, 4, 12, 1);


        JLabel numStartingCardsLabel = new JLabel("Anzahl Startkarten:");
        InputViewHelper.addLabelToLayout(numStartingCardsLabel, 0, mainPanel);

        numStartingCardsSpinner = new JSpinner(model1);
        InputViewHelper.makeDigitsOnlySpinnerUsingDocumentFilter(numStartingCardsSpinner);
        InputViewHelper.addComponentToLayout(numStartingCardsSpinner, 0, mainPanel);

        submitBtn = new JButton("Starten");
        GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
        gridBagConstraintForLabel.fill = GridBagConstraints.BOTH;
        gridBagConstraintForLabel.insets = new Insets(0, 0, 0, 0);
        gridBagConstraintForLabel.gridx = 1;
        gridBagConstraintForLabel.gridy = 3;
        submitBtn.addActionListener(actionEvent -> dispose());
        mainPanel.add(submitBtn, gridBagConstraintForLabel);

        numStartingCardsSpinner.setValue(6);

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    public int getNumStartingCardsSpinner() {
        return (int) numStartingCardsSpinner.getValue();
    }
}
