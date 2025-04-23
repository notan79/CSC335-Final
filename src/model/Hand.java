package model;

import java.util.ArrayList;
import java.util.Iterator;

public class Hand implements Iterable<Card>{
    private final ArrayList<Card> mainHand = new ArrayList<>(5);
    private final ArrayList<Card> faceUpHand = new ArrayList<>(3);
    private final ArrayList<Card> faceDownHand = new ArrayList<>(3);

    public void addCard(Card c){
        this.mainHand.add(c);
    }

    public boolean addCardBeginning(Card c){
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

    public Card playCard(Card c) {        
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