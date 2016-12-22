import java.util.ArrayList;
import java.util.HashMap;
public class Actor {
	
	enum StatusAliment { HEALTHY, POISON };

	private String name;
	private String gender;
	private StatusAliment status;
	
	//Attributes
	public Stats attributes;
	
	//Skills List
	private ArrayList<String> skillNames;
	
	public Actor(String nm, int gend, int max_hp, int max_mp, 
			int str, int pow, int end, int spd) {
		status = StatusAliment.HEALTHY;
		skillNames = new ArrayList<String>();
		
		name = nm;
		
		if (gend < 1 || gend > 2) {
			gender = "Neither/Genderless";
		}
		else {
			gender = (gend == 1) ? "Male" : "Female";
		}
		
		HashMap<String, Integer> stats = new HashMap<String, Integer>();
		
		stats.put("HP", max_hp);
		stats.put("Max_HP", max_hp);
		stats.put("MP", max_mp);
		stats.put("Max_MP", max_mp);
		stats.put("Strength", str);
		stats.put("Power", pow);
		stats.put("Endurance", end);
		stats.put("Speed", spd);
		
		attributes = new Stats(stats);
	}
	
	public void refresh() {
		int maxHP = attributes.baseStats.get("Max_HP");
		int maxMP = attributes.baseStats.get("Max_MP");
		
		heal(maxHP/8, maxMP/8);
	}
	
	public void takeDamage(int dmg) {
		int curHP = attributes.baseStats.get("HP");
		
		curHP -= dmg;
		
		attributes.baseStats.put("HP", curHP);
	}
	
	public void heal(int hpUp, int mpUp) {
		int maxHP = attributes.baseStats.get("Max_HP");
		int maxMP = attributes.baseStats.get("Max_MP");
		
		int curHP = attributes.baseStats.get("HP");
		int curMP = attributes.baseStats.get("MP");
		
		if (curHP + hpUp >= maxHP) {
			curHP = maxHP;
		}
		
		if (curMP + mpUp >= maxMP) {
			curMP = maxMP;
		}
		
		attributes.baseStats.put("HP", curHP);
		attributes.baseStats.put("MP", curMP);
		
	}
	
	public boolean isDead() {
		return (attributes.baseStats.get("HP") <= 0);
	}
	
	public boolean useSkill(String skillName) {
		return false;
	}
	
	public String getStats() {
		String ret = "Name: " + name + "\n";
		ret += "Gender: " + gender + "\n";
		ret += "Status: " + status.toString() + "\n";
		String statBlock = attributes.getStatBlock();
		
		return ret + statBlock;
	}
}
