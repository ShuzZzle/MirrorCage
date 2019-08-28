package component;

/**
 * Damage component
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Damage extends Component {
	
	/* Attributes */
	public float value;
	public float multiplier;
	
	/**
	 * Constructor
	 * @param value
	 */
	public Damage(float value){
		
		this.value = value;
		multiplier = 1;
	}

	/**
	 * Constructor 2
	 * @param value
	 */
	public Damage(float value, float multiplier){
		
		this.value = value;
		this.multiplier = multiplier;
	}
	
	/**
	 * Print the attributes
	 */
	@Override
	public String toString() {
		return "Damage [value=" + value + ", multiplier=" + multiplier + "]";
	}
	
}