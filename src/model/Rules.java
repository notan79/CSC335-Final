package model;

import java.util.ArrayList;
import java.util.Stack;

public class Rules {

    private enum Turn {
        PLAYER1, PLAYER2, PLAYER3, PLAYER4;
    }

    private Deck deck; // the deck that each player (Hand.java) takes from in order to receive a Card
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
                player.addCardBeginning(this.deck.takeCard());
            }
            this.players.add(player);
        }
        this.pile.push(this.deck.takeCard());
    }


    public Card viewTopCard() { 
        if (this.pile.isEmpty()) {
            return null;
        }
        return this.pile.peek();
    }

    // Precon: c1 and c2 are in the player's hand
//    public void swapCards(Card c1, Card c2) { 
//        // swaps the card from the current hand (5) to the face up hand (3)
//        Hand player = this.players.get(this.turn.ordinal()); // this gets the current player
//        player.swapCards(c1, c2);
//    }

    public boolean playCard(int num) {
        Hand curPlayer = this.players.get(this.turn.ordinal());
        Card c = curPlayer.getCard(num);
        
        if (c == null) {
            return false;
        }
        // Determine if this is a face-down card
        boolean isFaceDown = curPlayer.getMainHand().isEmpty() && curPlayer.getFaceUpHand().isEmpty();
        
        if (isFaceDown) {
            
            // Remove the card from player's hand
            Card playedCard = curPlayer.playCard(c);
            if (playedCard == null) {
                return false;
            }
            
            // Check if it would be a valid move
            boolean validMove = this.isValidMove(playedCard);
            // debug check can delete later 
            
            // Add the card to the pile regardless of if its valid or not
            this.pile.push(playedCard);
            
            if (!validMove) {
                // if its an invalid move the player must take all cards
                // debug check can delete later 
                this.takeAll();
                return false;
            }
            
            // Special rule: clear pile on TEN
            if (playedCard.rank == Rank.TEN) {
                this.pile.clear();
            }
            
            return true;
        } 

        // For face-up and main hand cards
        else {
            // Check if the move is valid before removing from hand
            if (!this.isValidMove(c)) {
                return false; // Invalid move, don't even add to pile
            }
            
            // Move is valid, now remove the card from the player's hand
            Card playedCard = curPlayer.playCard(c);
            
            if (playedCard == null) {
                return false;
            }
            
            // Push the card to the pile
            this.pile.push(playedCard);
            
            // Special rule: clear pile on TEN
            if (playedCard.rank == Rank.TEN) {
                this.pile.clear();
            }
            
            return true;
        }
    }

    // Returns true if the game is continuing, and false otherwise. Updates to the next turn
    public boolean nextTurn(){
        for(Hand p : this.players){
            if(p.totalCards() == 0)
                return false;
        }
        if(!this.pile.isEmpty() && this.pile.peek().rank == Rank.FIVE)
            this.turn = Turn.values()[(this.turn.ordinal() + 2 ) % 4];
        else
            this.turn = Turn.values()[(this.turn.ordinal() + 1 ) % 4];
       return true;
    }

    // Returns true if the game is continuing, and false otherwise. Updates to the next turn
    public boolean nextTurn(boolean start){
        for(Hand p : this.players){
            if(p.totalCards() == 0)
                return false;
        }
        this.turn = Turn.values()[(this.turn.ordinal() + 1 ) % 4];
       return true;
    }

    // need to make a take hand function (implement takeall, and take one)
    public Card takeCard() { 
        Hand curPlayer = this.players.get(this.turn.ordinal());
        Card temp = this.deck.takeCard();
        if(temp == null)
            return null;
        curPlayer.addCard(temp);
        return temp;
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
        ArrayList<Card> curHand;
        int cardCount = curPlayer.totalCards();

        // Main hand
        if(cardCount > 6 || curPlayer.getMainHand().size() > 0)
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

    public boolean isDeckEmpty(){
        return this.deck.isEmpty();
    }

    public ArrayList<Card> getFaceUpHand(){
        return new ArrayList<>(this.players.get(this.turn.ordinal()).getFaceUpHand());
    }

    public ArrayList<Card> getFaceDownHand(){
        return this.players.get(this.turn.ordinal()).getFaceDownHand();
    }

    public ArrayList<Card> getMainHand(){
        return new ArrayList<>(this.players.get(this.turn.ordinal()).getMainHand());
    }

    public ArrayList<Card> getFaceUpHand(int num){
        return new ArrayList<>(this.players.get(num).getFaceUpHand());
    }

    public ArrayList<Card> getFaceDownHand(int num){
        return this.players.get(num).getFaceDownHand();
    }

    public ArrayList<Card> getMainHand(int num){
        return new ArrayList<>(this.players.get(num).getMainHand());
    }

    public int getTurn(){
        return this.turn.ordinal();
    }
    
    // will always work if you use index 0-3
    public Hand getPlayer(int player) {
    	return this.players.get(player);
    }
    
    public void clearHand() {
    	this.players.get(turn.ordinal()).clear(); // this gets the player (Hand.java) and clears the hand
    	
    }

    public String toString() {
        String s = "PLAYER1 = " + this.players.get(0) + "\nPLAYER2 = " + this.players.get(1) + "\nPLAYER3 = " + this.players.get(2) + "\nPLAYER4 = " + this.players.get(3);
        return s; 
    }    
}