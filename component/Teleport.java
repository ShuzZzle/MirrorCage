package component;

import org.newdawn.slick.geom.Vector2f;

/**
 * Teleport component
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Teleport extends Component {
	
	/* Attributes */
	public boolean perform;
	public Vector2f position;
	
	/**
	 * Constructor
	 */
	public Teleport() {
		super();
		perform = false;
	}

	/**
	 * Print the attributes
	 */
	@Override
	public String toString() {
		return "Teleport [perform=" + perform + ", position=" + position + "]";
	}

}