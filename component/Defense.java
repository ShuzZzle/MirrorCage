package component;

/**
 * Defense component
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Defense extends Component {
	
	/* Attributes */
	public float value;
	public float multiplier;
	
	/**
	 * Constructor
	 * @param value
	 */
	public Defense(float value){
		
		multiplier = 1;
		this.value = value;
	}
	
	/**
	 * Constructor 2
	 * @param value
	 */
	public Defense(float value, float multiplier){
		
		this.value = value;
		this.multiplier = multiplier;
	}

	/**
	 * Print the attributes
	 */
	@Override
	public String toString() {
		return "Defense [value=" + value + ", multiplier=" + multiplier + "]";
	}
	
}