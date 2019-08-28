package component;

/**
 * Camera view component
 * Only used by the camera entity
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public final class CameraView extends Component {
	
	/* Attributes */
	public float x;
	public float y;
	public float width;
	public float height;
	
	/**
	 * Constructor
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public CameraView(float x, float y, float width, float height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Print the inner attributes
	 */
	@Override
	public String toString() {
		return "Bounds [x=" + x + ", y=" + y + ", width=" + width + ", height="
				+ height + "]";
	}

}