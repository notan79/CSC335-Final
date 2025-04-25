package model;

import java.util.ArrayList;
import java.util.Random;

public class RandomStrategy implements Strategy {
    /*
    Class RandomStrategy
    Author: James Montoya, Natalie Grubb, Cameron Liu, Nathan Crutchfield
    Purpose: This class implements the strategy of making decisions at random.
    Inherits, Interfaces, Constants: 
        Interfaces: Strategy
    Instance variables:
        Private:
            Random random
    Constructors: None
    Class Methods:
        Public:
            int whatCardToPlay(ArrayList<Card> mainHand, ArrayList<Card> faceUpHand, ArrayList<Card> faceDownHand, Card topOfPile, boolean hasMainCards, boolean hasFaceUpCards)
            Card[] whatCardsToSwap(HashSet<Card> mainHand, HashSet<Card> faceUpHand)
            int whatFaceDownCard(ArrayList<Card> faceDownHand)
        Private:
            boolean isValidMove(Card c, Card topOfPile)
    Instance Methods: None
     */
    
    private Random random = new Random();
    
    @Override
    public int whatCardToPlay(ArrayList<Card> mainHand, ArrayList<Card> faceUpHand, ArrayList<Card> faceDownHand, Card topOfPile, boolean hasMainCards, boolean hasFaceUpCards) {
        /*
        Method whatCardToPlay
        Purpose: Returns the index of the card to play, if theres no valid move it returns -1
        Pre-condition: None
        Post-Condition: None
        Parameters: 
            ArrayList<Card> mainHand
            ArrayList<Card> faceUpHand
            ArrayList<Card> faceDownHand
            Card topOfPile
            boolean hasMainCards
            boolean hasFaceUpCards
        Returns: an integer 
        */

        ArrayList<Integer> validCards = new ArrayList<>();
        
        // Next determine which hand to use based on the game rules and which is available 
        ArrayList<Card> currentHand;
        int cardPositionAdjustment = 0;
        
        if (hasMainCards) {
            currentHand = mainHand;
        } else if (hasFaceUpCards) {
            currentHand = faceUpHand;
            cardPositionAdjustment = mainHand.size();
        } else {
            // If you're out of other cards, play a random face down card
            // adds the size of the main hand and face up hand to ensure its on the right index and hand
            return mainHand.size() + faceUpHand.size() + this.random.nextInt(faceDownHand.size());
        }
        
        // Check which cards are valid plays
        int index = 0;
        for (Card card : currentHand) {
            // Use Rules.isValidMove
            if (this.isValidMove(card, topOfPile)) {
                validCards.add(index + cardPositionAdjustment);
            }
            index++;
        }
        
        // Return -1 if there is no valid moves
        if (validCards.isEmpty()) {
            return -1;
        }
        
        // Choose a random valid card to play from the validCards list 
        return validCards.get(this.random.nextInt(validCards.size()));
    }

    private boolean isValidMove(Card c, Card topOfPile) {
        /*
        Method isValidMove
        Purpose: Checks if the player is allowed to place the card onto the deck.
        Pre-condition: None
        Post-condition: None
        Parameters:
            Card c
            Card topOfPile
        Returns: a boolean value
         */

        if (c.rank == Rank.TEN || c.rank == Rank.TWO || topOfPile == null)
        //a two and a ten can be placed on anything.
            return true;

        if (topOfPile.rank == Rank.SEVEN)
        //if the top of the pile is seven, the card being placed must be lower in rank.
            return c.rank.ordinal() <= topOfPile.rank.ordinal();

        //must place a card of higher rank than the top of the pile.
        return c.rank.ordinal() >= topOfPile.rank.ordinal();
    }


    @Override
    public int whatFaceDownCard(ArrayList<Card> faceDownHand) {
        /*
        Method whatFaceDownCard
        Purpose: Randomly determines which face down card to play when no other options are available
        Pre-condition: None
        Post-Condition: None
        Parameters: ArrayList<Card> faceDownHand
        Returns: an integer representing the index of the face down card to play
        */

        // Pick a random face down card
        if (!faceDownHand.isEmpty()) {
            return this.random.nextInt(faceDownHand.size());
        }
        // if empty
        return 0;
    }

}