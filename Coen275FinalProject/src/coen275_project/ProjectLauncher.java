package coen275_project;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ProjectLauncher {
	
	// Different frames are declared statically here. This allows other frames to be able to switch between them.
	static Admin adminFrame;
	//static RecyclingStation recyclingStationFrame;
	
	
	public static void main(String[] args) {
		
		adminFrame = new Admin();
//		recyclingStationFrame = new RecyclingStation();
		
		adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		adminFrame.setTitle("Recycling Monitoring Station (RMOS) - Admin Login");

//		recyclingStationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		recyclingStationFrame.setTitle("Recycling Monitoring Station (RMOS)");
		
		// TODO: program will start with admin login screen. Display enter limit frame first
		adminFrame.pack();
		adminFrame.setVisible(true);      
	}
	
	
	// Underlining JLabels is a pain. So, I made this globally-available function to do it.
	@SuppressWarnings("unchecked")
	public static void underlineLabel (JLabel label) {
		
		Font font = label.getFont();
		@SuppressWarnings("rawtypes")
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		label.setFont(font.deriveFont(attributes));
	}
	
	public static Date setToBeginningOfDay(Date date) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    return cal.getTime();
	}
	
	public static Date setToEndOfDay(Date date) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
	    cal.set(Calendar.MINUTE, 59);
	    cal.set(Calendar.SECOND, 59);
	    cal.set(Calendar.MILLISECOND, 999);
	    return cal.getTime();
	}
	
	public static Date setToNDaysAgo(Date date, int n) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -n);
	    return cal.getTime();
	}
	
	public static Date setToWeekAgo(Date date) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -7);
		return cal.getTime();
	}
	
	public static Date setToMonthAgo(Date date) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();			
	}
}
