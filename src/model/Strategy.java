package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public interface Strategy {

    // boolean hasMainCards: checks if there are stil cards in the players main deck
    // boolean hasFaceUpCards: checks if there are stil cards in the players face up deck
    // Returns the index of the card to play, if theres no valid move it returns -1
    int whatCardToPlay(HashSet<Card> mainHand, HashSet<Card> faceUpHand, ArrayList<Card> faceDownHand, Stack<Card> pile, boolean hasMainCards, boolean hasFaceUpCards);
    
    // This returns an array of two cards to swap
    // Returns null if you don't want to swap
    Card[] whatCardsToSwap(HashSet<Card> mainHand, HashSet<Card> faceUpHand);


    // Which face down card to play when no other options are available
    // Returns the index of the face down card to play
    int whatFaceDownCard(ArrayList<Card> faceDownHand);



}