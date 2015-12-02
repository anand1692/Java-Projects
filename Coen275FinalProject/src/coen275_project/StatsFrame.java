package coen275_project;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;

@SuppressWarnings("serial")
public class StatsFrame extends ApplicationFrame {
	
	private Container contentPane;
	
	private MachineStatus machineStatus;
	
	private static String title = "title";
	
	JPanel panel1, panel2, panel3;

	public String mostUsedLocation;
	public int mostUsedId;
	
	public StatsFrame(MachineStatus status) {
		
		super(title);
		
		// initialization
		machineStatus = status;
		mostUsedLocation = "";
		mostUsedId = -1;
		
		contentPane = this.getContentPane();
		
		JTabbedPane tp = new JTabbedPane();
		
		tp.add("pane1", new StatisticsPanel()); // stats
		tp.add("pane2", new JPanel()); // bar graph of cash value vs coupon value
		tp.add("pane3", new JPanel()); // pie chart of items collected by type - call createPanel()
				
		contentPane.add(tp);
		

		
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
	
	//Function to create the dataset for the piechart
	public PieDataset createDataset(TreeMap<String, Integer> itemsCollectedByType) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for(String s:itemsCollectedByType.keySet()) {
			dataset.setValue(s, new Integer(itemsCollectedByType.get(s)));
		}
		return dataset;
	}
	
	public JFreeChart createChart(PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart("Items Collected", dataset, true, true, false);
		return chart;
	}
	
	public JPanel createPanel(TreeMap<String, Integer> categories) {
		JFreeChart chart = createChart(createDataset(categories));
		return new ChartPanel(chart);
	}
	
	class StatisticsPanel extends JPanel {
		
		private JLabel titleLabel, statLabel, mostUsedLabel;//, cashLabel, couponLabel, currentWeightLabel, weightCapacityLabel;
//		private JLabel cashValueLabel, couponValueLabel, totalValueLabel, numItemsCollectedLabel, lastEmptiedLabel;
//		private JLabel totalWeightLabel, timesEmptiedLabel1, timesEmptiedLabel2, mostUsedLabel1, mostUsedLabel2;
//		
		private JTextField mostUsedField;
		
//		private JComboBox timePeriodCombo;
		
		private JButton mostUsedButton, goBackButton;
		
		private JPanel flowPanel;
		
		private DecimalFormat df;
		
		private ActionListener checkListener, goBackListener;

		
		public StatisticsPanel () {

			// initialization
			checkListener = new CheckButtonListener();
			goBackListener = new GoBackButtonListener();
			df = new DecimalFormat(); 
			df.setMaximumFractionDigits(2);  // ensure money value is no more than 2 decimal places
			
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			
		    // Create title label. Make the font big and underlined!
			titleLabel = new JLabel("Stats for Machine " + machineStatus.getMachineId());
			titleLabel.setFont(new Font("Arial", Font.PLAIN, 32));
			ProjectLauncher.underlineLabel(titleLabel);
			this.add(titleLabel);
			
			this.add(Box.createRigidArea(new Dimension(0, 20)));
			
			statLabel = new JLabel("Location: " + machineStatus.getLocation());
			this.add(statLabel);
			
			statLabel = new JLabel("Active: ");
			if (machineStatus.isActive()) {
				statLabel.setText("Active: Yes");
			} else {
				statLabel.setText("Active: No");
			}
			this.add(statLabel);
			
			this.add(Box.createRigidArea(new Dimension(0, 20)));
			
			statLabel = new JLabel("Cash in machine: $" + df.format(machineStatus.getMoneyInMachine()));
			this.add(statLabel);
			
			statLabel = new JLabel("Coupons in machine: " + machineStatus.getCouponsInMachine());
			this.add(statLabel);
			
			statLabel = new JLabel("Weight in machine: " + df.format(machineStatus.getWeightInMachine()) + " lbs");
			this.add(statLabel);
			
			statLabel = new JLabel("Weight capactity: " + df.format(machineStatus.getWeightCapacity()) + " lbs");
			this.add(statLabel);
			
			this.add(Box.createRigidArea(new Dimension(0, 20)));
			
			statLabel = new JLabel("Cash issued: $" + df.format(machineStatus.getTotalCashIssued()));
			this.add(statLabel);
			
			statLabel = new JLabel("Coupons issued: " + machineStatus.getTotalCouponsIssued());
			this.add(statLabel);
			
			statLabel = new JLabel("Value of issued coupons: $" + df.format((machineStatus.getTotalValueIssued()-machineStatus.getTotalCashIssued())));
			this.add(statLabel);
			
			statLabel = new JLabel("Total money value issued: $" + df.format(machineStatus.getTotalValueIssued()));
			this.add(statLabel);
			
			this.add(Box.createRigidArea(new Dimension(0, 20)));
			
			statLabel = new JLabel("Number of items collected: " + machineStatus.getTotalItemsCollected());
			this.add(statLabel);
			
			statLabel = new JLabel("Number of times machine was emptied: " + machineStatus.getNumberOfTimesEmptied());
			this.add(statLabel);
			
			TreeMap<Date, Double> emptyTimestamps = machineStatus.getEmptyTimestamp();
			if(emptyTimestamps.isEmpty()) {
				statLabel = new JLabel("Last time emptied: never emptied");
			} else {
				
				Date mostRecent = new Date(0);
				for(Date date : emptyTimestamps.keySet()) {
					if(date.after(mostRecent)) {
						mostRecent = date;
					}
				}
				SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
				SimpleDateFormat format2 = new SimpleDateFormat("hh:mm:ss");
				statLabel = new JLabel("Last time emptied: " + format1.format(mostRecent) + " at " + format2.format(mostRecent));
			}
			this.add(statLabel);
			
			this.add(Box.createRigidArea(new Dimension(0, 20)));
			
			double weightToday = 0;
			Date today = new Date();
			Date startOfToday = ProjectLauncher.setToBeginningOfDay(today);
			Date endOfToday = ProjectLauncher.setToEndOfDay(today); 
			for(Date date : emptyTimestamps.keySet()) {
				if(date.after(startOfToday) && date.before(endOfToday)) {
					
					weightToday = weightToday + emptyTimestamps.get(date);
				}
			}
			statLabel = new JLabel("Total weight recycled today: " + df.format(weightToday) + " lbs");
			this.add(statLabel);
			
			double weightThisWeek = 0;
			Date startOfWeek = ProjectLauncher.setToWeekAgo(today);
			for(Date date : emptyTimestamps.keySet()) {
				if(date.after(startOfWeek) && date.before(endOfToday)) {
					
					weightThisWeek = weightThisWeek + emptyTimestamps.get(date);
				}
			}
			statLabel = new JLabel("Total weight recycled this week: " + df.format(weightThisWeek) + " lbs");
			this.add(statLabel);
			
			double weightThisMonth = 0;
			Date startOfMonth = ProjectLauncher.setToMonthAgo(today);
			for(Date date : emptyTimestamps.keySet()) {
				if(date.after(startOfMonth) && date.before(endOfToday)) {
					
					weightThisMonth = weightThisMonth + emptyTimestamps.get(date);
				}
			}
			statLabel = new JLabel("Total weight recycled this month: " + df.format(weightThisMonth) + " lbs");
			this.add(statLabel);
			
			this.add(Box.createRigidArea(new Dimension(0, 20)));

			
			flowPanel = new JPanel();
			flowPanel.setLayout(new FlowLayout());
			
			statLabel = new JLabel("Most used machine in ");
			mostUsedField = new JTextField("1", 3);
			mostUsedLabel = new JLabel(" day(s): ");
			flowPanel.add(statLabel);
			flowPanel.add(mostUsedField);
			flowPanel.add(mostUsedLabel);
			this.add(flowPanel);
			
			mostUsedButton = new JButton("Check");
			mostUsedButton.addActionListener(checkListener);
			this.add(mostUsedButton);

			this.add(Box.createRigidArea(new Dimension(0, 40)));
			
			goBackButton = new JButton("Go Back");
			goBackButton.addActionListener(goBackListener);
			this.add(goBackButton);
			
		}
		
		
		

		// Handles "Check" button. Queries the RecyclingStation for the most used machine
		class CheckButtonListener implements ActionListener {
			   public void actionPerformed(ActionEvent event) {
				   
				   try {
					   int numDays = Integer.parseInt(mostUsedField.getText());
					   					
					   // this function will update public variables: mostUsedLocation and mostUsedId
					   Admin.recyclingStationFrame.getMostUsedMachine(numDays);
					   mostUsedLabel.setText(" day(s): Machine " + mostUsedId + " at " + mostUsedLocation);
					   RecyclingStation.statsFrame.pack();
					   					   
				   } catch (Exception e) {
					   mostUsedLabel.setText(" day(s): " + "ERROR: please give int value for number of days");
					   RecyclingStation.statsFrame.pack();
				   }
				   
			   }		
		}

		// Handles "Go Back" button. Returns to the RecyclingStation
		class GoBackButtonListener implements ActionListener {
			   public void actionPerformed(ActionEvent event) {
				   
				   Admin.recyclingStationFrame.doneViewingStats();
			   }		
		}
		
	}
}
