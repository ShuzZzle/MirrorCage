package component;

import org.newdawn.slick.geom.Vector2f;

/**
 * Flee component
 * If you want to use this component, you also need the mass, acceleration and steering component 
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Flee extends Component {
	
	/* Attributes */
	public float speedingRadius; // in tiles
	public boolean perform;
	public Vector2f targetPosition;
		
	/**
	 * Constructor
	 */
	public Flee(){
		super();
		perform = false;
		speedingRadius = 8;
	}

	/**
	 * Print the attributes
	 */
	@Override
	public String toString() {
		return "Flee [speedingRadius=" + speedingRadius + ", perform="
				+ perform + ", targetPosition=" + targetPosition + "]";
	}
	
}