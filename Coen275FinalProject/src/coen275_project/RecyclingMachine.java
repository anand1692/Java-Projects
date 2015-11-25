package coen275_project;

import java.awt.Container;
import java.awt.Font;
import java.util.TreeMap;

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
	
	// This function will set the machine's settings. This should be called from the RecyclingStation when a new RecyclingMachine is created.
	public void setNewMachineSettings(int machineID, String machineLocation, TreeMap<String, Double> itemsAndPrices, double money, int numCoupons) {
		
		return;
	}
	
	// return the location of the machine
	public String getMachineLocation() {

		return "location!";
	}
	
	// This allows the RecyclingStation to know what items and prices the machine currently accepts.
	public TreeMap<String, Double> getCurrentMachineItemsAndPrices() {
		
		TreeMap<String, Double> items = new TreeMap<String, Double>();
		
		items.put("Aluminum", new Double(20.45));
		items.put("Glass", new Double(18.22));
		
		return items;
	}
	
	// This will be called when the Admin wants to change the settings of the machine. 
	public void modifyMachineSettings(String newLocation, TreeMap<String, Double> newItemsAndPrices) {
		
		return;
	}
	
	// Function to activate a machine
	public void activateMachine() {
		
		System.out.println("activate machine");
		return;
	}
	
	// Function to deactivate a machine
	public void deactivateMachine() {
		
		System.out.println("deactivate machine");
		return;
	}
	
}
