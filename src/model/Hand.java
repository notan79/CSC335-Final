package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Hand implements Iterable<Card>{
    private final ArrayList<Card> mainHand = new ArrayList<>(5);
    private final ArrayList<Card> faceUpHand = new ArrayList<>(3);
    private final ArrayList<Card> faceDownHand = new ArrayList<>(3);

    public boolean addCard(Card c){
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
        return false;
    }

    // Precondition: a valid card is picked
    public Card playCard(Card c){        
        // Remove from mainHand first
        if(this.mainHand.size() != 0){
            this.mainHand.remove(c);
        }
        // Remove from faceUp next
        else if(this.faceUpHand.size() != 0){
            this.faceUpHand.remove(c);
        }
        // Remove from faceDown last
        else{
            this.faceDownHand.remove(c);
        }
        return c; 
    }

    // Precondition: 0 <= num < this.totalCards
    public Card getCard(int num){
        if(this.totalCards() > 6){
            return this.mainHand.get(num);
        }else if(this.totalCards() > 3){
            return this.faceUpHand.get(num);
        }else{
            return this.faceDownHand.get(num);
        }
    }

    // C1 is from mainHand and c2 is from faceUp hand
    public void swapCards(Card c1, Card c2){
        this.mainHand.remove(c1);
        this.faceUpHand.remove(c2);

        this.mainHand.add(c2);
        this.faceUpHand.add(c1);
    }

    public int totalCards(){
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