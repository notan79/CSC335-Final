package controller;

/*
    Simple interface to allow for the model to directly update the UI after
    an action is taken to update the game.
 */
public interface Observer {
    void updateUI();
}
