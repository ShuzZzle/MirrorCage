package component;

/**
 * Wander component
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Wander extends Component {
	
	/* Attributes */
	public boolean perform; // perform the wander movement
	public float circleDistance; // distance to the circle above (in tiles)
	public float circleRadius; // radius of the circle (in tiles)
	public float wanderAngle; // current wander angle (in grad)
	public float angleChange; // max angle change (in grad)
	
	/**
	 * Constructor
	 * @param circleDistance (in tiles)
	 * @param circleRadius (in tiles)
	 * @param angleChange (in grad)
	 */
	public Wander(float circleDistance, float circleRadius, float angleChange) {
		super();
		this.circleDistance = circleDistance;
		this.circleRadius = circleRadius;
		this.angleChange = angleChange;
		perform = false;
		wanderAngle = 0;
	}

	/**
	 * Print the attributes
	 */
	@Override
	public String toString() {
		return "Wander [perform=" + perform + ", circleDistance="
				+ circleDistance + ", circleRadius=" + circleRadius
				+ ", wanderAngle=" + wanderAngle + ", angleChange="
				+ angleChange + "]";
	}

}