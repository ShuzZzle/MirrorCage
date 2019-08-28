package component;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

/**
 * Display rect component
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class DisplayRect extends Component {
	
	/* Attributes */
	public Rectangle rect;
	public Color color;
	public boolean isVisible;
	
	/**
	 * Constructor 1
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public DisplayRect(float width, float height){
		
		rect = new Rectangle(0, 0, width, height);
		color = new Color(Color.blue);
		isVisible = true;
	}

	/**
	 * Constructor 2
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param color
	 */
	public DisplayRect(float width, float height, Color color){
		rect = new Rectangle(0, 0, width, height);
		this.color = color;	
		isVisible = true;
	}
	
	/**
	 * Constructor 3
	 * @param rect
	 */
	public DisplayRect(Rectangle rect){
		
		this.rect = rect;
		this.color = new Color(Color.blue);	
		isVisible = true;
	}
	
	/**
	 * Constructor 4
	 * @param rect
	 * @param color
	 */
	public DisplayRect(Rectangle rect, Color color){
		
		this.rect = rect;
		this.color = color;		
		isVisible = true;
	}
	
	/**
	 * Print the inner methods
	 */
	@Override
	public String toString() {
		return "DisplayRect [rect=" + rect + ", color=" + color
				+ ", isVisible=" + isVisible + "]";
	}
	
}