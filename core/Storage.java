package core;

import java.io.IOException;

import org.newdawn.slick.SavedState;
import org.newdawn.slick.SlickException;

/**
 * Store certain variables in a file
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Storage {
	
	/* Constants */
	public static final String STORAGEPATH = "" /*"./res/save/"*/;
	
	/* Attributes */
	private static Storage storage;
	private SavedState save;
	
	/**
	 * Private Constructor
	 * @throws SlickException 
	 */
	private Storage() throws SlickException{
		
		save = new SavedState(STORAGEPATH);
	}
	
	/**
	 * Get the singleton class
	 * @return
	 */
	public static Storage getInstance(){
		
		if(storage == null){
			try {
				storage = new Storage();
			} 
			catch (SlickException e) {
				e.printStackTrace();
			}
		}
			
		return storage;
	}
	
	/**
	 * Store a new number
	 * @param nameOfField
	 * @param value
	 */
	public void setNumber(String nameOfField, float value){
		
		save.setNumber(nameOfField, value);
	}
	
	/**
	 * Store a new string
	 * @param nameOfField
	 * @param value
	 */
	public void setString(String nameOfField, String value){
		
		save.setString(nameOfField, value);
	}
	
	/**
	 * Load an existing number
	 * @param nameOfField
	 * @return
	 */
	public float getNumber(String nameOfField){
		
		return (float) save.getNumber(nameOfField);	
	}
	
	/**
	 * Load an existing string
	 * @param nameOfField
	 * @return
	 */
	public String getString(String nameOfField){
		
		return save.getString(nameOfField);	
	}

	/**
	 * Load an existing score
	 */
	public void load(){
		
		try {
			save.load();
		} 
		catch (IOException e) {
			e.printStackTrace();		
		}	
	}
	
	/** 
	 * Save the current score
	 */
	public void save(){
		
		try {
			save.save();
		}
		catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Clear the score
	 */
	public void clear(){
		
		save.clear();
	}

}