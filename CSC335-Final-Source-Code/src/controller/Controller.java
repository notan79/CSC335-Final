/*
    Controller to handle the communication between GUI and model
 */

package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.*;
import view.PalaceGameGUI;

public class Controller implements ActionListener{

    // Relevant variables to the gameplay
	private Rules model;
    private PalaceGameGUI view;
    private Strategy RS = new RandomStrategy();
    private Strategy BS = new BestStrategy();

    // Initialize the instance variables
	public Controller(Rules model, PalaceGameGUI view) {
		this.model = model;
        this.view = view;

        PileObserver po = new PileObserver(view);
        PlayerObserver plo = new PlayerObserver(view);
        model.addObserver(po);
        model.addObserver(plo);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        // On button press get action command
		String command = e.getActionCommand();

        // Do the relevant operatiin based on the action command
		if(command.equals("takeAll"))
            this.takeAllCards();
        else if(command.startsWith("CB")){
            this.cardButton(command);
        }else if(command.startsWith("FD")){
            this.playFaceDown(command);
        }
	}

    private void takeAllCards(){
        // Run the take all commands
        this.model.takeAll();
        this.view.nextTurn();
    }

    private void cardButton(String command){
        // Get the playIndex from the command
        int playIndex = Integer.parseInt(""+ command.charAt(2));
        boolean success = this.model.playCard(playIndex);
                    
        if (success) {        
            // Automatically draw a card if deck isn't empty
            if (!this.model.isDeckEmpty() && this.model.getMainHand().size() < 5) {
                this.model.takeCard();
            }
            this.view.nextTurn();
        }else{
            this.view.showCBMessage();
        }
    }

    private void playFaceDown(String command){
        // Get the playIndex from the command
        int playIndex = Integer.parseInt(""+ command.charAt(2));
        // Always allow attempt to play face down card
        boolean success = this.model.playCard(playIndex);
        
        if (!success) {
            this.view.invalidFaceDown();
        }

        this.view.nextTurn();
    }

    public void simulateAITurn(){
        // Get the hands from the model
        ArrayList<Card> mainHand = this.model.getMainHand(this.model.getTurn());
        ArrayList<Card> faceUpHand = this.model.getFaceUpHand(this.model.getTurn());
        ArrayList<Card> faceDownHand = this.model.getFaceDownHand(this.model.getTurn());

        boolean hasMainCards = !mainHand.isEmpty();
        boolean hasFaceUpCards = !faceUpHand.isEmpty();

        int cardIndex;

        // Run the correct strategy based on the player
        if(this.model.getTurn() == 2)
            cardIndex = this.RS.whatCardToPlay(mainHand, faceUpHand, faceDownHand, this.model.viewTopCard(), hasMainCards, hasFaceUpCards);
        else 
            cardIndex = this.BS.whatCardToPlay(mainHand, faceUpHand, faceDownHand, this.model.viewTopCard(), hasMainCards, hasFaceUpCards);

        boolean moveMade = false;

        // Check if a card was played, if not take deck
        if (cardIndex == -1) {
            this.model.takeAll();
        } else {
            moveMade = this.model.playCard(cardIndex);
            if (!moveMade) {
                // If invalid face-down card or unexpected issue, take all
                this.model.takeAll();
            }
        }

        // If card was played successfully and main hand is under 5, draw a card
        if (moveMade && !this.model.isDeckEmpty() && this.model.getMainHand(this.model.getTurn()).size() < 5) {
            this.model.takeCard();
        }

        this.view.nextTurn();
    }

    // Getters from model for the view...
    public boolean isValidMove(Card c){
        return this.model.isValidMove(c);
    }

    public ArrayList<Card> getMainHand(int playerIndex){
        return this.model.getMainHand(playerIndex);
    }

    public ArrayList<Card> getFaceUpHand(int playerIndex) {
        return this.model.getFaceUpHand(playerIndex);
    }
    
    public ArrayList<Card> getFaceDownHand(int playerIndex) {
        return this.model.getFaceDownHand(playerIndex);
    }

    public int getTurn(){
        return this.model.getTurn();
    }

    public boolean nextTurn(){
        return this.model.nextTurn();
    }

    public boolean isDeckEmpty(){
        return this.model.isDeckEmpty();
    }

    public boolean hasValidMove(){
        return this.model.hasValidMove();
    }

    public Card viewTopCard(){
        return this.model.viewTopCard();
    }
	
	
}
