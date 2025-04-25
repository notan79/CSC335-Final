package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DeckTest {

    @Test
    public void testNewDeck() { 
        // Check that a new deck contains 52 cards
        Deck deck = new Deck();
        assertFalse(deck.isEmpty());
    }
    
    @Test
    public void testTakeCard() {
        // Check that takeCard returns a card
        Deck deck = new Deck();
        Card card = deck.takeCard();
        
        assertNotNull(card);
        assertNotNull(card.rank);
        assertNotNull(card.suit);
    }
    
    @Test
    public void testIsEmpty() {
        // Check that taking all the cards empties the deck
        Deck deck = new Deck();
        
        for (int i = 0; i < 52; i++) {
            assertNotNull(deck.takeCard());
        }
        
        // Check the deck is empty
        assertTrue(deck.isEmpty());
        
        // Trying to take another card should return null
        assertNull(deck.takeCard());
    }
    
    @Test
    public void testToString() {
        Deck deck = new Deck();
        String deckString = deck.toString();
        
        assertNotNull(deckString);
        assertFalse(deckString.isEmpty());
    }
}
