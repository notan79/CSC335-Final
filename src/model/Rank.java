package model;

public enum Rank {
    /*
    Class Rank
    Author: James Montoya, Natalie Grubb, Cameron Liu, Nathan Crutchfield
    Purpose: A class that represents the Rank of a card from a standard deck.
    Inherits, Interfaces, Constants: None
    Instance Variables: None
    Constructors: None
    Class Methods:
        Public:
            String toString()
        Private:
            None
    Instance Methods: None
     */

    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;

    @Override
    public String toString(){
        //Returns a string version of the class.
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