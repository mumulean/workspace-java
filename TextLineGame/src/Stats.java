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
	}
	
	public Stats(HashMap<String, Integer> bStats) {
		baseStats = bStats;
		addStats = new HashMap<String, Integer>();
		multStats = new HashMap<String, Double>();
	}
	
	public void addAddMod(String id, int value) {
		addStats.put(id, value);
	}
	
	public void removeAddMod(String id) {
		addStats.remove(id);
	}
	
	public void addMultMod(String id, double value) {
		multStats.put(id, value);
	}
	
	public void removeMultMod(String id) {
		multStats.remove(id);
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
		
		for (Map.Entry<String, Integer> entry : addStats.entrySet())
		{
			if (entry.getKey().equals(id)) {
				factor += (entry.getValue());
			}
		}
		finalValue *= factor;
		return String.format("0.2d", Double.toString(finalValue));
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
	
}
