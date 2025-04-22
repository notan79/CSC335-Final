package view;

import model.Card;

public interface Observer {
	void cardPlayed(Card newCard);
}
