package component;

/**
 * Health Component
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Health extends Component {
	
	/* Attributes */
	public float currentHp;
	public float maxHp;
	
	/**
	 * Constructor 1
	 * @param hp
	 */
	public Health(float hp){
		
		currentHp = hp;
		maxHp = hp;
	}
	
	/**
	 * Constructor 2
	 * @param currentHp
	 * @param maxHp
	 */
	public Health(float currentHp, float maxHp){
		
		this.currentHp = currentHp;
		this.maxHp = maxHp;
	}

	/**
	 * Print the inner attributes of the class
	 */
	@Override
	public String toString() {
		return "Health [currentHp=" + currentHp + ", maxHp=" + maxHp + "]";
	}

}