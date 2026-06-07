package neurochess;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class QuizManager {
    private ArrayList<Question> questionPool;
    private ArrayList<Question> usedQuestions;
    
    private int timeLeft;
    private Timer countdownTimer;
    private int scoreCode; 
    private boolean isQuizResolved;
    private boolean hintUsedThisTurn;

    public QuizManager() {
        questionPool = new ArrayList<>();
        usedQuestions = new ArrayList<>();
        
        // =========================================================================
        // CATEGORY 1: Paradoxes & Perspective Shifts
        // =========================================================================
        questionPool.add(new Question("A prisoner is forced to choose between three rooms. The first is full of raging fires. The second is full of assassins"
        		+ " with loaded guns. The third is full of lions that haven't eaten in three years. Which room is safest?", "Third"));
        questionPool.add(new Question("If you have a drawer with 10 black socks and 10 white socks, what is the absolute minimum number of socks you"
        		+ " must pull out in the dark to guarantee you have a matching pair?","3"));
        questionPool.add(new Question("I am a 3-digit number. My 2nd digit is 4 times the 3rd. My 1st is 3 less than the 2nd. Number?", "141"));
        questionPool.add(new Question("If 5 machines make 5 widgets in 5 minutes, how many minutes for 100 machines to make 100?", "5"));

        // =========================================================================
        // CATEGORY 2: FUN & EASY GENERAL KNOWLEDGE RIDDLES
        // =========================================================================
        questionPool.add(new Question("What gets wetter and wetter the more it dries something else?", "Towel"));
        questionPool.add(new Question("What can you catch easily but can never throw back?", "Cold"));
        questionPool.add(new Question("If you feed me I grow, but if you give me water I die. What am I?", "Fire"));
        questionPool.add(new Question("If you hurt me, I will make you cry. What am I?","Onion"));
        questionPool.add(new Question("What has hands but cannot clap, and numbers but cannot count?", "Clock"));
        questionPool.add(new Question("What disappears the second you say its name?", "Silence"));
        questionPool.add(new Question("I have cities but no houses, and water but no fish. What am I?", "Map"));
        questionPool.add(new Question("What word contains all twenty-six letters but only has eight letters?", "Alphabet"));
        questionPool.add(new Question("What single digit number goes up in value when turned upside down?", "6"));
        questionPool.add(new Question("What goes up but never comes back down?", "Age"));
        questionPool.add(new Question("What single word becomes shorter when you add two letters to it?", "Short"));
        questionPool.add(new Question("What can hold water despite being completely full of holes?", "Sponge"));
        questionPool.add(new Question("What has a head and a tail but does not have a body?", "Coin"));
        questionPool.add(new Question("What belongs to you but is used much more by others?", "Name"));
        questionPool.add(new Question("What building has the most stories and tales inside it?", "Library"));

        // =========================================================================
        // CATEGORY 3: BASIC MEDICAL & HEALTH
        // =========================================================================
        questionPool.add(new Question("Which organ pumps blood throughout the human body?", "Heart"));
        questionPool.add(new Question("What is the name of the main gas that humans breathe in to survive?", "Oxygen"));
        questionPool.add(new Question("How many primary bones does an adult human skeleton have?", "206"));
        questionPool.add(new Question("What basic tool does a doctor use to listen to your heartbeat?", "Stethoscope"));
        questionPool.add(new Question("What is the outermost protective layer of the human body called?", "Skin"));
        questionPool.add(new Question("Which type of blood cells fight off infections and illnesses?", "White blood cells"));
        questionPool.add(new Question("Which popular vitamin is famously obtained from sitting under sunlight?", "Vitamin D"));
        questionPool.add(new Question("What basic substance makes up about seventy percent of the human body?", "Water"));
        questionPool.add(new Question("What is the common name for the hard structure that protects your brain?", "Skull"));
        questionPool.add(new Question("Which primary organs are responsible for helping you breathe air?", "Lungs"));

        // =========================================================================
        // CATEGORY 4: EASY SPORTS TRIVIA
        // =========================================================================
        questionPool.add(new Question("How many total players are on the field for one soccer team?", "11"));
        questionPool.add(new Question("Which global sports tournament event is held once every four years?", "Olympics"));
        questionPool.add(new Question("In which sport do players use a racket to hit a shuttlecock?", "Badminton"));
        questionPool.add(new Question("What sport uses the terms strike, spare, and pins?", "Bowling"));
        questionPool.add(new Question("How many total bails are placed on top of cricket wickets?", "2"));
        questionPool.add(new Question("What color is the standard ball used in a game of tennis?", "Yellow"));
        questionPool.add(new Question("Which popular sport is played on an ice rink using a rubber puck?", "Ice Hockey"));
        questionPool.add(new Question("What is the maximum number of points you can score with a single shot in basketball?", "3"));
        questionPool.add(new Question("In which board game do players move pieces like Knights and Bishops?", "Chess"));
        questionPool.add(new Question("What is the only sport played on the moon by an astronaut?", "Golf"));

        // =========================================================================
        // CATEGORY 5: LIGHT IQ & LOGICAL PATTERN QUESTIONS
        // =========================================================================
        questionPool.add(new Question("Complete the pattern sequence: 2, 4, 8, 16, ...?", "32"));
        questionPool.add(new Question("Complete the pattern sequence: 5, 10, 15, 20, ...?", "25"));
        questionPool.add(new Question("Complete the sequence: 100, 90, 80, 70, ...?", "60"));
        questionPool.add(new Question("Which number is missing from the logical sequence: 1, 3, 5, 7, ...?", "9"));
        questionPool.add(new Question("If two shirts take 2 hours to dry in the sun, how many hours do 4 shirts take to dry?", "2"));
        questionPool.add(new Question("What number comes next in the counting sequence: 11, 22, 33, 44, ...?", "55"));
        questionPool.add(new Question("Some months have 30 days, some have 31 days. How many months have 28 days?", "12"));
        questionPool.add(new Question("If you look at a mirror and touch your right ear, which ear does your reflection touch?", "Left"));
        if (questionPool.size() > 0) {
            questionPool.add(new Question("If a train is moving South, which direction is the smoke blowing if it is electric?", "None"));
        }
        questionPool.add(new Question("Mary's father has 5 daughters: Nana, Nene, Nini, Nono. Who is the fifth?", "Mary"));
        questionPool.add(new Question("Unscramble the word 'ATC' to find a common domestic pet animal.", "Cat"));
        questionPool.add(new Question("Unscramble the word 'NIGAMR.'The edge or border of something.","MARGIN"));
        Collections.shuffle(questionPool);
    }

    public void launchPartyPopper() {
        JDialog popper = new JDialog((Frame) null, false);
        popper.setUndecorated(true);
        popper.setBackground(new Color(0, 0, 0, 0)); 
        popper.setSize(800, 600);
        popper.setLocationRelativeTo(null);
        popper.setAlwaysOnTop(true);

        ArrayList<double[]> particles = new ArrayList<>();
        java.util.Random rand = new java.util.Random();
        Color[] colors = {Color.PINK, Color.CYAN, Color.YELLOW, Color.MAGENTA, Color.ORANGE, Color.GREEN};

        for (int i = 0; i < 120; i++) {
            double angle = rand.nextDouble() * 2 * Math.PI;
            double speed = 4 + rand.nextDouble() * 11;
            particles.add(new double[]{
                400, 260,                   
                Math.cos(angle) * speed,    
                Math.sin(angle) * speed,    
                rand.nextInt(colors.length),
                6 + rand.nextInt(7),        
                1.0                         
            });
        }

        JPanel canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                boolean stillAlive = false;
                for (double[] p : particles) {
                    if (p[6] > 0) {
                        stillAlive = true;
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)p[6]));
                        g2d.setColor(colors[(int)p[4]]);
                        g2d.fillRect((int)p[0], (int)p[1], (int)p[5], (int)p[5]);
                    }
                }
                if (!stillAlive) { 
                    popper.dispose(); 
                }
            }
        };
        canvas.setOpaque(false);
        popper.add(canvas);

        Timer animTimer = new Timer(16, null);
        animTimer.addActionListener(e -> {
            for (double[] p : particles) {
                p[0] += p[2]; 
                p[1] += p[3]; 
                p[3] += 0.28; 
                p[2] *= 0.97; 
                p[6] -= 0.018; 
            }
            canvas.repaint();
            if (!popper.isDisplayable()) { 
                animTimer.stop(); 
            }
        });

        popper.setVisible(true);
        animTimer.start();
    }

    // ⚡ INSTANT THUMBS DOWN RAIN ANIMATION (YELLOW COLOR EDITION) ⚡
    public void launchThumbsDownRain() {
        JDialog rainDialog = new JDialog((Frame) null, false);
        rainDialog.setUndecorated(true);
        rainDialog.setBackground(new Color(0, 0, 0, 0)); 
        rainDialog.setSize(800, 600);
        rainDialog.setLocationRelativeTo(null);
        rainDialog.setAlwaysOnTop(true);

        ArrayList<double[]> thumbs = new ArrayList<>();
        java.util.Random rand = new java.util.Random();

        for (int i = 0; i < 45; i++) {
            thumbs.add(new double[]{
                rand.nextInt(800),          
                -30 - rand.nextInt(150), 
                4.0 + rand.nextDouble() * 6, // Snappy vertical speed movement
                16 + rand.nextInt(18),      
                1.0                         
            });
        }

        JPanel rainCanvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                boolean clearScreen = true;
                for (double[] t : thumbs) {
                    if (t[4] > 0 && t[1] < 600) {
                        clearScreen = false;
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)t[4]));
                        g2d.setColor(new Color(255, 215, 0)); // Yellow Emojis Theme Color
                        g2d.setFont(new Font("Segoe UI Emoji", Font.PLAIN, (int)t[3]));
                        g2d.drawString("👎", (int)t[0], (int)t[1]);
                    }
                }
                if (clearScreen) {
                    rainDialog.dispose();
                }
            }
        };
        rainCanvas.setOpaque(false);
        rainDialog.add(rainCanvas);

        Timer rainTimer = new Timer(16, null);
        rainTimer.addActionListener(e -> {
            for (double[] t : thumbs) {
                t[1] += t[2]; 
                if (t[1] > 350) {
                    t[4] -= 0.08; // Fast opacity fade decay out
                    if (t[4] < 0) t[4] = 0;
                }
            }
            rainCanvas.repaint();
            if (!rainDialog.isDisplayable()) {
                rainTimer.stop();
            }
        });

        rainDialog.setVisible(true);
        rainTimer.start();
    }

    public void triggerVictoryConfetti() {
        launchPartyPopper();
        Timer multiBurst = new Timer(300, new ActionListener() {
            int bursts = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                launchPartyPopper();
                bursts++;
                if (bursts >= 3) {
                    ((Timer)e.getSource()).stop();
                }
            }
        });
        multiBurst.start();
    }

    public int askQuestion(int playerHintsRemaining) {
        if (questionPool.isEmpty()) {
            questionPool.addAll(usedQuestions);
            usedQuestions.clear();
            Collections.shuffle(questionPool);
        }

        Question currentQuestion = questionPool.remove(0);
        usedQuestions.add(currentQuestion);

        scoreCode = -1; 
        timeLeft = 60;  
        isQuizResolved = false;
        hintUsedThisTurn = false;

        JDialog quizDialog = new JDialog((Frame) null, "NeuroChess Challenge Mode!", true);
        quizDialog.setLayout(new BorderLayout());
        quizDialog.setSize(540, 360); 
        quizDialog.setLocationRelativeTo(null);
        quizDialog.getContentPane().setBackground(new Color(35, 35, 45)); 

        JPanel timerHeaderPanel = new JPanel(new BorderLayout(10, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color dynamicAlertColor = (timeLeft > 20) ? new Color(46, 139, 87) : new Color(180, 40, 40);
                g2d.setColor(dynamicAlertColor);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        timerHeaderPanel.setPreferredSize(new Dimension(540, 55));
        timerHeaderPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 15, 5, 15));

        JLabel timerLabel = new JLabel("Time Remaining: 60 seconds", SwingConstants.LEFT);
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        timerLabel.setForeground(Color.WHITE);

        JPanel clockGraphicComponent = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int diameter = Math.min(getWidth(), getHeight()) - 6;
                int x = (getWidth() - diameter) / 2;
                int y = (getHeight() - diameter) / 2;

                g2d.setColor(Color.WHITE);
                g2d.fillOval(x, y, diameter, diameter);

                g2d.setColor(new Color(35, 35, 45));
                g2d.setStroke(new java.awt.BasicStroke(2.5f));
                g2d.drawOval(x, y, diameter, diameter);

                int centerPointX = x + diameter / 2;
                int centerPointY = y + diameter / 2;
                double handSweepAngleAngle = Math.toRadians((60 - timeLeft) * 6 - 90);
                
                int indicatorHandLength = diameter / 2 - 4;
                int targetTipX = centerPointX + (int) (Math.cos(handSweepAngleAngle) * indicatorHandLength);
                int targetTipY = centerPointY + (int) (Math.sin(handSweepAngleAngle) * indicatorHandLength);

                g2d.setColor((timeLeft > 20) ? new Color(46, 139, 87) : Color.RED);
                g2d.setStroke(new java.awt.BasicStroke(2.0f));
                g2d.drawLine(centerPointX, centerPointY, targetTipX, targetTipY);

                g2d.setColor(Color.DARK_GRAY);
                g2d.fillOval(centerPointX - 3, centerPointY - 3, 6, 6);
            }
        };
        clockGraphicComponent.setPreferredSize(new Dimension(45, 45));
        clockGraphicComponent.setOpaque(false);

        timerHeaderPanel.add(clockGraphicComponent, BorderLayout.WEST);
        timerHeaderPanel.add(timerLabel, BorderLayout.CENTER);
        quizDialog.add(timerHeaderPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        centerPanel.setBackground(new Color(35, 35, 45)); 
        centerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel questionLabel = new JLabel("<html><center>" + currentQuestion.getQuestionText() + "</center></html>", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        questionLabel.setForeground(new Color(240, 217, 181)); 
        centerPanel.add(questionLabel);

        JTextField answerField = new JTextField();
        answerField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        centerPanel.add(answerField);

        JPanel buttonGridPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonGridPanel.setBackground(new Color(35, 35, 45));

        String hintButtonText = "💡 Hint (" + playerHintsRemaining + " Left)";
        JButton hintButton = new JButton(hintButtonText);
        hintButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        hintButton.setBackground(new Color(70, 80, 95)); 
        hintButton.setForeground(Color.WHITE);
        hintButton.setFocusPainted(false);
        
        if (playerHintsRemaining <= 0) {
            hintButton.setEnabled(false);
            hintButton.setText("💡 No Hints Left");
        }
        buttonGridPanel.add(hintButton);

        JButton submitButton = new JButton("Submit Answer");
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        submitButton.setBackground(new Color(181, 136, 99)); 
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        buttonGridPanel.add(submitButton);

        centerPanel.add(buttonGridPanel);
        quizDialog.add(centerPanel, BorderLayout.CENTER);

        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerHintsRemaining > 0) {
                    hintUsedThisTurn = true; 
                    String answer = currentQuestion.getCorrectAnswer();
                    char firstLetter = answer.charAt(0);
                    
                    StringBuilder cluePattern = new StringBuilder();
                    cluePattern.append(firstLetter);
                    for (int i = 1; i < answer.length(); i++) {
                        if (answer.charAt(i) == ' ') {
                            cluePattern.append("   "); 
                        } else {
                            cluePattern.append("  _");
                        }
                    }

                    hintButton.setEnabled(false);
                    hintButton.setText("💡 Clue Revealed!");
                    
                    questionLabel.setText("<html><center>" + currentQuestion.getQuestionText() 
                        + "<br><font color='#FFCC00'><b>HINT: " + cluePattern.toString() + "</b></font></center></html>");
                }
            }
        });

        ActionListener submitAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isQuizResolved) return;
                isQuizResolved = true;
                countdownTimer.stop();

                String playerAnswer = answerField.getText().trim();
                String actualAnswer = currentQuestion.getCorrectAnswer();

                int distance = calculateLevenshteinDistance(playerAnswer.toLowerCase(), actualAnswer.toLowerCase());
                
                if (distance <= 2) {
                    scoreCode = hintUsedThisTurn ? 2 : 1;
                    if (distance > 0) {
                        JOptionPane.showMessageDialog(null, 
                            "⚠️ Close Match! (Typo Detected)\nThe exact answer was: " + actualAnswer, 
                            "Close Match", 
                            JOptionPane.WARNING_MESSAGE);
                    } else {
                        try { Toolkit.getDefaultToolkit().beep(); } catch(Exception ex){}
                        launchPartyPopper();

                        JOptionPane.showMessageDialog(null, 
                            "🎉🎉 SUCCESS! 🎉🎉\n\n💥 🥳 Perfectly Correct Answer! 🥳 💥\n\nYour strategic piece move has been authorized!", 
                            "Move Approved", 
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    scoreCode = hintUsedThisTurn ? -2 : 0;
                    
                    launchThumbsDownRain();

                    JOptionPane.showMessageDialog(null, 
                        "❌❌ INCORRECT ANSWER ❌❌\n\n😔 🧠 Oh no! That was not right. 🧠 😔\n\nThe correct answer was: " + actualAnswer + "\n\nYour piece has been forced to retreat!", 
                        "Move Denied", 
                        JOptionPane.ERROR_MESSAGE);
                }
                quizDialog.dispose();
            }
        };

        submitButton.addActionListener(submitAction);
        answerField.addActionListener(submitAction);

        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time Remaining: " + timeLeft + " seconds");
                clockGraphicComponent.repaint(); 

                if (timeLeft <= 0) {
                    if (!isQuizResolved) {
                        isQuizResolved = true;
                        countdownTimer.stop();
                        scoreCode = hintUsedThisTurn ? -2 : 0;
                        
                        launchThumbsDownRain();

                        JOptionPane.showMessageDialog(null, 
                            "⏰⏰ TIME OUT! ⏰⏰\n\n⏳ 😱 You ran out of time! 😱 ⏳\n\nThe answer was: " + currentQuestion.getCorrectAnswer() + "\n\nMove denied.", 
                            "Move Denied", 
                            JOptionPane.ERROR_MESSAGE);
                        quizDialog.dispose();
                    }
                }
            }
        });

        countdownTimer.start();
        quizDialog.setVisible(true);

        return scoreCode;
    }

    private int calculateLevenshteinDistance(String s1, String s2) {
        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    costs[j] = j;
                } else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                            newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
                        }
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0) costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }
}