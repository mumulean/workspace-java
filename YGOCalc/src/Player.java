
public class Player {
	protected String name;
	private int life;
	private final int startLife;
	
	public Player() {
		name = "Player";
		life = startLife = 8000;
	}
	
	public Player(String n, int start) {
		name = n;
		life = startLife = start;
	}
	
	public void halveLP() {
		life = (life % 2) + (life / 2);
	}
	
	public void setLP(int lp) {
		life = lp;
	}
	
	public void modLPByValue(int lp) {
		life += lp;
		if (life < 0) {
			life = 0;
		}
	}
	
	public int getLP() {
		return life;
	}
	
	public String getLPString() {
		String ret = Integer.toString(life);
		
		while (ret.length() < 4) {
			ret = "0" + ret;
		}
		
		return ret;
	}
	
	public int getStartLP() {
		return startLife;
	}
	
	public double getPercent() {
		if (life > startLife) {
			return 1.0;
		}
		return (double)life/(double)startLife;
	}
	
	public String getName() {
		return name;
	}
}
