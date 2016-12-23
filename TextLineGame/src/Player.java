import java.util.HashMap;

public class Player extends Actor {
	public enum ClassType { BRAWLER, SHOOTER, TANK, THIEF, BALANCED };
	ClassType heroClass;
	long xp;
	int level;
	HashMap<Integer, Long> xpTable;
	final int MAX_LEVEL = 10;
	
	/*
	 * Needless code; only here so the code can compile
	 */
	public Player(String nm, Gender gend, int max_hp, int max_mp, int str, int pow, int end, int spd) {
		super(nm, gend, max_hp, max_mp, str, pow, end, spd);
	}
	
	public Player(ClassType c, String nm, Gender gend, int max_hp, 
			int max_mp, int str, int pow, int end, int spd) {
		super(nm, gend, max_hp, max_mp, str, pow, end, spd); 
		heroClass = c;
		xp = 0;
		level = 1;
		
		xpTable = ExpTableLibrary.getTable(name, heroClass);
		
	}
	
	public String viewInfo() {
		String retVal = super.getStats();

		retVal += "Level: " + Integer.toString(level) + "\n";
		retVal += "EXP: " + Long.toString(xp) + "\n";
		retVal += "To next level: " + Long.toString(calcToNext()) + "\n";
		retVal += "----------------------";
		
		return retVal;
	}
	
	private long calcToNext() {
		return level < MAX_LEVEL ? xpTable.get(level + 1) - xp : 0;
	}
}
