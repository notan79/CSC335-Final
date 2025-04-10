package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Hand implements Iterable<Card>{
    private final HashSet<Card> mainHand = new HashSet<>(5);
    private final HashSet<Card> faceUpHand = new HashSet<>(3);
    private final ArrayList<Card> faceDownHand = new ArrayList<>(3);


    @Override
    public Iterator<Card> iterator(){
        ArrayList<Card> temp = new ArrayList<>(mainHand);
        for(Card c : faceUpHand){
            temp.add(c);
        }
        for(Card c: faceDownHand){
            temp.add(c);
        }
        return temp.iterator();
    }

    public boolean addCard(Card c){
        // Add to face down first
        if(faceDownHand.size() < 3){
            faceDownHand.add(c);
            return true;
        }
        // Add to faceUp hand next
        else if (faceUpHand.size() < 3) {
            faceUpHand.add(c);
            return true;
        }
        // Add to mainHand last
        else if(mainHand.size() < 5){
            mainHand.add(c);
            return true;
        }
        return false;
    }

    // Precondition: a valid card is picked
    public boolean playCard(int num){

        Card c = null;; 
        int i = 0;
        for(Card temp: this){
            if(i == num){
                c = temp;
                break;
            }
            ++i;
        }
        
        // Remove from mainHand first
        if(mainHand.size() != 0){
            mainHand.remove(c);
        }
        // Remove from faceUp next
        else if(faceUpHand.size() != 0){
            faceUpHand.remove(c);
        }
        // Remove from faceDown last
        else{
            faceDownHand.remove(c);
        }
        return true;
    }

    // Precondition: 0 <= num < this.totalCards
    public Card getCard(int num){
        int i = 0;
        for(Card c: this){
            if(i == num)
                return c;
            ++i;
        }
        // Cannot happen
        return null;
    }

    public int totalCards(){
        return this.mainHand.size() + this.faceUpHand.size() + this.faceDownHand.size();
    }


    public HashSet<Card> getFaceUpHand() {
        return new HashSet<>(faceUpHand);
    }

    public HashSet<Card> getMainHand() {
        return new HashSet<>(mainHand);
    }

    // added a getter for the face down hand
    public ArrayList<Card> getFaceDownHand() {
        return new ArrayList<>(faceDownHand);
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
}
