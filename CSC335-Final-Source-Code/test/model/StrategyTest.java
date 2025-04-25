package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class StrategyTest {
	private ArrayList<Card> all = Card.getShuffledCards();


	@Test
	void bestStrategy() {
		Rules game = new Rules();
		BestStrategy bs = new BestStrategy();
		
		ArrayList<Card> mh = game.getMainHand();
		ArrayList<Card> fu = game.getFaceUpHand();
		ArrayList<Card> fd = game.getFaceDownHand();
		
		Card two = null;
		Card ace = null;
		ArrayList<Card> badSet = new ArrayList<>();
		for(Card c : all) {
			if(c.rank == Rank.TWO) {
				two = c;
			}else if(c.rank == Rank.THREE || c.rank == Rank.FOUR) {
				badSet.add(c);
			}else if(c.rank == Rank.ACE) {
				ace = c;
			}
		}
		
		int min = 0;
		for(int i = 1; i < mh.size(); ++i) {
			if(mh.get(i).rank.ordinal() < mh.get(min).rank.ordinal())
				min = i;
		}
		
		// Assert the same rank for the minimum card for main hand
		assertEquals(mh.get(min).rank, mh.get(bs.whatCardToPlay(mh, fu, fd, two, true, true)).rank);
		
		min = 0;
		for(int i = 1; i < fu.size(); ++i) {
			if(fu.get(i).rank.ordinal() < fu.get(min).rank.ordinal())
				min = i;
		}
		// Assert the same rank for the minimum card for face up
		assertEquals(fu.get(min).rank, fu.get(bs.whatCardToPlay(mh, fu, fd, two, false, true)-5).rank);
		
		min = 0;
		for(int i = 1; i < fu.size(); ++i) {
			if(fu.get(i).rank.ordinal() < fu.get(min).rank.ordinal())
				min = i;
		}
		
		// Some value greater than 7
		assertTrue(bs.whatCardToPlay(mh, fu, fd, two, false, false) > 7);
		
		assertEquals(bs.whatCardToPlay(badSet, fu, fd, ace, true, true), -1);
			
	}
	
	@Test
	void faceDownBS() {
		Rules game = new Rules();
		BestStrategy bs = new BestStrategy();
		
		
		ArrayList<Card> fd = game.getFaceDownHand();
		int temp = bs.whatFaceDownCard(fd);
		assertTrue(temp >= 0 && temp < 3);
		ArrayList<Card> empty = new ArrayList<>();
		assertEquals(bs.whatFaceDownCard(empty), 0);

	}
	
	@Test
	void randomStrategy(){
		Rules game = new Rules();
		RandomStrategy rs = new RandomStrategy();
		
		Card two = null;
		Card ace = null;
		Card seven = null;
		ArrayList<Card> badSet = new ArrayList<>();
		for(Card c : all) {
			if(c.rank == Rank.TWO) {
				two = c;
			}else if(c.rank == Rank.THREE || c.rank == Rank.FOUR) {
				badSet.add(c);
			}else if(c.rank == Rank.ACE) {
				ace = c;
			}else if(c.rank == Rank.SEVEN) {
				seven = c;
			}
		}
		
		ArrayList<Card> mh = game.getMainHand();
		ArrayList<Card> fu = game.getFaceUpHand();
		ArrayList<Card> fd = game.getFaceDownHand();
		
		assertTrue(mh.contains(mh.get(rs.whatCardToPlay(mh, fu, fd, two, true, true))));
		assertTrue(fu.contains(fu.get(rs.whatCardToPlay(mh, fu, fd, two, false, true) -5 )));
		assertTrue(fd.contains(fd.get(rs.whatCardToPlay(mh, fu, fd, two, false, false) -8 )));
		assertEquals(rs.whatCardToPlay(badSet, fu, fd, ace, true, true), -1);
		
		assertTrue(rs.whatCardToPlay(mh, fu, fd, null, true, true) > -1);
		assertTrue(rs.whatCardToPlay(mh, fu, fd, seven, true, true) > -1);

	}
	
	@Test
	void faceDownRS() {
		Rules game = new Rules();
		RandomStrategy rs = new RandomStrategy();
		
		
		ArrayList<Card> fd = game.getFaceDownHand();
		int temp = rs.whatFaceDownCard(fd);
		assertTrue(temp >= 0 && temp < 3);
		ArrayList<Card> empty = new ArrayList<>();
		assertEquals(rs.whatFaceDownCard(empty), 0);

	}
}
