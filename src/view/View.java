package view;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Rules;
import model.Hand;

public class View extends JFrame {

	private Rules model = new Rules(); 
	private Hand bottomPlayer = new Hand();
	
	public View() {
		this.setTitle("Palace Game Demo");
		this.setSize(1920,1080);
		this.setUp();
	}
	
	private void setUp() {
		
		//setting up the main panel
		JPanel mainPanel = new JPanel();
		this.add(mainPanel);
		mainPanel.setLayout(null);


		// pull deck

		JButton deckButton = new JButton(model.viewTopCard().toString());
		deckButton.setBounds(735, 400, 70, 50);
		deckButton.setActionCommand("deck");
		mainPanel.add(deckButton);
		
		// top player? 
		//setting up the count button
		JButton topButton = new JButton("top");
		topButton.setActionCommand("top");
		mainPanel.add(topButton);




		// bottom player? 
		JButton bottomButtonL = new JButton("BL");
		bottomButtonL.setBounds(660, 750, 70, 50);
		bottomButtonL.setActionCommand("BL");
		mainPanel.add(bottomButtonL);

		// bottom player? 
		JButton bottomButton = new JButton("bottom");
		bottomButton.setBounds(740, 750, 70, 50);
		bottomButton.setActionCommand("bottom");
		mainPanel.add(bottomButton);

		// bottom player? 
		JButton bottomButtonR = new JButton("BR");
		bottomButtonR.setBounds(820, 750, 70, 50);
		bottomButtonR.setActionCommand("BR");
		mainPanel.add(bottomButtonR);





		
		//adding a window listener for closing the app
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		



		this.setVisible(true);
	}
	
}
