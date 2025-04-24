package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class BestStrategy implements Strategy {
    /*
    Class BestStrategy
    Author: James Montoya, Natalie Grubb, Cameron Liu, Nathan Crutchfield
    Purpose: This class implements the strategy of choosing the lowest card in the hand.
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

        //Determine which hand to use based on game rules and which is available.
        ArrayList<Card> currentHand;
        int cardPositionAdjustment = 0;

        if(hasMainCards){
            currentHand = mainHand;
        } else if(hasFaceUpCards){
            currentHand = faceUpHand;
            cardPositionAdjustment = mainHand.size();
        }else{
            //if you're out of other cards, play a random face down card.
            //adds the size of the main hand and face up hand to ensure its on the right index and hand.
            return mainHand.size() + faceUpHand.size() + this.random.nextInt(faceDownHand.size());
        }

        //Find the lowest card in your main hand. 
        int lowestRank = 13; //the highest possible rank is 13. Consider the card if it is lower.
        for(Card c: currentHand){
            if(c.rank.ordinal() <= lowestRank && this.isValidMove(c, topOfPile)){
                lowestRank = c.rank.ordinal();
            }
        }
        int index = 0;
        for(Card c: currentHand){
            if(c.rank.ordinal() == lowestRank){
                validCards.add(index + cardPositionAdjustment);
            }
            index ++;
        }

        // Return -1 if there is no valid moves
        if (validCards.isEmpty()) {
            return -1;
        }
        
        //Chose a random card between the lowest to play from the validCards, as either would be the best move.
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
            return true;

        if (topOfPile.rank == Rank.SEVEN)
            return c.rank.ordinal() <= topOfPile.rank.ordinal();

        return c.rank.ordinal() >= topOfPile.rank.ordinal();
    }

    @Override
    public Card[] whatCardsToSwap(HashSet<Card> mainHand, HashSet<Card> faceUpHand) {
        /*
        Method whatCardsToSwap
        Purpose: This method chooses the lowest card in your hand and the highest card in the face up deck to be swapped.
        Pre-Condition: None
        Post-Condition: None
        Parameters:
            HashSet<Card> mainHand
            HashSet<Card> faceUpHand
        Returns: An array containing the two cards that will be swapped with each other. Index 1 is from the face up hand, and index 0 is from the main hand. Returns null if does not want to swap.
        */

        Card[] cardsToSwap = new Card[2];

        // Converts the sets to lists
        ArrayList<Card> mainHandList = new ArrayList<>(mainHand);
        ArrayList<Card> faceUpHandList = new ArrayList<>(faceUpHand);

        //Find the lowest card in your hand. 
        int lowestMainRank = 13;
        for(Card c: mainHandList){
            if(c.rank.ordinal() <= lowestMainRank){
                lowestMainRank = c.rank.ordinal();
                cardsToSwap[0] = c;
            }
        }
        //Find the highest card in the face up deck.
        int highestFaceUpRank = 0;
        for(Card c: faceUpHandList){
            if(c.rank.ordinal() >= highestFaceUpRank){
                highestFaceUpRank = c.rank.ordinal();
                cardsToSwap[1] = c;
            }
        }
        /*If the highest card of the face up deck is the same rank as
        the lowest card in your hand then there is no point in swapping.*/
        if(cardsToSwap[0].rank.ordinal() == cardsToSwap[1].rank.ordinal()){
            return null;
        }else{
            return cardsToSwap;
        }
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
