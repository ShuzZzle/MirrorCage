package component;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import entity.Entity;

/**
 * View component
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class View extends Component {

	/* Attributes */
	public Vector2f direction;
	public float radius;
	public float angle;
	public ArrayList <Entity> seenEntities;
	public boolean seenHero;
	
	/**
	 * Constructor
	 * @param direction
	 * @param radius (in tiles)
	 * @param angle (like <)
	 */
	public View(Vector2f direction, float radius, float angle){
		
		this.direction = direction;
		this.radius = radius;
		this.angle = angle;
		seenEntities = new ArrayList<>();
		seenHero = false;
	}
	
	/**
	 * Constructor 2
	 * @param directionX
	 * @param directionY
	 * @param radius (in tiles)
	 * @param angle (like <)
	 */
	public View(float directionX, float directionY, float radius, float angle){
		
		this.direction = new Vector2f(directionX, directionY);
		this.radius = radius;
		this.angle = angle;	
		seenEntities = new ArrayList<>();
		seenHero = false;
	}

	/**
	 * Print the attributes
	 */
	@Override
	public String toString() {
		return "View [direction=" + direction + ", radius=" + radius
				+ ", angle=" + angle + ", seenEntities=" + seenEntities
				+ ", seenHero=" + seenHero + "]";
	}

}