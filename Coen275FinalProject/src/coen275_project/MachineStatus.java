package coen275_project;

import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

public class MachineStatus {
	
	double moneyInMachine; 	// in $
	int couponsInMachine;	// worth in $
	double weightInMachine;	// in tons
	private TreeMap<Date, Double> emptyTimestamp;
	int numberOfTimesEmptied;
	double weightCapacity; 	// in pounds
	int totalItemsCollected;
	private TreeMap<String, Integer> itemsCollectedByType;
	double totalCashIssued;
	int totalCouponsIssued;
	double totalValueIssued;
	
	public MachineStatus(ArrayList<String> recyclableItems) {
		// TODO Auto-generated constructor stub
		moneyInMachine = 200;
		couponsInMachine = 2;
		weightInMachine = 0;
		numberOfTimesEmptied = 0;
		weightCapacity = 1000; 
		totalItemsCollected = 0;
		totalCashIssued = 0;
		totalCouponsIssued = 0;
		totalValueIssued = 0;
		
		emptyTimestamp = new TreeMap<Date, Double>();
		
		itemsCollectedByType = new TreeMap<String, Integer>();
		for(String s: recyclableItems) {
			itemsCollectedByType.put(s, 0);
		}
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

	/**
	 * @param itemsCollectedByType the itemsCollectedByType to set
	 */
	public void setItemsCollectedByType(String item) {
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
}
