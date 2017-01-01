import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.sql.*;
public class Room {
	public int id;
	public String name;
	public String descript;
	public ArrayList<String> onGround;
	//public ArrayList<String> npcInSight;
	//public double encRate;
	//public ArrayList<Integer> enemyIds;
	
	public Room(int x, String n, String d) {
		this.id = x;
		this.name = n;
		this.descript = d;
		this.onGround = new ArrayList<String>();
	}
	
	public String getLook(Connection c) throws Exception {
		String ret = this.name + "\n" + this.descript + "\n";
		if (onGround.size() > 0) {
			ret += "The following items are nearby:\n";
			
			Map<String, Long> itemCounts = 
					onGround.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
			
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
		}
		return ret;
	}
	
	public void addItem(String s) {
		onGround.add(s);
	}
	
	public void removeItem(String s) {
		onGround.remove(s);
	}
}
