package model;

public enum Suit {
    /*
    Class Suit
    Author: James Montoya, Natalie Grubb, Cameron Liu, Nathan Crutchfield
    Purpose: A class that implements the Suit of a card from a standard deck.
    Inherits, Interfaces, Constansts: None
    Instance Variables: None
    Constructors: None
    Class Methods: None
    Instance Methods:
        Public:
            String toString()
     */

    HEART, DIAMOND, CLUB, SPADE;
    
    @Override
    public String toString(){
        //Returns a string version of the class.
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