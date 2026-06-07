package neurochess;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainMenu extends JPanel {

    public MainMenu(GameFrame parentFrame) {
        this.setBackground(new Color(30, 30, 40));
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;

        // Title Header
        JLabel titleLabel = new JLabel("NEUROCHESS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));
        titleLabel.setForeground(new Color(240, 217, 181));
        gbc.gridy = 0;
        this.add(titleLabel, gbc);

        // Subtitle Header
        JLabel subLabel = new JLabel("The Pawn Challenge Trivia Arcade");
        subLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        subLabel.setForeground(Color.LIGHT_GRAY);
        gbc.gridy = 1;
        this.add(subLabel, gbc);

        // Play Navigation Button Component
        JButton playButton = new JButton("START MATCH");
        playButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        playButton.setBackground(new Color(181, 136, 99));
        playButton.setForeground(Color.WHITE);
        playButton.setFocusPainted(false);
        playButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 25, 10, 25));
        gbc.gridy = 2;
        this.add(playButton, gbc);

        // Button Click Event
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open our separate standalone registration modal window
                NameSelectionWindow nameWindow = new NameSelectionWindow(parentFrame);
                nameWindow.setVisible(true);

                if (nameWindow.isConfirmed()) {
                    String p1 = nameWindow.getWhiteName();
                    String p2 = nameWindow.getBlackName();
                    parentFrame.switchToChessBoard(p1, p2);
                }
            }
        });
    }
}