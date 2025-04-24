package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class BestStrategy implements Strategy {
    //implement whatCardToPlay, whatCardsToSwap, and whatCardsToSwap
    private Random random = new Random();

    @Override
    public int whatCardToPlay(ArrayList<Card> mainHand, ArrayList<Card> faceUpHand, ArrayList<Card> faceDownHand, Card topOfPile, boolean hasMainCards, boolean hasFaceUpCards) {
        //create a list of valid cards.
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

        //Find the lowest card in your hand. 
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
        if (c.rank == Rank.TEN || c.rank == Rank.TWO || topOfPile == null)
            return true;

        if (topOfPile.rank == Rank.SEVEN)
            return c.rank.ordinal() <= topOfPile.rank.ordinal();

        return c.rank.ordinal() >= topOfPile.rank.ordinal();
    }

    @Override
    public Card[] whatCardsToSwap(HashSet<Card> mainHand, HashSet<Card> faceUpHand) {
        //swaps the lowest card in your hand with the highest card in the face up deck.
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
        the lowest card in your hand thenn there is no point in swapping.*/
        if(cardsToSwap[0].rank.ordinal() == cardsToSwap[1].rank.ordinal()){
            return null;
        }else{
            return cardsToSwap;
        }
    }

    @Override
    public int whatFaceDownCard(ArrayList<Card> faceDownHand) {
        // Pick a random face down card
        if (!faceDownHand.isEmpty()) {
            return this.random.nextInt(faceDownHand.size());
        }
        // if empty
        return 0;
    }
    
}
