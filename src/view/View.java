package view;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class View extends JFrame{
	
	
	public View() {
		this.setTitle("MVC Demo");
		this.setSize(400,400);
		this.setUp();
	}
	
	private void setUp() {
		
		//setting up the main panel
		JPanel mainPanel = new JPanel();
		this.add(mainPanel);
		
		//setting up the switch button
		JButton incButton = new JButton("increment");
		incButton.setActionCommand("inc");
		mainPanel.add(incButton);
		
		//setting up the count button
		JButton decButton = new JButton("decrement");
		decButton.setActionCommand("dec");
		mainPanel.add(decButton);
		
		//adding a window listener for closing the app
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		
		this.setVisible(true);
	}
	
}
