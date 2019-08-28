package component;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


/**
 * Display animation component
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class DisplayAnimation extends Component {
	
	public String current; // name of the current animation
	public HashMap <String, Animation> list; // list of all animations
	
	/**
	 * Constructor
	 */
	public DisplayAnimation(){
		
		current = "none";
		list = new HashMap<>();
	}
	
	/**
	 * Add a new animation based on the given sprite sheet
	 * @param name
	 * @param pathToFile
	 * @param frameWidth
	 * @param frameHeight
	 * @param duration
	 */
	public void addAnimation(String name, String fileName, int frameWidth, int frameHeight, int duration){
		
		/* Setup */
		Animation animation = null;
		SpriteSheet spriteSheet = null;
		
		/* Create the new animation */
		try {
			spriteSheet = new SpriteSheet("./res/sprite/" + fileName, frameWidth, frameHeight);
			animation = new Animation(spriteSheet, duration);
		}
		catch (SlickException e) {
			e.printStackTrace();
		}
		
		/* Add the new animation to the list */
		if(animation != null){
			
			animation.setAutoUpdate(true);
			list.put(name, animation);
			
		}
	}
	
	/**
	 * Remove an existing animation by name
	 * @param name
	 */
	public void removeAnimation(String name){
		
		if(list != null && !list.isEmpty())
			list.remove(name);	
	}
	
}