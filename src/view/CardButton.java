package view;

import model.Card;
import javax.swing.JButton;

public class CardButton extends JButton implements Observer {
    public CardButton(String text){
        super(text);
    }

    @Override
    public void cardPlayed(Card newCard){
        this.setText(newCard.toString());
    }
}
