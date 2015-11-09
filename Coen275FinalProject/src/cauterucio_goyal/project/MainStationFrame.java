package cauterucio_goyal.project;

import java.awt.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class MainStationFrame extends JFrame {
	
    private static final int FRAME_WIDTH = 1000;
    private static final int FRAME_HEIGHT = 800;

    private JPanel titlePanel;
    private Container contentPane;
   	
	//  Constructs the frame with a label and a control panel.
	 public MainStationFrame() {  		 

		titlePanel = new MainFrameTitlePanel();
		 
		//contentPane = getContentPane();
	    // Construct a Label

        contentPane = this.getContentPane();

       	contentPane.add(titlePanel);
	    
	    // Set the size of the frame
	    setSize(FRAME_WIDTH, FRAME_HEIGHT);
	     
	    // Center the frame on the screen
	    Toolkit toolkit = getToolkit();
	    Dimension size = toolkit.getScreenSize();
	    setLocation(size.width/2 - getWidth()/2,size.height/2 - getHeight()/2);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
