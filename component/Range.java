package component;

/**
 * Range component
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Range extends Component {
	
	/* Attributes */
	public float value;
	
	/**
	 * Constructor
	 * @param value
	 */
	public Range(float value){
		
		this.value = value;
	}

	/**
	 * Print the attributes
	 */
	@Override
	public String toString() {
		return "Damage [value=" + value + "]";
	}	

}