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

		// bottom player? 
		ArrayList<Card> faceUp = this.controller.getFaceUpHand();
		for(int i = 0; i < 3; ++i){
			int j = i;
			CardButton temp = new CardButton(faceUp.get(i).toString());
			temp.setBounds(660+i*80, 750, 70, 50);
			temp.setActionCommand("F" + String.valueOf(j));
			temp.addActionListener(this.controller);
			this.mainPanel.add(temp);
			this.faceUpButtons.add(temp);
		}


		// main hand
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


		//adding a window listener for closing the app
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		
	}

	public void refresh() {
		// Clear old buttons
		for (CardButton b : this.mainButtons) this.remove(b);
		for (CardButton b : this.faceUpButtons) this.remove(b);
	
		this.mainButtons.clear();
		this.faceUpButtons.clear();
	
		// Update main hand buttons
		ArrayList<Card> main = this.controller.getMainHand();
		for (int i = 0; i < main.size(); i++) {
			CardButton temp = new CardButton(main.get(i).toString());
			temp.setBounds(580 + i * 80, 650, 70, 50);
			temp.setActionCommand("C" + i);
			temp.addActionListener(this.controller);
			this.add(temp);
			this.mainButtons.add(temp);
		}
	
		// Update face-up hand buttons
		ArrayList<Card> faceUp = this.controller.getFaceUpHand();
		for (int i = 0; i < faceUp.size(); i++) {
			CardButton temp = new CardButton(faceUp.get(i).toString());
			temp.setBounds(660 + i * 80, 750, 70, 50);
			temp.setActionCommand("F" + i);
			temp.addActionListener(this.controller);
			this.add(temp);
			this.faceUpButtons.add(temp);
		}
	
		// Update pile button
		this.deckButton.setText(this.controller.viewTopCard().toString());
	
		this.revalidate();
		this.repaint();
	}
}
