package neurochess;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class NameSelectionWindow extends JDialog {
    private JTextField whitePlayerField;
    private JTextField blackPlayerField;
    private JButton startMatchButton;
    private boolean confirmed = false;

    public NameSelectionWindow(GameFrame parent) {
        super(parent, "Player Registration", true);
        
        getContentPane().setBackground(new Color(45, 45, 55));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel headerLabel = new JLabel("ENTER PLAYER NAMES", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(new Color(240, 217, 181));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(headerLabel, gbc);

        gbc.gridwidth = 1;
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);

        JLabel whiteLabel = new JLabel("White Player:");
        whiteLabel.setFont(labelFont);
        whiteLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1;
        add(whiteLabel, gbc);

        whitePlayerField = new JTextField("Player 1", 12);
        whitePlayerField.setFont(labelFont);
        gbc.gridx = 1;
        add(whitePlayerField, gbc);

        JLabel blackLabel = new JLabel("Black Player:");
        blackLabel.setFont(labelFont);
        blackLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2;
        add(blackLabel, gbc);

        blackPlayerField = new JTextField("Player 2", 12);
        blackPlayerField.setFont(labelFont);
        gbc.gridx = 1;
        add(blackPlayerField, gbc);

        startMatchButton = new JButton("LAUNCH MATCH");
        startMatchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        startMatchButton.setBackground(new Color(181, 136, 99));
        startMatchButton.setForeground(Color.WHITE);
        startMatchButton.setFocusPainted(false);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(startMatchButton, gbc);

        startMatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = true;
                dispose();
            }
        });

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmed() { return confirmed; }
    public String getWhiteName() { return whitePlayerField.getText().trim().isEmpty() ? "Player 1" : whitePlayerField.getText().trim(); }
    public String getBlackName() { return blackPlayerField.getText().trim().isEmpty() ? "Player 2" : blackPlayerField.getText().trim(); }
}