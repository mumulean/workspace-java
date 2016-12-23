import java.util.HashMap;
import java.util.Random;
public final class ExpTableLibrary {
	private static final int MAX_LEVEL = 10; //what the highest level must be
	private static final int MIN_LEVEL = 2; //second lowest level
	private ExpTableLibrary() { }
	
	public static HashMap<Integer, Long> getTable(String heroName, 
			Player.ClassType heroClass) {
		HashMap<Integer, Long> tbl = new HashMap<Integer, Long>();
		Random rng = new Random(stringToSeed(heroName));
		long baseXP;
		switch (heroClass) {
			case BRAWLER:
				baseXP = (rng.nextLong() % 100) + 900;
				for (int i = MIN_LEVEL; i <= MAX_LEVEL; i++) {
					tbl.put(i, baseXP * (i - 1) * (i - 1));
				}
				break;
			case SHOOTER:
				baseXP = (rng.nextLong() % 250) + 1000;
				for (int i = MIN_LEVEL; i <= MAX_LEVEL; i++) {
					tbl.put(i, baseXP * (i - 1) * (i - 1));
				}
				break;
			case TANK:
				baseXP = (rng.nextLong() % 350) + 850;
				for (int i = MIN_LEVEL; i <= MAX_LEVEL; i++) {
					tbl.put(i, baseXP * (i - 1) * (i - 1));
				}
				break;
			case THIEF:
				baseXP = (rng.nextLong() % 150) + 950;
				for (int i = MIN_LEVEL; i <= MAX_LEVEL; i++) {
					tbl.put(i, baseXP * (i - 1) * (i - 1));
				}
				break;
			default: //balanced
				baseXP = (rng.nextLong() % 400) + 800;
				for (int i = MIN_LEVEL; i <= MAX_LEVEL; i++) {
					tbl.put(i, baseXP * (i - 1) * (i - 1));
				}
				break;
		}

		return tbl;
	}
	
	//Algorithm and name from here.
	//http://stackoverflow.com/a/23981907
	private static long stringToSeed(String s) {
		if (s == null) {
			return 0;
		}
		long hash = 0;
		for (char c : s.toCharArray()) {
			hash = 31L*hash + c;
		}
		return hash;
	}
}
