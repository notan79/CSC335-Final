package model;

import java.util.ArrayList;
import java.util.Iterator;

public class Hand implements Iterable<Card>{
    /*
    Class Iterable
    Author: James Montoya, Cameron Liu, Natalie Grubb, Nathan Crutchfield
    Purpose: This class represents a player in the palace card game. 
    Inherits, Interfaces, Constants:
        Interfaces: Iterable<T>
        Constants:
            Private:
                final ArrayList<Card> mainHand
                final ArrayList<Card> faceUpHand
                final ArrayList<Card> faceDownHand
    Instance Variables: None
    Constructors: None
    Class Methods:
        Public: 
            Iterator<Card> iterator()
    Instance Methods:
        Public:
            void addCard(Card c)
            boolean addCardBeginning(Card c)
            Card playCard(Card c)
            Card getCard(int num)
            void swapCards(Card c1, Card c2)
            int totalCards()
            ArrayList<Card> getFaceUpHand()
            ArrayList<Card> getMainHand()
            ArrayList<Card> getFaceDownHand()
            String toString()
     */
    private final ArrayList<Card> mainHand = new ArrayList<>(5);
    private final ArrayList<Card> faceUpHand = new ArrayList<>(3);
    private final ArrayList<Card> faceDownHand = new ArrayList<>(3);

    public void addCard(Card c){
        /*
        Method addCard
        Purpose: adds a card to the player's main hand.
        Pre-Condition: None
        Post-Condition: None
        Parameters: Card c
        Returns: None
         */
        this.mainHand.add(c);
    }

    public boolean addCardBeginning(Card c){
        /*
        Method addCardBeginning
        Purpose: This method adds cards to a hand at the start of a game, making sure each hand stays the right size.
        Pre-Condition: None
        Post-Condition: None
        Parameters: Card c
        Returns: a boolean value
         */

        // Add to face down first
        if(this.faceDownHand.size() < 3){
            this.faceDownHand.add(c);
            return true;
        }
        // Add to faceUp hand next
        else if (this.faceUpHand.size() < 3) {
            this.faceUpHand.add(c);
            return true;
        }
        // Add to mainHand last
        else if(this.mainHand.size() < 5){
            this.mainHand.add(c);
            return true;
        }
        //each hand is full
        return false;
    }

    public Card playCard(Card c) { 
        /*
        Method playCard
        Purpose: This method plays a card from a hand. 
        Pre-Condition: None
        Post-Condition: None
        Parameters: Card c
        Returns: The Card object that will be played. 
         */       

        // Remove from the mainHand first
        if(this.mainHand.contains(c)) {
            this.mainHand.remove(c);
            return c;
        }
        // Remove from faceUp next
        else if(this.faceUpHand.contains(c)) {
            this.faceUpHand.remove(c);
            return c;
        }
        // Remove from faceDown last
        else if(this.faceDownHand.contains(c)) {
            this.faceDownHand.remove(c);
            return c;
        }
        return null;
    }

    public Card getCard(int num){
        /*
        Method getCard
        Purpose: Gets a card from a hand at the index provided. 
        Pre-Condition: None
        Post-Condition: None
        Parameters: int num
        Returns: a Card object
        */
        // Check if the index is in the main hand range
        if(num < this.mainHand.size()){
            return this.mainHand.get(num);
        } 
        // ADJUST index and check if it's in the face up hand range
        num -= this.mainHand.size();
        if(num < this.faceUpHand.size()){
            return this.faceUpHand.get(num);
        }
        // Adjust index again and check if it's in the face down hand range
        num -= this.faceUpHand.size();
        if(num < this.faceDownHand.size()){
            return this.faceDownHand.get(num);
        }
        return null;
    }


    // C1 is from mainHand and c2 is from faceUp hand
    public void swapCards(Card c1, Card c2){
        /*
        Method swapCards
        Purpose: Swaps a card in the main hand with a card in the face up hand.
        Pre-Condition: None
        Post-Condition: None
        Parameters: Card c1, Card c2
        Returns: None
         */
        this.mainHand.remove(c1);
        this.faceUpHand.remove(c2);

        this.mainHand.add(c2);
        this.faceUpHand.add(c1);
    }

    public int totalCards(){
        /*
        Method totalCards
        Purpose: Gets the total amount of cards in all hands. 
        Pre-Condition: None
        Post-Condition: None
        Parameters: None
        Returns: an integer
         */
        return this.mainHand.size() + this.faceUpHand.size() + this.faceDownHand.size();
    }


    public ArrayList<Card> getFaceUpHand() {
        return new ArrayList<>(this.faceUpHand);
    }

    public ArrayList<Card> getMainHand() {
        return new ArrayList<>(this.mainHand);
    }

    // added a getter for the face down hand
    public ArrayList<Card> getFaceDownHand() {
        return new ArrayList<>(this.faceDownHand);
}
    
    public StringBuilder getMainHandToString() { 
    		StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.mainHand.size(); i++) {
            sb.append(mainHand.get(i));
        }
        return sb;
    }
    
    public StringBuilder getFaceUpHandToString() { 
    	StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.faceUpHand.size(); i++) {
            sb.append(faceUpHand.get(i));
        }
        return sb;
    }

    public StringBuilder getFaceDownToString() { 
    	StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.faceDownHand.size(); i++) {
            sb.append(faceDownHand.get(i));
        }
        return sb;
    }



    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("Main Hand: ");
        for(Card c : this.mainHand){
            s.append(c);
        }

        s.append("\nFace up Hand: ");
        for(Card c : this.faceUpHand){
            s.append(c);
        }

        s.append("\nFace down Hand: ");
        for(Card c : this.faceDownHand){
            s.append(c);
        }
        s.append("\n"); // delete later
        return s.toString();
    }

    @Override
    public Iterator<Card> iterator(){
        ArrayList<Card> temp = new ArrayList<>(this.mainHand);
        for(Card c : this.faceUpHand){
            temp.add(c);
        }
        for(Card c: this.faceDownHand){
            temp.add(c);
        }
        return temp.iterator();
    }
}