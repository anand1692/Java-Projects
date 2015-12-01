package coen275_project;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RecyclingMachine extends JFrame{

	private ItemsInMachine itemList;
	private MachineStatus machineStatus;
	
	private int sessionType = 1, modeOfPayment = 1;
	private String itemTypeSelected;
	private Double pricePerPound, moneyToBeReturned = (double)0, weightAdded;
	private Boolean toggle = false, sessionEnded = false;
	private String metricSystem = "lbs";
	
	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 300;
	Container content;
	JLabel machineInfoLabel, metricLabel;
	JRadioButton singleItem, multipleItem, cash, coupons;
	JComboBox itemTypeList;
	JTextArea priceDisplay, messageDisplay;
	JButton toggleMetric, insertItem, endSession;
	
//	public enum MachineLocation {
//		SCHOOL,
//		UNIVERSITY,
//		OFFICE,
//		HOSPITAL,
//		LARGE_BUILDING
//	}
	
	public RecyclingMachine() {
		// TODO Auto-generated constructor stub
		itemList = new ItemsInMachine();
		machineStatus = new MachineStatus(itemList.getRecyclableItems());
	}

	public RecyclingMachine(int id) {
		this();
		machineStatus.setMachineId(id);
		content = this.getContentPane();
		machineInfoLabel = new JLabel("RCM "+ machineStatus.getMachineId() + " : At " + machineStatus.getLocation());
		JPanel session = getSessionTypePanel();
		JPanel insertItem = getInsertItemPanel();
		JPanel moneyBack = getMoneyBackPanel();
		JPanel display = getDisplayPanel();
		
		content.setLayout(new FlowLayout());
		content.add(machineInfoLabel);
		content.add(session);
		content.add(insertItem);
		content.add(moneyBack);
		content.add(display);
		
		// Set the size of the frame
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Recycling Machine");
		this.setVisible(true);

		// center the mainFrame on screen
		this.setLocationRelativeTo(null);
	}
	
	private class RadioButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == singleItem) {
				sessionType = 1;
				moneyToBeReturned = (double)0;
				endSession.setEnabled(false);
			} else if(e.getSource() == multipleItem) {
				sessionType = 2;
				moneyToBeReturned = (double)0;
				endSession.setEnabled(true);
				//new frame
			} else if(e.getSource() == cash) {
				modeOfPayment = 1;
			} else if(e.getSource() == coupons) {
				modeOfPayment = 2;
			}
		}
	}
	
	private JPanel getSessionTypePanel() {
		JPanel panel = new JPanel();
		singleItem = new JRadioButton("Single Item");
		multipleItem = new JRadioButton("Start Session");
		ButtonGroup group = new ButtonGroup();
		group.add(singleItem);
		group.add(multipleItem);
		
		singleItem.addActionListener(new RadioButtonHandler());
		multipleItem.addActionListener(new RadioButtonHandler());
		
		singleItem.setSelected(true);
		panel.setLayout(new FlowLayout());
		panel.add(singleItem);
		panel.add(multipleItem);
		return panel;
	}
	
	/**
	 *	Creates and returns a string of choices from a combo box
	 */
	static private String selectedString(ItemSelectable is) {
		Object selected[] = is.getSelectedObjects();
		return ((selected.length == 0) ? "null" : (String) selected[0]);
	}
	
	private Double convertPriceToPerKg(Double money) {
		return money*2.20462;
	}
	
	private Double convertPriceToPerPound(Double money) {
		return money/2.20462;
	}
	
	private void finalizeTransaction() {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		
		if(moneyToBeReturned == -1) { 
			messageDisplay.setText(itemTypeSelected + " is not accepted by this machine\n");
			return;
		}
		else if(moneyToBeReturned == -2) {
			messageDisplay.setText("MACHINE FULL!!! Weight of " + itemTypeSelected + 
					" put in, exceeds the machine capacity\n");
			return;
		}
		
		Boolean cashAvail, couponAvail;
		if(modeOfPayment == 1) {
			cashAvail = returnCashMoney(moneyToBeReturned);
			if(!cashAvail) {
				couponAvail = returnCouponMoney(moneyToBeReturned);
				if(couponAvail) {
					messageDisplay.setText("Sorry cash not available.\n Here's your money in coupons worth "
							+ df.format(moneyToBeReturned) + " for " + df.format(weightAdded) + " lbs of " + itemTypeSelected
							+ ".\nThanks for recycling!\n");
				}else {
					if(sessionType == 1)
						messageDisplay.setText("Sorry no money available. Thanks for recycling!\n");
					else {
						messageDisplay.setText("Sorry no money available.\n");
						messageDisplay.append("Here's your money worth $" + df.format(moneyToBeReturned) + 
											  "for all items till now.\n");
						messageDisplay.append(itemTypeSelected + " is not accepted. Thanks for recycling!\n");
					}
				}
			} else {
				messageDisplay.setText("Here's your money in cash worth $" + df.format(moneyToBeReturned)
										+ " for " + df.format(weightAdded) + " lbs of " + itemTypeSelected 
										+ ".\nThanks for recycling!\n");
			}
		} else if(modeOfPayment == 2) {
			couponAvail = returnCouponMoney(moneyToBeReturned);
			if(!couponAvail) {
				cashAvail = returnCashMoney(moneyToBeReturned);
				if(cashAvail) {
					messageDisplay.setText("Sorry coupon not available.\n Here's your money in cash worth "
							+ df.format(moneyToBeReturned) + " for " + df.format(weightAdded) + " lbs of " + itemTypeSelected
							+ ".\nThanks for recycling!\n");	
				}else {
					if(sessionType == 1)
						messageDisplay.setText("Sorry no money available. Thanks for recycling!\n");
					else {
						messageDisplay.setText("Sorry no money available.\n");
						messageDisplay.append("Here's your money worth $" + df.format(moneyToBeReturned) + 
											  " for all items till now.\n");
						messageDisplay.append(itemTypeSelected + " is not accepted. Thanks for recycling!\n");
					}
				}
			} else {
				messageDisplay.setText("Here's your money in coupons worth $" + df.format(moneyToBeReturned)
						+ " for " + df.format(weightAdded) + " lbs of " + itemTypeSelected + ".\nThanks for recycling!\n");
			}
		}
	}
	
	private class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			
			if(e.getSource() == toggleMetric) {
				if(!toggle) {
					toggle = true;
					metricLabel.setText("$/Kg");
					if(!priceDisplay.getText().equals(""))
						priceDisplay.setText(" " + 
									df.format(convertPriceToPerKg(Double.parseDouble(priceDisplay.getText()))));
				} else {
					toggle = false;
					metricLabel.setText("$/lbs");
					if(!priceDisplay.getText().equals(""))
						priceDisplay.setText(" " + 
									df.format(convertPriceToPerPound(Double.parseDouble(priceDisplay.getText()))));
				}
			} else if(e.getSource() == insertItem) {
				Double weight = Math.random()*10 + 1;
				if(sessionType == 1) {
					moneyToBeReturned = addItemToMachine(itemTypeSelected, weight);
					finalizeTransaction();
				} else {
					if(!sessionEnded) {
						moneyToBeReturned += addItemToMachine(itemTypeSelected, weight);
						if(moneyToBeReturned < machineStatus.getMoneyInMachine() && 
						   machineStatus.getCouponsInMachine() <= 0) {
							sessionEnded = true;
							finalizeTransaction();
							endSession.setEnabled(false);
						}
						messageDisplay.append(df.format(weight) + " lbs of "+ itemTypeSelected + " accepted! Total Money earned = $" 
												+ df.format(moneyToBeReturned)+"\n");
					}	
				}
			} else if(e.getSource() == endSession) {
				sessionEnded = true;
				finalizeTransaction();
			}
		}
	}
	
	private JPanel getInsertItemPanel() {
		JPanel panel = new JPanel();
		JLabel label1 = new JLabel("Item type: ");
		metricLabel = new JLabel("$/lbs");
		itemTypeList = new JComboBox();
		toggleMetric = new JButton("Toggle Metric");
		priceDisplay = new JTextArea(1,4);
		
		toggleMetric.addActionListener(new ButtonHandler());
		itemTypeList.addItem("Default");
		for(String s: itemList.getRecyclableItems()) {
			itemTypeList.addItem(s);
		}
		
		ItemListener itemListener = new ItemListener() {
	    	public void itemStateChanged(ItemEvent itemEvent) {
	    		DecimalFormat df = new DecimalFormat();
	    		df.setMaximumFractionDigits(2);
	    
	    		int state = itemEvent.getStateChange();
	    		if (state == ItemEvent.SELECTED){
	    			ItemSelectable itemS = itemEvent.getItemSelectable();
	    			String type = selectedString(itemS);
	    			itemTypeSelected = type;
	    			pricePerPound = itemList.getPriceOfItem(itemTypeSelected);
	    			if(pricePerPound != -1)
	    				priceDisplay.setText(" " + df.format(pricePerPound));
	    			else {
	    				priceDisplay.setText(" 0");
	    				messageDisplay.setText(itemTypeSelected + " is not accepted by this machine\n");
	    			}
	    		}
	    	}
	    };
		itemTypeList.addItemListener(itemListener);
		
		panel.setLayout(new FlowLayout());
		panel.add(label1);
		panel.add(itemTypeList);
		panel.add(metricLabel);
		panel.add(priceDisplay);
		panel.add(toggleMetric);
		return panel;
	}
	
	private JPanel getMoneyBackPanel() {
		JPanel panel = new JPanel();
		cash = new JRadioButton("Cash");
		coupons = new JRadioButton("Coupons");
		ButtonGroup group = new ButtonGroup();
		group.add(cash);
		group.add(coupons);
		insertItem = new JButton("Insert Item");
		endSession = new JButton("End Session");
		endSession.setEnabled(false);
		
		cash.addActionListener(new RadioButtonHandler());
		coupons.addActionListener(new RadioButtonHandler());
		insertItem.addActionListener(new ButtonHandler());
		endSession.addActionListener(new ButtonHandler());
		
		cash.setSelected(true);
		panel.setLayout(new FlowLayout());
		panel.add(cash);
		panel.add(coupons);
		panel.add(insertItem);
		panel.add(endSession);
		return panel;
	}
	
	private JPanel getDisplayPanel() {
		JPanel panel = new JPanel();
		messageDisplay = new JTextArea(4,35);
		JScrollPane scroll = new JScrollPane(messageDisplay);
		panel.add(scroll);
		return panel;
	}
	
	public void modifyMachineSettings(String newLocation, TreeMap<String, Double> newItemList, 
									  double money, int coupons) {
		machineStatus.setLocation(newLocation);
		this.itemList.setItemList(newItemList);
		machineStatus.setMoneyInMachine(money);
		machineStatus.setCouponsInMachine(coupons);
		TreeMap<String, Integer> itemsCollectedByType = new TreeMap<String, Integer>();
		for(String s: newItemList.keySet())
			itemsCollectedByType.put(s, 0);
		
		machineStatus.setItemsCollectedByType(itemsCollectedByType);
		
		machineInfoLabel.setText(("RCM "+ machineStatus.getMachineId() + " : At " + machineStatus.getLocation()));
		DefaultComboBoxModel model = (DefaultComboBoxModel)itemTypeList.getModel();
		model = itemList.updateItemList(model, newItemList);
		itemTypeList.setModel(model);
	}
	
	public void addItemToList(String item, Double price) {
		this.itemList.addItemToList(item, price);
		DefaultComboBoxModel model = (DefaultComboBoxModel)itemTypeList.getModel();
		model = itemList.updateItemList(model, itemList.getItemList());
		itemTypeList.setModel(model);
	}
	
	public void removeItemFromList(String item) {
		this.itemList.removeItemFromList(item);
		DefaultComboBoxModel model = (DefaultComboBoxModel)itemTypeList.getModel();
		model = itemList.updateItemList(model, itemList.getItemList());
		itemTypeList.setModel(model);
	}

	public void modifyPriceOfItem(String item, Double price) {
		this.itemList.modifyPriceOfItem(item, price);
	}
	
	/*
	 * Adds the Item to the machine and returns the corresponding value of money
	 * to be paid. If Item not acceptable by the machine, then it returns -1
	 * 
	 * */
	public Double addItemToMachine(String item, Double weight) {
		System.out.println("reached inside add");
		TreeMap<String, Double> currentState = itemList.getItemList();
		double capacity = machineStatus.getWeightCapacity();
		double currentWtInMachine = machineStatus.getWeightInMachine();
		
		if(!currentState.containsKey(item))
			return (double)-1;
			
		if(currentWtInMachine+weight > capacity)
			return (double)-2;
		
		Double money = currentState.get(item)*weight;
		weightAdded = weight;
		machineStatus.setWeightInMachine(machineStatus.getWeightInMachine()+weight);
		machineStatus.setTotalItemsCollected(machineStatus.getTotalItemsCollected()+1);
		machineStatus.updateItemsCollectedByType(item);
		return money;
	}
	
	/*
	 * Returns the money in cash to the user. If not enough cash, returns false.
	 * Should check coupons if false
	 * 
	 * */
	public boolean returnCashMoney(double money) {
		if(money > machineStatus.getMoneyInMachine())
			return false;
		
		machineStatus.setMoneyInMachine(machineStatus.getMoneyInMachine() - money);
		machineStatus.setTotalCashIssued(machineStatus.getTotalCashIssued()+money);
		machineStatus.setTotalValueIssued(money);
		return true;
	}
	
	/*
	 * Returns the money in coupons to the user. If not enough coupons, returns false.
	 * Should check cash if false
	 * 
	 * */
	public boolean returnCouponMoney(double money) {
		if(machineStatus.getCouponsInMachine() <= 0)
			return false;
		
		machineStatus.setCouponsInMachine(machineStatus.getCouponsInMachine() - 1);
		machineStatus.setTotalCouponsIssued(machineStatus.getTotalCouponsIssued() - 1);
		machineStatus.setTotalValueIssued(money);
		return true;
	}
	
	public void emptyMachine() {
		TreeMap<Date, Double> emptyTimestamp = machineStatus.getEmptyTimestamp();
		Double weight = machineStatus.getWeightInMachine();
		emptyTimestamp.put(new Date(), weight);
		machineStatus.setWeightInMachine(0);
		machineStatus.setNumberOfTimesEmptied(machineStatus.getNumberOfTimesEmptied() + 1);
		messageDisplay.setText("Machine has been emptied.\n" + weight + " of recycled material has been emptied\n");
	}
	
	
	public MachineStatus getMachineStatus() {
		return machineStatus;
	}

	/**
	 * @return the machineId
	 */
	public int getMachineId() {
		return machineStatus.getMachineId();
	}

	/**
	 * @return the location
	 */
	public String getMachineLocation() {
		return machineStatus.getLocation();
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return machineStatus.isActive();
	}

	/**
	 * @return the recyclableItems
	 */
	public ArrayList<String> getRecyclableItems() {
		return itemList.getRecyclableItems();
	}

	/**
	 * @return the itemList
	 */
	public TreeMap<String, Double> getItemList() {
		return itemList.getItemList();
	}

	/**
	 * @return the moneyInMachine
	 */
	public double getMoneyInMachine() {
		return machineStatus.getMoneyInMachine();
	}

	/**
	 * @return the couponsInMachine
	 */
	public int getCouponsInMachine() {
		return machineStatus.getCouponsInMachine();
	}

	/**
	 * @return the weightInMachine
	 */
	public double getWeightInMachine() {
		return machineStatus.getWeightInMachine();
	}

	/**
	 * @return the emptyTimestamp
	 */
	public TreeMap<Date, Double> getEmptyTimestamp() {
		return machineStatus.getEmptyTimestamp();
	}

	/**
	 * @return the numberOfTimesEmptied
	 */
	public int getNumberOfTimesEmptied() {
		return machineStatus.getNumberOfTimesEmptied();
	}

	/**
	 * @return the weightCapacity
	 */
	public double getWeightCapacity() {
		return machineStatus.getWeightCapacity();
	}

	/**
	 * @return the totalItemsCollected
	 */
	public int getTotalItemsCollected() {
		return machineStatus.getTotalItemsCollected();
	}

	/**
	 * @return the itemsCollectedByType
	 */
	public TreeMap<String, Integer> getItemsCollectedByType() {
		return machineStatus.getItemsCollectedByType();
	}

	/**
	 * @return the totalCashIssued
	 */
	public double getTotalCashIssued() {
		return machineStatus.getTotalCashIssued();
	}

	/**
	 * @return the totalCouponsIssued
	 */
	public int getTotalCouponsIssued() {
		return machineStatus.getTotalCouponsIssued();
	}
	
	/**
	 * @return the totalValueIssued
	 */
	public double getTotalValueIssued() {
		return machineStatus.getTotalValueIssued();
	}

	/**
	 * @param machineId the machineId to set
	 */
	public void setMachineId(int machineId) {
		machineStatus.setMachineId(machineId);
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		machineStatus.setLocation(location);
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		machineStatus.setActive(active);
	}

	/**
	 * @param itemList the itemList to set
	 */
	public void setItemList(TreeMap<String, Double> newItemList) {
		this.itemList.setItemList(newItemList);
	}

	/**
	 * @param moneyInMachine the moneyInMachine to set
	 */
	public void setMoneyInMachine(double moneyInMachine) {
		this.machineStatus.setMoneyInMachine(moneyInMachine);
	}

	/**
	 * @param couponsInMachine the couponsInMachine to set
	 */
	public void setCouponsInMachine(int couponsInMachine) {
		this.machineStatus.setCouponsInMachine(couponsInMachine);
	}

	/**
	 * @param weightCapacity the weightCapacity to set
	 */
	public void setWeightCapacity(double weightCapacity) {
		this.machineStatus.setWeightCapacity(weightCapacity);
	}
	
	public static void main(String[] args) {
		RecyclingMachine rcm = new RecyclingMachine(1);
	}
}
