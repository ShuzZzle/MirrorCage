package component;

import java.util.ArrayList;

import entity.Entity;

/**
 * Inventory component
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Inventory {
	
	/* Attributes */
	public ArrayList <Entity> items; // these entities have to hold the item marker component
	
	/**
	 * Constructor
	 */
	public Inventory(){
		
		items = new ArrayList<>();
	}

	/**
	 * Print the attributes
	 */
	@Override
	public String toString() {
		return "Inventory [items=" + items + "]";
	}

}