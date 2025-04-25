package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class SuitTest {

	@Test
	void testToString() {
		ArrayList<Card> arr = Card.getShuffledCards();
		for(Card c : arr) {
			if(c.suit == Suit.HEART) {
				assertEquals(c.suit.toString(), "\u2665");
			}else if(c.suit == Suit.DIAMOND){
				assertEquals(c.suit.toString(),"\u2666");
	        }else if(c.suit == Suit.CLUB){
	        	assertEquals(c.suit.toString(),"\u2663");
	        }else{
	        	assertEquals(c.suit.toString(),"\u2660");
	        }
		}
	}

}
