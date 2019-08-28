package component;

import java.util.ArrayList;

/**
 * Mouse control component
 * Component to control the entities with the mouse
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class MouseControl extends Component {
	
	/* Attributes */
	public ArrayList<Integer> enabledKeys;
	
	/**
	 * Constructor
	 */
	public MouseControl(int key){
		
		enabledKeys = new ArrayList<>();
		enabledKeys.add(key);		
	}
	
	/**
	 * Constructor 2
	 */
	public MouseControl(int key1, int key2){
		
		enabledKeys = new ArrayList<>();
		enabledKeys.add(key1);		
		enabledKeys.add(key2);	
	}
	
	/**
	 * Constructor 3
	 */
	public MouseControl(int key1, int key2, int key3){
		
		enabledKeys = new ArrayList<>();
		enabledKeys.add(key1);		
		enabledKeys.add(key2);	
		enabledKeys.add(key3);	
	}
	
	/**
	 * Enable a new key
	 * @param key
	 */
	public void enable(Integer key){
		
		enabledKeys.add(key);
	}
	
	/**
	 * Disable a new key
	 * @param key
	 */
	public void disable(Integer key){
		
		enabledKeys.remove(key);
	}
	
	/**
	 * Check if a certain key is enabled
	 * @param key
	 * @return
	 */
	public boolean isEnabled(Integer key){
		
		return enabledKeys.contains(key); 
	}

}