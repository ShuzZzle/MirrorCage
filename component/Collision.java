package component;

import org.newdawn.slick.geom.Rectangle;

/**
 * Collision component
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Collision extends Component {
	
	/* Attributes */
	public boolean onceCollided;
	public Rectangle rect;
	
	/**
	 * Constructor
	 * @param shape
	 */
	public Collision(float width, float height){
		
		rect = new Rectangle(0, 0, width, height);
		onceCollided = false;
	}

	/**
	 * Print the attributes
	 */
	@Override
	public String toString() {
		return "Collision [rect=" + rect + "]";
	}

}