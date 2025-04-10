package model;

import java.util.ArrayList;


public class Rules {

    private Deck deck; // the deck that each player (Hand.java) takes from in order to recieve a Card
    private ArrayList<Hand> players; // stores all the players 

    public Rules() { 
        deck = new Deck();
        players = new ArrayList<Hand>();

        for (int i = 0; i < 4; i++) { 
            // creates 4 players with full hands
            Hand player = new Hand(); 
            for (int j = 0; j < 11; j++) { // this gives the person their 11 cards 
                player.addCard(deck.takeCard());
            }
            players.add(player);
        }
    }
    


    



    
}
