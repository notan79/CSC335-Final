package model;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * A GUI for the Palace Card Game with proper player control and draw pile mechanics
 */
public class PalaceGameGUI extends JFrame {
    
    
    // Game components
    private Rules game;
    
    // UI components
    private JPanel mainPanel;
    private JPanel[] playerPanels;
    private JPanel pilePanel;
    private JLabel pileLabel;
    private JButton takeAllButton;
    private JLabel statusLabel;
    private JLabel deckLabel;
    
    // Game state
    private int currentPlayerIndex = 0;

    private Strategy RS = new RandomStrategy();
    private Strategy BS = new BestStrategy();
    
    /**
     * Constructor - sets up the game and UI
     */
    public PalaceGameGUI() {
        // Create a new game
        this.game = new Rules();
        
        // Set up the frame
        this.setTitle("Palace Card Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        
        // Create the main panel with BorderLayout
        this.mainPanel = new JPanel(new BorderLayout());
        
        // Create player panels (4 players)
        this.playerPanels = new JPanel[4];
        for (int i = 0; i < 4; i++) {
            this.playerPanels[i] = this.createPlayerPanel(i);
        }
        
        // Position player panels around the main panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(this.playerPanels[2], BorderLayout.CENTER);
        northPanel.setBorder(BorderFactory.createTitledBorder("Player 3"));
        
        // EAST
        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.add(this.playerPanels[1], BorderLayout.CENTER);
        eastPanel.setBorder(BorderFactory.createTitledBorder("Player 2"));
        
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(this.playerPanels[0], BorderLayout.CENTER);
        southPanel.setBorder(BorderFactory.createTitledBorder("Player 1 (You)"));
        
        // WEST
        JPanel westPanel = new JPanel(new BorderLayout());
        westPanel.add(this.playerPanels[3], BorderLayout.CENTER);
        westPanel.setBorder(BorderFactory.createTitledBorder("Player 4"));

        
        this.mainPanel.add(northPanel, BorderLayout.NORTH);
        this.mainPanel.add(eastPanel, BorderLayout.EAST);
        this.mainPanel.add(southPanel, BorderLayout.SOUTH);
        this.mainPanel.add(westPanel, BorderLayout.WEST);
        
        // Create the center panel for the pile and controls
        this.createCenterPanel();
        
        // Add the main panel to the frame
        this.getContentPane().add(this.mainPanel);
        
        // Update the UI with initial game state
        this.updateUI();
    }
    
    /**
     * Creates the center panel with pile and controls
     */
    private void createCenterPanel() {
        this.pilePanel = new JPanel(new BorderLayout());
        
        // Create pile display
        this.pileLabel = new JLabel("", JLabel.CENTER);
        this.pileLabel.setFont(new Font("Arial", Font.BOLD, 24));
        this.updatePileLabel();
        
        // Create deck indicator
        this.deckLabel = new JLabel("Deck: Cards Remaining", JLabel.CENTER);
        this.deckLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Create buttons
        JPanel buttonPanel = new JPanel();
        
        // Take all button
        this.takeAllButton = new JButton("Take All Cards");
        this.takeAllButton.addActionListener(e -> {
            this.game.takeAll();
            this.updateUI();
            this.nextTurn();
        });
        buttonPanel.add(this.takeAllButton);
        
        
        // Create status label
        this.statusLabel = new JLabel("Game Started! Player 1's Turn", JLabel.CENTER);
        this.statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Create rules reminder label
        JLabel rulesLabel = new JLabel("<html>Rules: You must either play a valid card or take all cards from the pile. Draw a card after playing.</html>", JLabel.CENTER);
        rulesLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        
        // Create a panel for rules and buttons
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.CENTER);
        southPanel.add(rulesLabel, BorderLayout.SOUTH);
        
        // Add deck label to top of center panel
        JPanel centerContent = new JPanel(new BorderLayout());
        centerContent.add(this.deckLabel, BorderLayout.NORTH);
        centerContent.add(this.pileLabel, BorderLayout.CENTER);
        
        // Put everything in the pile panel
        this.pilePanel.add(centerContent, BorderLayout.CENTER);
        this.pilePanel.add(southPanel, BorderLayout.SOUTH);
        this.pilePanel.add(this.statusLabel, BorderLayout.NORTH);
        this.pilePanel.setBorder(BorderFactory.createTitledBorder("Pile"));
        
        // Add pile panel to the center of the main panel
        this.mainPanel.add(this.pilePanel, BorderLayout.CENTER);
    }
    
    /**
     * Creates a panel to display a player's cards
     */
    private JPanel createPlayerPanel(int playerIndex) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        
        // Main hand section
        JPanel mainHandPanel = new JPanel(new FlowLayout());
        mainHandPanel.setBorder(BorderFactory.createTitledBorder("Main Hand"));
        
        // Face up hand section
        JPanel faceUpPanel = new JPanel(new FlowLayout());
        faceUpPanel.setBorder(BorderFactory.createTitledBorder("Face Up"));
        
        // Face down hand section
        JPanel faceDownPanel = new JPanel(new FlowLayout());
        faceDownPanel.setBorder(BorderFactory.createTitledBorder("Face Down"));
        
        // Add all sections to the player panel
        panel.add(mainHandPanel);
        panel.add(faceUpPanel);
        panel.add(faceDownPanel);
        
        return panel;
    }
    
    /**
     * Updates all UI elements to reflect current game state
     */
    private void updateUI() {
        for (int i = 0; i < 4; i++) {
            this.updatePlayerPanel(i);
        }
        
        this.updatePileLabel();
        this.updateDeckLabel();
        
        // Check if it's Player 1's turn
        boolean isPlayerTurn = this.currentPlayerIndex == 0;
        
        // Enable/disable controls based on whose turn it is
        this.takeAllButton.setEnabled(isPlayerTurn);
        
        // Update status message
        this.statusLabel.setText("Player " + (this.currentPlayerIndex + 1) + "'s turn");
        
        // Update the window title to show whose turn it is
        this.setTitle("Palace Card Game - Player " + (this.currentPlayerIndex + 1) + "'s Turn");
        
        // If it's the player's turn, check if there's a valid move
        if (isPlayerTurn && !this.hasValidMove()) {
            this.statusLabel.setText("No valid moves! You must take all cards from the pile.");
            // Highlight the take all button if there's no valid move
            this.takeAllButton.setBackground(Color.YELLOW);
        } else {
            this.takeAllButton.setBackground(null);
        }
    }
    
    /**
     * Updates the deck label to show remaining cards
     */
    private void updateDeckLabel() {
        boolean isDeckEmpty = this.isDeckEmpty();
        if (!isDeckEmpty) {
            this.deckLabel.setText("Deck: Cards Available");
        } else {
            this.deckLabel.setText("Deck: Empty");
        }
    }
    
    /**
     * Checks if the deck is empty
     */
    private boolean isDeckEmpty() {
        return this.game.isDeckEmpty();
    }
    
    /**
     * Checks if the current player has any valid moves
     */
    private boolean hasValidMove() {
        return this.game.hasValidMove();
    }
    
    /**
     * Updates a player's panel with their current cards
     */
    private void updatePlayerPanel(int playerIndex) {
        JPanel playerPanel = this.playerPanels[playerIndex];
        playerPanel.removeAll();
        
        playerPanel.setLayout(new GridLayout(3, 1));
        
        
        // Create each hand section
        ArrayList<Card> mainHand = this.getMainHand(playerIndex);
        ArrayList<Card> faceUpHand = this.getFaceUpHand(playerIndex);
        ArrayList<Card> faceDownHand = this.getFaceDownHand(playerIndex);
        
        // Create panels for the sections
        JPanel mainHandPanel, faceUpPanel, faceDownPanel;
        
        // Only create clickable panels for Player 1
        if (playerIndex == 0) {
            mainHandPanel = this.createHandSection(mainHand, "Main Hand", playerIndex);
            faceUpPanel = this.createHandSection(faceUpHand, "Face Up", playerIndex);
            faceDownPanel = this.createFaceDownSection(faceDownHand, playerIndex);
        } else {
            // For AI players, create non-interactive displays
            mainHandPanel = this.createAIHandDisplay(mainHand, "Main Hand");
            faceUpPanel = this.createAIHandDisplay(faceUpHand, "Face Up");
            faceDownPanel = this.createAIFaceDownDisplay(faceDownHand);
        }
        
        // Add sections to player panel
        playerPanel.add(mainHandPanel);
        playerPanel.add(faceUpPanel);
        playerPanel.add(faceDownPanel);
        
        // Refresh the panel
        playerPanel.revalidate();
        playerPanel.repaint();
    }
    
    /**
     * Creates a display panel for AI player cards (not interactive)
     */
    private JPanel createAIHandDisplay(ArrayList<Card> cards, String title) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        
        for (Card card : cards) {
            JLabel cardLabel = this.createCardLabel(card, title);
            panel.add(cardLabel);
        }
        
        return panel;
    }
    
    /**
     * Creates a display panel for AI player face down cards
     */
    private JPanel createAIFaceDownDisplay(ArrayList<Card> cards) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Face Down"));
        
        for (int i = 0; i < cards.size(); i++) {
            JLabel cardLabel = new JLabel("?");
            cardLabel.setPreferredSize(new Dimension(40, 60));
            cardLabel.setHorizontalAlignment(JLabel.CENTER);
            cardLabel.setVerticalAlignment(JLabel.CENTER);
            cardLabel.setBackground(Color.LIGHT_GRAY);
            cardLabel.setOpaque(true);
            cardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            
            panel.add(cardLabel);
        }
        
        return panel;
    }
    
    /**
     * Creates a label to display a card (for AI players)
     */
    private JLabel createCardLabel(Card card, String title) {
        String displayRank = this.getDisplayRank(card.rank);
        String suitSymbol = this.getSuitSymbol(card.suit);
        String cardText = displayRank + suitSymbol;
        
        if(title.equals("Main Hand"))
            cardText = "?";
        JLabel cardLabel = new JLabel(cardText);
        cardLabel.setPreferredSize(new Dimension(40, 60));
        cardLabel.setHorizontalAlignment(JLabel.CENTER);
        cardLabel.setVerticalAlignment(JLabel.CENTER);
        cardLabel.setBackground(Color.WHITE);
        cardLabel.setOpaque(true);
        cardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        // Color red or black based on suit
        if(title.equals("Main Hand"))
            return cardLabel;
        String suitString = card.suit.toString();
        if (suitString.equals("♥") || suitString.equals("♦")) {
            cardLabel.setForeground(Color.RED);
        } else {
            cardLabel.setForeground(Color.BLACK);
        }
        
        return cardLabel;
    }
    
    /**
     * Creates a section for displaying cards (main hand or face up) - only for Player 1
     */
    private JPanel createHandSection(ArrayList<Card> cards, String title, int playerIndex) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        
        boolean isMainHand = title.equals("Main Hand");
        boolean isFaceUp = title.equals("Face Up");
        
        // Determine if this player can play cards from this section
        boolean canPlayFromThisSection = (playerIndex == this.currentPlayerIndex) && 
                                        (isMainHand || 
                                        (isFaceUp && this.getMainHand(playerIndex).isEmpty()));
        
        // Add card buttons
        for (int i = 0; i < cards.size(); i++) {
            final Card card = cards.get(i);
            
            // Get the play index for this card
            final int playIndex = this.getPlayIndex(playerIndex, title, i);
            
            // Only enable valid moves
            boolean isValidMove = canPlayFromThisSection && this.game.isValidMove(card);
            
            JButton cardButton = this.createCardButton(card, isValidMove);
            
            if (isValidMove) {
                cardButton.addActionListener(e -> {
                    
                    // Call the game logic
                    boolean success = this.game.playCard(playIndex);
                    
                    if (success) {
                        this.updateUI();
                    
                        // Automatically draw a card if deck isn't empty
                        if (!this.isDeckEmpty() && this.game.getMainHand().size() < 5) {
                            this.game.takeCard();
                        }
                    
                        this.updateUI();
                        this.nextTurn();
                    }
                     else {
                        JOptionPane.showMessageDialog(null, "Invalid move!");
                    }
                });
            }
            
            panel.add(cardButton);
        }
        
        return panel;
    }
    
    /**
     * Creates a section for displaying face down cards - only for Player 1
     */
    private JPanel createFaceDownSection(ArrayList<Card> cards, int playerIndex) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Face Down"));
        
        // Check if player can play from face down section
        boolean canPlayFromFaceDown = (playerIndex == this.currentPlayerIndex) && 
                                     this.getMainHand(playerIndex).isEmpty() && 
                                     this.getFaceUpHand(playerIndex).isEmpty();
        
        // Add card buttons
        for (int i = 0; i < cards.size(); i++) {
            final int playIndex = this.getPlayIndex(playerIndex, "Face Down", i);
            
            JButton cardButton = new JButton("?");
            cardButton.setPreferredSize(new Dimension(50, 75));
            cardButton.setBackground(Color.LIGHT_GRAY);
            cardButton.setEnabled(canPlayFromFaceDown);
            
            if (canPlayFromFaceDown) {
                cardButton.addActionListener(e -> {                    
                    // Always allow attempt to play face down card
                    boolean success = this.game.playCard(playIndex);
                    
                    if (!success) {
                        JOptionPane.showMessageDialog(null, "It was an invalid face down card. You must pick up the pile!");
                        //this.game.takeAll();
                    }
            
                    this.updateUI();
                    this.nextTurn();
                });
            }
            
            panel.add(cardButton);
        }
        
        return panel;
    }
    
    /**
     * Calculate the index to use when playing a card
     */
    private int getPlayIndex(int playerIndex, String handType, int cardIndex) {
        if (handType.equals("Main Hand")) {
            return cardIndex;
        } else if (handType.equals("Face Up")) {
            return this.getMainHand(playerIndex).size() + cardIndex;
        } else { // Face Down
            return this.getMainHand(playerIndex).size() + this.getFaceUpHand(playerIndex).size() + cardIndex;
        }
    }
    
    /**
     * Creates a button representing a card
     */
    private JButton createCardButton(Card card, boolean enabled) {
        String displayRank = this.getDisplayRank(card.rank);
        String suitSymbol = this.getSuitSymbol(card.suit);
        String cardText = displayRank + suitSymbol;
        
        JButton cardButton = new JButton(cardText);
        cardButton.setPreferredSize(new Dimension(50, 75));
        
        // Color red or black based on suit
        String suitString = card.suit.toString();
        if (suitString.equals("♥") || suitString.equals("♦")) {
            cardButton.setForeground(Color.RED);
        } else {
            cardButton.setForeground(Color.BLACK);
        }
        
        cardButton.setEnabled(enabled);
        
        // Add tooltip to show the full card details for debugging
        cardButton.setToolTipText("Rank: " + card.rank + " (ordinal: " + card.rank.ordinal() + ")");
        
        return cardButton;
    }
    
    /**
     * Gets a display-friendly representation of a card rank
     */
    private String getDisplayRank(Rank rank) {
        if (rank == Rank.TEN) {
            return "10"; // Make sure 10 is displayed as "10"
        } else {
            return rank.toString();
        }
    }
    
    /**
     * Returns the Unicode symbol for a suit
     */
    private String getSuitSymbol(Suit suit) {
        // Just return the suit's toString value, assuming it returns the symbol
        return suit.toString();
    }
    
    /**
     * Updates the pile label to show the top card
     */
    private void updatePileLabel() {
        Card topCard = this.game.viewTopCard();
        if (topCard != null) {
            String displayRank = this.getDisplayRank(topCard.rank);
            String suitSymbol = this.getSuitSymbol(topCard.suit);
            this.pileLabel.setText(displayRank + suitSymbol);
            
            // Color red or black based on suit
            String suitString = topCard.suit.toString();
            if (suitString.equals("♥") || suitString.equals("♦")) {
                this.pileLabel.setForeground(Color.RED);
            } else {
                this.pileLabel.setForeground(Color.BLACK);
            }
        } else {
            this.pileLabel.setText("Empty");
            this.pileLabel.setForeground(Color.BLACK);
        }
    }
    
    /**
     * Get the main hand for a player index using reflection
     */
    private ArrayList<Card> getMainHand(int playerIndex) {
        return this.game.getMainHand(playerIndex);
    }
    
    /**
     * Get the face up hand for a player index using reflection
     */
    private ArrayList<Card> getFaceUpHand(int playerIndex) {
        return this.game.getFaceUpHand(playerIndex);
    }
    
    /**
     * Get the face down hand for a player index using reflection
     */
    private ArrayList<Card> getFaceDownHand(int playerIndex) {
        return this.game.getFaceDownHand(playerIndex);
    }
    
    
    /**
     * Gets the current player index from the game
     */
    private int getCurrentPlayerFromGame() {
        return this.game.getTurn();
    }
    
    /**
     * Advances to the next player's turn
     */
    private void nextTurn() {
        int winner = this.currentPlayerIndex;
        boolean gameContinues = this.game.nextTurn();
        if (!gameContinues) {
            // Find winner
            // int winner = (this.game.getTurn() + 3) % 4;
            
            JOptionPane.showMessageDialog(this, "Game Over! Player " + (winner + 1) + " wins!");
                
            return;
        }
        
        // Update current player index based on game's turn
        this.currentPlayerIndex = this.getCurrentPlayerFromGame();
        this.updateUI();
        
        // If AI player's turn, make a move
        if (this.currentPlayerIndex != 0) {
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(1500); // Add a delay for AI turn
                    this.simulateAITurn();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }
    
    /**
 * Makes a move for an AI player using the RandomStrategy
 */
    private void simulateAITurn() {
        ArrayList<Card> mainHand = this.getMainHand(this.currentPlayerIndex);
        ArrayList<Card> faceUpHand = this.getFaceUpHand(this.currentPlayerIndex);
        ArrayList<Card> faceDownHand = this.getFaceDownHand(this.currentPlayerIndex);

        boolean hasMainCards = !mainHand.isEmpty();
        boolean hasFaceUpCards = !faceUpHand.isEmpty();

        int cardIndex;
        if(this.currentPlayerIndex == 2)
            cardIndex = this.RS.whatCardToPlay(mainHand, faceUpHand, faceDownHand, this.game.viewTopCard(), hasMainCards, hasFaceUpCards);
        else 
            cardIndex = this.BS.whatCardToPlay(mainHand, faceUpHand, faceDownHand, this.game.viewTopCard(), hasMainCards, hasFaceUpCards);

        boolean moveMade = false;

        if (cardIndex == -1) {
            this.game.takeAll();
        } else {
            moveMade = this.game.playCard(cardIndex);
            if (!moveMade) {
                // If invalid face-down card or unexpected issue, take all
                this.game.takeAll();
            }
        }

        // If card was played successfully and main hand is under 5, draw a card
        if (moveMade && !this.isDeckEmpty() && this.getMainHand(this.currentPlayerIndex).size() < 5) {
            this.game.takeCard();
        }

        this.updateUI();
        this.nextTurn();
    }

    
    /**
     * Main method to run the application
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new PalaceGameGUI().setVisible(true);
        });
    }

    
}