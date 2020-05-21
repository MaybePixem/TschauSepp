package View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;

public class StartGameView extends JDialog {

    private JSpinner numPlayersSpinner;
    private JSpinner numAISpinner;
    private JSpinner numStartingCardsSpinner;
    JButton submitBtn;


    public StartGameView() throws HeadlessException {
        setTitle("Spiel starten");
        setSize(300, 160);
        setLocationRelativeTo(null);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel jPanel = new JPanel();
        Border border = jPanel.getBorder();
        Border margin = new EmptyBorder(10, 10, -10, 10);
        jPanel.setBorder(new CompoundBorder(border, margin));

        GridBagLayout panelGridBagLayout = new GridBagLayout();
        panelGridBagLayout.columnWidths = new int[]{86, 86, 0};
        panelGridBagLayout.rowHeights = new int[]{20, 20, 20, 20, 20, 0};
        panelGridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        panelGridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE};
        jPanel.setLayout(panelGridBagLayout);

        SpinnerModel model1 = new SpinnerNumberModel(1, 0, 4, 1);
        SpinnerModel model2 = new SpinnerNumberModel(0, 0, 4, 1);
        SpinnerModel model3 = new SpinnerNumberModel(0, 0, 12, 1);


        JLabel numPlayersLabel = new JLabel("Anzahl Spieler:");
        addLabelToLayout(numPlayersLabel, 0, jPanel);

        numPlayersSpinner = new JSpinner(model1);
        makeDigitsOnlySpinnerUsingDocumentFilter(numPlayersSpinner);
        addSpinnerToLayout(numPlayersSpinner, 0, jPanel);

        JLabel numAILabel = new JLabel("Anzahl Roboter:");
        addLabelToLayout(numAILabel, 1, jPanel);

        numAISpinner = new JSpinner(model2);
        makeDigitsOnlySpinnerUsingDocumentFilter(numAISpinner);
        addSpinnerToLayout(numAISpinner, 1, jPanel);

        JLabel numStartingCardsLabel = new JLabel("Anzahl Startkarten:");
        addLabelToLayout(numStartingCardsLabel, 2, jPanel);

        numStartingCardsSpinner = new JSpinner(model3);
        makeDigitsOnlySpinnerUsingDocumentFilter(numStartingCardsSpinner);
        addSpinnerToLayout(numStartingCardsSpinner, 2, jPanel);

        submitBtn = new JButton("Starten");
        GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
        gridBagConstraintForLabel.fill = GridBagConstraints.BOTH;
        gridBagConstraintForLabel.insets = new Insets(0, 0, 0, 0);
        gridBagConstraintForLabel.gridx = 1;
        gridBagConstraintForLabel.gridy = 3;
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        jPanel.add(submitBtn, gridBagConstraintForLabel);

        numAISpinner.setValue(3);
        numStartingCardsSpinner.setValue(6);

        getContentPane().add(jPanel);
        setVisible(true);
    }

    private void addLabelToLayout(JLabel component, int yPos, Container containingPanel) {

        GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
        gridBagConstraintForLabel.fill = GridBagConstraints.BOTH;
        gridBagConstraintForLabel.insets = new Insets(0, 0, 5, 5);
        gridBagConstraintForLabel.gridx = 0;
        gridBagConstraintForLabel.gridy = yPos;
        containingPanel.add(component, gridBagConstraintForLabel);
    }

    private void addSpinnerToLayout(JSpinner component, int yPos, Container containingPanel) {

        GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
        gridBagConstraintForLabel.fill = GridBagConstraints.BOTH;
        gridBagConstraintForLabel.insets = new Insets(0, 0, 5, 0);
        gridBagConstraintForLabel.gridx = 1;
        gridBagConstraintForLabel.gridy = yPos;
        containingPanel.add(component, gridBagConstraintForLabel);
    }

    public int getNumPlayersSpinner() {
        return (int) numAISpinner.getValue();
    }

    public int getNumAISpinner() {
        return (int) numAISpinner.getValue();
    }

    public int getNumStartingCardsSpinner() {
        return (int) numAISpinner.getValue();
    }

    private void makeDigitsOnlySpinnerUsingDocumentFilter(JSpinner spinner) {
        JSpinner.NumberEditor jsEditor = (JSpinner.NumberEditor) spinner.getEditor();
        final Document jsDoc = jsEditor.getTextField().getDocument();
        if (jsDoc instanceof PlainDocument) {
            AbstractDocument doc = new PlainDocument() {

                private static final long serialVersionUID = 1L;

                @Override
                public void setDocumentFilter(DocumentFilter filter) {
                    if (filter instanceof SpinnerNumberFilter) {
                        super.setDocumentFilter(filter);
                    }
                }
            };
            doc.setDocumentFilter(new SpinnerNumberFilter());
            jsEditor.getTextField().setDocument(doc);
        }
    }

}
