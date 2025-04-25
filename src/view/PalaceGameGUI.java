/*
    The Java swing GUI class, implemented with help from AI (Claude, ChatGPT)
*/

package view;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import controller.Controller;
import model.Card;
import model.Rank;
import model.Rules;
import model.Suit;

public class PalaceGameGUI extends JFrame {
    
    
    private Controller controller;
    
    // Components for the UI
    private JPanel mainPanel;
    private JPanel[] playerPanels;
    private JPanel pilePanel;
    private JLabel pileLabel;
    private JButton takeAllButton;
    private JLabel statusLabel;
    private JLabel deckLabel;
    
    
    /*
        Sets up the initial components and controller
     */
    public PalaceGameGUI() {
        // Creates the controller / model
        this.controller = new Controller(new Rules(), this);
        
        // Sets up the window
        this.setTitle("Palace Card Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1500, 1000);
        this.setLocationRelativeTo(null);
        
        // Creates the main panel
        this.mainPanel = new JPanel(new BorderLayout());
        
        // Creates the panel for the 4 players
        this.playerPanels = new JPanel[4];
        for (int i = 0; i < 4; i++) {
            this.playerPanels[i] = this.createPlayerPanel(i);
        }
        
        // Positions the player panels
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(this.playerPanels[2], BorderLayout.CENTER);
        northPanel.setBorder(BorderFactory.createTitledBorder("Player 3"));
        
        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.add(this.playerPanels[1], BorderLayout.CENTER);
        eastPanel.setBorder(BorderFactory.createTitledBorder("Player 2"));
        
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(this.playerPanels[0], BorderLayout.CENTER);
        southPanel.setBorder(BorderFactory.createTitledBorder("Player 1 (You)"));
        
        JPanel westPanel = new JPanel(new BorderLayout());
        westPanel.add(this.playerPanels[3], BorderLayout.CENTER);
        westPanel.setBorder(BorderFactory.createTitledBorder("Player 4"));

        // Add the player panels to the main panel
        this.mainPanel.add(northPanel, BorderLayout.NORTH);
        this.mainPanel.add(eastPanel, BorderLayout.EAST);
        this.mainPanel.add(southPanel, BorderLayout.SOUTH);
        this.mainPanel.add(westPanel, BorderLayout.WEST);
        
        // Creates the center panel
        this.createCenterPanel();
        
        // Adds the main to the content
        this.getContentPane().add(this.mainPanel);
        
        // Update the UI to match the current game state
        this.updateUI();
    }
    
    /*
        Creates the center panel for displaying
     */
    private void createCenterPanel() {

        // Creates a panel for the pile
        this.pilePanel = new JPanel(new BorderLayout());
        
        // Creates the label for the pile
        this.pileLabel = new JLabel("", JLabel.CENTER);
        this.pileLabel.setFont(new Font("Arial", Font.BOLD, 24));
        this.updatePileLabel();
        
        // Creates a deck label 
        this.deckLabel = new JLabel("Deck: Cards Remaining", JLabel.CENTER);
        this.deckLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Add a panel for the buttons
        JPanel buttonPanel = new JPanel();
        
        // Create a button for the take all
        this.takeAllButton = new JButton("Take All Cards");
        this.takeAllButton.setActionCommand("takeAll");

        // Add the controller to listen for the take all command
        this.takeAllButton.addActionListener(this.controller);
        buttonPanel.add(this.takeAllButton);
        
        
        // Adds a label to show current player
        this.statusLabel = new JLabel("Game Started! Player 1's Turn", JLabel.CENTER);
        this.statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Creates a label of the rules
        JLabel rulesLabel = new JLabel("<html>Rules: You must either play an equal or larger card (smaller if seven is top of pile) or take all cards from the pile.</html>", JLabel.CENTER);
        rulesLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        
        // Creates a label for the rules and the buttons
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.CENTER);
        southPanel.add(rulesLabel, BorderLayout.SOUTH);
        
        // Adds the deck label to the center content
        JPanel centerContent = new JPanel(new BorderLayout());
        centerContent.add(this.deckLabel, BorderLayout.NORTH);
        centerContent.add(this.pileLabel, BorderLayout.CENTER);
        
        // Puts everything in the panel
        this.pilePanel.add(centerContent, BorderLayout.CENTER);
        this.pilePanel.add(southPanel, BorderLayout.SOUTH);
        this.pilePanel.add(this.statusLabel, BorderLayout.NORTH);
        this.pilePanel.setBorder(BorderFactory.createTitledBorder("Pile"));
        
        // Adds the pile panel to the center of the main panel
        this.mainPanel.add(this.pilePanel, BorderLayout.CENTER);
    }
    
    /*
        Creates a panel for the given player
     */
    private JPanel createPlayerPanel(int playerIndex) {

        // Create the panel for the player
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        
        // Create the main hand
        JPanel mainHandPanel = new JPanel(new FlowLayout());
        mainHandPanel.setBorder(BorderFactory.createTitledBorder("Main Hand"));
        
        // Create the face up hand
        JPanel faceUpPanel = new JPanel(new FlowLayout());
        faceUpPanel.setBorder(BorderFactory.createTitledBorder("Face Up"));
        
        // Create the face down hand
        JPanel faceDownPanel = new JPanel(new FlowLayout());
        faceDownPanel.setBorder(BorderFactory.createTitledBorder("Face Down"));
        
        // Add the components to the panel
        panel.add(mainHandPanel);
        panel.add(faceUpPanel);
        panel.add(faceDownPanel);
        
        return panel;
    }
    
    /*
        Updates the UI to the current state from controller
     */
    public void updateUI() {

        // Update each player panel
        for (int i = 0; i < 4; i++) {
            this.updatePlayerPanel(i);
        }
        
        // See if it is player one's turn
        boolean isPlayerTurn = this.controller.getTurn() == 0;
        
        // If it is player one, enable, otherwise disable
        this.takeAllButton.setEnabled(isPlayerTurn);
        
        // Update the current status of the game
        this.statusLabel.setText("Player " + (this.controller.getTurn() + 1) + "'s turn");
        
        // Update the title of the window
        this.setTitle("Palace Card Game - Player " + (this.controller.getTurn() + 1) + "'s Turn");
        
        // Check if there is a valid move and update the text
        if (isPlayerTurn && !this.hasValidMove()) {
            this.statusLabel.setText("No valid moves! You must take all cards from the pile.");
            // If there is no valid move, highlight the take all
            this.takeAllButton.setBackground(Color.YELLOW);
        } else {
            this.takeAllButton.setBackground(null);
        }
    }
    
    /*
        Update the deck label based on the current state of controller's deck
     */
    public void updateDeckLabel() {
        boolean isDeckEmpty = this.isDeckEmpty();
        if (!isDeckEmpty) {
            this.deckLabel.setText("Deck: Cards Available");
        } else {
            this.deckLabel.setText("Deck: Empty");
        }
    }
    
    /*
        Check if the deck is empty
     */
    private boolean isDeckEmpty() {
        return this.controller.isDeckEmpty();
    }
    
    /*
        Check if the current player has a valid move
     */
    private boolean hasValidMove() {
        return this.controller.hasValidMove();
    }
    
    /*
        Update the player panel based on the index
     */
    private void updatePlayerPanel(int playerIndex) {

        // Get the correct panel
        JPanel playerPanel = this.playerPanels[playerIndex];

        // Reset the panel
        playerPanel.removeAll();
        
        // Set the layout of the player panel
        playerPanel.setLayout(new GridLayout(3, 1));
        
        // Get the information for each hand
        ArrayList<Card> mainHand = this.getMainHand(playerIndex);
        ArrayList<Card> faceUpHand = this.getFaceUpHand(playerIndex);
        ArrayList<Card> faceDownHand = this.getFaceDownHand(playerIndex);
        
        // Have panels for each section
        JPanel mainHandPanel, faceUpPanel, faceDownPanel;
        
        // Only create clickable section if the main player
        if (playerIndex == 0) {
            mainHandPanel = this.createHandSection(mainHand, "Main Hand", playerIndex);
            faceUpPanel = this.createHandSection(faceUpHand, "Face Up", playerIndex);
            faceDownPanel = this.createFaceDownSection(faceDownHand, playerIndex);
        } else {
            // For CPU players create separate displays
            mainHandPanel = this.createAIHandDisplay(mainHand, "Main Hand");
            faceUpPanel = this.createAIHandDisplay(faceUpHand, "Face Up");
            faceDownPanel = this.createAIFaceDownDisplay(faceDownHand);
        }
        
        // Add all the sections to the player panel
        playerPanel.add(mainHandPanel);
        playerPanel.add(faceUpPanel);
        playerPanel.add(faceDownPanel);
        
        // Refresh the panel
        playerPanel.revalidate();
        playerPanel.repaint();
    }
    
    /*
        Create a panel for the CPU players
     */
    private JPanel createAIHandDisplay(ArrayList<Card> cards, String title) {

        // Create the panel
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        
        // Create the label for each card
        for (Card card : cards) {
            JLabel cardLabel = this.createCardLabel(card, title);
            panel.add(cardLabel);
        }
        
        return panel;
    }
    
    /*
        Creates the face down display for the CPU
     */
    private JPanel createAIFaceDownDisplay(ArrayList<Card> cards) {

        // Create the panel
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Face Down"));
        
        // For each card
        for (int i = 0; i < cards.size(); i++) {

            // Set the text to hidden
            JLabel cardLabel = new JLabel("?");

            // Set the alignments and color settings
            cardLabel.setPreferredSize(new Dimension(40, 60));
            cardLabel.setHorizontalAlignment(JLabel.CENTER);
            cardLabel.setVerticalAlignment(JLabel.CENTER);
            cardLabel.setBackground(Color.LIGHT_GRAY);
            cardLabel.setOpaque(true);
            cardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            
            // Add to the panel
            panel.add(cardLabel);
        }
        
        return panel;
    }
    
    /*
        Creates a card label for the CPU players
     */
    private JLabel createCardLabel(Card card, String title) {

        // Get the relevant information for the card
        String displayRank = this.getDisplayRank(card.rank);
        String suitSymbol = this.getSuitSymbol(card.suit);
        String cardText = displayRank + suitSymbol;
        
        // If its the main hand set the text to hidden
        if(title.equals("Main Hand"))
            cardText = "?";
        
        // Set the label for the card and set its visual settings
        JLabel cardLabel = new JLabel(cardText);
        cardLabel.setPreferredSize(new Dimension(40, 60));

        cardLabel.setHorizontalAlignment(JLabel.CENTER);
        cardLabel.setVerticalAlignment(JLabel.CENTER);
        cardLabel.setBackground(Color.WHITE);
        cardLabel.setOpaque(true);
        cardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        // If its the main hand don't color it, needs to be hidden
        if(title.equals("Main Hand"))
            return cardLabel;

        // Color the card based on suit
        String suitString = card.suit.toString();
        if (suitString.equals("♥") || suitString.equals("♦")) {
            cardLabel.setForeground(Color.RED);
        } else {
            cardLabel.setForeground(Color.BLACK);
        }
        
        return cardLabel;
    }
    
    /*
        Creates the hand section for the main player
     */
    private JPanel createHandSection(ArrayList<Card> cards, String title, int playerIndex) {

        // Create the panel
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        
        // Get the current hand
        boolean isMainHand = title.equals("Main Hand");
        boolean isFaceUp = title.equals("Face Up");
        
        // See if the player can play cards from the section
        boolean canPlayFromThisSection = (playerIndex == this.controller.getTurn()) && 
                                        (isMainHand || 
                                        (isFaceUp && this.getMainHand(playerIndex).isEmpty()));
        
        // Create the card buttons
        for (int i = 0; i < cards.size(); i++) {
            final Card card = cards.get(i);
            
            // Get the play index for the card
            final int playIndex = this.getPlayIndex(playerIndex, title, i);
            
            // Only allow valid moves to be clicked
            boolean isValidMove = canPlayFromThisSection && this.controller.isValidMove(card);
            
            // Create the card button
            JButton cardButton = this.createCardButton(card, isValidMove);
            
            // If its a valid move add the controller to action
            if (isValidMove) {
                cardButton.setActionCommand("CB" + playIndex);
                cardButton.addActionListener(this.controller);
            }
            
            panel.add(cardButton);
        }
        
        return panel;
    }

    // Show the invalid move message
    public void showCBMessage(){
        JOptionPane.showMessageDialog(null, "Invalid move!");
    }
    
    /*
        Creates the face down section for the main player
     */
    private JPanel createFaceDownSection(ArrayList<Card> cards, int playerIndex) {

        // Create the panel
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Face Down"));
        
        // Check if there are valid moves
        boolean canPlayFromFaceDown = (playerIndex == this.controller.getTurn()) && 
                                     this.getMainHand(playerIndex).isEmpty() && 
                                     this.getFaceUpHand(playerIndex).isEmpty();
        
        // Add the Card buttons
        for (int i = 0; i < cards.size(); i++) {            
            JButton cardButton = new JButton("?");
            cardButton.setPreferredSize(new Dimension(50, 75));
            cardButton.setBackground(Color.LIGHT_GRAY);
            cardButton.setEnabled(canPlayFromFaceDown);
            
            if (canPlayFromFaceDown) {
                cardButton.setActionCommand("FD"+i);
                cardButton.addActionListener(this.controller);
            }
            
            panel.add(cardButton);
        }
        
        return panel;
    }

    // Print the message if its an invalid face down Card
    public void invalidFaceDown(){
        JOptionPane.showMessageDialog(null, "It was an invalid face down card. You must pick up the pile!");
    }
    
    /*
        Gets the current index of the selected card
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
    
    /*
        Creates a button for the card for main player, and sets its visibility
     */
    private JButton createCardButton(Card card, boolean enabled) {

        // Get the information for the Card
        String displayRank = this.getDisplayRank(card.rank);
        String suitSymbol = this.getSuitSymbol(card.suit);
        String cardText = displayRank + suitSymbol;
        
        // Create the button
        JButton cardButton = new JButton(cardText);
        cardButton.setPreferredSize(new Dimension(55, 75));
        
        // Color red or black based on suit
        String suitString = card.suit.toString();
        if (suitString.equals("♥") || suitString.equals("♦")) {
            cardButton.setForeground(Color.RED);
        } else {
            cardButton.setForeground(Color.BLACK);
        }
        
        cardButton.setEnabled(enabled);
        
        // // Add tooltip to show the full card details for debugging
        // cardButton.setToolTipText("Rank: " + card.rank + " (ordinal: " + card.rank.ordinal() + ")");
        
        return cardButton;
    }
    
    /*
        Gets the rank for the Card
     */
    private String getDisplayRank(Rank rank) {
        if (rank == Rank.TEN) {
            return "10";
        } else {
            return rank.toString();
        }
    }
    
    /*
        Get the suit symbol
     */
    private String getSuitSymbol(Suit suit) {
        // Just return the suit's toString value, assuming it returns the symbol
        return suit.toString();
    }
    
    /*
        Update the pile to show the top Card
     */
    public void updatePileLabel() {

        // Get the top Card
        Card topCard = this.controller.viewTopCard();

        // Update the card to its respective information
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
        } 
        // No top card means deck is empty
        else {
            this.pileLabel.setText("Empty");
            this.pileLabel.setForeground(Color.BLACK);
        }
    }
    
    /*
        Get the mainHand from controller
     */
    private ArrayList<Card> getMainHand(int playerIndex) {
        return this.controller.getMainHand(playerIndex);
    }
    
    /*
        Get the faceUp hand from the controller
     */
    private ArrayList<Card> getFaceUpHand(int playerIndex) {
        return this.controller.getFaceUpHand(playerIndex);
    }
    
    /*
        Get the faceDown hand from the controller
     */
    private ArrayList<Card> getFaceDownHand(int playerIndex) {
        return this.controller.getFaceDownHand(playerIndex);
    }
    
    
    /*
        Advance to the next turn and see if its end state
     */
    public void nextTurn() {
        int winner = this.controller.getTurn();
        boolean gameContinues = this.controller.nextTurn();
        if (!gameContinues) {
            
            JOptionPane.showMessageDialog(this, "Game Over! Player " + (winner + 1) + " wins!");
            this.takeAllButton.setEnabled(false);    
            return;
        }
        
        // Update current player index based on game's turn
        this.updateUI();
        
        // If CPU player's turn, make a move
        if (this.controller.getTurn() != 0) {
            // Set a delay so the CPU's don't play instantly
            SwingUtilities.invokeLater(() -> {
                try {
                    Thread.sleep(1500); // Add a delay for CPU turn
                    this.simulateAITurn();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }
    
    /*
        Make a move for the CPU turn based on the controller
    */
    private void simulateAITurn() {
        this.controller.simulateAITurn();
    }
}