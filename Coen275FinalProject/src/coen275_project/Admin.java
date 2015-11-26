package coen275_project;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Admin extends JFrame implements ActionListener {

	private JLabel titleLabel, usernameLabel, passwordLabel, errMsgLabel;
	private JTextField usernameField;//, passwordField;
	private JPasswordField passwordField;
    private JButton loginButton;
    
    private JPanel usernamePanel, passwordPanel;

    private Container contentPane;

	public Admin() {

		contentPane = this.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		titleLabel = new JLabel("Admin Login");
	    Font titleLabelFont = new Font("Arial", Font.PLAIN, 48);
	    titleLabel.setFont(titleLabelFont);
	    titleLabel.setAlignmentX(CENTER_ALIGNMENT);	     
		
	    usernamePanel = new JPanel();
	    usernameLabel = new JLabel("username: ");
	    usernameField = new JTextField(20);
	    usernameField.setEditable(true);	    
	    usernamePanel.add(usernameLabel);
	    usernamePanel.add(usernameField);
	    
	    passwordPanel = new JPanel();
	    passwordLabel = new JLabel("password: ");
	    passwordField = new JPasswordField(20);
	    passwordField.setEditable(true);
	    passwordPanel.add(passwordLabel);
	    passwordPanel.add(passwordField);
	    
	    errMsgLabel = new JLabel("");
	    errMsgLabel.setForeground(Color.red);
	    errMsgLabel.setAlignmentX(CENTER_ALIGNMENT);	   
	    
		loginButton = new JButton("Login");
		loginButton.addActionListener(this);
		loginButton.setAlignmentX(CENTER_ALIGNMENT);
		
		contentPane.add(titleLabel);
		contentPane.add(usernamePanel);
		contentPane.add(passwordPanel);
		contentPane.add(errMsgLabel);
		contentPane.add(loginButton);
	}
	

    // There's only one button in this JFrame. So, this class can implement an action listener, itself, to handle that button.
    public void actionPerformed(ActionEvent ae) {
    	
    	// JPasswordFields gives char arrays instead of Strings. Need to convert.
    	char[] enteredPassword = passwordField.getPassword();
    	String enteredPasswordStr = new String(enteredPassword);
    	
    	// check login info. If it's good we can dispose of this frame and open the RMOS's frame.
    	if (usernameField.getText().equals("admin") && enteredPasswordStr.equals("admin")) {

    		// log in was correct
    		ProjectLauncher.adminFrame.dispose();
        	
        	ProjectLauncher.recyclingStationFrame.validate();
        	ProjectLauncher.recyclingStationFrame.pack();
        	ProjectLauncher.recyclingStationFrame.setVisible(true);

    	} else {
    		
    		errMsgLabel.setText("Incorrect username/password");
    		ProjectLauncher.adminFrame.pack();
    	}
    }
}
