package model;

public class PlayerObserver implements Observer{
    PalaceGameGUI view;

    public PlayerObserver(PalaceGameGUI view){
        this.view = view; 
    }

    public void updateUI(){
        this.view.updateUI();
    }
}
