package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Hand implements Iterable<Card>{
    private final HashSet<Card> mainHand = new HashSet<>(5);
    private final HashSet<Card> faceUpHand = new HashSet<>(3);
    private final ArrayList<Card> faceDownHand = new ArrayList<>(3);


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

    public void playCard(Card c){

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
    }

    public int totalCards(){
        return this.mainHand.size() + this.faceUpHand.size() + this.faceDownHand.size();
    }
}
