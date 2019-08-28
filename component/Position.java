package component;

import org.newdawn.slick.geom.Vector2f;

/**
 * Position component
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Position extends Component{
	
	/* Attributes */
	public Vector2f vector;

	/**
	 * Constructor 1
	 * @param x
	 * @param y
	 */
	public Position(float x, float y) {
		super();
		
		vector = new Vector2f(x,y);
	}

	/**
	 * Constructor 2
	 * @param vector
	 */
	public Position(Vector2f vector) {
		super();
		this.vector = vector;
	}

	/**
	 * Print the attributes
	 */
	@Override
	public String toString() {
		return "Position [vector=" + vector + "]";
	}

}