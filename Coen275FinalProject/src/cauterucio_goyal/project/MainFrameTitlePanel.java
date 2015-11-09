package cauterucio_goyal.project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class MainFrameTitlePanel extends JPanel {
    
	private JLabel titleLabel, locationLabel;
    
	public MainFrameTitlePanel () {
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
	     titleLabel = new JLabel("RecyclingMachine", SwingConstants.CENTER);
	     titleLabel.setForeground(Color.BLACK);
	     Font titleLabelFont = new Font("Arial", Font.PLAIN, 48);
	     titleLabel.setFont(titleLabelFont);
	     titleLabel.setAlignmentX(CENTER_ALIGNMENT);	     
	     
	     locationLabel = new JLabel("Location string will go here!", SwingConstants.LEFT);
	     locationLabel.setForeground(Color.BLACK);
	     locationLabel.setFont(new Font("Serif", Font.PLAIN, 24));
	     locationLabel.setAlignmentX(CENTER_ALIGNMENT);
	     
	     // add the label. By default, the layout manager for a JFrame is a BorderLayout.
	     this.add(titleLabel, BorderLayout.PAGE_START);;
	     
	     add(titleLabel);
	     add(locationLabel);

	}
}
