package model;

import java.util.ArrayList;
import java.util.HashSet;

public interface Strategy {
    /*
    Class Strategy
    Author: James Montoya, Natalie Grubb, Cameron Liu, Nathan Crutchfield
    Purpose: This class is the Strategy interface. It is used as to implement different strategies of playing Palace.
    Inherits, Interfaces, Constants: This class is an Interface.
    Instance variables: None
    Constructors: None
    Class Methods:
        int whatCardToPlay(ArrayList<Card> mainHand, ArrayList<Card> faceUpHand, ArrayList<Card> faceDownHand, Card topOfPile, boolean hasMainCards, boolean hasFaceUpCards)
        Card[] whatCardsToSwap(HashSet<Card> mainHand, HashSet<Card> faceUpHand);
        int whatFaceDownCard(ArrayList<Card> faceDownHand);
    Instance Methods: None
     */

    /*
    Method whatCardToPlay
    Purpose: Returns the index of the card to play, if theres no valid move it returns -1
    Pre-condition: None
    Post-Condition: None
    Parameters: 
        ArrayList<Card> mainHand
        ArrayList<Card> faceUpHand
        ArrayList<Card> faceDownHand
        Card topOfPile
        boolean hasMainCards
        boolean hasFaceUpCards
    Returns: an integer
     */
    int whatCardToPlay(ArrayList<Card> mainHand, ArrayList<Card> faceUpHand, ArrayList<Card> faceDownHand, Card topOfPile, boolean hasMainCards, boolean hasFaceUpCards);

    /*
    Method whatFaceDownCard
    Purpose: Determines which face down card to play when no other options are available
    Pre-condition: None
    Post-Condition: None
    Parameters: ArrayList<Card> faceDownHand
    Returns: an integer representing the index of the face down card to play
     */
    int whatFaceDownCard(ArrayList<Card> faceDownHand);



}