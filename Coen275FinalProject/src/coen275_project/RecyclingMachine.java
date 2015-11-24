package coen275_project;

import java.awt.Container;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class RecyclingMachine extends JFrame {

	private JLabel titleLabel;

	private Container contentPane;
	
	public RecyclingMachine() {
	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Recycling Machine");
		
	    contentPane = this.getContentPane();
	    
	    titleLabel = new JLabel("Recycling Machine");
	    Font titleLabelFont = new Font("Arial", Font.PLAIN, 48);
	    titleLabel.setFont(titleLabelFont);
	    	    
	    contentPane.add(titleLabel);
	}
}
