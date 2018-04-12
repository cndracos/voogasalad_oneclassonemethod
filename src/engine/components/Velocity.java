package engine.components;


import java.util.HashMap;
import java.util.Map;

/**
 *  Velocity component class
 *  @author fitzj
 *  @author Yameng
 **/
public class Velocity extends ShowableComponent {

	private double XVel; //X velocity associated with an entity that has this VelocityComponent
	private double YVel; //Y velocity associated with an entity that has this VelocityComponent

	public static String KEY = "Velocity";

    /**
     * Constructor for a VelocityComponent, just giving it its XVel and YVel values to be stored.
     * @param XVel 		entity's initial XVel
     * @param YVel 		entity's initial YVel
     **/
    public Velocity (int pid, double XVel, double YVel) {
        super(pid, KEY);
    	this.XVel = XVel;
        this.YVel = YVel;
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
	
	public static String getKey() {
		return KEY;
	}

	@Override
	public Map<String, String> getParameters(){
		Map<String,String> res = new HashMap<String, String>(){{
			put("Velocity X", Double.toString(XVel));
			put("Velocity Y", Double.toString(YVel));
		}};
		return res;
	}
}
