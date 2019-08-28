package component;

import org.newdawn.slick.geom.Vector2f;

/**
 * Velocity component
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Velocity extends Component {
	
	/* Attributes */
	public Vector2f vector;
	public float max;
	
	/**
	 * Constructor 1
	 * @param x
	 * @param y
	 */
	public Velocity(float x, float y, float max){
		
		vector = new Vector2f(x, y);
		this.max = max;
	}
	
	/**
	 * Constructor 2
	 * @param vector
	 */
	public Velocity(Vector2f vector, float max){
		
		this.vector = vector;
		this.max = max;
	}

	/**
	 * Print the attributes
	 */
	@Override
	public String toString() {
		return "Velocity [vector=" + vector + ", max=" + max + "]";
	}

}