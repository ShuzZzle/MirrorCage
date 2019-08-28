package component;

/**
 * Zoom component
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Zoom extends Component {
	
	/* Attributes */
	public float value;

	/**
	 * Constructor
	 * @param value
	 */
	public Zoom(float value) {
		super();
		this.value = value;
	}

	/**
	 * Print the inner attributes
	 */
	@Override
	public String toString() {
		return "Zoom [value=" + value + "]";
	}
	
}