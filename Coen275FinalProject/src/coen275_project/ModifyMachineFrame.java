package coen275_project;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ModifyMachineFrame extends JFrame implements ActionListener {

	private JLabel titleLabel, locationLabel, errorMsgLabel;
	private JTextField locationField;
	private JButton doneButton;
	
	private JPanel locationPanel; 
	static ModifyMachinePanel modifyItemsPanel;
					
	private Container contentPane;
	
	int machineId;
	String machineLocation;
	TreeMap<String, Double> itemsAndPrices;
	double machineMoney;
	int machineCoupons;
	
	public ModifyMachineFrame(RecyclingMachine machine) {

		// initialization
		machineId = machine.getMachineId();
		machineLocation = machine.getMachineLocation();
		//itemsAndPrices = machine.getCurrentMachineItemsAndPrices();
		itemsAndPrices = machine.getItemList();
		machineMoney = machine.getMoneyInMachine();
		machineCoupons = machine.getCouponsInMachine();
		
	    contentPane = this.getContentPane();
	    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		// Create title label. Make the font big and underlined!
	    String machineTitle = "Modify Machine " + machineId;
		titleLabel = new JLabel(machineTitle);
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 32));
		ProjectLauncher.underlineLabel(titleLabel);
		
		locationPanel = new JPanel();
		locationLabel = new JLabel("Location: ");
		locationField = new JTextField(machineLocation, 16);
		locationField.setEditable(true);
		
		locationPanel.add(locationLabel);
		locationPanel.add(locationField);

		modifyItemsPanel = new ModifyMachinePanel(itemsAndPrices, machineMoney, machineCoupons);
		
		doneButton = new JButton("Done");
		doneButton.addActionListener(this);
		
		errorMsgLabel = new JLabel("");
		errorMsgLabel.setForeground(Color.red);
		
		contentPane.add(titleLabel);
		contentPane.add(locationPanel);
		contentPane.add(modifyItemsPanel);
		contentPane.add(Box.createRigidArea(new Dimension(50, 50)));
		contentPane.add(errorMsgLabel);
		contentPane.add(doneButton);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	    System.out.println("done button pressed");
		   
	    String location = locationField.getText();
	    TreeMap<String, Double> itemsAndPrices = modifyItemsPanel.getItemsAndPrices();
	    double money = modifyItemsPanel.getMoney();
	    int coupons = modifyItemsPanel.getCoupons();
	   
	    if ((money < 0) || (coupons < 0) || location.equals("") || itemsAndPrices.isEmpty()) {
		   
	    	if (location.equals("")) {
	    		errorMsgLabel.setText("Error: enter the location of the machine!");
	    	} else if (itemsAndPrices.isEmpty()) { 
	    		errorMsgLabel.setText("Error: Machine must accept at least one type of item!");
	    	} else if(money < 0) {
	    		errorMsgLabel.setText("Error: Money must be a positive number!");
	    	} else if (coupons < 0) {
	    		errorMsgLabel.setText("Error: Value provided for coupons must be an integer!");
	    	} else {
	    		errorMsgLabel.setText("Error creating machine!");
	    	}
	    	
		    this.pack();
	   
	    } else {

	    	// Entered values appear to be okay
		    errorMsgLabel.setText("");
		    ProjectLauncher.recyclingStationFrame.modifyMachine(machineId, location, itemsAndPrices, money, coupons);
	    }
	}
	
}
