package rmos;

import rcm.MachineStatus;
import java.awt.Color;
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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;

@SuppressWarnings("serial")
public class StatsFrame extends ApplicationFrame {
	
	private Container contentPane;
	
	private MachineStatus machineStatus;
	
	private static String title = "title";
	
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
		
		tp.add("Statistics", new StatisticsPanel()); // stats
		tp.add("Value Issued", createBarPanel(machineStatus.getTotalCashIssued(), machineStatus.getTotalValueIssued())); // bar graph of cash value vs coupon value
		tp.add("Items Collected", createPiePanel(machineStatus.getItemsCollectedByType())); // pie chart of items collected by type - call createPanel()
				
		contentPane.setBackground(Color.decode("#edd9c0")); // background light brown
		contentPane.add(tp);
	}
	
	//Function to create the dataset for the piechart
	private PieDataset createPieDataset(TreeMap<String, Integer> itemsCollectedByType) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for(String s:itemsCollectedByType.keySet()) {
			dataset.setValue(s, new Integer(itemsCollectedByType.get(s)));
		}
		return dataset;
	}
	
	// creates a pie chart using the given dataset argument
	private JFreeChart createPieChart(PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart("Items Collected", dataset, true, true, false);
		return chart;
	}
	
	// returns a JPanel with a pie chart
	private JPanel createPiePanel(TreeMap<String, Integer> categories) {
		JFreeChart chart = createPieChart(createPieDataset(categories));
		return new ChartPanel(chart);
	}
	
	// creates the dataset to be used for cash value issued vs coupon value issued bargraph  
	private DefaultCategoryDataset createBarDataset(double cashValueIssued, double couponValueIssued) {
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		dataset.addValue(cashValueIssued, "", "Cash Value Issued");
		dataset.addValue(couponValueIssued, "", "Coupon Value Issued");
		dataset.addValue((cashValueIssued + couponValueIssued), "", "Total Value Issued");
		
		return dataset;
	}
	
	// returns a JPanel with a bar graph
	private JPanel createBarPanel(double cashValueIssued, double totalValueIssued) {

		DefaultCategoryDataset dataset = createBarDataset(cashValueIssued, totalValueIssued-cashValueIssued);
		
		// create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            "Cash Issued Value vs Coupon Issued Value",         // chart title
            "Money type",             // domain axis label
            "Money value",            // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            false,                    // include legend
            true,                     // tooltips?
            false                     // URLs?
        );
        
  		return new ChartPanel(chart);
	}
	
	
	// This class handles the JPanel for the first pane of the statistics. It lists all the relevant statistics about the given 
	// recycling machine.
	class StatisticsPanel extends JPanel {
		
		private JLabel titleLabel, statLabel, mostUsedLabel;
		
		private JTextField mostUsedField;
		
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
			flowPanel.setBackground(Color.decode("#edd9c0")); // background light brown
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
			
			this.setBackground(Color.decode("#edd9c0")); // background light brown
		}
		
		// Handles "Check" button. Queries the RecyclingStation for the "most used" machine. This is defined as the machine which has
		// been emptied the highest number of times.
		class CheckButtonListener implements ActionListener {
			   public void actionPerformed(ActionEvent event) {
				   
				   try {
					   int numDays = Integer.parseInt(mostUsedField.getText());
					   					
					   // this function will update public variables: mostUsedLocation and mostUsedId
					   Admin.getRecyclingStationFrame().getMostUsedMachine(numDays);
					   
					   // mostUsedId is initialized to -1. If no machine was ever emptied, then it will remain -1.
					   if (mostUsedId < 0) {
						   
						   mostUsedLabel.setText(" day(s): No machine has ever been emptied");
					   
					   } else {
						   
						   mostUsedLabel.setText(" day(s): Machine " + mostUsedId + " at " + mostUsedLocation);
						   RecyclingStation.statsFrame.pack();
					   }		   
				   } catch (Exception e) {
					   mostUsedLabel.setText(" day(s): " + "ERROR: please give int value for number of days");
					   RecyclingStation.statsFrame.pack();
				   }
				   
			   }		
		}

		// Handles "Go Back" button. Returns to the RecyclingStation
		class GoBackButtonListener implements ActionListener {
			   public void actionPerformed(ActionEvent event) {
				   
				   Admin.getRecyclingStationFrame().doneViewingStats();
			   }		
		}

	}
}
