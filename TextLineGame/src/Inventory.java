import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import java.sql.*;

public class Inventory {
	public ArrayList<String> bag;
	
	public Inventory() { bag = new ArrayList<String>(); }
	
	public int numInBag(String s) {
		if (!bag.contains(s)) {
			return 0;
		}
		else {
			int count = 0;
			
			for (String str : bag) {
				if (str.equalsIgnoreCase(s)) {
					count++;
				}
			}
			
			return count;
		}
	}
	
	public boolean removeItem(String s) {
		return bag.remove(s);
	}
	
	public boolean addItem(String s) {
		return bag.add(s);
	}
	
	public String printInventory(Connection c) throws Exception {
		
		//get the counts of each item in the arraylist
		//using Collections class
		Map<String, Long> itemCounts = 
				bag.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
		
		//build the return string
		String ret = "Your Inventory:\n---------------------------------------------------------------------\n";
		ret = "Item\t[Commands]\tQuantity (if > 1)\n---------------------------------------------------------------------\n";
		
		//for each entry
		for (Map.Entry<String, Long> e : itemCounts.entrySet()) {
			//get the id of the item (should exist regardless)
			PreparedStatement qs = c.prepareStatement("select id from items where name = ?");
			qs.setString(1, e.getKey());
			ResultSet itemId = qs.executeQuery();
			int iId = itemId.getInt(1);
			
			//get the list of commands that identifies the item
			//the commands are how you interact with the item
			PreparedStatement ps = c.prepareStatement("Select name from nouns where item_id = ?");
			ps.setInt(1, iId);
			ResultSet cmds = ps.executeQuery();
			//build the commands list
			String commands = "[";
			while (cmds.next()) {
				commands += cmds.getString(1) + ", ";
			}
			
			//get the item name, get the commands list
			ret += e.getKey() + "\t" + commands + "]";
			if (e.getValue() == 1) {
				ret += "\n"; //if the item is unique, the quantity is not shown
			}
			else {
				ret += "\t" + e.getValue().toString() + "\n"; //else, show how many.
			}
		}
		
		return ret;
	}
	
}
