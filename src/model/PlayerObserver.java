package model;

/*
    Observer that updates the label information (main UI components)
 */
public class PlayerObserver implements Observer{
    PalaceGameGUI view;

    public PlayerObserver(PalaceGameGUI view){
        this.view = view; 
    }

    public void updateUI(){
        this.view.updateUI();
    }
}
