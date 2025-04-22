package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.Card;
import model.Rules;

import model.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Controller implements ActionListener {

    private Rules model;
    private ArrayList<CardButton> observers = new ArrayList<>();
    private View view;

    public Controller(Rules model, View v) {
        this.model = model;
        this.view = v;
    }


    public void addObserver(CardButton b){
        this.observers.add(b);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if(!this.model.hasValidMove()){
            this.model.takeCard();
            this.updateView();
        }

        else if (cmd.startsWith("C")) {
            int index = Integer.parseInt(cmd.substring(1));
            boolean success = this.model.playCard(index);

            if (success) {
                this.model.takeCard();
                this.updateView();
            } else {
                System.out.println("Invalid move.");
            }
        }

        // Handle face-up cards if needed
        else if (cmd.startsWith("F")) {
            if(this.model.getMainHand().size() > 0)
                System.out.println("Face-up play not allowed: main hand not empty.");
            else{
                int index = Integer.parseInt(cmd.substring(1));
                boolean success = this.model.playCard(index);

                if (success) {
                    this.model.takeCard();
                    this.updateView();
                } else {
                    System.out.println("Invalid move.");
                }
            }
        }

        // You could also handle "deck" click for drawing manually if you want
        else if (cmd.equals("deck")) {
            this.model.takeCard();
            this.updateView();
        }
    }

    public void updateView() {
        this.view.refresh(); // Custom method in View to re-draw hand and top card
    }

    public Card viewTopCard() {
        return this.model.viewTopCard();
    }

    public ArrayList<Card> getFaceUpHand() {
        return this.model.getFaceUpHand();
    }

    public ArrayList<Card> getFaceDownHand() {
        return this.model.getFaceDownHand();
    }

    public ArrayList<Card> getMainHand() {
        return this.model.getMainHand();
    }
}
