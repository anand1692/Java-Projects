package coen275_project;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class RecyclingStation extends JFrame {

	private static final int MACHINE_WIDTH = 500;
	private static final int MACHINE_HEIGHT = 300;
	
	private static final int MAX_NUM_MACHINES = 10;

	private RecyclingMachine[] machines;
	
	static AddMachineFrame addMachineFrame;
	static ModifyMachineFrame modifyMachineFrame;
		
	// numMachines is total number of machines; machineId is the latest, unused ID number. They will be the same until a machine is removed.
	private int numMachines, machineId;  
	
    private JLabel titleLabel, locationLabel;
	private JButton addNewMachineButton;
    private JTextArea informationDisplay;
    JScrollPane scrollPane; 

    private MachineListPanel machineListPanel;
	private Container contentPane;
	
	private ActionListener activateListener, removeListener, modifyListener, viewStatsListener, addMachineListener;  
    
    public RecyclingStation() {
	
    	// initialization
    	machines = new RecyclingMachine[MAX_NUM_MACHINES];
    	numMachines = 0;
    	machineId = 0;
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
 
	    locationLabel = new JLabel("Location: Santa Clara University", SwingConstants.LEFT);
	    locationLabel.setFont(new Font("Serif", Font.PLAIN, 24));
	    locationLabel.setAlignmentX(CENTER_ALIGNMENT);

	    // This panel is completely generated in the constructor.
		machineListPanel = new MachineListPanel();

		addNewMachineButton = new JButton("Add new machine");
		addNewMachineButton.addActionListener(addMachineListener);
		addNewMachineButton.setAlignmentX(CENTER_ALIGNMENT);
		
		informationDisplay = new JTextArea("", 5, 30);
		informationDisplay.setEditable(false);
		scrollPane = new JScrollPane(informationDisplay);
		
	    contentPane.add(titleLabel);
	    contentPane.add(locationLabel);
	    contentPane.add(Box.createRigidArea(new Dimension(0,10)));
	   	contentPane.add(machineListPanel);
	   	contentPane.add(Box.createRigidArea(new Dimension(0,10)));
	   	contentPane.add(addNewMachineButton);
	    contentPane.add(scrollPane);
			
	}
    
	
    // This class adds/removes from this panel as recycling machines (RCMs) are added/removed from the Recycling Station (RMOS).
	public class MachineListPanel extends JPanel {
	
		private JLabel titleLabel, machineIdLabel, machineLocationLabel;
		private JButton activationButton, removeMachineButton, modifyMachineButton, viewStatsButton;
		
		private GridBagConstraints c;
		
		public MachineListPanel () {
			
			this.setLayout(new GridBagLayout());
			c = new GridBagConstraints();
			
			// Create title label. Make the font big and underlined!
			titleLabel = new JLabel("Recycling Machines");
			titleLabel.setFont(new Font("Arial", Font.PLAIN, 32));
			ProjectLauncher.underlineLabel(titleLabel);
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
		}
		
		
		
		public void updateMachinePanel() {
			
			// This function will re-create the entire panel based on what machines have been added, removed, or modified. 
			// First we call removeAll() to give us a blank slate.
			machineListPanel.removeAll();
			
			// Create title label. Make the font big and underlined!
			titleLabel = new JLabel("Recycling Machines");
			titleLabel.setFont(new Font("Arial", Font.PLAIN, 32));
			ProjectLauncher.underlineLabel(titleLabel);
			c.gridx = 0;
			c.gridy = 0;
			c.fill = GridBagConstraints.CENTER;
			this.add(titleLabel, c);
			
			// Print message if no machines are currently assigned to the station.
			if (numMachines == 0) {
				
				machineIdLabel = new JLabel("There are no machines currently assigned to this station.");
				machineIdLabel.setFont(new Font("Arial", Font.PLAIN, 24));
				c.gridx = 0;
				c.gridy = 2;
				c.weightx = 0.4;
				c.gridheight = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				this.add(machineIdLabel, c);
				
			} else {
				
				machineIdLabel.setText(null);
			}
			
			// Create a row for each existing machine
			for (int i = 1; i <= (numMachines*2); i = i+2) {
				
				machineIdLabel = new JLabel("Machine " + machines[i/2].getMachineId());
				machineIdLabel.setFont(new Font("Arial", Font.PLAIN, 24));
				c.gridx = 0;
				c.gridy = i+1;
				c.weightx = 0.4;
				c.gridheight = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				this.add(machineIdLabel, c);
				
				machineLocationLabel = new JLabel("        " + machines[i/2].getMachineLocation());
				machineLocationLabel.setFont(new Font("Arial", Font.PLAIN, 24));
				c.gridx = 0;
				c.gridy = i+2;
				c.weightx = 0.4;
				this.add(machineLocationLabel, c);
			
				c.fill = GridBagConstraints.CENTER;
				
				//c.insets = new Insets(0, 10, 10, 0);
				activationButton = new JButton();
				
				if(machines[i/2].isActive()) {
					
					activationButton.setText("Deactivate");
					activationButton.setToolTipText("Deactivate this machine");
				} else {
					activationButton.setText("Activate");
					activationButton.setToolTipText("Activate this machine");
				}
				activationButton.addActionListener(activateListener);
				activationButton.setActionCommand(""+(i/2));
				c.gridx = 1;
				c.gridy = i+1;
				c.gridheight = 2;
				c.weightx = 0.15;
				this.add(activationButton, c);
			
				removeMachineButton = new JButton("Remove");
				removeMachineButton.setToolTipText("Remove this machine from the Recycling Station");
				removeMachineButton.addActionListener(removeListener);
				removeMachineButton.setActionCommand(""+(i/2));
				c.gridx = 2;
				c.gridy = i+1;
				c.gridheight = 2;
				c.weightx = 0.15;
				this.add(removeMachineButton, c);
				
				modifyMachineButton = new JButton("Modify");
				modifyMachineButton.setToolTipText("Modify this machine's settings");
				modifyMachineButton.addActionListener(modifyListener);
				modifyMachineButton.setActionCommand(""+(i/2));
				c.gridx = 3;
				c.gridy = i+1;
				c.gridheight = 2;
				c.weightx = 0.15;
				this.add(modifyMachineButton, c);
				
				viewStatsButton = new JButton("View Stats");
				viewStatsButton.setToolTipText("View statistics about this machine");;
				viewStatsButton.addActionListener(viewStatsListener);
				c.gridx = 4;
				c.gridy = i+1;
				c.gridheight = 2;
				c.weightx = 0.15;
				this.add(viewStatsButton, c);
			}
		}
	}
	

	
	// Handles "Activate"/"Deactivate" button for machines
	class ToggleActivationListener implements ActionListener {
		   public void actionPerformed(ActionEvent event) {
			   
			   // The action command contains the index into the machines[] array. 
			   // if the machine is active, it should be deactivated - meaning its GUI should disappear
			   // if the machine is inactive, it should be activated - meaning its GUI should reappear
			   int machineIndex = Integer.parseInt(event.getActionCommand());
			   int id = machines[machineIndex].getMachineId();
			   
			   if(machines[machineIndex].isActive()) {
				   
				   // deactivate machine
				   machines[machineIndex].setActive(false);
				   machines[machineIndex].setVisible(false);
				   informationDisplay.append("Deactivated machine " + id + "\n");
			   
			   } else {
				   
				   // activate machine
				   machines[machineIndex].setActive(true);
				   machines[machineIndex].setVisible(true);
				   informationDisplay.append("Activated machine " + id + "\n");
			   }
			   
			   machineListPanel.updateMachinePanel();
			   machineListPanel.validate();
			   ProjectLauncher.recyclingStationFrame.pack();
		   }		
	}
	
	// Handles "Remover" button for machines
	class RemoveMachineListener implements ActionListener {
		   public void actionPerformed(ActionEvent event) {
			 
			   // The action command contains the index into the machines[] array. The machine needs to be removed. Then, we want to redo the 
			   // machines[] array so that there are no gaps.
			   int machineIndex = Integer.parseInt(event.getActionCommand());
			   
			   int id = machines[machineIndex].getMachineId();
			   
			   machines[machineIndex].dispose();
			   
			   int i = 0;
			   for (i = machineIndex; i < numMachines-1; i++) {
				   
				   machines[i] = machines[i+1];
			   }
			   
			   numMachines--;
			   
			   // redraw the RecyclingStation's GUI reflecting the removal
			   machineListPanel.updateMachinePanel();
			   machineListPanel.validate();
			   ProjectLauncher.recyclingStationFrame.pack();
			   
			   informationDisplay.append("Removed machine " + id + "\n");
		   }		
	}
	
	// Handles "Modify" button for machines
	class ModifyMachineListener implements ActionListener {
		   public void actionPerformed(ActionEvent event) {
			   
			   int machineIndex = Integer.parseInt(event.getActionCommand());
			   
			   modifyMachineFrame = new ModifyMachineFrame(machines[machineIndex]);
			   modifyMachineFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			   modifyMachineFrame.setTitle("Recycling Monitoring Station (RMOS) - modify machine " + machines[machineIndex].getMachineId());
			   modifyMachineFrame.validate();
			   modifyMachineFrame.pack();
			   modifyMachineFrame.setVisible(true);
			   ProjectLauncher.recyclingStationFrame.setVisible(false);
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
			   			   
			   if (numMachines < MAX_NUM_MACHINES) {
				   				   
//				   machines[numMachines] = new RecyclingMachine(machineId);
				   
				   addMachineFrame = new AddMachineFrame(machineId);
				   addMachineFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				   addMachineFrame.setTitle("Recycling Monitoring Station (RMOS) - add new machine");
				   addMachineFrame.validate();
				   addMachineFrame.pack();
				   addMachineFrame.setVisible(true);
				   ProjectLauncher.recyclingStationFrame.setVisible(false);
				   				   
			   } else {
				   
				   //print that we can't create more machines...
				   informationDisplay.append("This station only supports up to 10 machines" + "\n");
			   }
		   }		
	}
	
	// Called from addMachineFrame. This relays the information filled in the addMachineFrame to the newly created RecyclingMachine 
	public void addNewMachine(int machineID, String machineLocation, TreeMap<String, Double> itemsAndPrices, double money, int numCoupons) {
		
		machines[numMachines] = new RecyclingMachine(machineId);
		machines[numMachines].modifyMachineSettings(machineLocation, itemsAndPrices, money, numCoupons);
//		setNewMachineSettings(machineID, machineLocation, itemsAndPrices, money, numCoupons);

		informationDisplay.append("add machine " + machineId + "\n");
	   
		// We'll create a new addMachineFrame when the time is right. For now, we are done with this one and can kill it.
		addMachineFrame.dispose();
		
		// make the new machine's GUI pop up.
		machines[numMachines].setSize(MACHINE_WIDTH, MACHINE_HEIGHT);
		machines[numMachines].setLocationRelativeTo(null);
		machines[numMachines].validate();
		machines[numMachines].setTitle("Recycling Machine " + machineId);
//		machines[numMachines].pack();
		machines[numMachines].setVisible(true);
	   			   
		numMachines++;
		machineId++;
	   
		// redraw the RecyclingStation's GUI with the new machine
		machineListPanel.updateMachinePanel();
		machineListPanel.validate();
		ProjectLauncher.recyclingStationFrame.pack();
		ProjectLauncher.recyclingStationFrame.setVisible(true);
		
		return;
	}
	
	// Called from modifyMachineFrame. This relays the information filled in the modifyMachineFrame to the corresponding RecyclingMachine
	public void modifyMachine(int id, String machineLocation, TreeMap<String, Double> itemsAndPrices, double money, int numCoupons) {
		
		int machineIndex = getMachineIndexFromId(id);
		machines[machineIndex].modifyMachineSettings(machineLocation, itemsAndPrices, money, numCoupons);
		
		// We'll create a new modifyMachineFrame when the time is right. For now, we are done with this one and can kill it.
		modifyMachineFrame.dispose();
	   
		// redraw the RecyclingStation's GUI with the new machine
		machineListPanel.updateMachinePanel();
		machineListPanel.validate();
		ProjectLauncher.recyclingStationFrame.pack();
		ProjectLauncher.recyclingStationFrame.setVisible(true);
		
		informationDisplay.append("Modified machine " + id + "\n");
	}
	
	// RecyclingStation needs to know the index of a machine into the machines[] array. Other components only know about the machineId though
	// which is not necessarily the same number. This function translates. 
	private int getMachineIndexFromId(int machineId) {
		System.out.println("machineId= "+machineId );
		System.out.println("numMachines= "+numMachines );
		
		
		for (int i = 0; i < numMachines; i++) {
			if (machines[i].getMachineId() == machineId) {
				return i;
			}
			System.out.println("i= "+i );
		}

		System.out.println("machineId= "+machineId );
		System.out.println("numMachines= "+numMachines );
		
		

		
		System.out.println("ERROR-getMachineIndexFromId");
		return -1;
	}
}
