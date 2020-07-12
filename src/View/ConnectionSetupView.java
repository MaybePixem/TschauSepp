package View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * View to setup a connection.
 *
 * @author Tim Bucher
 */
public class ConnectionSetupView extends JDialog {

    private JTextField ipInput;
    private JTextField portInput;
    private JButton submitBtn;
    private boolean isHost = false;

    public ConnectionSetupView() throws HeadlessException {
        setTitle("Verbindung aufbauen");
        setSize(300, 160);
        setLocationRelativeTo(null);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

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

        JLabel ipLabel = new JLabel("IP Adresse:");
        InputViewHelper.addLabelToLayout(ipLabel, 0, inputPanel);

        ipInput = new JTextField();
        InputViewHelper.addComponentToLayout(ipInput, 0, inputPanel);

        JLabel numAILabel = new JLabel("Port:");
        InputViewHelper.addLabelToLayout(numAILabel, 1, inputPanel);

        portInput = new JTextField();
        InputViewHelper.addComponentToLayout(portInput, 1, inputPanel);

        submitBtn = new JButton("Verbinden");
        InputViewHelper.addComponentToLayout(submitBtn, 2, inputPanel);

        submitBtn.addActionListener(e -> dispose());

        JPanel jPanel = new JPanel(new BorderLayout());
        jPanel.add(inputPanel, BorderLayout.CENTER);

        portInput.setText("25565");
        ipInput.setText("192.168.1.139");

        getContentPane().add(jPanel);
        setVisible(true);
    }

    public String getIpInput() {
        return ipInput.getText();
    }

    public int getPortInput() {
        return Integer.parseInt(portInput.getText());
    }
}
