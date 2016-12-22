import java.sql.*;
import java.util.*;
public class Game {
	
	private int location;
	private String currentMap = "Tyrant's Town";
	public HashMap<Integer, Room> allRooms;
	public Connection c = null;
	public String errorString = "";
	public boolean inProgress = true;
	
	
	public Game() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:TLG.db");
			init();
			
		} catch (Exception e) {
			e.printStackTrace();
			errorString = "Failed to find the database!";
		}
		
		//First we query the maps table for the relevant starting id
		//Then we query the rooms table with the starting id.
		//Each time we do a move command (north/south/west/east)
		//we move in that direction.
	}

	private void init() throws Exception {
		//Map table query
		PreparedStatement ps = c.prepareStatement("SELECT initial_id FROM maps"
				+ " WHERE name = ?");
		ps.setString(1, currentMap);

		ResultSet firstLoc = ps.executeQuery();
		location = firstLoc.getInt(1);
		
		allRooms = new HashMap<Integer, Room>();
		
		PreparedStatement ps2 = c.prepareStatement("SELECT * From Rooms");
		
		ResultSet getMaps = ps2.executeQuery();
		while (getMaps.next()) {
			int rId = getMaps.getInt(1);
			String rName =  getMaps.getString(2);
			String rDesc = getMaps.getString(3);
			
			allRooms.put(rId, new Room(rId, rName, rDesc));
		}
	}
	
	public String doLook() throws Exception {
		Room current = allRooms.get(location);
		
		return current.name + "\n" + current.descript + "\n";
	}
	
	public String doMove(String dir) throws Exception {
		PreparedStatement ps = c.prepareStatement("Select north_exit_id, east_exit_id, south_exit_id, west_exit_id FROM exits where room_id = ?");
		ps.setInt(1, location);
		
		ResultSet rs = ps.executeQuery();
		
		if (dir.equalsIgnoreCase("north") && rs.getInt(1) != 0) {
			location = rs.getInt(1);
		}
		else if (dir.equalsIgnoreCase("west") && rs.getInt(4) != 0) {
			location = rs.getInt(4);
		}
		else if (dir.equalsIgnoreCase("east") && rs.getInt(2) != 0) {
			location = rs.getInt(2);
		}
		else if (dir.equalsIgnoreCase("south") && rs.getInt(3) != 0) {
			location = rs.getInt(3);
		}
		else {
			return "Cannot move that way!\n";
		}
		
		
		return doLook();
	}
	
	public String parseCommand(String input) throws Exception {
		if (input.isEmpty()) {
			return "";
		}
		
		String[] toks = input.split(" ");
		
		if (toks.length < 1 || toks.length > 3) {
			return "Use one through three words only!\n";
		}
		//check if in combat
		if (toks[0].equalsIgnoreCase("look")) {
			return doLook();
		}
		else if (toks[0].equalsIgnoreCase("north") || toks[0].equalsIgnoreCase("east") ||
			toks[0].equalsIgnoreCase("south") || toks[0].equalsIgnoreCase("west")) {
			return doMove(toks[0]);
		}
		else if (toks[0].equalsIgnoreCase("exit") || toks[0].equalsIgnoreCase("done")) {
			inProgress = false;
			return "Thank you for playing. Goodbye!\n";
		}
		
		
		return "Invalid command!\n";
	}
}
