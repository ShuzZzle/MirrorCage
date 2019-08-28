package component;

public class Translation extends Component {
	
	/* Attributes */
	public float x;
	public float y;
	
	/**
	 * Constructor
	 * @param x
	 * @param y
	 */
	public Translation(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Print the inner attributes
	 */
	@Override
	public String toString() {
		return "Translation [x=" + x + ", y=" + y + "]";
	}
	
}