package component;

import java.util.ArrayList;

/**
 * Key control component
 * Component to control the entities with keys
 * There is a weird error in Lua. Thats why you have to add an implicit parameter for each key... :(
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class KeyControl extends Component {
	
	/* Attributes */
	public ArrayList<Integer> enabledKeys;
	
	/**
	 * Constructor
	 */
	public KeyControl(int key){
		
		enabledKeys = new ArrayList<>();
		enabledKeys.add(key);		
	}
	
	/**
	 * Constructor 2
	 */
	public KeyControl(int key1, int key2, int key3, int key4){
		
		enabledKeys = new ArrayList<>();
		enabledKeys.add(key1);		
		enabledKeys.add(key2);	
		enabledKeys.add(key3);	
		enabledKeys.add(key4);	
	}
	
	/**
	 * Constructor 2
	 */
	public KeyControl(int key1, int key2, int key3, int key4, int key5){
		
		enabledKeys = new ArrayList<>();
		enabledKeys.add(key1);		
		enabledKeys.add(key2);	
		enabledKeys.add(key3);	
		enabledKeys.add(key4);	
		enabledKeys.add(key5);	
	}
	
	/**
	 * Constructor 3
	 */
	public KeyControl(int... keys){
		
		enabledKeys = new ArrayList<>();
		
		if(keys != null)
			for(int i = keys.length; i > 0;)
				enabledKeys.add(keys[i]);		
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