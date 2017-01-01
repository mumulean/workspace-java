import java.sql.*;
import java.util.*;
public class Game {
	
	public String errorString = "";
	public boolean inProgress = true;
	
	
	
	private String currentMap = "Tyrant's Town";
	private Connection c = null;
	private HashMap<Integer, Room> allRooms;
	private int location;

	private Player hero;
	private int gold;
	private Inventory inv;
	
	private static final String[] statKeys = {"Max_HP", "Max_MP", "Strength", "Power", "Endurance", "Speed"};
	
	public Game() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:TLG.db");
			init(); //pass to init handler method
			generateHero(); 
			
		} catch (Exception e) {
			e.printStackTrace();
			errorString = "Failed to find the database!";
		}
		
		
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
	
	private void generateHero() {
		boolean flag = true;	
		do {
			CharacterGenerator gen = new CharacterGenerator();
			gen.setVisible(true);
			flag = gen.charOK;
			
			if (gen.charOK) {
				HashMap<String, Integer> tempStats = gen.playerAttribs;
				hero = new Player(gen.playerClass, gen.playerName, gen.playerGender, 
						tempStats.get("Max_HP"), tempStats.get("Max_MP"), tempStats.get("Strength"),
						tempStats.get("Power"), tempStats.get("Endurance"), tempStats.get("Speed"));
			}
			
		} while (!flag);
		
		
		gold = (int)(Math.ceil(Math.random() * 10) * 10) + 40;
		
		inv = new Inventory();
		//TODO set starting inventory
		//TODO get items tables going
		switch (hero.heroClass) {
			case SHOOTER:
				inv.addItem("Staff");
				break;
			case BRAWLER:
				inv.addItem("Brass Knuckles");
				break;
			case THIEF:
				inv.addItem("Fleetfoot Shoes");
				break;
			case TANK:
				inv.addItem("Armor");
			default:
				inv.addItem("Sword");
				break;
		}
		
	}
	
	public String doLook() throws Exception {
		Room current = allRooms.get(location);
		
		return current.getLook(c);
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
	
	public String doExamine(String noun) throws Exception {
		
		//Uppercase the noun first.
		noun = noun.toUpperCase();
		
		PreparedStatement nounId = c.prepareStatement("Select item_id from nouns where name = ?");
		nounId.setString(1, noun);
		ResultSet itemId = nounId.executeQuery();
		int iId = itemId.getInt(1);
		
		if (iId != 0) {
			PreparedStatement itemSuch = c.prepareStatement("Select name, descript from items where id = ?");
			itemSuch.setInt(1, iId);
			ResultSet itemInfo = itemSuch.executeQuery();
			String itemName = itemInfo.getString(1);
			String itemDesc = itemInfo.getString(2);
			
			if (inv.numInBag(itemName) > 0 || 
				hero.equipment.containsValue(itemName)) {
				return itemName + "\n-------------\n" + itemDesc + "\n";
			}
			else {
				return "You don't even have this item!\n";
			}
		}
		return "Item does not exist!\n";
	}
	
	public String doDrop(String noun) throws Exception {
		//Uppercase the noun first.
		noun = noun.toUpperCase();
				
		PreparedStatement nounId = c.prepareStatement("Select item_id from nouns where name = ?");
		nounId.setString(1, noun);
		ResultSet itemId = nounId.executeQuery();
		int iId = itemId.getInt(1);
		
		if (iId != 0) {
			PreparedStatement itemSuch = c.prepareStatement("Select name from items where id = ?");
			itemSuch.setInt(1, iId);
			ResultSet itemInfo = itemSuch.executeQuery();
			String itemName = itemInfo.getString(1);
			
			if (inv.numInBag(itemName) > 0) {
				inv.removeItem(itemName);
				allRooms.get(location).addItem(itemName);
				return "You drop a " + itemName + ".\n";
			}
			else {
				return "You don't even have this item!\n";
			}
		}
		return "Item does not exist!\n";
	}
	
	public String doTake(String noun) throws Exception {
		//Uppercase the noun first.
		noun = noun.toUpperCase();
						
		PreparedStatement nounId = c.prepareStatement("Select item_id from nouns where name = ?");
		nounId.setString(1, noun);
		ResultSet itemId = nounId.executeQuery();
		int iId = itemId.getInt(1);
		
		if (iId != 0) {
			PreparedStatement itemSuch = c.prepareStatement("Select name from items where id = ?");
			itemSuch.setInt(1, iId);
			ResultSet itemInfo = itemSuch.executeQuery();
			String itemName = itemInfo.getString(1);
			Room current = allRooms.get(location);
			if (current.onGround.contains(itemName)) {
				current.onGround.remove(itemName);
				inv.addItem(itemName);
				return "You take the " + itemName + " and stow it in your bag.\n";
			}
			else {
				return "That item is not on the ground!\n";
			}
		}
		return "Item does not exist!\n";
	}
	
	public String doEquip(String noun) throws Exception {
		noun = noun.toUpperCase();
		
		PreparedStatement nounId = c.prepareStatement("Select item_id from nouns where name = ?");
		nounId.setString(1, noun);
		ResultSet itemId = nounId.executeQuery();
		int iId = itemId.getInt(1);
		
		if (iId != 0) {
			PreparedStatement itemSuch = c.prepareStatement("Select name from items where id = ?");
			itemSuch.setInt(1, iId);
			ResultSet itemInfo = itemSuch.executeQuery();
			String itemName = itemInfo.getString(1);
			
			PreparedStatement equipInfo = c.prepareStatement("select * from equips where id = ?");
			equipInfo.setInt(1, iId);
			ResultSet equipment = equipInfo.executeQuery();
			
			if (!equipment.isBeforeFirst()) {
				return "You can't equip that.\n";
			}

			if (inv.numInBag(itemName) > 0) {
				String equipSpot = equipment.getString(2);
				inv.removeItem(itemName);
				int index = 0;
				for (int i = 3; i <= 14; i++) {
					index = (i - 3) / 2;
					if (i % 2 == 0) { //double, multiplier
						double value = equipment.getDouble(i);
						if (value != 0.0)
							hero.attributes.addMultMod(statKeys[index], value);		
					}
					else { //int, adder
						int value = equipment.getInt(i);
						if (value != 0)
							hero.attributes.addAddMod(statKeys[index], value);
					}
				}
				String previousEquip = hero.doEquip(equipSpot, itemName);
				if (previousEquip.equals("Nothing"))
					return "You equip the " + itemName + ".\n";
				else {
					inv.addItem(previousEquip);
					return "You equip the " + itemName + " by swapping it with your " + previousEquip + ".\n";
				}
			}
			else {
				return "You do not have that on you!\n";
			}
		}
		return "Item does not exist!\n";
	}
	
	public String doUnequip(String noun) throws Exception {
		
		if (hero.equipment.isEmpty())
			return "You have nothing equipped!\n";
		
		noun = noun.toUpperCase();
		
		PreparedStatement nounId = c.prepareStatement("Select item_id from nouns where name = ?");
		nounId.setString(1, noun);
		ResultSet itemId = nounId.executeQuery();
		int iId = itemId.getInt(1);
		
		if (iId != 0) {
			PreparedStatement itemSuch = c.prepareStatement("Select name from items where id = ?");
			itemSuch.setInt(1, iId);
			ResultSet itemInfo = itemSuch.executeQuery();
			String itemName = itemInfo.getString(1);
			
			PreparedStatement equipInfo = c.prepareStatement("select * from equips where id = ?");
			equipInfo.setInt(1, iId);
			ResultSet equipment = equipInfo.executeQuery();
			
			if (!equipment.isBeforeFirst()) {
				return "That is not equipment.\n";
			}

			if (hero.equipment.containsValue(itemName)) {
				String equipSpot = equipment.getString(2);
				inv.removeItem(itemName);
				int index = 0;
				for (int i = 3; i <= 14; i++) {
					index = (i - 3) / 2;
					if (i % 2 == 0) { //double, multiplier
						double value = equipment.getDouble(i);
						if (value != 0.0)
							hero.attributes.removeMultMod(statKeys[index], value);		
					}
					else { //int, adder
						int value = equipment.getInt(i);
						if (value != 0)
							hero.attributes.removeAddMod(statKeys[index], value);
					}
				}
				String previousEquip = hero.doUnequip(equipSpot);
				if (!previousEquip.equals("Nothing")) {
					inv.addItem(previousEquip);
					return "You unequip the " + itemName + " and put it back in your bag.\n";
				}
			}
			else {
				return "You do not have that on you!\n";
			}
		}
		return "Item does not exist!\n";
	}
	
	public String parseCommand(String input) throws Exception {
		
		input = input.trim();
		
		if (input.isEmpty()) {
			return "Please enter a command.\n";
		}
		
		String[] toks = input.split(" ");
		
		if (toks.length < 1 || toks.length > 3) {
			return "Use one through three words only!\n";
		}
		if (toks.length == 1) {
			if (toks[0].equalsIgnoreCase("look")) {
				return doLook();
			}
			else if (toks[0].equalsIgnoreCase("north") || toks[0].equalsIgnoreCase("east") ||
				toks[0].equalsIgnoreCase("south") || toks[0].equalsIgnoreCase("west")) {
				return doMove(toks[0]);
			}
			else if (toks[0].equalsIgnoreCase("stats")) {
				return hero.viewInfo() + "\n";
			}
			else if (toks[0].equalsIgnoreCase("gold") || toks[0].equalsIgnoreCase("money")) {
				return "You have " + Integer.toString(gold) + " gold.\n";
			}
			else if (toks[0].equalsIgnoreCase("bag") || toks[0].equalsIgnoreCase("inv") ||
					toks[0].equalsIgnoreCase("inventory")) {
				return inv.printInventory(c) + "\n";
			}
			else if (toks[0].equalsIgnoreCase("exit") || toks[0].equalsIgnoreCase("done")) {
				inProgress = false;
				return "Thank you for playing. Goodbye!\n";
			}
		}
		if (toks.length == 2) {
			if (toks[0].equalsIgnoreCase("look") || toks[0].equalsIgnoreCase("examine")) {
				return doExamine(toks[1]);
			}
			else if (toks[0].equalsIgnoreCase("drop")) {
				return doDrop(toks[1]);
			}
			else if (toks[0].equalsIgnoreCase("take")) {
				return doTake(toks[1]);
			}
			else if (toks[0].equalsIgnoreCase("equip")) {
				return doEquip(toks[1]);
			}
			else if (toks[0].equalsIgnoreCase("unequip")) {
				return doUnequip(toks[1]);
			}
			else if (toks[0].equalsIgnoreCase("use")) {
				return "NOT IMPLEMENTED!\n";
			}
		}
		return "Invalid command!\n";
	}
}
