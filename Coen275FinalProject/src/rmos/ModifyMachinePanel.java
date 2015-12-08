package rmos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



// This Panel is used for both adding and modifying RecyclingMachines. It is a separate class in its own file so that it can 
// be reused for both purposes.
@SuppressWarnings("serial")
class ModifyMachinePanel extends JPanel {
	
	private JLabel itemsAcceptedLabel, itemLabel, priceLabel, addNewItemLabel, newTypeLabel, newPriceLabel, moneyLabel, couponLabel;

	private JButton removeButton, addButton;
	private JTextField typeField, priceField, moneyField, couponField;
	
	private TreeMap<String, Double> itemsAndPrices;

	private GridBagConstraints c;
	
	private ActionListener removeListener, addListener;
	
	private JPanel currentItemsPanel, addItemsPanel, moneyPanel, couponPanel;
	private DecimalFormat df;
	
	public ModifyMachinePanel() {
		
		// initializations
		removeListener = new RemoveItemListener();
		addListener = new AddItemListener();
		itemsAndPrices = new TreeMap<String, Double>();
		df = new DecimalFormat(); 
		df.setMaximumFractionDigits(2);  // ensure money value is no more than 2 decimal places
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// set up title label for this panel
		itemsAcceptedLabel = new JLabel("Current Items Accepted:");
		itemsAcceptedLabel.setFont(new Font("Arial", Font.PLAIN, 24));
		ProjectLauncher.underlineLabel(itemsAcceptedLabel);
		
		// set up currentItemsPanel
		currentItemsPanel = new JPanel();
		currentItemsPanel.setLayout(new GridBagLayout());
		currentItemsPanel.setBackground(Color.decode("#edd9c0")); // background light brown
		c = new GridBagConstraints();
		
		itemLabel = new JLabel("Item");
		ProjectLauncher.underlineLabel(itemLabel);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.33;
		currentItemsPanel.add(itemLabel, c);
		
		priceLabel = new JLabel("Price");
		ProjectLauncher.underlineLabel(priceLabel);
		c.gridx = 1;
		c.gridy = 0;
		currentItemsPanel.add(priceLabel, c);
		
		// set up addItemsPanel
		addItemsPanel = new JPanel();
		addItemsPanel.setLayout(new GridBagLayout());
		addItemsPanel.setBackground(Color.decode("#edd9c0")); // background light brown

		addNewItemLabel = new JLabel("Add new item:");
		ProjectLauncher.underlineLabel(addNewItemLabel);
		c.gridx = 0;
		c.gridy = 0;
		addItemsPanel.add(addNewItemLabel, c);
				
		newTypeLabel = new JLabel("Type:");
		c.gridx = 0;
		c.gridy = 1;
		addItemsPanel.add(newTypeLabel, c);
		
		typeField = new JTextField("", 10);
		typeField.setEditable(true);
		c.gridx = 1;
		c.gridy = 1;
		addItemsPanel.add(typeField, c);
		
		newPriceLabel = new JLabel("Price:");
		c.gridx = 0;
		c.gridy = 2;
		addItemsPanel.add(newPriceLabel, c);
		
		priceField = new JTextField("", 10);
		priceField.setEditable(true);
		c.gridx = 1;
		c.gridy = 2;
		addItemsPanel.add(priceField, c);
		
		addButton = new JButton("Add");
		addButton.addActionListener(addListener);
		c.gridx = 1;
		c.gridy = 3;
		addItemsPanel.add(addButton, c);
		
		// set up the money panel
		moneyPanel = new JPanel();
		moneyPanel.setLayout(new FlowLayout());
		moneyLabel = new JLabel("Money in the machine (in dollars): ");
		moneyField = new JTextField("100.00", 10);
		moneyField.setEditable(true);
		moneyPanel.setBackground(Color.decode("#edd9c0")); // background light brown
		moneyPanel.add(moneyLabel);
		moneyPanel.add(moneyField);
		
		// set up the coupon panel
		couponPanel = new JPanel();
		couponPanel.setLayout(new FlowLayout());
		couponLabel = new JLabel("Coupons in the machine: ");
		couponField = new JTextField("10", 10);
		couponField.setEditable(true);
		couponPanel.setBackground(Color.decode("#edd9c0")); // background light brown
		couponPanel.add(couponLabel);
		couponPanel.add(couponField);
		
		this.setBackground(Color.decode("#edd9c0")); // background light brown
		this.add(itemsAcceptedLabel);
		this.add(currentItemsPanel);
		this.add(Box.createRigidArea(new Dimension(0,50)));
		this.add(addItemsPanel);
		this.add(Box.createRigidArea(new Dimension(0,50)));
		this.add(moneyPanel);
		this.add(couponPanel);
		
	}
	
	// This constructor is called when it is time to modify a machine (as opposed to adding a machine). In that case, the station already
	// has the current data for the machine. 
	public ModifyMachinePanel(TreeMap<String, Double> currentItemsAndPrices, double machineMoney, int machineCoupons) {
		
		// calls default constructor
		this();  
		
		itemsAndPrices = currentItemsAndPrices;
		moneyField.setText(df.format(machineMoney));
		couponField.setText("" + machineCoupons);
		
		updateItemPanel();
		
	}

	private void updateItemPanel(){
		
		int i = 1;

		// This function will re-create the entire panel based on what has been added or removed from the itemsAndPrices TreeMap. 
		// First we call removeAll() to give us a blank slate.
		currentItemsPanel.removeAll();
		
		itemLabel = new JLabel("Item");
		ProjectLauncher.underlineLabel(itemLabel);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.33;
		currentItemsPanel.add(itemLabel, c);
		
		priceLabel = new JLabel("Price");
		ProjectLauncher.underlineLabel(priceLabel);
		c.gridx = 1;
		c.gridy = 0;
		currentItemsPanel.add(priceLabel, c);
		
		// iterate through the itemsAndPrices TreeMap and print all current keys and values
		for (Entry<String, Double> entry : itemsAndPrices.entrySet()) {
			
			System.out.println("i="+i+"  key="+entry.getKey());
		    String item = entry.getKey();
		    Double price = entry.getValue();

		    itemLabel = new JLabel(item);
			c.gridx = 0;
			c.gridy = i;
			c.weightx = 0.33;
			currentItemsPanel.add(itemLabel, c);
			
			priceLabel = new JLabel(price.toString());
			c.gridx = 1;
			c.gridy = i;
			currentItemsPanel.add(priceLabel, c);
			
			removeButton = new JButton("Remove");
			removeButton.addActionListener(removeListener);
			removeButton.setActionCommand(item);
			c.gridx = 2;
			c.gridy = i;
			currentItemsPanel.add(removeButton, c);
			
		    i++;
		}
		
		// redraws the panel. This panel is used for both the addMachineFrame and the modifyMachineFrame. Either way, we want to re pack the frame
		// at the end of this function, but we don't know which frame we're in. Instead of creating some flag, we'll just try to pack each one. By
		// using try/catch, we won't crash when we try to pack a null frame.
		this.validate();
		try {
			RecyclingStation.addMachineFrame.pack();
		} catch (Exception e) {}
		
		try {
			RecyclingStation.modifyMachineFrame.pack();
		} catch (Exception e) {}
	}
		
	public TreeMap<String, Double> getItemsAndPrices() {
		
		return itemsAndPrices;
	}
	
	public double getMoney() {
		
		try {
			
			return Double.parseDouble(moneyField.getText());
		
		} catch (Exception e) {
		
			return -1;
		}
	}

	public int getCoupons() {
		
		try {
			
			return Integer.parseInt(couponField.getText());
			
		} catch (Exception e) {
			
			return -1;
		}
	}
	
	// Handles remove item button(s)
	class RemoveItemListener implements ActionListener {
		   public void actionPerformed(ActionEvent event) {
			   
			   // The remove button will have the itemsAndPrices TreeMap's key as the action command.
			   itemsAndPrices.remove(event.getActionCommand());

			   updateItemPanel();
			   
		   }		
	}
	
	// Handles add item button
	class AddItemListener implements ActionListener {
		   public void actionPerformed(ActionEvent event) {
			   
			   try {
				   String type = typeField.getText();
				   Double price = Double.parseDouble(priceField.getText());
				   
				   itemsAndPrices.put(type, price);
				   
				   updateItemPanel();
				   
			   } catch (Exception e) {
				   
				   // invalid input. Should probably print an error. We'll add that if we have time...
			   }			   

		   }		
	}
}
