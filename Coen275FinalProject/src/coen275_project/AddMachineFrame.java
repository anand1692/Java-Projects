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
public class AddMachineFrame extends JFrame implements ActionListener {

	private JLabel titleLabel, idLabel, locationLabel, errorMsgLabel;
	private JTextField locationField;
	private JButton doneButton;
	
	private JPanel locationPanel; 
	private ModifyMachinePanel modifyItemsPanel;
	
	private Container contentPane;
		
	private int machineId;
		

	public AddMachineFrame(int id) {
		
		// initialization	
		machineId = id;
		
	    contentPane = this.getContentPane();
	    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		// Create title label. Make the font big and underlined!
		titleLabel = new JLabel("Add New Machine");
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 32));
		ProjectLauncher.underlineLabel(titleLabel);
		
		idLabel = new JLabel("Machine ID:  "+id);
		
		locationPanel = new JPanel();
		locationLabel = new JLabel("Location: ");
		locationField = new JTextField("", 16);
		locationField.setEditable(true);
		
		locationPanel.setBackground(Color.decode("#edd9c0")); // background light brown
		locationPanel.add(locationLabel);
		locationPanel.add(locationField);
		
		modifyItemsPanel = new ModifyMachinePanel();
		
		doneButton = new JButton("Done");
		doneButton.addActionListener(this);
		
		errorMsgLabel = new JLabel("");
		errorMsgLabel.setForeground(Color.red);
		
		contentPane.setBackground(Color.decode("#edd9c0")); // background light brown
		contentPane.add(titleLabel);
		contentPane.add(idLabel);
		contentPane.add(locationPanel);
		contentPane.add(modifyItemsPanel);
		contentPane.add(Box.createRigidArea(new Dimension(50, 50)));
		contentPane.add(errorMsgLabel);
		contentPane.add(doneButton);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		   
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
		    Admin.recyclingStationFrame.addNewMachine(machineId, location, itemsAndPrices, money, coupons);
	    }
	    
    }	
}
