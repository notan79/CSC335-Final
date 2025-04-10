package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Hand implements Iterable<Card>{
    HashSet<Card> mainHand = new HashSet<>(5);
    HashSet<Card> faceUpHand = new HashSet<>(3);
    ArrayList<Card> faceDownHand = new ArrayList<>(3);

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
        if(mainHand.size() < 5){
            mainHand.add(c);
            return true;
        }
        return false;
    }

    public void playCard(Card c){
        if(mainHand.size() == 0){
            mainHand.remove(c);
        }else if(faceUpHand.size() != 0){
            faceUpHand.remove(c);
        }else{
            faceDownHand.remove(c);
        }
    }

    public int totalCards(){
        return this.mainHand.size() + this.faceUpHand.size() + this.faceDownHand.size();
    }
}
