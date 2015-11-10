package cauterucio_goyal.project;

import java.awt.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class MainStationFrame extends JFrame {
	
    private static final int FRAME_WIDTH = 1000;
    private static final int FRAME_HEIGHT = 800;

    private JPanel titlePanel, machineListPanel;
    private Container contentPane;
   	
	//  Constructs the frame with a label and a control panel.
	 public MainStationFrame() {  		 

		titlePanel = new MainFrameTitlePanel();
		machineListPanel = new MachineListPanel();
		
		//contentPane = getContentPane();
	    // Construct a Label

        contentPane = this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        //contentPane.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

       	contentPane.add(titlePanel);
       	contentPane.add(machineListPanel);
	    
	    // Set the size of the frame
	    setSize(FRAME_WIDTH, FRAME_HEIGHT);
	     
	    // Center the frame on the screen
	    Toolkit toolkit = getToolkit();
	    Dimension size = toolkit.getScreenSize();
	    setLocation(size.width/2 - getWidth()/2,size.height/2 - getHeight()/2);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
