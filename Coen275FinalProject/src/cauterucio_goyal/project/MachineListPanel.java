package cauterucio_goyal.project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class MachineListPanel extends JPanel {

	private JLabel titleLabel;
	
	private JLabel machineIdLabel, machineLocationLabel;
	private JButton activationButton, removeMachineButton, modifyMachineButton, viewStatsButton;
	
	
	public MachineListPanel () {
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		titleLabel = new JLabel("Recycling Machines");
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 32));
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.CENTER;
		this.add(titleLabel, c);
		
		
		machineIdLabel = new JLabel("Machine <ID>");
		machineIdLabel.setFont(new Font("Arial", Font.PLAIN, 24));
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.4;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(machineIdLabel, c);
		
		machineLocationLabel = new JLabel("        <Location>");
		machineLocationLabel.setFont(new Font("Arial", Font.PLAIN, 24));
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0.4;
		this.add(machineLocationLabel, c);
		
		c.fill = GridBagConstraints.CENTER;
		
		//c.insets = new Insets(0, 10, 10, 0);
		activationButton = new JButton("Activate");
		activationButton.setToolTipText("Activate this machine");
		c.gridx = 1;
		c.gridy = 1;
		c.gridheight = 2;
		c.weightx = 0.15;
		this.add(activationButton, c);
		
		removeMachineButton = new JButton("Remove");
		removeMachineButton.setToolTipText("Remove this machine from the Recycling Station");
		c.gridx = 2;
		c.gridy = 1;
		c.gridheight = 2;
		c.weightx = 0.15;
		this.add(removeMachineButton, c);
		
		modifyMachineButton = new JButton("Modify");
		modifyMachineButton.setToolTipText("Modify this machine's settings");
		c.gridx = 3;
		c.gridy = 1;
		c.gridheight = 2;
		c.weightx = 0.15;
		this.add(modifyMachineButton, c);
		
		viewStatsButton = new JButton("View Stats");
		viewStatsButton.setToolTipText("View statistics about this machine");;
		c.gridx = 4;
		c.gridy = 1;
		c.gridheight = 2;
		c.weightx = 0.15;
		//c.insets = new Insets(0, 10, 10, 0);
		this.add(viewStatsButton, c);

		/*
		JButton button;
button = new JButton("Button 2");
c.fill = GridBagConstraints.HORIZONTAL;
c.weightx = 0.5;
c.gridx = 0;
c.gridy = 1;
this.add(button, c);

button = new JButton("Button 3");
c.fill = GridBagConstraints.HORIZONTAL;
c.weightx = 0.5;
c.gridx = 1;
c.gridy = 2;
this.add(button, c);

c.insets = new Insets(0, 0, 0, 0);

*/

	}
}
