package component;

import org.newdawn.slick.geom.Vector2f;

/**
 * Steering component
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Steering extends Component {
	
	/* Attributes */
	public Vector2f vector;
	public float max;
	
	/**
	 * Constructor
	 * @param max
	 */
	public Steering(float max) {
		super();
		this.max = max;
	}

	/**
	 * Print the attributes
	 */
	@Override
	public String toString() {
		return "Steering [vector=" + vector + ", max=" + max + "]";
	}

}