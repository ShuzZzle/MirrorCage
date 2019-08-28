package component;

import org.newdawn.slick.geom.Vector2f;

/**
 * Seek component
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Seek extends Component {
	
	/* Attributes */
	public float slowingRadius; // in tiles
	public boolean perform;
	public Vector2f targetPosition;
		
	/**
	 * Constructor
	 */
	public Seek(){
		super();
		perform = false;
		slowingRadius = 2;
	}

	/**
	 * Print the attributes
	 */
	@Override
	public String toString() {
		return "Seek [slowingRadius=" + slowingRadius + ", perform=" + perform
				+ ", targetPosition=" + targetPosition + "]";
	}
	
}