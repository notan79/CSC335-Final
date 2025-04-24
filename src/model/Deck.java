package model;
import java.util.ArrayList;
import java.util.Stack;

public class Deck {
    /*
    Class Deck
    Author: James Montoya, Natalie Grubb, Cameron Liu, Nathan Crutchfield
    Purpose: This class represents a standard Deck of cards. 
    Inherites, Interfaces, Constants: None
    Instance variables:
        Private:
            Stack<Card> deck
    Constructors:
        Public:
            Deck()
    Class Methods: None
    Instance Methods:
        Public:
            boolean isEmpty()
            Card takeCard()
     */
    
    private Stack<Card> deck = new Stack<Card>();
    
    public Deck(){
        ArrayList<Card> cards = Card.getShuffledCards();
        for(Card c: cards){
            this.deck.push(c);
        }
    }

    public Card takeCard(){
        /*
        Method takeCard
        Purpose: This method pops a Card object of the top of the stack and returns it.
        Pre-condition: None
        Post-condition: None
        Parameters: None
        Returns: a Card object
         */
        if(!this.deck.empty()){
            return this.deck.pop();
        }
        return null;
    }

    public boolean isEmpty(){
        /*
        Mehod isEmpty
        Purpose: checks if the stack of Card objects is empty.
        Returns: a Boolean value.
         */
        return this.deck.isEmpty();
    }

    public String toString() { 
        return this.deck.toString(); 
    }


}