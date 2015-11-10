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
		
		int i=0;
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		titleLabel = new JLabel("Recycling Machines");
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 32));
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.CENTER;
		this.add(titleLabel, c);
		
		// currently hardcoded to display 10 machines. This will change once we know how many active
		// machines there are.
		for (i = 1; i <= 20; i = i+2) {
			
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
			c.gridx = 1;
			c.gridy = i;
			c.gridheight = 2;
			c.weightx = 0.15;
			this.add(activationButton, c);
		
			removeMachineButton = new JButton("Remove");
			removeMachineButton.setToolTipText("Remove this machine from the Recycling Station");
			c.gridx = 2;
			c.gridy = i;
			c.gridheight = 2;
			c.weightx = 0.15;
			this.add(removeMachineButton, c);
			
			modifyMachineButton = new JButton("Modify");
			modifyMachineButton.setToolTipText("Modify this machine's settings");
			c.gridx = 3;
			c.gridy = i;
			c.gridheight = 2;
			c.weightx = 0.15;
			this.add(modifyMachineButton, c);
			
			viewStatsButton = new JButton("View Stats");
			viewStatsButton.setToolTipText("View statistics about this machine");;
			c.gridx = 4;
			c.gridy = i;
			c.gridheight = 2;
			c.weightx = 0.15;
			//c.insets = new Insets(0, 10, 10, 0);
			this.add(viewStatsButton, c);
		}

	}
}
