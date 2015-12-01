package coen275_project;

import java.awt.Container;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class StatsFrame extends JFrame {
	
	private JLabel titleLabel;//, idLabel, locationLabel, errorMsgLabel;

	private Container contentPane;
	
	int machineId;
	
	public StatsFrame(int id) {

		// initialization
		machineId = id;
		
		contentPane = this.getContentPane();
	    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

	    // Create title label. Make the font big and underlined!
		titleLabel = new JLabel("Stats for Machine " + machineId);
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 32));
		ProjectLauncher.underlineLabel(titleLabel);
		
		contentPane.add(titleLabel);
	}
}
