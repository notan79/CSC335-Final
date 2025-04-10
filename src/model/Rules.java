package model;

import java.util.ArrayList;


public class Rules {

    private Deck deck; // the deck that each player (Hand.java) takes from in order to recieve a Card
    private ArrayList<Hand> players; // stores all the players 
    private enum Turn {
        PLAYER1, PLAYER2, PLAYER3, PLAYER4;
    }
    private Turn turn = Turn.PLAYER1;
   

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

    // need to make a swap function 
    // need to make an add to hand function

    public void playCard(int num){
       Hand curPlayer = players.get(this.turn.ordinal());
    //    curPlayer.playCard(c);
    }


    public String toString() {
        String s = "PLAYER1 = " + players.get(0) + "\nPLAYER2 = " + players.get(1) + "\nPLAYER3 = " + players.get(2) + "\nPLAYER4 = " + players.get(3);
        return s; 
    }


    public static void main(String args[]){
        Rules game = new Rules();
        System.out.println(game.toString()); 
        System.out.println("DECK: " + game.deck);


        System.out.println(Turn.values()[(game.turn.ordinal()+1) % 4]);

    }
    

    
    



    
}
