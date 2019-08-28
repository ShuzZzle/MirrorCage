package component;

import entity.Entity;

/**
 * Pursuit component
 * Seek the target entity with future prediction
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Pursuit extends Component {
	
	/* Attributes */
	public float slowingRadius; // in tiles
	public boolean perform; // you have to set it to true or false to perform this movement
	public Entity target; // target entity to pursuit
		
	/**
	 * Constructor
	 */
	public Pursuit(){
		super();
		perform = false;
		slowingRadius = 3; // default value (3 tiles)
	}
	
	/**
	 * Constructor 2
	 */
	public Pursuit(Entity target, float slowingRadius){
		super();
		perform = true;
		this.slowingRadius = slowingRadius;
	}

	/**
	 * Print the attributes
	 */
	@Override
	public String toString() {
		return "Pursuit [slowingRadius=" + slowingRadius + ", perform=" + perform
				+ ", target=" + target + "]";
	}

}