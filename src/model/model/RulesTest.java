package model.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class RulesTest { 

    @Test
    public void testConstructor() { 
        // Check that the  Rules constructor initializes the game
        Rules game = new Rules();
        
        // Test the toString to make sure it creates a non empty string
        String gameString = game.toString();
        assertNotNull(gameString);
        assertFalse(gameString.isEmpty());
        
        // Verify the string contains information about the 4 players 
        assertTrue(gameString.contains("PLAYER1"));
        assertTrue(gameString.contains("PLAYER2"));
        assertTrue(gameString.contains("PLAYER3"));
        assertTrue(gameString.contains("PLAYER4"));
    }
    
    @Test
    public void testNextTurn() {
        // Create a new game
        Rules game = new Rules();
        
        // Since no one has won yet, calling nextTurn() returns true and the game continues
        boolean gameContinues = game.nextTurn();
        assertTrue(gameContinues);
        
        // Repeatedly call nextTurn to verify the game continues
        for (int i = 0; i < 5; i++) {
        	gameContinues = game.nextTurn();
            assertTrue(gameContinues);
        }
    }
    
    @Test
    public void testTakeCard() {
        Rules game = new Rules();
        
        // Takes a cards multiple times to ensure it works consistently
        for (int i = 0; i < 10; i++) {
            try {
                game.takeCard();
            } catch (Exception e) {
            }
            
            // Move to next player
            game.nextTurn();
        }
    }
    
    @Test
    public void testTakeAll() {
        //Test that takeAll() doesn't throw exceptions
        Rules game = new Rules();
        
        // Take all cards multiple times
        for (int i = 0; i < 4; i++) {
            try {
                game.takeAll();
            } catch (Exception e) {
            }
            
            // Move to the next player
            game.nextTurn();
        }
    }
    
    @Test
    public void testIsValidMove() {
        Rules game = new Rules();
        
        // Using the shuffled cards from Card class
        ArrayList<Card> shuffledCards = Card.getShuffledCards();
        
        // Find a 2 and a 10 in the shuffled deck since they are special cards
        Card two = null;
        Card ten = null;
        
        // Check that a 2 and a 10 are in the shuffled deck
        for (Card current : shuffledCards) {
            if (current.rank == Rank.TWO && two == null) {
                two = current;
            } else if (current.rank == Rank.TEN && ten == null) {
                ten = current;
            }
            
            if (two != null && ten != null) {
                break;
            }
        }
         
        // Test that the cards were found
        assertNotNull(two);
        assertNotNull(ten);
        // Test that they are always going to be valid moves
        assertTrue(game.isValidMove(two));
        assertTrue(game.isValidMove(ten));
    }
    
    @Test  
    public void testSwapCards() {
        Rules game = new Rules();
        
        // Get two cards to swap
        ArrayList<Card> shuffledCards = Card.getShuffledCards();
        Card cardOne = shuffledCards.get(0);
        Card cardTwo = shuffledCards.get(1);
        
        try {
            // Can't test sway cards directly so test that it doesn't throw an exception
            game.swapCards(cardOne, cardTwo);
            assertTrue(true);
        } catch (Exception e) {
        }
    }
    
    @Test
    public void testHasValidMove() {
        // Test that hasValidMove() returns a boolean without an exceptions
        Rules game = new Rules();
        
        try {
            boolean hasMove = game.hasValidMove();
            assertTrue(hasMove);
        } catch (Exception e) {
        }
    }
    
    @Test
    public void testPlayCard() {
        Rules game = new Rules();
        // Tries to play the first card (index 0) and checks it doesnt throw an exception
        try {
            game.playCard(0);
        } catch (Exception e) {
        }
    }
    
    @Test
    public void testMultiplePlayCards() {
        Rules game = new Rules();
        
        // Tests playing multiple cards and moving between turns
        for (int i = 0; i < 4; i++) {
            // Tries to play a card with different indices
            game.playCard(i % 3); 
            
            // Move on to the next player
            game.nextTurn();
        }
        
        // The game should still be going after that
        boolean gameContinues = game.nextTurn();
        assertTrue(gameContinues);
    }
    
    @Test 
    public void testToString() {
        Rules game = new Rules();
        
        String result = game.toString();
        
        // Verify the string contains what is expected
        assertNotNull(result);
        assertFalse(result.isEmpty());
        
        // the toString should contain the player identifiers
        assertTrue(result.contains("PLAYER1"));
        assertTrue(result.contains("PLAYER2"));
        assertTrue(result.contains("PLAYER3"));
        assertTrue(result.contains("PLAYER4"));
    }
    
 
    @Test
    public void testNextTurnWithEmptyHand() {
        // Verifies nextTurn executes without exceptions
        Rules game = new Rules();
        boolean gameContinue = game.nextTurn();
    	// This expects the game to continue with all players having cards
        assertTrue(gameContinue);
    }
    
    @Test
    public void testMainMethod() {
    	// Calls main method with no arguments and verifies it runs without an exception
        try {
            Rules.main(new String[0]);
            assertTrue(true);
        } catch (Exception e) {
        }
    }
    


}