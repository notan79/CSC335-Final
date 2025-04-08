package model;
import java.util.ArrayList;
import java.util.Stack;

public class Deck {
    private Stack<Card> deck = new Stack<Card>();

    public Deck(){
        ArrayList<Card> cards = Card.getShuffledCards();
        for(Card c: cards){
            deck.push(c);
        }
    }

    public Card takeCard(){
        if(!deck.empty()){
            return deck.pop();
        }
        return null;
    }


}
