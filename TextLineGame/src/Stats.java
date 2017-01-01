import java.util.HashMap;
import java.util.Map;

public class Stats {
	public HashMap<String, Integer> baseStats;
	private HashMap<String, Integer> addStats;
	private HashMap<String, Double> multStats;
	
	public Stats() {
		addStats = new HashMap<String, Integer>();
		multStats = new HashMap<String, Double>();
		baseStats = new HashMap<String, Integer>();
		baseStats.put("HP", 5);
		baseStats.put("Max_HP", 5);
		baseStats.put("MP", 0);
		baseStats.put("Max_MP", 0);
		baseStats.put("Strength", 1);
		baseStats.put("Power", 0);
		baseStats.put("Endurance", 1);
		baseStats.put("Speed", 1);
		
		addStats.put("Max_HP", 0);
		addStats.put("Max_MP", 0);
		addStats.put("Strength", 0);
		addStats.put("Power", 0);
		addStats.put("Endurance", 0);
		addStats.put("Speed", 0);
		
		multStats.put("Max_HP", 0.0);
		multStats.put("Max_MP", 0.0);
		multStats.put("Strength", 0.0);
		multStats.put("Power", 0.0);
		multStats.put("Endurance", 0.0);
		multStats.put("Speed", 0.0);
	}
	
	public Stats(HashMap<String, Integer> bStats) {
		baseStats = bStats;
		addStats = new HashMap<String, Integer>();
		multStats = new HashMap<String, Double>();
		
		addStats.put("Max_HP", 0);
		addStats.put("Max_MP", 0);
		addStats.put("Strength", 0);
		addStats.put("Power", 0);
		addStats.put("Endurance", 0);
		addStats.put("Speed", 0);
		
		multStats.put("Max_HP", 0.0);
		multStats.put("Max_MP", 0.0);
		multStats.put("Strength", 0.0);
		multStats.put("Power", 0.0);
		multStats.put("Endurance", 0.0);
		multStats.put("Speed", 0.0);
	}
	
	public void addAddMod(String id, int value) {
		int curVal = addStats.get(id);
		addStats.put(id, curVal + value);
	}
	
	public void removeAddMod(String id, int value) {
		int curVal = addStats.get(id);
		addStats.put(id, value - curVal);
	}
	
	public void addMultMod(String id, double value) {
		double curVal = multStats.get(id);
		multStats.put(id, curVal + value);
	}
	
	public void removeMultMod(String id, double value) {
		double curVal = multStats.get(id);
		multStats.put(id, value - curVal);
	}
	
	public String deriveStat(String id) {
		double finalValue = baseStats.get(id);
		double factor = 1.0;
		
		for (Map.Entry<String, Integer> entry : addStats.entrySet())
		{
			if (entry.getKey().equals(id)) {
				finalValue += (entry.getValue() / 1.0);
			}
		}
		
		for (Map.Entry<String, Double> entry : multStats.entrySet())
		{
			if (entry.getKey().equals(id)) {
				factor += (entry.getValue());
			}
		}
		finalValue *= factor;
		return String.format("%1$,.0f", finalValue);
	}
	
	public String getStatBlock() {
		String block = "";
		block += "----------------------\n";	
		//for health and mana, only the final (derived) values are shown
		String health = deriveStat("HP") + " / " + deriveStat("Max_HP");
		String mana = deriveStat("MP") + " / " + deriveStat("Max_MP");
		
		block += "HP: " + health + "\n";
		block += "MP: " + mana + "\n";
		block += "----------------------\n";
		//for the rest of the stats, show the current stat, with the base stat
		//in square brackets next to it.
		block += "STR: " + deriveStat("Strength") + " [ " + 
				Integer.toString(baseStats.get("Strength")) + " ]\n";
		block += "POW: " + deriveStat("Power") + " [ " + 
				Integer.toString(baseStats.get("Power")) + " ]\n";
		block += "END: " + deriveStat("Endurance") + " [ " + 
				Integer.toString(baseStats.get("Endurance")) + " ]\n";
		block += "SPD: " + deriveStat("Speed") + " [ " + 
				Integer.toString(baseStats.get("Speed")) + " ]\n";		
		block += "----------------------\n";
		
		return block;
	}
	
	public static HashMap<String, Integer> getStartingBaseStats(Player.ClassType playerClass) {
		HashMap<String, Integer> returnMe = new HashMap<String, Integer>();
		
		switch (playerClass) {
			case BRAWLER:
				returnMe.put("HP", 45);
				returnMe.put("Max_HP", 45);
				returnMe.put("MP", 30);
				returnMe.put("Max_MP", 30);
				returnMe.put("Strength", 13);
				returnMe.put("Power", 8);
				returnMe.put("Endurance", 9);
				returnMe.put("Speed", 8);
				break;
			case SHOOTER:
				returnMe.put("HP", 35);
				returnMe.put("Max_HP", 35);
				returnMe.put("MP", 50);
				returnMe.put("Max_MP", 50);
				returnMe.put("Strength", 9);
				returnMe.put("Power", 13);
				returnMe.put("Endurance", 9);
				returnMe.put("Speed", 8);
				break;
			case TANK:
				returnMe.put("HP", 55);
				returnMe.put("Max_HP", 55);
				returnMe.put("MP", 35);
				returnMe.put("Max_MP", 35);
				returnMe.put("Strength", 10);
				returnMe.put("Power", 7);
				returnMe.put("Endurance", 14);
				returnMe.put("Speed", 7);
				break;
			case THIEF:
				returnMe.put("HP", 35);
				returnMe.put("Max_HP", 35);
				returnMe.put("MP", 35);
				returnMe.put("Max_MP", 35);
				returnMe.put("Strength", 8);
				returnMe.put("Power", 10);
				returnMe.put("Endurance", 8);
				returnMe.put("Speed", 14);
				break;
			default:
				returnMe.put("HP", 50);
				returnMe.put("Max_HP", 50);
				returnMe.put("MP", 50);
				returnMe.put("Max_MP", 50);
				returnMe.put("Strength", 10);
				returnMe.put("Power", 10);
				returnMe.put("Endurance", 10);
				returnMe.put("Speed", 10);
				break;
		}
		return returnMe;
	}
	
}
