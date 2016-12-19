/*
 * 
 */
public class Player {
	//class fields
	protected String name;
	private int life;
	private final int startLife;
	
	//default values
	public Player() {
		name = "Player";
		life = startLife = 8000;
	}
	
	//pass values
	public Player(String n, int start) {
		name = n;
		life = startLife = start;
	}
	
	//cut player's LP in half
	//as LP is an int, half is rounded down
	//halveLP would reduce LP of 75 to 38 after call
	public void halveLP() {
		life = (life % 2) + (life / 2);
	}
	
	//sets the LP equal to a value
	public void setLP(int lp) {
		life = lp;
	}
	
	//modifies the LP by a positive or negative value
	public void modLPByValue(int lp) {
		life += lp;
		if (life < 0) { //life cannot go below 0
			life = 0;
		}
	}
	
	//gets the LP as an integer
	public int getLP() {
		return life;
	}
	
	//gets the LP as an appropriately padded string
	public String getLPString() {
		String ret = Integer.toString(life);
		
		while (ret.length() < 4) {
			ret = "0" + ret; //0100, 0050, 0001, 0000
		}
		
		return ret;
	}
	
	//get the player's starting LP (default 8000)
	public int getStartLP() {
		return startLife;
	}
	
	//used for the UI, gets the proportion
	public double getPercent() {
		if (life > startLife) {
			return 1.0;
		}
		return (double)life/(double)startLife;
	}
	
	//get player's name
	public String getName() {
		return name;
	}
}
