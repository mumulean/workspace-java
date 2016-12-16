import java.util.ArrayList;
public class Actor {
	
	enum ActorClasses { NONE };
	enum StatusAliment { HEALTHY, POISON };
	
	private String name;
	private int classType;
	private int status;
	
	private int hp, max_hp;
	private int mp, max_mp;
	
	private int level;
	private long experience;
	//private XPCurve curve;
	
	//Attributes
	
	//Skills List
	private ArrayList<String> skillNames;
	
	
}
