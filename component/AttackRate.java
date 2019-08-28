package component;

/**
 * Attack rate component
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class AttackRate extends Component {
	
	/* Attributes */
	public float rateTime; // in milliseconds
	public float elapsedTime; // in milliseconds too
	
	/**
	 * Constructor
	 * @param rateTime (in milliseconds)
	 */
	public AttackRate(float rateTime){
		
		this.rateTime = rateTime;
		elapsedTime = rateTime / 2; // to make the first attack more realistic
	}

	/**
	 * Print the inner attributes of the class
	 */
	@Override
	public String toString() {
		return "AttackRate [rateTime=" + rateTime + ", elapsedTime=" + elapsedTime
				+ "]";
	}

}