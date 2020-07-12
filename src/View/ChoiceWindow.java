package View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Basic 2-way choice window.
 *
 * @author Tim Bucher
 */
public class ChoiceWindow extends JDialog{

    private int selectedOption;

    public ChoiceWindow(String title, String option1, String option2) throws HeadlessException {
        setTitle(title);
        setSize(300, 160);
        setLocationRelativeTo(null);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel buttonPanel = new JPanel();

        JButton option1Btn = new JButton(option1);
        JButton option2Btn = new JButton(option2);

        buttonPanel.add(option1Btn);
        buttonPanel.add(option2Btn);

        JPanel inputPanel = new JPanel();
        Border border = inputPanel.getBorder();
        Border margin = new EmptyBorder(10, 10, -10, 10);
        inputPanel.setBorder(new CompoundBorder(border, margin));

        GridBagLayout panelGridBagLayout = new GridBagLayout();
        panelGridBagLayout.columnWidths = new int[]{86, 86, 0};
        panelGridBagLayout.rowHeights = new int[]{20, 20, 20, 20, 20, 0};
        panelGridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        panelGridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE};
        inputPanel.setLayout(panelGridBagLayout);

        inputPanel.setVisible(false);

        option1Btn.addActionListener(e -> {
            selectedOption = 1;
            dispose();
        });
        option2Btn.addActionListener(e -> {
            selectedOption = 2;
            dispose();
        });

        JPanel jPanel = new JPanel(new BorderLayout());
        jPanel.add(buttonPanel, BorderLayout.NORTH);
        jPanel.add(inputPanel, BorderLayout.CENTER);

        getContentPane().add(jPanel);
        setVisible(true);
    }

    public int getSelectedOption() {
        return selectedOption;
    }
}
