package component;

/**
 * Mass component
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Mass extends Component {
	
	/* Attributes */
	public float value;
	
	/**
	 * Constructor 1
	 */
	public Mass(){
		
		this.value = 70;
	}
	
	/**
	 * Constructor 2
	 * @param value
	 */
	public Mass(float value){
		
		this.value = value;
	}
	
	/**
	 * Print the inner attributes
	 */
	@Override
	public String toString() {
		return "Mass [value=" + value + "]";
	}

}