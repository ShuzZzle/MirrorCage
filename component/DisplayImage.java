package component;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Display image component
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class DisplayImage extends Component {
	
	/* Attributes */
	public Image image;
	public boolean isVisible;
	public Color colorFilter;
	
	/**
	 * Constructor 1
	 * @param image
	 */
	public DisplayImage(Image image){
		
		this.image = image;
		isVisible = true;
		colorFilter = new Color(Color.transparent);
	}
	
	/**
	 * Constructor 2
	 * @param imageName
	 */
	public DisplayImage(String imageName){
		
		try {
			this.image = new Image("./res/image/" + imageName);
		} 
		catch (SlickException e) {
			e.printStackTrace();
		}
		isVisible = true;
		colorFilter = new Color(Color.blue);		
	}

	
	/**
	 * Print the inner attributes of the class
	 */
	@Override
	public String toString() {
		return "DisplayImage [image=" + image + ", isVisible=" + isVisible
				+ ", colorFilter=" + colorFilter + "]";
	}

}