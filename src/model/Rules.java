package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;


public class Rules {
    public static void main(String args[]){
        Rules game = new Rules();
        System.out.println(game.toString()); 
        System.out.println("DECK: " + game.deck);


        System.out.println(Turn.values()[(game.turn.ordinal()+1) % 4]);

    }

    private enum Turn {
        PLAYER1, PLAYER2, PLAYER3, PLAYER4;
    }

    private Deck deck; // the deck that each player (Hand.java) takes from in order to recieve a Card
    private Stack<Card> pile;
    private ArrayList<Hand> players; // stores all the players 
    private Turn turn = Turn.PLAYER1;
   

    public Rules() { 
        this.deck = new Deck();
        this.players = new ArrayList<Hand>();
        this.pile = new Stack<>();

        for (int i = 0; i < 4; i++) { 
            // creates 4 players with full hands
            Hand player = new Hand(); 
            for (int j = 0; j < 11; j++) { // this gives the person their 11 cards 
                player.addCard(this.deck.takeCard());
            }
            this.players.add(player);
        }
        this.pile.push(this.deck.takeCard());
    }

    
    // Precon: c1 and c2 are in the player's hand
    public void swapCards(Card c1, Card c2) { 
        // swaps the card from the current hand (5) to the face up hand (3)
        Hand player = this.players.get(this.turn.ordinal()); // this gets the current player
        player.swapCards(c1, c2);
    }

    public boolean playCard(int num){
       Hand curPlayer = this.players.get(this.turn.ordinal());
       Card c = curPlayer.getCard(num);
       if(!this.isValidMove(c))
            return false; 
    
       this.pile.push(curPlayer.playCard(c));  
       if(this.pile.peek().rank == Rank.TEN){
            this.pile = new Stack<>();
       } 
       return true;
    }

    // Returns true if the game is continuing, and false otherwise. Updates to the next turn
    public boolean nextTurn(){
        for(Hand p : this.players){
            if(p.totalCards() == 0)
                return false;
        }
       this.turn = Turn.values()[(this.turn.ordinal() + 1 ) % 4];
       return true;
    }

    // need to make a take hand function (implement takeall, and take one)
    public void takeCard() { 
        Hand curPlayer = this.players.get(this.turn.ordinal());
        curPlayer.addCard(this.deck.takeCard());
    }

    public void takeAll(){
        Hand curPlayer = this.players.get(this.turn.ordinal());
        while(!this.pile.empty()){
            curPlayer.addCard(this.pile.pop());
        }
    }

    public boolean isValidMove(Card c){
        // Can always play a ten or a two, or if pile is empty
        if(c.rank == Rank.TEN || c.rank == Rank.TWO || this.pile.empty())
            return true;

        Card topOfPile = this.pile.peek();

        // If top is a seven, play lte
        if(topOfPile.rank == Rank.SEVEN)
            return c.rank.ordinal() <= topOfPile.rank.ordinal();

        // If not a seven, play gte
        return c.rank.ordinal() >= topOfPile.rank.ordinal();
    }

    public boolean hasValidMove(){
        Hand curPlayer = this.players.get(this.turn.ordinal());
        HashSet<Card> curHand;
        int cardCount = curPlayer.totalCards();

        // Main hand
        if(cardCount > 6)
            curHand = curPlayer.getMainHand();
        
        // Face up hand
        else if(cardCount > 3)
            curHand = curPlayer.getFaceUpHand();

        // Has to "guess"
        else 
            return true;
        
        for(Card c : curHand){
            if(this.isValidMove(c))
             return true;
        }
        return false;
    }

    public String toString() {
        String s = "PLAYER1 = " + this.players.get(0) + "\nPLAYER2 = " + this.players.get(1) + "\nPLAYER3 = " + this.players.get(2) + "\nPLAYER4 = " + this.players.get(3);
        return s; 
    }    
}
