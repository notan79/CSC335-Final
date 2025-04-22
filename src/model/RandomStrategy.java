package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;

public class RandomStrategy implements Strategy {
    private Random random = new Random();
    
    @Override
    public int whatCardToPlay(HashSet<Card> mainHand, HashSet<Card> faceUpHand, ArrayList<Card> faceDownHand, Stack<Card> pile, boolean hasMainCards, boolean hasFaceUpCards) {
        // Create a list of valid card 
        ArrayList<Integer> validCards = new ArrayList<>();
        
        // Next determine which hand to use based on the game rules and which is available 
        HashSet<Card> currentHand;
        int cardPositionAdjustment = 0;
        
        if (hasMainCards) {
            currentHand = mainHand;
        } else if (hasFaceUpCards) {
            currentHand = faceUpHand;
            cardPositionAdjustment = mainHand.size();
        } else {
            // If you're out of other cards, play a random face down card
            // adds the size of the main hand and face up hand to ensure its on the right index and hand
            return mainHand.size() + faceUpHand.size() + random.nextInt(faceDownHand.size());
        }
        
        // Check which cards are valid plays
        int index = 0;
        for (Card card : currentHand) {
            // Use Rules.isValidMove
            if (isValidMove(card, pile)) {
                validCards.add(index + cardPositionAdjustment);
            }
            index++;
        }
        
        // Return -1 if there is no valid moves
        if (validCards.isEmpty()) {
            return -1;
        }
        
        // Choose a random valid card to play from the validCards list 
        return validCards.get(random.nextInt(validCards.size()));
    }

    private boolean isValidMove(Card c, Stack<Card> pile) {
        if (c.rank == Rank.TEN || c.rank == Rank.TWO || pile.empty())
            return true;

        Card topOfPile = pile.peek();
        if (topOfPile.rank == Rank.SEVEN)
            return c.rank.ordinal() <= topOfPile.rank.ordinal();

        return c.rank.ordinal() >= topOfPile.rank.ordinal();
    }

    @Override
    public Card[] whatCardsToSwap(HashSet<Card> mainHand, HashSet<Card> faceUpHand) {
        // Randomly decide whether to swap
        // If the random booleon is yes, and the main/hand and FaceUpHands aren't empty, then swap
        if (random.nextBoolean() && !mainHand.isEmpty() && !faceUpHand.isEmpty()) {
            Card[] cardsToSwap = new Card[2];
        
        // Converts the sets to lists
        ArrayList<Card> mainHandList = new ArrayList<>(mainHand);
        ArrayList<Card> faceUpHandList = new ArrayList<>(faceUpHand);
        
        // Gets random cards from each hand
        cardsToSwap[0] = mainHandList.get(random.nextInt(mainHandList.size()));
        cardsToSwap[1] = faceUpHandList.get(random.nextInt(faceUpHandList.size()));
        
        return cardsToSwap;
    }
    
    // return null if the random boolean comes up no and theres no swap (or the lists are empty)
    return null;
}

    @Override
    public int whatFaceDownCard(ArrayList<Card> faceDownHand) {
        // Pick a random face down card
        if (!faceDownHand.isEmpty()) {
            return random.nextInt(faceDownHand.size());
        }
        // if empty
        return 0;
    }

}