package model;

public enum Rank {

    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;

    @Override
    public String toString(){
        int ord = this.ordinal();
        if(ord <= 8){
            return "" + (ord + 2);
        }else if(this == Rank.JACK){
            return "J";
        }else if(this == Rank.QUEEN){
            return "Q";
        }else if(this == Rank.KING){
            return "K";
        }else{
            return "A";
        }
    }
}