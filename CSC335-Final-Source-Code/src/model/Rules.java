package model;

import java.util.ArrayList;
import java.util.Stack;

import controller.Observer;


/* Authors: Cameron Liu, Nathan Crutchfield, Natalie Grubb, James Montoya
 * 
 * Purpose: Represents the overview of the game state, essentially acting as the "rules" of the class. 
 * It is basically the skeleton of the basic game, storing players, and creating one main deck for all players 
 * to draw their cards from, and manages which player can move, etc. 
 * 
 * Instance Variables: 
 *      deck: Stack of cards to represent an actual deck 
 *      pile: Stack of cards to represent the placed cards pile
 *      players: ArrayList<Hand> of players (Hand.java) in order to store all the players being created
 *      turn: Turn enum that represents which player's turn it is 
 *      observers: ArrayList<Observer> that stores all observers necessary for class to interact with UI
 * 
 * Methods: 
 *      public void addObserver(Observer): adds observer to the ArrayList of observers
 *      public void removeObserver(Observer): removes observe from the ArrayList of observers
 *      public void notifyObservers(): updates the UI in correspondence to the observer 
 *      public Card viewTopCard(): is just the method peek() in order card game implementations where it returns the top card from the deck. 
 *      public boolean playCard(num): plays the current card that is relative to the int in the parameters 
 *      public boolean nextTurn(): determines if there is a next turn, if so, it updates the current player to the next one 
 *      public boolean nextTurn(boolean): determines if there is a next turn whilst also using given boolean in paramters. if so, it updates the current player to the next one. 
 *      public Card takeCard(): method that takes one card and gives it to the current player  
 *      public Card takeAll(): method that takes all cards and gives it to the current player 
 *      public boolean isValidMove(Card): methods that returns a boolean if the card in the params is a valid move for the current player
 *      public boolean hasValidMove(): method that returns a boolean if the current player has a valid move (basically can play a card)
 *      public boolean isDeckEmpty(): returns if the deck has 0 cards or not
 *   
 */

public class Rules {

    private enum Turn {
        PLAYER1, PLAYER2, PLAYER3, PLAYER4;
    }

    private Deck deck; // the deck that each player (Hand.java) takes from in order to receive a Card
    private Stack<Card> pile;
    private ArrayList<Hand> players; // stores all the players 
    private Turn turn = Turn.PLAYER1;
    private ArrayList<Observer> observers = new ArrayList<>();

    public Rules() { 
        this.deck = new Deck();
        this.players = new ArrayList<Hand>();
        this.pile = new Stack<>();

        for (int i = 0; i < 4; i++) { 
            // creates 4 players with full hands
            Hand player = new Hand(); 
            for (int j = 0; j < 11; j++) { // this gives the person their 11 cards in their main hand, top hand, and face down hand. 
                player.addCardBeginning(this.deck.takeCard());
                this.notifyObservers();
            }
            this.players.add(player);
        }
        this.pile.push(this.deck.takeCard());
        this.notifyObservers();
    }

    public void addObserver(Observer o){
        this.observers.add(o);
    }

    public void removeObserver(Observer o){
        this.observers.remove(o);
    }

    private void notifyObservers(){
        for(Observer o : this.observers){
            // updates UI in correspondence to the observers
            o.updateUI();
        }
    }

    public Card viewTopCard() { 
        // this returns and gets the top card of the pile. essentially is just peek
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
            
            this.notifyObservers();
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
            this.notifyObservers();
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
        // method that takes one card and gives it to the current player (whoever turn it is)
        Hand curPlayer = this.players.get(this.turn.ordinal());
        Card temp = this.deck.takeCard();
        if(temp == null)
            return null;
        curPlayer.addCard(temp);
        return temp;
    }

    public void takeAll(){
        // current player takes the whole pile 
        Hand curPlayer = this.players.get(this.turn.ordinal());
        while(!this.pile.empty()){
            curPlayer.addCard(this.pile.pop()); 
        }
        this.notifyObservers();
    }


    // game state method that just determines if the current move is a valid one or not
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

    // another game state method to determine if the player has a valid move or not 
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

    // checks if the current deck is empty 
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
    	this.players.get(this.turn.ordinal()).clear(); // this gets the player (Hand.java) and clears the hand
    	
    }

    public String toString() {
        String s = "PLAYER1 = " + this.players.get(0) + "\nPLAYER2 = " + this.players.get(1) + "\nPLAYER3 = " + this.players.get(2) + "\nPLAYER4 = " + this.players.get(3);
        return s; 
    }    
}