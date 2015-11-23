package coen275_project;

import javax.swing.JFrame;

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
}
