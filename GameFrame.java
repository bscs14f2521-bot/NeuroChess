package neurochess;

import java.awt.Dimension;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {

    private MainMenu mainMenu;
    private ChessBoard chessBoard;

    public GameFrame() {
        mainMenu = new MainMenu(this);
        this.add(mainMenu);

        this.setTitle("NeuroChess Quiz Game");
        
        // Responsive Window Layout Restrictions
        this.setMinimumSize(new Dimension(750, 650)); 
        this.setSize(950, 750);                        
        this.setResizable(true);                       
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void switchToChessBoard(String player1, String player2) {
        this.remove(mainMenu);

        chessBoard = new ChessBoard(player1, player2);
        this.add(chessBoard);

        // Synchronize and re-render visual frame elements instantly
        this.revalidate();
        this.repaint();
    }
}