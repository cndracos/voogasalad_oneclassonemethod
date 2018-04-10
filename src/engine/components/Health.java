package engine.components;


import java.util.ArrayList;
import java.util.List;

/**
 * Component for an entitie's health. Contains one double to represent this value.
 * @author fitzj
 */
public class Health extends Component {
	private double health;
	
	
	public Health(int pid, double health) {
		super(pid);
		this.health = health;
	}

	public static String getKey() {
		return "Health";
	}
	
	public double getHealth() {
		return health;
	}
	
	public void setHealth(double health) {
		this.health = health;
	}

	@Override
	public List<String[]> getParameters(){
		List<String[]> parameters = new ArrayList<String[]>(){{
		     add(new String[] {"health","double"});
		}};
		
		return parameters;
	}

}
