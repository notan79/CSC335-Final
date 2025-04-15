package model;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class CardTest {

	@Test
    public void testGetShuffledCards() {
        // Checks that the player gets 52 shuffled cards
        ArrayList<Card> cards = Card.getShuffledCards();
        assertEquals(52, cards.size());
    }
    
    @Test
    public void testCardRankAndSuit() {
        // Checks that a card has a rank and suit
        ArrayList<Card> cards = Card.getShuffledCards();
        Card card = cards.get(0);
        
        assertNotNull(card.rank);
        assertNotNull(card.suit);
    }
    
    @Test
    public void testToString() {
        // Checks that the toString method returns the expected string
        ArrayList<Card> cards = Card.getShuffledCards();
        Card card = cards.get(0);
        
        assertEquals(card.rank + " ", card.toString());
    }
    
    @Test
    public void testShuffling() {
        // Generates two shuffles and checks that they are both different
        ArrayList<Card> firstShuffle = Card.getShuffledCards();
        ArrayList<Card> secondShuffle = Card.getShuffledCards();
        
        // Make sure we got two different list instances
        assertNotSame(firstShuffle, secondShuffle);
    }
}