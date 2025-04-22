package model;

public enum Suit {

    HEART, DIAMOND, CLUB, SPADE;
    
    @Override
    public String toString(){
        if(this == Suit.HEART){
            return "\u2665";
        }else if(this == Suit.DIAMOND){
            return "\u2666";
        }else if(this == Suit.CLUB){
            return "\u2663";
        }else{
            return "\u2660";
        }
    }
}