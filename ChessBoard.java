package neurochess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class ChessBoard extends JPanel implements ActionListener {
    private JButton[][] squares = new JButton[8][8];
    private QuizManager quizManager;
    private JLabel statusLabel;
    
    private JLabel whiteScoreLabel;
    private JLabel blackScoreLabel;

    private String whitePlayer;
    private String blackPlayer;
    private boolean isWhiteTurn = true;
    
    private int whiteScore = 0;
    private int blackScore = 0;

    private int whiteHints = 5;
    private int blackHints = 5;

    private boolean isPieceSelected = false;
    private int selectedRow = -1;
    private int selectedCol = -1;

    private final Color WOOD_DARK   = new Color(181, 136, 99);   
    private final Color CREAM_LIGHT = new Color(240, 217, 181);  
    private final Color PANEL_BG    = new Color(35, 35, 45);     
    private final Color TEXT_GOLD   = new Color(240, 217, 181);  

    public ChessBoard(String whitePlayer, String blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.quizManager = new QuizManager();

        setLayout(new BorderLayout());
        setBackground(PANEL_BG);

        statusLabel = new JLabel(whitePlayer + "'s Turn (White) - Select a piece", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        statusLabel.setBackground(PANEL_BG); 
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setOpaque(true);
        statusLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));
        add(statusLabel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(8, 8));
        initializeBoard(gridPanel);
        add(gridPanel, BorderLayout.CENTER);

        JPanel scorePanel = new JPanel(new GridLayout(5, 1, 10, 10)); 
        scorePanel.setBackground(PANEL_BG);
        scorePanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel scoreboardTitle = new JLabel("SCOREBOARD", SwingConstants.CENTER);
        scoreboardTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        scoreboardTitle.setForeground(TEXT_GOLD);
        scorePanel.add(scoreboardTitle);

        whiteScoreLabel = new JLabel(whitePlayer + " (W): 0 pts (💡 5 Left)", SwingConstants.LEFT);
        whiteScoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        whiteScoreLabel.setForeground(Color.WHITE);
        scorePanel.add(whiteScoreLabel);

        blackScoreLabel = new JLabel(blackPlayer + " (B): 0 pts (💡 5 Left)", SwingConstants.LEFT);
        blackScoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        blackScoreLabel.setForeground(Color.LIGHT_GRAY);
        scorePanel.add(blackScoreLabel);
        
        add(scorePanel, BorderLayout.EAST);
    }

    private void initializeBoard(JPanel gridPanel) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                squares[r][c] = new JButton();
                squares[r][c].setFocusPainted(false);
                squares[r][c].addActionListener(this);
                gridPanel.add(squares[r][c]);
            }
        }
        clearHighlights();
        setupPieces();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        adjustButtonFonts();
    }

    private void adjustButtonFonts() {
        int squareWidth = squares[0][0].getWidth();
        int squareHeight = squares[0][0].getHeight();
        int dynamicFontSize = Math.min(squareWidth, squareHeight) / 2;

        if (dynamicFontSize < 16) dynamicFontSize = 16;
        Font responsiveFont = new Font("Segoe UI Symbol", Font.BOLD, dynamicFontSize);

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                squares[r][c].setFont(responsiveFont);
            }
        }
    }

    private void setupPieces() {
        // Black Pieces
        squares[0][0].setText("♜"); squares[0][1].setText("♞"); squares[0][2].setText("♝");
        squares[0][3].setText("♛"); squares[0][4].setText("♚"); squares[0][5].setText("♝");
        squares[0][6].setText("♞"); squares[0][7].setText("♜");
        for (int i = 0; i < 8; i++) squares[1][i].setText("♟");

        // White Pieces
        for (int i = 0; i < 8; i++) squares[6][i].setText("♙");
        squares[7][0].setText("♖"); squares[7][1].setText("♘"); squares[7][2].setText("♗");
        squares[7][3].setText("♕"); squares[7][4].setText("♔"); squares[7][5].setText("♗");
        squares[7][6].setText("♘"); squares[7][7].setText("♖");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedSquare = (JButton) e.getSource();
        int clickedRow = -1;
        int clickedCol = -1;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (squares[r][c] == clickedSquare) {
                    clickedRow = r;
                    clickedCol = c;
                    break;
                }
            }
        }

        if (!isPieceSelected) {
            String piece = clickedSquare.getText();
            if (!piece.equals("")) {
                boolean isWhitePiece = "♙♖♘♗♕♔".contains(piece);
                if ((isWhiteTurn && !isWhitePiece) || (!isWhiteTurn && isWhitePiece)) {
                    statusLabel.setText("Not your turn! It is " + (isWhiteTurn ? whitePlayer : blackPlayer) + "'s turn.");
                    return;
                }

                selectedRow = clickedRow;
                selectedCol = clickedCol;
                isPieceSelected = true;
                
                squares[selectedRow][selectedCol].setBackground(Color.YELLOW);
                showValidMoves(piece, selectedRow, selectedCol);
            }
        } else {
            String movingPiece = squares[selectedRow][selectedCol].getText();
            String targetPiece = squares[clickedRow][clickedCol].getText();
            
            clearHighlights();

            if (selectedRow == clickedRow && selectedCol == clickedCol) {
                isPieceSelected = false;
                return;
            }

            if (!targetPiece.equals("")) {
                boolean isMovingWhite = "♙♖♘♗♕♔".contains(movingPiece);
                boolean isTargetWhite = "♙♖♘♗♕♔".contains(targetPiece);
                if (isMovingWhite == isTargetWhite) {
                    statusLabel.setText("You cannot capture your own piece!");
                    isPieceSelected = false;
                    return;
                }
            }

            if (!isValidChessMove(movingPiece, selectedRow, selectedCol, clickedRow, clickedCol, !targetPiece.equals(""))) {
                statusLabel.setText("Invalid move pattern!");
                isPieceSelected = false;
                return;
            }

            int pointsAwarded = getPieceBountyValue(movingPiece); 
            if (!targetPiece.equals("")) {
                pointsAwarded += getPieceBountyValue(targetPiece);
            }

            int activeHints = isWhiteTurn ? whiteHints : blackHints;
            int scoreStatus = quizManager.askQuestion(activeHints); 

            if (scoreStatus == 2 || scoreStatus == -2) {
                if (isWhiteTurn) whiteHints--;
                else blackHints--;
            }

            if (scoreStatus > 0) { 
                updatePlayerScore(pointsAwarded); 
            } else { 
                updatePlayerScore(-5); 
                isPieceSelected = false;
                switchTurn(); 
                return; 
            }

            if (targetPiece.equals("♔") || targetPiece.equals("♚")) {
                squares[clickedRow][clickedCol].setText(movingPiece);
                squares[selectedRow][selectedCol].setText("");
                
                quizManager.triggerVictoryConfetti();
                
                Timer transitionTimer = new Timer(2500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ((Timer)e.getSource()).stop();
                        showVictoryScreen(); 
                    }
                });
                transitionTimer.setRepeats(false);
                transitionTimer.start();
                return;
            }

            squares[clickedRow][clickedCol].setText(movingPiece);
            squares[selectedRow][selectedCol].setText("");
            isPieceSelected = false;
            switchTurn();
        }
    }

    private int getPieceBountyValue(String piece) {
        switch (piece) {
            case "♙": case "♟": return 10;
            case "♘": case "♞":
            case "♗": case "♝": return 30;
            case "♖": case "♜": return 50;
            case "♕": case "♛": return 90;
            case "♔": case "♚": return 100;
            default: return 10;
        }
    }

    private void updatePlayerScore(int points) {
        if (isWhiteTurn) {
            whiteScore += points;
            if (whiteScore < 0) whiteScore = 0;
            whiteScoreLabel.setText(whitePlayer + " (W): " + whiteScore + " pts (💡 " + whiteHints + " Left)");
        } else {
            blackScore += points;
            if (blackScore < 0) blackScore = 0;
            blackScoreLabel.setText(blackPlayer + " (B): " + blackScore + " pts (💡 " + blackHints + " Left)");
        }
    }

    private void showVictoryScreen() {
        String winnerName = isWhiteTurn ? whitePlayer : blackPlayer;
        int winnerScore = isWhiteTurn ? whiteScore : blackScore;
        int hintsSaved = isWhiteTurn ? whiteHints : blackHints;
        String rankTitle;

        if (winnerScore >= 200 && hintsSaved >= 3) {
            rankTitle = "🧠 NEURO-GRANDMASTER 👑";
        } else if (winnerScore >= 100) {
            rankTitle = "⚔️ Tactical Elite Chess-Master ⚔️";
        } else {
            rankTitle = "🎉 Strategic Victor 🎉";
        }

        javax.swing.JDialog victoryDialog = new javax.swing.JDialog((java.awt.Frame) null, "🏆 Champions Circle 🏆", true);
        victoryDialog.setLayout(new BorderLayout());
        victoryDialog.setSize(500, 360);
        victoryDialog.setLocationRelativeTo(this);
        victoryDialog.getContentPane().setBackground(PANEL_BG);

        JLabel headerLabel = new JLabel("✨ VICTORY DECLARED ✨", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerLabel.setBackground(WOOD_DARK);
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setOpaque(true);
        headerLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        victoryDialog.add(headerLabel, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        statsPanel.setBackground(PANEL_BG);
        statsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel winnerLabel = new JLabel("🏆 Winner: " + winnerName.toUpperCase(), SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        winnerLabel.setForeground(Color.YELLOW);
        statsPanel.add(winnerLabel);

        JLabel rankLabel = new JLabel("Rank: " + rankTitle, SwingConstants.CENTER);
        rankLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        rankLabel.setForeground(CREAM_LIGHT);
        statsPanel.add(rankLabel);

        JLabel scoreLabel = new JLabel("📈 Final Score: " + winnerScore + " Points", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        scoreLabel.setForeground(Color.WHITE);
        statsPanel.add(scoreLabel);

        JLabel hintsLabel = new JLabel("💡 Hints Preserved: " + hintsSaved + " / 5 Unused", SwingConstants.CENTER);
        hintsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        hintsLabel.setForeground(new Color(144, 238, 144));
        statsPanel.add(hintsLabel);

        victoryDialog.add(statsPanel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Claim Reward & Close Game");
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        closeButton.setBackground(WOOD_DARK);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> System.exit(0));
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 25, 20, 25));
        bottomPanel.setBackground(PANEL_BG);
        bottomPanel.add(closeButton, BorderLayout.CENTER);
        victoryDialog.add(bottomPanel, BorderLayout.SOUTH);

        victoryDialog.setVisible(true);
    }

    private void showValidMoves(String piece, int startR, int startC) {
        boolean isMovingWhite = "♙♖♘♗♕♔".contains(piece);

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (r == startR && c == startC) continue;
                String targetPiece = squares[r][c].getText();
                boolean isCapture = !targetPiece.equals("");

                if (isCapture) {
                    boolean isTargetWhite = "♙♖♘♗♕♔".contains(targetPiece);
                    if (isMovingWhite == isTargetWhite) continue;
                }

                if (isValidChessMove(piece, startR, startC, r, c, isCapture)) {
                    squares[r][c].setBackground(new Color(144, 238, 144)); 
                }
            }
        }
    }

    private boolean isValidChessMove(String piece, int startR, int startC, int endR, int endC, boolean isCapture) {
        int rowDiff = Math.abs(endR - startR);
        int colDiff = Math.abs(endC - startC);

        switch (piece) {
            case "♙":
                if (!isCapture && colDiff == 0 && startR - endR == 1) return true; 
                if (!isCapture && colDiff == 0 && startR == 6 && startR - endR == 2 && squares[5][startC].getText().equals("")) return true; 
                if (isCapture && colDiff == 1 && startR - endR == 1) return true; 
                return false;
            case "♟":
                if (!isCapture && colDiff == 0 && endR - startR == 1) return true; 
                if (!isCapture && colDiff == 0 && startR == 1 && endR - startR == 2 && squares[2][startC].getText().equals("")) return true; 
                if (isCapture && colDiff == 1 && endR - startR == 1) return true; 
                return false;
            case "♖": case "♜": return (startR == endR || startC == endC);
            case "♗": case "♝": return (rowDiff == colDiff);
            case "♕": case "♛": return (startR == endR || startC == endC || rowDiff == colDiff);
            case "♔": case "♚": return (rowDiff <= 1 && colDiff <= 1);
            case "♘": case "♞": return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
        }
        return false;
    }

    private void switchTurn() {
        isWhiteTurn = !isWhiteTurn;
        
        whiteScoreLabel.setText(whitePlayer + " (W): " + whiteScore + " pts (💡 " + whiteHints + " Left)");
        blackScoreLabel.setText(blackPlayer + " (B): " + blackScore + " pts (💡 " + blackHints + " Left)");

        if (isWhiteTurn) {
            statusLabel.setText(whitePlayer + "'s Turn (White) - Select a piece");
        } else {
            statusLabel.setText(blackPlayer + "'s Turn (Black) - Select a piece");
        }
    }

    private void clearHighlights() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if ((r + c) % 2 == 0) {
                    squares[r][c].setBackground(CREAM_LIGHT);
                } else {
                    squares[r][c].setBackground(WOOD_DARK);
                }
            }
        }
    }
}