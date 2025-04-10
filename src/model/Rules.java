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

    
    public void swapHand(Card c1, Card c2) { 
        // swaps the card from the current hand (5) to the under hand (3)
        Hand player = players.get(this.turn.ordinal()); // this gets the current player

        if (player.getFaceUpHand().contains(c1) && player.getMainHand().contains(c2)) {
            
        } else { 
            System.out.println("Tried to swap cards that aren't in your hand");
            System.exit(1);
        }

        //player.faceUpHand;





        // Turn.values()[(this.turn.ordinal() + 1 ) % 4]; // this gets the next turn


        
        

    }

    // need to make a take hand function (implement takeall, and take one)

    public void takeCard() { 
        
    }

    public String toString() {
        String s = "PLAYER1 = " + players.get(0) + "\nPLAYER2 = " + players.get(1) + "\nPLAYER3 = " + players.get(2) + "\nPLAYER4 = " + players.get(3);
        return s; 
    }


    public static void main(String args[]){
        Rules game = new Rules();
        System.out.println(game.toString()); 
        System.out.println("DECK: " + game.deck);



    }
    

    
    



    
}
