package coen275_project;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ProjectLauncher {
	
	// Different frames are declared statically here. This allows other frames to be able to switch between them.
	static Admin adminFrame;
	static RecyclingStation recyclingStationFrame;
	
	
	public static void main(String[] args) {
		
		adminFrame = new Admin();
		recyclingStationFrame = new RecyclingStation();
		
		adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		adminFrame.setTitle("Recycling Monitoring Station (RMOS) - Admin Login");

		recyclingStationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		recyclingStationFrame.setTitle("Recycling Monitoring Station (RMOS)");
		
		// TODO: program will start with admin login screen. Display enter limit frame first
		adminFrame.pack();
		adminFrame.setVisible(true);      
	}
	
	
	// Underlining JLabels is a pain. So, I made this globally-available function to do it.
	@SuppressWarnings("unchecked")
	public static void underlineLabel (JLabel label) {
		
		Font font = label.getFont();
		@SuppressWarnings("rawtypes")
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		label.setFont(font.deriveFont(attributes));
	}
}
