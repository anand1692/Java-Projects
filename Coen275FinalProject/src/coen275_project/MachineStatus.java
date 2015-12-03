package coen275_project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class MachineStatus implements Serializable {
	
	private int machineId;
	private String location;
	private boolean active;
	private double moneyInMachine; 	// in $
	private int couponsInMachine;	
	private double weightInMachine;	// in pounds
	private TreeMap<Date, Double> emptyTimestamp;  // date and weight emptied
	private int numberOfTimesEmptied;
	private double weightCapacity; 	// in pounds
	private int totalItemsCollected;
	private TreeMap<String, Integer> itemsCollectedByType;
	private double totalCashIssued;
	private int totalCouponsIssued;
	private double totalValueIssued;
	private TreeMap<String, Double> itemsInList;
	private ArrayList<String> recyclableItemList;
	
	public MachineStatus(ArrayList<String> recyclableItems, TreeMap<String, Double> itemList) {

		machineId = 0;
		location = "University";
		active = true;
		moneyInMachine = 200;
		couponsInMachine = 2;
		weightInMachine = 0;
		numberOfTimesEmptied = 0;
		weightCapacity = 100; 
		totalItemsCollected = 0;
		totalCashIssued = 0;
		totalCouponsIssued = 0;
		totalValueIssued = 0;
		
		emptyTimestamp = new TreeMap<Date, Double>();
		
		itemsCollectedByType = new TreeMap<String, Integer>();
		for(String s: recyclableItems) {
			itemsCollectedByType.put(s, 0);
		}
		itemsInList = itemList;
		recyclableItemList = recyclableItems;
	}
	
	public static void serialize(MachineStatus msOb, String filename) {
		FileOutputStream fout = null;
		ObjectOutputStream out = null;
		try {
			fout = new FileOutputStream(filename);
			out = new ObjectOutputStream(fout);
			out.writeObject(msOb);
			out.close();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static MachineStatus deSerialize(String filename) {
		MachineStatus msOb = null;
		FileInputStream fis = null;
		ObjectInputStream fin = null;
		
		try {
			fis = new FileInputStream(filename);
			fin = new ObjectInputStream(fis);
			msOb = (MachineStatus)fin.readObject();
			fin.close();
		} catch(IOException ex) {
			ex.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return msOb;
	}
	
	
	/**
	 * @return the machineId
	 */
	public int getMachineId() {
		return machineId;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @return the moneyInMachine
	 */
	public double getMoneyInMachine() {
		return moneyInMachine;
	}

	/**
	 * @return the couponsInMachine
	 */
	public int getCouponsInMachine() {
		return couponsInMachine;
	}

	/**
	 * @return the weightInMachine
	 */
	public double getWeightInMachine() {
		return weightInMachine;
	}

	/**
	 * @return the emptyTimestamp
	 */
	public TreeMap<Date, Double> getEmptyTimestamp() {
		return emptyTimestamp;
	}

	/**
	 * @return the numberOfTimesEmptied
	 */
	public int getNumberOfTimesEmptied() {
		return numberOfTimesEmptied;
	}

	/**
	 * @return the weightCapacity
	 */
	public double getWeightCapacity() {
		return weightCapacity;
	}

	/**
	 * @return the totalItemsCollected
	 */
	public int getTotalItemsCollected() {
		return totalItemsCollected;
	}

	/**
	 * @return the itemsCollectedByType
	 */
	public TreeMap<String, Integer> getItemsCollectedByType() {
		return itemsCollectedByType;
	}

	/**
	 * @return the totalCashIssued
	 */
	public double getTotalCashIssued() {
		return totalCashIssued;
	}

	/**
	 * @return the totalCouponsIssued
	 */
	public int getTotalCouponsIssued() {
		return totalCouponsIssued;
	}
	
	/**
	 * @return the totalValueIssued
	 */
	public double getTotalValueIssued() {
		return totalValueIssued;
	}
	
	
	public TreeMap<String, Double> getItemsInList() {
		return itemsInList;
	}


	public ArrayList<String> getRecyclableItemList() {
		return recyclableItemList;
	}

	/**
	 * @param machineId the machineId to set
	 */
	public void setMachineId(int machineId) {
		this.machineId = machineId;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * @param moneyInMachine the moneyInMachine to set
	 */
	public void setMoneyInMachine(double moneyInMachine) {
		this.moneyInMachine = moneyInMachine;
	}

	/**
	 * @param couponsInMachine the couponsInMachine to set
	 */
	public void setCouponsInMachine(int couponsInMachine) {
		this.couponsInMachine = couponsInMachine;
	}

	/**
	 * @param weightInMachine the weightInMachine to set
	 */
	public void setWeightInMachine(double weightInMachine) {
		this.weightInMachine = weightInMachine;
	}

	/**
	 * @param emptyTimestamp the emptyTimestamp to set
	 */
	public void setEmptyTimestamp(TreeMap<Date, Double> emptyTimestamp) {
		this.emptyTimestamp = emptyTimestamp;
	}

	/**
	 * @param numberOfTimesEmptied the numberOfTimesEmptied to set
	 */
	public void setNumberOfTimesEmptied(int numberOfTimesEmptied) {
		this.numberOfTimesEmptied = numberOfTimesEmptied;
	}

	/**
	 * @param weightCapacity the weightCapacity to set
	 */
	public void setWeightCapacity(double weightCapacity) {
		this.weightCapacity = weightCapacity;
	}

	/**
	 * @param totalItemsCollected the totalItemsCollected to set
	 */
	public void setTotalItemsCollected(int totalItemsCollected) {
		this.totalItemsCollected = totalItemsCollected;
	}

	
	public void setItemsCollectedByType(
			TreeMap<String, Integer> itemsCollectedByType) {
		this.itemsCollectedByType = itemsCollectedByType;
	}

	/**
	 * @param itemsCollectedByType the itemsCollectedByType to updated
	 */
	public void updateItemsCollectedByType(String item) {
		TreeMap<String, Integer> itemsCollected = this.getItemsCollectedByType();
		itemsCollected.put(item, itemsCollected.get(item)+1);	
		this.itemsCollectedByType = itemsCollected;
	}

	/**
	 * @param totalCashIssued the totalCashIssued to set
	 */
	public void setTotalCashIssued(double totalCashIssued) {
		this.totalCashIssued = totalCashIssued;
	}

	/**
	 * @param totalCouponsIssued the totalCouponsIssued to set
	 */
	public void setTotalCouponsIssued(int totalCouponsIssued) {
		this.totalCouponsIssued = totalCouponsIssued;
	}
	
	public void setTotalValueIssued(double newValue) {
		this.totalValueIssued += newValue;
	}
	
	public void setItemsInList(TreeMap<String, Double> itemsInList) {
		this.itemsInList = itemsInList;
	}

	public void setRecyclableItemList(ArrayList<String> recyclableItemList) {
		this.recyclableItemList = recyclableItemList;
	}

}
