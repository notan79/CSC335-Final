package model;
import java.util.ArrayList;
import java.util.Collections;

public class Card{
    /*
    Class Card
    Author: James Montoya, Natalie Grubb, Cameron Liu, Nathan Crutchfield
    Purpose: A class that represents a Card from a deck of cards. This is done through a flyweight implementation.
    Inherits, Interfaces: None
    Constants:
        Private:
            static final ArrayList<Card> CARDS
    Instance Variables:
        Private:
            static final ArrayList<Card> CARDS
        Public:
            Rank rank
            Suit suit
    Constructors:
        Private:
            Card(Rank r, Suit s)
    Class Methods:
        Public:
            static ArrayList<Card> getShuffledCards()
    Instance Methods:
        Public:
            String toString()
     */

    private static final ArrayList<Card> CARDS = new ArrayList<>(52);
    public Rank rank;
    public Suit suit;

    static{
        for (Rank r: Rank.values()){
            for (Suit s: Suit.values()){
                CARDS.add(new Card(r,s));
            }
        }
    }

    //Constructor
    private Card(Rank r, Suit s){
        this.rank = r;
        this.suit = s;
    }

    public static ArrayList<Card> getShuffledCards(){
        /*
        Method getShuffledCards
        Purpose: Return a shuffled arraylist of Cards that are pulled from the store.
        Pre-conditions: None
        Post-Conditions: None
        Parameters: None
        Returns: an ArrayList of Card objects.
         */
        Collections.shuffle(CARDS);
        return new ArrayList<>(CARDS);
    }

    public String toString() { 
        return this.rank + " ";
    }

    
}