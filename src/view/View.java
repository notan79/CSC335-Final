package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import model.Card;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Rules;
import model.Hand;

public class View extends JFrame {

	private Controller controller;
	private ArrayList<CardButton> mainButtons = new ArrayList<>();
	private ArrayList<CardButton> faceUpButtons = new ArrayList<>();
	private ArrayList<CardButton> faceDownButtons = new ArrayList<>();
	private JPanel mainPanel;
	private Rules model;

	private JButton deckButton;
	
	public View() {
		this.setTitle("Palace Game Demo");
		this.setSize(1920,1080);
		this.mainPanel = new JPanel();
		this.model = new Rules();                 
    	this.controller = new Controller(this.model, this);

		this.setUp();
		this.setVisible(true);
	}
	
	public void setUp() {
		
		//setting up the main panel
		this.mainPanel.removeAll();
		this.add(this.mainPanel);
		this.mainPanel.setLayout(null);


		// pile deck
		this.deckButton = new JButton(this.controller.viewTopCard().toString());
		this.deckButton.setBounds(740, 400, 70, 50);
		this.deckButton.setActionCommand("deck");
		this.mainPanel.add(this.deckButton);


		// PLAYER 1:
		ArrayList<Card> faceUp = this.controller.getFaceUpHand();
		for(int i = 0; i < 3; ++i){
			int j = i;
			CardButton temp = new CardButton(faceUp.get(i).toString());
			temp.setBounds(660+i*80, 700, 70, 50);
			temp.setActionCommand("F" + String.valueOf(j));
			temp.addActionListener(this.controller);
			this.mainPanel.add(temp);
			this.faceUpButtons.add(temp);
		}


		ArrayList<Card> main = this.controller.getMainHand();
		for(int i = 0; i < 5; ++i){
			int j = i;
			CardButton temp = new CardButton(main.get(i).toString());
			temp.setBounds(580+i*80, 650, 70, 50);
			temp.setActionCommand("C" + String.valueOf(j));
			temp.addActionListener(this.controller);
			this.mainPanel.add(temp);
			this.mainButtons.add(temp);

			this.controller.addObserver(temp);
		}

		ArrayList<Card> faceDown = this.controller.getFaceDownHand();
		for(int i = 0; i < 3; ++i){
			int j = i;
			CardButton temp = new CardButton(faceDown.get(i).toString());
			temp.setBounds(660+i*80, 750, 70, 50);
			temp.setActionCommand("D" + String.valueOf(j));
			temp.addActionListener(this.controller);
			this.mainPanel.add(temp);
			this.faceUpButtons.add(temp);
		}

		// PLAYER 2:
		this.controller.nextTurn();



		

		//adding a window listener for closing the app
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		
	}

	public void refresh() {
		// Clear old buttons
		for (CardButton b : this.mainButtons){
			this.remove(b);
			this.mainPanel.remove(b);
		}
		for (CardButton b : this.faceUpButtons){
			this.remove(b);
			this.mainPanel.remove(b);
		} 

		for (CardButton b : this.faceDownButtons){
			this.remove(b);
			this.mainPanel.remove(b);
		} 
	
		this.mainButtons.clear();
		this.faceUpButtons.clear();
		this.faceDownButtons.clear();
	
		this.drawMainHand();
		this.drawFaceUpHand();
		this.drawFaceDownHand();
	
		// Update pile button
		if(this.controller.viewTopCard() == null){
			this.deckButton.setText("Empty");
		}else
			this.deckButton.setText(this.controller.viewTopCard().toString());
	
		this.revalidate();
		this.repaint();
	}

	private void drawMainHand(){
		// Update main hand buttons
		ArrayList<Card> main = this.controller.getMainHand();
		System.out.println("Main: " + main);
		for (int i = 0; i < main.size(); i++) {
			System.out.println("Creating: " + main.get(i).toString());
			CardButton temp = new CardButton(main.get(i).toString());
			temp.setBounds(580 + i * 80, 650, 70, 50);
			temp.setActionCommand("C" + i);
			temp.addActionListener(this.controller);
			this.add(temp);
			this.mainPanel.add(temp);
			this.mainButtons.add(temp);
		}
	}

	private void drawFaceUpHand(){
		// Update face-up hand buttons
		ArrayList<Card> faceUp = this.controller.getFaceUpHand();
		for (int i = 0; i < faceUp.size(); i++) {
			CardButton temp = new CardButton(faceUp.get(i).toString());
			temp.setBounds(660 + i * 80, 700, 70, 50);
			temp.setActionCommand("F" + i);
			temp.addActionListener(this.controller);
			this.add(temp);
			this.mainPanel.add(temp);
			this.faceUpButtons.add(temp);
		}
	}

	private void drawFaceDownHand(){
		// Update face-up hand buttons
		ArrayList<Card> faceDown = this.controller.getFaceDownHand();
		for (int i = 0; i < faceDown.size(); i++) {
			CardButton temp = new CardButton("Hidden..." + faceDown.get(i).toString());
			temp.setBounds(660 + i * 80, 750, 70, 50);
			temp.setActionCommand("F" + i);
			temp.addActionListener(this.controller);
			this.add(temp);
			this.mainPanel.add(temp);
			this.faceDownButtons.add(temp);
		}
	}
}
