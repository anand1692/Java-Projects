package coen275_project;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Admin extends JFrame implements ActionListener {

    private JButton loginButton;
    
    private Container contentPane;
    

	public Admin() {

		contentPane = this.getContentPane();
		
		loginButton = new JButton("Login (will add more to this frame later.....)");
		loginButton.addActionListener(this);
		
		contentPane.add(loginButton);
	}
	

    // There's only one button in this JFrame. So, this class can implement an action listener, itself, to handle that button.
    public void actionPerformed(ActionEvent ae) {
    	
    	// check login info. If it's good we can dispose of this frame and open the RMOS's frame.
    	
    	ProjectLauncher.adminFrame.dispose();
    	
    	ProjectLauncher.recyclingStationFrame.validate();
    	ProjectLauncher.recyclingStationFrame.pack();
    	ProjectLauncher.recyclingStationFrame.setVisible(true);
    	
    	
    
    }
}
