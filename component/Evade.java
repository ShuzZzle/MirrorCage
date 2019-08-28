package component;

import entity.Entity;

/**
 * Evade component
 * Flee from the target entity with future prediction
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Evade extends Component {
	
	/* Attributes */
	public float speedingRadius; // in tiles
	public boolean perform; // you have to set it to true or false to perform this movement
	public Entity target; // target entity to pursuit
		
	/**
	 * Constructor
	 */
	public Evade(){
		super();
		perform = false;
		speedingRadius = 3; // default value (3 tiles)
	}
	
	/**
	 * Constructor 2
	 */
	public Evade(Entity target, float speedingRadius){
		super();
		perform = true;
		this.speedingRadius = speedingRadius;
	}

	/**
	 * Print the attributes
	 */
	@Override
	public String toString() {
		return "Evade [speedingRadius=" + speedingRadius + ", perform="
				+ perform + ", target=" + target + "]";
	}

}