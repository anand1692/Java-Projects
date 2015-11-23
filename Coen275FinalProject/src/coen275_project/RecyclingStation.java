package coen275_project;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class RecyclingStation extends JFrame {

	private static final int MAX_NUM_MACHINES = 10;

	private RecyclingMachine[] machines;
	private int numMachines;
	
    private JLabel titleLabel, locationLabel;
	private JButton addNewMachineButton;
    
	private JPanel machineListPanel;
	private Container contentPane;
	
	private ActionListener activateListener, removeListener, modifyListener, viewStatsListener, addMachineListener;  
    
    public RecyclingStation() {
	
    	// initialization
    	machines = new RecyclingMachine[MAX_NUM_MACHINES];
    	numMachines = 0;
    	activateListener = new ToggleActivationListener();
    	removeListener = new RemoveMachineListener();
    	modifyListener = new ModifyMachineListener();
    	viewStatsListener = new ViewStatsListener();
    	addMachineListener = new AddMachineListener();
    	
    	
	    contentPane = this.getContentPane();
	    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

	    
	    // Create labels to be displayed at top of the frame
	    titleLabel = new JLabel("Recycling Station");
	    Font titleLabelFont = new Font("Arial", Font.PLAIN, 48);
	    titleLabel.setFont(titleLabelFont);
	    titleLabel.setAlignmentX(CENTER_ALIGNMENT);	     
 
	    locationLabel = new JLabel("Location string will go here!", SwingConstants.LEFT);
	    locationLabel.setFont(new Font("Serif", Font.PLAIN, 24));
	    locationLabel.setAlignmentX(CENTER_ALIGNMENT);

	    // This panel is completely generated in the constructor.
		machineListPanel = new MachineListPanel();

		addNewMachineButton = new JButton("Add new machine");
		addNewMachineButton.addActionListener(addMachineListener);
		addNewMachineButton.setAlignmentX(CENTER_ALIGNMENT);
		
	    
	    contentPane.add(titleLabel);
	    contentPane.add(locationLabel);
	    contentPane.add(Box.createRigidArea(new Dimension(0,10)));
	   	contentPane.add(machineListPanel);
	   	contentPane.add(Box.createRigidArea(new Dimension(0,10)));
	   	contentPane.add(addNewMachineButton);
	    
			
	}
    
	
    // This class adds/removes from this panel as recycling machines (RCMs) are added/removed from the Recycling Station (RMOS).
	public class MachineListPanel extends JPanel {
	
		private JLabel titleLabel, machineIdLabel, machineLocationLabel;
		private JButton activationButton, removeMachineButton, modifyMachineButton, viewStatsButton;
		
		
		@SuppressWarnings("unchecked")
		public MachineListPanel () {
			
			int i=0;
			
			this.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			
			// Create title label. Make the font big and underlined!
			titleLabel = new JLabel("Recycling Machines");
			titleLabel.setFont(new Font("Arial", Font.PLAIN, 32));
			Font font = titleLabel.getFont();
			@SuppressWarnings("rawtypes")
			Map attributes = font.getAttributes();
			attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			titleLabel.setFont(font.deriveFont(attributes));
			c.gridx = 0;
			c.gridy = 0;
			c.fill = GridBagConstraints.CENTER;
			this.add(titleLabel, c);
						
			
			if (numMachines == 0) {
				
				// Print message if no machines are currently assigned to the station.
				machineIdLabel = new JLabel("There are no machines currently assigned to this station.");
				machineIdLabel.setFont(new Font("Arial", Font.PLAIN, 24));
				c.gridx = 0;
				c.gridy = 1;
				c.weightx = 0.4;
				c.gridheight = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				this.add(machineIdLabel, c);
			}
			
			// Create a row for each existing machine
			for (i = 1; i <= (numMachines*2); i = i+2) {
				
				machineIdLabel = new JLabel("Machine " + i/2);
				machineIdLabel.setFont(new Font("Arial", Font.PLAIN, 24));
				c.gridx = 0;
				c.gridy = i;
				c.weightx = 0.4;
				c.gridheight = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				this.add(machineIdLabel, c);
				
				machineLocationLabel = new JLabel("        <Location>");
				machineLocationLabel.setFont(new Font("Arial", Font.PLAIN, 24));
				c.gridx = 0;
				c.gridy = i+1;
				c.weightx = 0.4;
				this.add(machineLocationLabel, c);
			
				c.fill = GridBagConstraints.CENTER;
				
				//c.insets = new Insets(0, 10, 10, 0);
				activationButton = new JButton("Activate");
				activationButton.setToolTipText("Activate this machine");
				activationButton.addActionListener(activateListener);
				c.gridx = 1;
				c.gridy = i;
				c.gridheight = 2;
				c.weightx = 0.15;
				this.add(activationButton, c);
			
				removeMachineButton = new JButton("Remove");
				removeMachineButton.setToolTipText("Remove this machine from the Recycling Station");
				removeMachineButton.addActionListener(removeListener);
				c.gridx = 2;
				c.gridy = i;
				c.gridheight = 2;
				c.weightx = 0.15;
				this.add(removeMachineButton, c);
				
				modifyMachineButton = new JButton("Modify");
				modifyMachineButton.setToolTipText("Modify this machine's settings");
				modifyMachineButton.addActionListener(modifyListener);
				c.gridx = 3;
				c.gridy = i;
				c.gridheight = 2;
				c.weightx = 0.15;
				this.add(modifyMachineButton, c);
				
				viewStatsButton = new JButton("View Stats");
				viewStatsButton.setToolTipText("View statistics about this machine");;
				viewStatsButton.addActionListener(viewStatsListener);
				c.gridx = 4;
				c.gridy = i;
				c.gridheight = 2;
				c.weightx = 0.15;
				//c.insets = new Insets(0, 10, 10, 0);
				this.add(viewStatsButton, c);
			}
	
		}
	}
	
	// Handles "Activate"/"Deactivate" button for machines
	class ToggleActivationListener implements ActionListener {
		   public void actionPerformed(ActionEvent event) {
			   
			   System.out.println("activate button pressed");
		   }		
	}
	
	// Handles "Remover" button for machines
	class RemoveMachineListener implements ActionListener {
		   public void actionPerformed(ActionEvent event) {
			   
			   System.out.println("remove button pressed");
		   }		
	}
	
	// Handles "Modify" button for machines
	class ModifyMachineListener implements ActionListener {
		   public void actionPerformed(ActionEvent event) {

			   System.out.println("modify button pressed");
		   }		
	}
	
	// Handles "View Stats" button for machines
	class ViewStatsListener implements ActionListener {
		   public void actionPerformed(ActionEvent event) {

			   System.out.println("view stats button pressed");
		   }		
	}
	
	// Handles "Add New Machine" button for machines
	class AddMachineListener implements ActionListener {
		   public void actionPerformed(ActionEvent event) {
			   
			   System.out.println("Add new machine button pressed");
		   }		
	}
}
