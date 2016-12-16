import java.sql.*;
public class Game {
	
	private int location;
	private String currentMap = "Tyrant's Town";
	
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
	}
	
	public String doLook() throws Exception {
		PreparedStatement ps = c.prepareStatement("Select name, descript FROM rooms WHERE id = ?");
		ps.setInt(1, location);
		
		ResultSet rs = ps.executeQuery();
		
		return rs.getString(1) + "\n" + rs.getString(2) + "\n";
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
		if (input.equalsIgnoreCase("look")) {
			return doLook();
		}
		else if (input.equalsIgnoreCase("north") || input.equalsIgnoreCase("south") || 
				input.equalsIgnoreCase("east") || input.equalsIgnoreCase("west")) {
			return doMove(input);
		}
		else if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("done")) {
			c.close();
			inProgress = false;
			return "Goodbye!\n";
		}
		else {
			return "Invalid command.\n";
		}
	}
}
