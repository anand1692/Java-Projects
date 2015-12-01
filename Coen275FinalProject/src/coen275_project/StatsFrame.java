package coen275_project;

import java.awt.Container;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.jfree.ui.ApplicationFrame;

@SuppressWarnings("serial")
public class StatsFrame extends ApplicationFrame {
	
	private JLabel titleLabel;//, idLabel, locationLabel, errorMsgLabel;

	private Container contentPane;
	
	private int machineId;
	private static String title = "title";
	
	JPanel panel1, panel2, panel3;
	
	public StatsFrame(int id) {
		
		super(title);
		contentPane = this.getContentPane();
		
		JTabbedPane tp = new JTabbedPane();
		
		tp.add("pane1", new JPanel()); // stats
		tp.add("pane2", new JPanel()); // bar graph of cash value vs coupon value
		tp.add("pane3", new JPanel()); // pie chart of items collected by type
				
		contentPane.add(tp);
		
		// initialization
		machineId = id;
		
//		
//	    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
//
//	    // Create title label. Make the font big and underlined!
//		titleLabel = new JLabel("Stats for Machine " + machineId);
//		titleLabel.setFont(new Font("Arial", Font.PLAIN, 32));
//		ProjectLauncher.underlineLabel(titleLabel);
//		
//		contentPane.add(titleLabel);
	}
	
}
