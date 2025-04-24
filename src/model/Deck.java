package model;
import java.util.ArrayList;
import java.util.Stack;

public class Deck {
    private Stack<Card> deck = new Stack<Card>();
    
    public Deck(){
        ArrayList<Card> cards = Card.getShuffledCards();
        for(Card c: cards){
            this.deck.push(c);
        }
    }

    public Card takeCard(){
        if(!this.deck.empty()){
            return this.deck.pop();
        }
        return null;
    }

    public boolean isEmpty(){
        return this.deck.isEmpty();
    }

    public String toString() { 
        return this.deck.toString(); 
    }


}