package engine.components;

import java.util.HashMap;
import java.util.Map;

/**
 * Component housing acceleration information. Can be used to apply gravity, force, etc
 * @author fitzj
 */
public class Acceleration extends Component {

	public static String KEY = "Acceleration";
	
	private double xAcc, yAcc;
	
	/**
	 * Static method to get key for this component
	 * @return	Acceleration key
	 */
	public static String getKey() {
		return KEY;
	}
	
	/**
	 * Constructs component with initial values and parent entity ID
	 * @param pid	Parent ID
	 * @param x		Initial x acceleration
	 * @param y		Initial y acceleration
	 */
	public Acceleration(int pid, double x, double y) {
		super(pid);
		this.xAcc = x;
		this.yAcc = y;
	}

	public double getxAcc() {
		return xAcc;
	}

	public void setxAcc(double xAcc) {
		this.xAcc = xAcc;
	}

	public double getyAcc() {
		return yAcc;
	}

	public void setyAcc(double yAcc) {
		this.yAcc = yAcc;
	}

	@Override
	public Map<String, String> getParameters(){
		Map<String, String> map = new HashMap<>(){{
		     put("x", "double");
		     put("y", "double");
		}};
		
		return map;
	}
}