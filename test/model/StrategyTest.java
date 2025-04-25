package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class StrategyTest {

	@Test
	void BestStrategy() {
		Rules game = new Rules();
		BestStrategy bs = new BestStrategy();
		
		ArrayList<Card> mh = game.getMainHand();
		ArrayList<Card> fu = game.getFaceUpHand();
		ArrayList<Card> fd = game.getFaceDownHand();
		
		ArrayList<Card> all = Card.getShuffledCards();
		Card two = null;
		for(Card c : all) {
			if(c.rank == Rank.TWO) {
				two = c;
				break;
			}
		}
		System.out.println(bs.whatCardToPlay(mh, fu, fd, two, true, true));
		
		
		
	}

}
