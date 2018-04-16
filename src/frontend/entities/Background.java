package frontend.entities;

/**
 * 
 * @author Hemanth Yakkali
 *
 */
public class Background extends Entity{
	
	private final String TYPE = "Background";
	private final double BKGRND_DIMENSION = 50;

	public Background(int ID) {
		super(ID);
	}

	@Override
	protected void addDefaultComponents() {
		this.setEntityType(TYPE);
		this.setDimension(BKGRND_DIMENSION, BKGRND_DIMENSION);
	}

	@Override
	public String type() {
		return TYPE;
	}
	
}
