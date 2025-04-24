package model;

/*
    Observer that updates the pile information
 */
public class PileObserver implements Observer{
    PalaceGameGUI view;

    public PileObserver(PalaceGameGUI view){
        this.view = view; 
    }

    public void updateUI(){
        // Update the pile and deck
        this.view.updatePileLabel();
        this.view.updateDeckLabel();
    }
}
