package coen275_project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import javax.swing.DefaultComboBoxModel;

public class ItemsInMachine {

	private ArrayList<String> recyclableItems;
	private TreeMap<String, Double> itemList; // price per pound
	
	public ItemsInMachine() {

		String[] items = {"Aluminium", "Glass", "Plastic", "Paper", "Steel"};
		recyclableItems = new ArrayList<String>();
		Collections.addAll(recyclableItems, items);
		
		itemList = new TreeMap<String, Double>();
		for(String s : recyclableItems) {
			double price = Math.random()*10 + 1;
			itemList.put(s, price);
		}
	}
	
	public Double getPriceOfItem(String item) {
		if(itemList.containsKey(item))
			return itemList.get(item);
		else
			return (double)-1;
	}
	
	/**
	 * @return the recyclableItems
	 */
	public ArrayList<String> getRecyclableItems() {
		return recyclableItems;
	}
	
	/**
	 * @return the itemList
	 */
	public TreeMap<String, Double> getItemList() {
		return itemList;
	}

	/**
	 * @param recyclableItems the recyclableItems to set
	 */
	public void setRecyclableItems(ArrayList<String> recyclableItems) {
		this.recyclableItems = recyclableItems;
	}
	
	/**
	 * @param itemList the itemList to set
	 */
	public void setItemList(TreeMap<String, Double> itemList) {
		TreeMap<String, Double> currentItems = new TreeMap<String, Double>();
		for(String s: itemList.keySet()) {
			currentItems.put(s, itemList.get(s));
		}
		this.itemList = itemList;
		
		ArrayList<String> newItemList = new ArrayList<String>();
		for(String s:itemList.keySet()) {
				newItemList.add(s);
		}
		this.setRecyclableItems(newItemList);
	}
	
	public DefaultComboBoxModel updateItemList(DefaultComboBoxModel model, TreeMap<String, Double> itemList) {
		model.removeAllElements();
		ArrayList<String> newItemList = new ArrayList<String>();
		
		model.addElement("Default");
		
		for(String s:itemList.keySet()) {
			model.addElement(s);
			newItemList.add(s);
		}
		
		this.setRecyclableItems(newItemList);
		return model;
	}
	
	public void addItemToList(String item, Double price) {
		TreeMap<String, Double> currentItems = this.getItemList();
		if(!currentItems.containsKey(item))
			currentItems.put(item, price);
		
		this.setItemList(currentItems);
		ArrayList<String> newItemList = this.getRecyclableItems();
		if(!newItemList.contains(item))
			newItemList.add(item);
		
		this.setRecyclableItems(newItemList);
	}
	
	public void removeItemFromList(String item) {
		TreeMap<String, Double> currentItems = this.getItemList();
		if(currentItems.containsKey(item))
			currentItems.remove(item);
		
		this.setItemList(currentItems);
		
		ArrayList<String> newItemList = this.getRecyclableItems();
		if(newItemList.contains(item))
			newItemList.remove(item);
		
		this.setRecyclableItems(newItemList);
	}
	
	public void modifyPriceOfItem(String item, Double price) {
		TreeMap<String, Double> currentItems = this.getItemList();
		if(currentItems.containsKey(item))
			currentItems.put(item, price);
		
		this.setItemList(currentItems);
	}
}
