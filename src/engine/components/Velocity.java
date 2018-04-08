package engine.components;

import java.util.HashMap;
import java.util.Map;

/**
 *  Velocity component class
 *  @author fitzj
 **/
public class Velocity extends Component {

    private double XVel; //X velocity associated with an entity that has this VelocityComponent
	private double YVel; //Y velocity associated with an entity that has this VelocityComponent

    /**
     * Constructor for a VelocityComponent, just giving it its XVel and YVel values to be stored.
     * @param XVel 		entity's initial XVel
     * @param YVel 		entity's initial YVel
     * @param gravAcc	entity's gravity acceleration
     **/
    public Velocity (int pid, double XVel, double YVel) {
        super(pid);
    		this.XVel = XVel;
        this.YVel = YVel;
    }
    
    public static String getKey() {
		return "Velocity";
	}
    
    public double getXVel() {
		return XVel;
	}

	public void setXVel(double xVel) {
		XVel = xVel;
	}

	public double getYVel() {
		return YVel;
	}

	public void setYVel(double yVel) {
		YVel = yVel;
	}
	
	@Override
	public Map<String, String> getParameters(){
		Map<String, String> map = new HashMap<>(){{
		     put("xVel", "double");
		     put("yVel", "double");
		}};
		
		return map;
	}
}
