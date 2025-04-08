
import java.util.ArrayList;
import java.util.Collections;

import model.Rank;
import model.Suit;

public class Card{

    private static final ArrayList<Card> CARDS = new ArrayList<Card>(52);
    public static Rank rank;
    public static Suit suit;

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