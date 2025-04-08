package model;
import java.util.ArrayList;
import java.util.Collections;

public class Card{

    public static void main(String[] args){
        System.out.println("Hello World");
    }

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

    public ArrayList<Card> getShuffledCards(){
        Collections.shuffle(CARDS);
        return CARDS;
    }

    
}