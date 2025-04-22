package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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
    private JButton drawCardButton;
    private JLabel statusLabel;
    private JLabel deckLabel;
    
    // Game state
    private int currentPlayerIndex = 0;
    
    /**
     * Constructor - sets up the game and UI
     */
    public PalaceGameGUI() {
        // Create a new game
        game = new Rules();
        
        // Set up the frame
        setTitle("Palace Card Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        
        // Create the main panel with BorderLayout
        mainPanel = new JPanel(new BorderLayout());
        
        // Create player panels (4 players)
        playerPanels = new JPanel[4];
        for (int i = 0; i < 4; i++) {
            playerPanels[i] = createPlayerPanel(i);
        }
        
        // Position player panels around the main panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(playerPanels[2], BorderLayout.CENTER);
        northPanel.setBorder(BorderFactory.createTitledBorder("Player 3"));
        
        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.add(playerPanels[1], BorderLayout.CENTER);
        eastPanel.setBorder(BorderFactory.createTitledBorder("Player 2"));
        
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(playerPanels[0], BorderLayout.CENTER);
        southPanel.setBorder(BorderFactory.createTitledBorder("Player 1 (You)"));
        
        JPanel westPanel = new JPanel(new BorderLayout());
        westPanel.add(playerPanels[3], BorderLayout.CENTER);
        westPanel.setBorder(BorderFactory.createTitledBorder("Player 4"));
        
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(eastPanel, BorderLayout.EAST);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        mainPanel.add(westPanel, BorderLayout.WEST);
        
        // Create the center panel for the pile and controls
        createCenterPanel();
        
        // Add the main panel to the frame
        getContentPane().add(mainPanel);
        
        // Update the UI with initial game state
        updateUI();
    }
    
    /**
     * Creates the center panel with pile and controls
     */
    private void createCenterPanel() {
        pilePanel = new JPanel(new BorderLayout());
        
        // Create pile display
        pileLabel = new JLabel("", JLabel.CENTER);
        pileLabel.setFont(new Font("Arial", Font.BOLD, 24));
        updatePileLabel();
        
        // Create deck indicator
        deckLabel = new JLabel("Deck: Cards Remaining", JLabel.CENTER);
        deckLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Create buttons
        JPanel buttonPanel = new JPanel();
        
        // Take all button
        takeAllButton = new JButton("Take All Cards");
        takeAllButton.addActionListener(e -> {
            game.takeAll();
            printPileSize();
            updateUI();
            nextTurn();
        });
        buttonPanel.add(takeAllButton);
        
        // Draw card button
        drawCardButton = new JButton("Draw Card");
        drawCardButton.addActionListener(e -> {
            Card drawnCard = game.takeCard();
            if (drawnCard != null) {
                JOptionPane.showMessageDialog(this, "Drew card: " + getDisplayRank(drawnCard.rank) + getSuitSymbol(drawnCard.suit));
                updateUI();
            } else {
                JOptionPane.showMessageDialog(this, "No more cards in the deck!");
            }
        });
        buttonPanel.add(drawCardButton);
        
        // Create status label
        statusLabel = new JLabel("Game Started! Player 1's Turn", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Create rules reminder label
        JLabel rulesLabel = new JLabel("<html>Rules: You must either play a valid card or take all cards from the pile. Draw a card after playing.</html>", JLabel.CENTER);
        rulesLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        
        // Create a panel for rules and buttons
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.CENTER);
        southPanel.add(rulesLabel, BorderLayout.SOUTH);
        
        // Add deck label to top of center panel
        JPanel centerContent = new JPanel(new BorderLayout());
        centerContent.add(deckLabel, BorderLayout.NORTH);
        centerContent.add(pileLabel, BorderLayout.CENTER);
        
        // Put everything in the pile panel
        pilePanel.add(centerContent, BorderLayout.CENTER);
        pilePanel.add(southPanel, BorderLayout.SOUTH);
        pilePanel.add(statusLabel, BorderLayout.NORTH);
        pilePanel.setBorder(BorderFactory.createTitledBorder("Pile"));
        
        // Add pile panel to the center of the main panel
        mainPanel.add(pilePanel, BorderLayout.CENTER);
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
            updatePlayerPanel(i);
        }
        
        updatePileLabel();
        updateDeckLabel();
        
        // Check if it's Player 1's turn
        boolean isPlayerTurn = currentPlayerIndex == 0;
        
        // Enable/disable controls based on whose turn it is
        takeAllButton.setEnabled(isPlayerTurn);
        drawCardButton.setEnabled(isPlayerTurn);
        
        // Update status message
        statusLabel.setText("Player " + (currentPlayerIndex + 1) + "'s turn");
        
        // Update the window title to show whose turn it is
        setTitle("Palace Card Game - Player " + (currentPlayerIndex + 1) + "'s Turn");
        
        // If it's the player's turn, check if there's a valid move
        if (isPlayerTurn && !hasValidMove()) {
            statusLabel.setText("No valid moves! You must take all cards from the pile.");
            // Highlight the take all button if there's no valid move
            takeAllButton.setBackground(Color.YELLOW);
        } else {
            takeAllButton.setBackground(null);
        }
    }
    
    /**
     * Updates the deck label to show remaining cards
     */
    private void updateDeckLabel() {
        boolean isDeckEmpty = isDeckEmpty();
        if (!isDeckEmpty) {
            deckLabel.setText("Deck: Cards Available");
            drawCardButton.setEnabled(currentPlayerIndex == 0);
        } else {
            deckLabel.setText("Deck: Empty");
            drawCardButton.setEnabled(false);
        }
    }
    
    /**
     * Checks if the deck is empty
     */
    private boolean isDeckEmpty() {
        try {
            java.lang.reflect.Field deckField = Rules.class.getDeclaredField("deck");
            deckField.setAccessible(true);
            Object deck = deckField.get(game);
            
            java.lang.reflect.Method isEmptyMethod = deck.getClass().getMethod("isEmpty");
            return (boolean) isEmptyMethod.invoke(deck);
        } catch (Exception e) {
            System.err.println("Error checking if deck is empty: " + e.getMessage());
            return true; // Assume empty if there's an error
        }
    }
    
    /**
     * Checks if the current player has any valid moves
     */
    private boolean hasValidMove() {
        return game.hasValidMove();
    }
    
    /**
     * Updates a player's panel with their current cards
     */
    private void updatePlayerPanel(int playerIndex) {
        JPanel playerPanel = playerPanels[playerIndex];
        playerPanel.removeAll();
        
        playerPanel.setLayout(new GridLayout(3, 1));
        
        // Get the player's hand
        ArrayList<Hand> players = getPlayers();
        if (players == null || playerIndex >= players.size()) {
            return;
        }
        
        Hand playerHand = players.get(playerIndex);
        
        // Create each hand section
        ArrayList<Card> mainHand = getMainHand(playerIndex);
        ArrayList<Card> faceUpHand = getFaceUpHand(playerIndex);
        ArrayList<Card> faceDownHand = getFaceDownHand(playerIndex);
        
        // Create panels for the sections
        JPanel mainHandPanel, faceUpPanel, faceDownPanel;
        
        // Only create clickable panels for Player 1
        if (playerIndex == 0) {
            mainHandPanel = createHandSection(mainHand, "Main Hand", playerIndex);
            faceUpPanel = createHandSection(faceUpHand, "Face Up", playerIndex);
            faceDownPanel = createFaceDownSection(faceDownHand, playerIndex);
        } else {
            // For AI players, create non-interactive displays
            mainHandPanel = createAIHandDisplay(mainHand, "Main Hand");
            faceUpPanel = createAIHandDisplay(faceUpHand, "Face Up");
            faceDownPanel = createAIFaceDownDisplay(faceDownHand);
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
            JLabel cardLabel = createCardLabel(card);
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
    private JLabel createCardLabel(Card card) {
        String displayRank = getDisplayRank(card.rank);
        String suitSymbol = getSuitSymbol(card.suit);
        String cardText = displayRank + suitSymbol;
        
        JLabel cardLabel = new JLabel(cardText);
        cardLabel.setPreferredSize(new Dimension(40, 60));
        cardLabel.setHorizontalAlignment(JLabel.CENTER);
        cardLabel.setVerticalAlignment(JLabel.CENTER);
        cardLabel.setBackground(Color.WHITE);
        cardLabel.setOpaque(true);
        cardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        // Color red or black based on suit
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
        boolean canPlayFromThisSection = (playerIndex == currentPlayerIndex) && 
                                        (isMainHand || 
                                        (isFaceUp && getMainHand(playerIndex).isEmpty()));
        
        // Add card buttons
        for (int i = 0; i < cards.size(); i++) {
            final Card card = cards.get(i);
            
            // Get the play index for this card
            final int playIndex = getPlayIndex(playerIndex, title, i);
            
            // Only enable valid moves
            boolean isValidMove = canPlayFromThisSection && game.isValidMove(card);
            
            JButton cardButton = createCardButton(card, isValidMove);
            
            if (isValidMove) {
                cardButton.addActionListener(e -> {
                    // For debug
                    System.out.println("Playing card " + card + " at index " + playIndex);
                    
                    // Call the game logic
                    boolean success = game.playCard(playIndex);
                    
                    if (success) {
                        updateUI();
                        
                        // Check if we need to draw a card after playing
                        if (!isDeckEmpty()) {
                            // Ask if player wants to draw a card
                            int drawOption = JOptionPane.showConfirmDialog(
                                this,
                                "Do you want to draw a card from the deck?",
                                "Draw Card",
                                JOptionPane.YES_NO_OPTION
                            );
                            
                            if (drawOption == JOptionPane.YES_OPTION) {
                                Card drawnCard = game.takeCard();
                                if (drawnCard != null) {
                                    JOptionPane.showMessageDialog(
                                        this,
                                        "Drew card: " + getDisplayRank(drawnCard.rank) + getSuitSymbol(drawnCard.suit)
                                    );
                                    updateUI();
                                }
                            }
                        }
                        
                        nextTurn();
                    } else {
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
        boolean canPlayFromFaceDown = (playerIndex == currentPlayerIndex) && 
                                     getMainHand(playerIndex).isEmpty() && 
                                     getFaceUpHand(playerIndex).isEmpty();
        
        // Add card buttons
        for (int i = 0; i < cards.size(); i++) {
            final int playIndex = getPlayIndex(playerIndex, "Face Down", i);
            
            JButton cardButton = new JButton("?");
            cardButton.setPreferredSize(new Dimension(50, 75));
            cardButton.setBackground(Color.LIGHT_GRAY);
            cardButton.setEnabled(canPlayFromFaceDown);
            
            if (canPlayFromFaceDown) {
                cardButton.addActionListener(e -> {
                    System.out.println("Playing face down card at index " + playIndex);
                    boolean success = game.playCard(playIndex);
                    if (success) {
                        updateUI();
                        nextTurn();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid move for face down card!");
                    }
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
            return getMainHand(playerIndex).size() + cardIndex;
        } else { // Face Down
            return getMainHand(playerIndex).size() + getFaceUpHand(playerIndex).size() + cardIndex;
        }
    }
    
    /**
     * Creates a button representing a card
     */
    private JButton createCardButton(Card card, boolean enabled) {
        String displayRank = getDisplayRank(card.rank);
        String suitSymbol = getSuitSymbol(card.suit);
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
        Card topCard = game.viewTopCard();
        if (topCard != null) {
            String displayRank = getDisplayRank(topCard.rank);
            String suitSymbol = getSuitSymbol(topCard.suit);
            pileLabel.setText(displayRank + suitSymbol);
            
            // Color red or black based on suit
            String suitString = topCard.suit.toString();
            if (suitString.equals("♥") || suitString.equals("♦")) {
                pileLabel.setForeground(Color.RED);
            } else {
                pileLabel.setForeground(Color.BLACK);
            }
        } else {
            pileLabel.setText("Empty");
            pileLabel.setForeground(Color.BLACK);
        }
    }
    
    /**
     * Get the main hand for a player index using reflection
     */
    private ArrayList<Card> getMainHand(int playerIndex) {
        if (playerIndex == currentPlayerIndex) {
            try {
                java.lang.reflect.Method method = Rules.class.getDeclaredMethod("getMainHand");
                method.setAccessible(true);
                return (ArrayList<Card>) method.invoke(game);
            } catch (Exception e) {
                System.err.println("Error getting main hand: " + e.getMessage());
                return new ArrayList<>();
            }
        } else {
            // For other players, use reflection on the Hand object
            try {
                ArrayList<Hand> players = getPlayers();
                if (players != null && playerIndex < players.size()) {
                    Hand hand = players.get(playerIndex);
                    java.lang.reflect.Method method = Hand.class.getDeclaredMethod("getMainHand");
                    return (ArrayList<Card>) method.invoke(hand);
                }
            } catch (Exception e) {
                System.err.println("Error getting main hand for player " + playerIndex + ": " + e.getMessage());
            }
            return new ArrayList<>();
        }
    }
    
    /**
     * Get the face up hand for a player index using reflection
     */
    private ArrayList<Card> getFaceUpHand(int playerIndex) {
        if (playerIndex == currentPlayerIndex) {
            try {
                java.lang.reflect.Method method = Rules.class.getDeclaredMethod("getFaceUpHand");
                method.setAccessible(true);
                return (ArrayList<Card>) method.invoke(game);
            } catch (Exception e) {
                System.err.println("Error getting face up hand: " + e.getMessage());
                return new ArrayList<>();
            }
        } else {
            // For other players, use reflection on the Hand object
            try {
                ArrayList<Hand> players = getPlayers();
                if (players != null && playerIndex < players.size()) {
                    Hand hand = players.get(playerIndex);
                    java.lang.reflect.Method method = Hand.class.getDeclaredMethod("getFaceUpHand");
                    return (ArrayList<Card>) method.invoke(hand);
                }
            } catch (Exception e) {
                System.err.println("Error getting face up hand for player " + playerIndex + ": " + e.getMessage());
            }
            return new ArrayList<>();
        }
    }
    
    /**
     * Get the face down hand for a player index using reflection
     */
    private ArrayList<Card> getFaceDownHand(int playerIndex) {
        if (playerIndex == currentPlayerIndex) {
            try {
                java.lang.reflect.Method method = Rules.class.getDeclaredMethod("getFaceDownHand");
                method.setAccessible(true);
                return (ArrayList<Card>) method.invoke(game);
            } catch (Exception e) {
                System.err.println("Error getting face down hand: " + e.getMessage());
                return new ArrayList<>();
            }
        } else {
            // For other players, use reflection on the Hand object
            try {
                ArrayList<Hand> players = getPlayers();
                if (players != null && playerIndex < players.size()) {
                    Hand hand = players.get(playerIndex);
                    java.lang.reflect.Method method = Hand.class.getDeclaredMethod("getFaceDownHand");
                    return (ArrayList<Card>) method.invoke(hand);
                }
            } catch (Exception e) {
                System.err.println("Error getting face down hand for player " + playerIndex + ": " + e.getMessage());
            }
            return new ArrayList<>();
        }
    }
    
    /**
     * Gets the list of players from the game
     */
    @SuppressWarnings("unchecked")
    private ArrayList<Hand> getPlayers() {
        try {
            java.lang.reflect.Field playersField = Rules.class.getDeclaredField("players");
            playersField.setAccessible(true);
            return (ArrayList<Hand>) playersField.get(game);
        } catch (Exception e) {
            System.err.println("Error accessing players: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Gets the current player index from the game
     */
    private int getCurrentPlayerFromGame() {
        try {
            java.lang.reflect.Field turnField = Rules.class.getDeclaredField("turn");
            turnField.setAccessible(true);
            Object turnValue = turnField.get(game);
            java.lang.reflect.Method ordinalMethod = turnValue.getClass().getMethod("ordinal");
            return (int) ordinalMethod.invoke(turnValue);
        } catch (Exception e) {
            System.err.println("Error accessing turn: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Advances to the next player's turn
     */
    private void nextTurn() {
        boolean gameContinues = game.nextTurn();
        if (!gameContinues) {
            // Find winner
            ArrayList<Hand> players = getPlayers();
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).totalCards() == 0) {
                    JOptionPane.showMessageDialog(this, "Game Over! Player " + (i + 1) + " wins!");
                    break;
                }
            }
            return;
        }
        
        // Update current player index based on game's turn
        currentPlayerIndex = getCurrentPlayerFromGame();
        updateUI();
        
        // If AI player's turn, make a move
        if (currentPlayerIndex != 0) {
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(500); // Add a delay for AI turn
                    simulateAITurn();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }
    
    /**
     * Makes a move for an AI player
     */
    private void simulateAITurn() {
        // Get the player's hands
        ArrayList<Card> mainHand = getMainHand(currentPlayerIndex);
        ArrayList<Card> faceUpHand = getFaceUpHand(currentPlayerIndex);
        ArrayList<Card> faceDownHand = getFaceDownHand(currentPlayerIndex);
        
        // Simple AI strategy: play first valid card or take all
        boolean validMove = false;
        
        // Try main hand first
        for (int i = 0; i < mainHand.size(); i++) {
            Card card = mainHand.get(i);
            if (game.isValidMove(card)) {
                game.playCard(i);
                validMove = true;
                break;
            }
        }
        
        // Try face up hand if main hand is empty
        if (!validMove && mainHand.isEmpty()) {
            int offset = mainHand.size();
            for (int i = 0; i < faceUpHand.size(); i++) {
                Card card = faceUpHand.get(i);
                if (game.isValidMove(card)) {
                    game.playCard(offset + i);
                    validMove = true;
                    break;
                }
            }
        }
        
        // Try face down card if both other hands are empty
        if (!validMove && mainHand.isEmpty() && faceUpHand.isEmpty() && !faceDownHand.isEmpty()) {
            // Just play the first face down card - it's a blind guess
            int offset = mainHand.size() + faceUpHand.size();
            game.playCard(offset);
            validMove = true;
        }
        
        if (!validMove) {
            game.takeAll();
            printPileSize();
        } else {
            // AI draws a card if possible
            if (!isDeckEmpty()) {
                Card drawnCard = game.takeCard();
                if (drawnCard != null) {
                    System.out.println("AI Player " + (currentPlayerIndex + 1) + " drew a card: " + getDisplayRank(drawnCard.rank) + getSuitSymbol(drawnCard.suit));
                } else {
                    System.out.println("AI Player " + (currentPlayerIndex + 1) + " attempted to draw but deck was empty.");
                }
            printPileSize();
            }
        }
        
        updateUI();
        nextTurn();
    }

    /**
 * Prints the current pile size to the console
 */
    private void printPileSize() {
        try {
            java.lang.reflect.Field pileField = Rules.class.getDeclaredField("pile");
            pileField.setAccessible(true);
            Object pile = pileField.get(game);

            java.lang.reflect.Method sizeMethod = pile.getClass().getMethod("size");
            int pileSize = (int) sizeMethod.invoke(pile);

            System.out.println("Current pile size: " + pileSize);
        } catch (Exception e) {
            System.err.println("Error printing pile size: " + e.getMessage());
        }  
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