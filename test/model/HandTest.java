package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;

class HandTest {

    @Test
    public void testAddCard() { 
        // Create a new hand
        Hand hand = new Hand();
        ArrayList<Card> cards = Card.getShuffledCards();
        
        // Add cards to the new hand
        for (int i = 0; i < 11; i++) {
            assertTrue(hand.addCard(cards.get(i)));
        }
        
        // Trying to add a 12th card shouldn't work since the hand is full
        assertFalse(hand.addCard(cards.get(11)));
    }
    
    @Test
    public void testPlayCardMainHand() {
        // Create a new hand
        Hand hand = new Hand();
        ArrayList<Card> cards = Card.getShuffledCards();
        
        // Add cards to fill the sections: 3 face down, 3 face up, 5 main hand
        for (int i = 0; i < 11; i++) {
            hand.addCard(cards.get(i));
        }
        
        // Gets a card from main hand
        Card mainCard = hand.getMainHand().iterator().next();
        
        // Plays the card
        Card playedCard = hand.playCard(mainCard);
        
        // Verifies that the right card was played and removed from the main hand section
        assertEquals(10, hand.totalCards());
        assertEquals(4, hand.getMainHand().size());
        assertEquals(mainCard, playedCard);
        assertFalse(hand.getMainHand().contains(mainCard));
    }
    
    @Test
    public void testPlayCardFaceUpHand() {
        Hand hand = new Hand();
        ArrayList<Card> cards = Card.getShuffledCards();
        
        // Add cards to fill the face down section and face up section: 3 face down, 3 face up, 0 main hand
        for (int i = 0; i < 6; i++) {
            hand.addCard(cards.get(i));
        }
        
        // Gets a card from face up hand
        Card faceUpCard = hand.getFaceUpHand().iterator().next();
        
        // Plays the card
        Card playedCard = hand.playCard(faceUpCard);
        
        // Verifies that the right card was played and removed from the face up hand section
        assertEquals(5, hand.totalCards());
        assertEquals(2, hand.getFaceUpHand().size());
        assertEquals(faceUpCard, playedCard);
        assertFalse(hand.getFaceUpHand().contains(faceUpCard));
    }
    
    @Test
    public void testPlayCardFaceDownHand() {
        Hand hand = new Hand();
        ArrayList<Card> cards = Card.getShuffledCards();
        
        // Add cards to fill face down section: 3 face down, 0 face up, 0 main hand
        for (int i = 0; i < 3; i++) {
            hand.addCard(cards.get(i));
        }
        
        // Gets a card from face down hand
        Card faceDownCard = hand.getFaceDownHand().get(0);
        
        // Plays the card
        Card playedCard = hand.playCard(faceDownCard);
        
        // Verifies that the right card was played and removed from the face down hand section
        assertEquals(faceDownCard, playedCard);
        assertFalse(hand.getFaceDownHand().contains(faceDownCard));
        assertEquals(2, hand.getFaceDownHand().size());
        assertEquals(2, hand.totalCards());
    }

    
    @Test
    public void testGetCard() {
        Hand hand = new Hand();
        ArrayList<Card> cards = Card.getShuffledCards();
        
        for (int i = 0; i < 3; i++) {
            hand.addCard(cards.get(i));
        }
        
        // Gets the first card
        Card firstCard = hand.getCard(0);
        assertNotNull(firstCard);
    }
    
    @Test
    public void testSwapCards() {
        Hand hand = new Hand();
        ArrayList<Card> cards = Card.getShuffledCards();
        
        // 3 face down cards, 3 face up cards, 5 in main hand cards
        for (int i = 0; i < 11; i++) {
            hand.addCard(cards.get(i));
        }
        
        // Gets a card from main hand and face up hand
        Card mainCard = hand.getMainHand().iterator().next();
        Card faceUpCard = hand.getFaceUpHand().iterator().next();
        
        // Swaps the cards
        hand.swapCards(mainCard, faceUpCard);
        
        // Verify the swap worked 
        assertTrue(hand.getMainHand().contains(faceUpCard));
        assertTrue(hand.getFaceUpHand().contains(mainCard));
    }
    
    @Test
    public void testTotalCards() {
        Hand hand = new Hand();
        ArrayList<Card> cards = Card.getShuffledCards();
        
        // Add cards and then verify the count
        assertEquals(0, hand.totalCards());
        
        hand.addCard(cards.get(0));
        assertEquals(1, hand.totalCards());

    }
    
    @Test
    public void testGetHands() {
        Hand hand = new Hand();
        ArrayList<Card> cards = Card.getShuffledCards();
        
        // 3 face down cards, 3 face up cards, 5 in main hand cards
        for (int i = 0; i < 11; i++) {
            hand.addCard(cards.get(i));
        }
        
        // Verify the hand sizes
        HashSet<Card> mainHand = hand.getMainHand();
        HashSet<Card> faceUpHand = hand.getFaceUpHand();
        ArrayList<Card> faceDownHand = hand.getFaceDownHand();
        
        assertEquals(5, mainHand.size());
        assertEquals(3, faceUpHand.size());
        assertEquals(3, faceDownHand.size());
    }
    
    @Test
    public void testToString() {
        Hand hand = new Hand();
        ArrayList<Card> cards = Card.getShuffledCards();
        hand.addCard(cards.get(0));
        
        String handString = hand.toString();
        assertNotNull(handString);
        assertFalse(handString.isEmpty());
    }
    
    @Test
    public void testIterator() {
        Hand hand = new Hand();
        ArrayList<Card> cards = Card.getShuffledCards();
        
        // Adds 5 cards
        for (int i = 0; i < 5; i++) {
            hand.addCard(cards.get(i));
        }
        
        // Test the iterator and check that the count returns 5 
        Iterator<Card> iterator = hand.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            Card card = iterator.next();
            assertNotNull(card);
            count++;
        }
        
        assertEquals(5, count);
    }
}
