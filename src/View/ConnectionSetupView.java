package View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

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

        JPanel buttonPanel = new JPanel();

        JButton serverBtn = new JButton("Host");
        JButton clientBtn = new JButton("Client");

        buttonPanel.add(serverBtn);
        buttonPanel.add(clientBtn);

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

        SpinnerModel model = new SpinnerNumberModel(25565, 1024, 65535, 1);

        JLabel ipLabel = new JLabel("IP Adresse:");
        addLabelToLayout(ipLabel, 0, inputPanel);

        ipInput = new JTextField();
        addComponentToLayout(ipInput, 0, inputPanel);

        JLabel numAILabel = new JLabel("Port:");
        addLabelToLayout(numAILabel, 1, inputPanel);

        portInput = new JTextField();
        addComponentToLayout(portInput, 1, inputPanel);

        submitBtn = new JButton("Verbinden");
        addComponentToLayout(submitBtn, 2, inputPanel);

        submitBtn.addActionListener(e -> dispose());

        inputPanel.setVisible(false);

        serverBtn.addActionListener(e -> {
            isHost = true;
            dispose();
        });
        clientBtn.addActionListener(e -> {
            inputPanel.setVisible(true);
            buttonPanel.setVisible(false);
        });

        portInput.setText("25565");
        ipInput.setText("192.168.1.139");

        JPanel jPanel = new JPanel(new BorderLayout());
        jPanel.add(buttonPanel, BorderLayout.NORTH);
        jPanel.add(inputPanel, BorderLayout.CENTER);

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

    private void addComponentToLayout(Component component, int yPos, Container containingPanel) {

        GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
        gridBagConstraintForLabel.fill = GridBagConstraints.BOTH;
        gridBagConstraintForLabel.insets = new Insets(0, 0, 5, 0);
        gridBagConstraintForLabel.gridx = 1;
        gridBagConstraintForLabel.gridy = yPos;
        containingPanel.add(component, gridBagConstraintForLabel);
    }


    public String getIpInput() {
        return ipInput.getText();
    }

    public int getPortInput() {
        return Integer.parseInt(portInput.getText());
    }

    public boolean isHost() {
        return isHost;
    }
}
