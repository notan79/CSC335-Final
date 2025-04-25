package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import model.Card;

class HandTest {

//    @Test
//    public void testAddCard() { 
//        // Create a new hand
//        Hand hand = new Hand();
//        ArrayList<Card> cards = Card.getShuffledCards();
//        
//        // Add cards to the new hand
//        for (int i = 0; i < 11; i++) {
//            assertTrue(hand.addCard(cards.get(i)));
//        }
//        
//        // Trying to add a 12th card shouldn't work since the hand is full
//        assertFalse(hand.addCard(cards.get(11)));
//    }
	
	@Test 
	public void testAddCardBeginning() {
		Hand hand1 = new Hand();
		Deck deck = new Deck(); 
		
		for (int i = 0; i < 11; i++) { // adds 11 cards to the deck, 3 face down, 3 face up, and 5 in the main hand. 
			hand1.addCardBeginning(deck.takeCard());
		}
		
		Card extra = deck.takeCard();
		
		assertTrue(hand1.totalCards() == 11);
		assertFalse(hand1.addCardBeginning(extra));
	
	}
	
	@Test
	public void testPlayCard() { 
		Hand hand = new Hand();
		Deck deck = new Deck(); 
		ArrayList<Card> cardsInHand = new ArrayList<Card>();
		
		for (int i = 0; i < 11; i++) { // adds 11 cards to the deck, 3 face down, 3 face up, and 5 in the main hand. 
			Card temp = deck.takeCard();
			hand.addCardBeginning(temp);
			cardsInHand.add(temp);
		} 

		for (int i = 10; i > 5; i--) { // adds 11 cards to the deck, 3 face down, 3 face up, and 5 in the main hand. 
			hand.playCard(cardsInHand.remove(i));
		}	
		assertEquals(hand.playCard(hand.getFaceUpHand().get(2)).rank, cardsInHand.remove(5).rank);
		
		for (int i = 4; i > 0; i--) { // adds 11 cards to the deck, 3 face down, 3 face up, and 5 in the main hand. 
			hand.playCard(cardsInHand.remove(i));
		}	
		assertEquals(hand.playCard(hand.getFaceDownHand().get(0)).rank, cardsInHand.remove(0).rank);
		
		assertNull(hand.playCard(deck.takeCard()));

	}
    
    @Test
    public void testPlayCardMainHand() {
        // Create a new hand
        Hand hand = new Hand();
        ArrayList<Card> cards = Card.getShuffledCards();
        
        // Add cards to fill the sections: 3 face down, 3 face up, 5 main hand
        for (int i = 0; i < 11; i++) {
            hand.addCardBeginning(cards.get(i));
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
            hand.addCardBeginning(cards.get(i));
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
            hand.addCardBeginning(cards.get(i));
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

        // Add 3 cards to faceDownHand
        for (int i = 0; i < 3; i++) {
            assertTrue(hand.addCardBeginning(cards.get(i)));
        }

        for (int i = 3; i < 6; i++) {
            assertTrue(hand.addCardBeginning(cards.get(i)));
        }

        for (int i = 6; i < 9; i++) {
            assertTrue(hand.addCardBeginning(cards.get(i)));
        }
        
        assertEquals(cards.get(6), hand.getCard(0));
        assertEquals(cards.get(7), hand.getCard(1));
        assertEquals(cards.get(8), hand.getCard(2));

        assertEquals(cards.get(3), hand.getCard(3));
        assertEquals(cards.get(4), hand.getCard(4));
        assertEquals(cards.get(5), hand.getCard(5));

        assertEquals(cards.get(0), hand.getCard(6));
        assertEquals(cards.get(1), hand.getCard(7));
        assertEquals(cards.get(2), hand.getCard(8));

        assertNull(hand.getCard(9));
    }
    
    @Test
    public void testSwapCards() {
        Hand hand = new Hand();
        ArrayList<Card> cards = Card.getShuffledCards();
        
        // 3 face down cards, 3 face up cards, 5 in main hand cards
        for (int i = 0; i < 11; i++) {
            hand.addCardBeginning(cards.get(i));
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
            hand.addCardBeginning(cards.get(i));
        }
        
        // Verify the hand sizes
        ArrayList<Card> mainHand = hand.getMainHand();
        ArrayList<Card> faceUpHand = hand.getFaceUpHand();
        ArrayList<Card> faceDownHand = hand.getFaceDownHand();
        
        assertEquals(5, mainHand.size());
        assertEquals(3, faceUpHand.size());
        assertEquals(3, faceDownHand.size());
    }
    
    @Test
    public void testToString() {
        Hand hand = new Hand();
        Hand hand2 = new Hand();
        Deck deck = new Deck(); 
        ArrayList<Card> cards = Card.getShuffledCards();
        ArrayList<Card> hand2Cards = new ArrayList<Card>();
        hand.addCard(cards.get(0));
        
        String handString = hand.toString();
        assertNotNull(handString);
        assertFalse(handString.isEmpty());
        
        for (int i = 0; i < 11; i++) {
        	Card temp = deck.takeCard();
        	hand2.addCardBeginning(temp);
        	hand2Cards.add(temp);
        }
       
        StringBuilder sb = new StringBuilder();
        sb.append("Main Hand: " + hand2.getMainHandToString() + "\n");
        sb.append("Face up Hand: " + hand2.getFaceUpHandToString().toString() + "\n");
        sb.append("Face down Hand: " + hand2.getFaceDownToString().toString() + "\n");

        assertEquals(sb.toString(), hand2.toString());
       
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
