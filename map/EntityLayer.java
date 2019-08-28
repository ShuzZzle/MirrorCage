package map;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import collision.Quadtree;
import csystem.SystemManager;
import entity.Entity;

/**
 * This layer stores all entities in the chunk
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class EntityLayer extends ChunkLayer {
	
	private final static SystemManager systemManager = SystemManager.getInstance();
	private ArrayList<Entity>[] field; // Stores all entities in a coordinate system
	private Quadtree quadtree; // efficient collision prematch
	
	/**
	 * Constructor
	 * @param bounds
	 */
	@SuppressWarnings("unchecked")
	public EntityLayer(Rectangle bounds) {
		super(bounds);
		field = new ArrayList[(int)(bounds.getWidth() * bounds.getHeight())];
		quadtree = new Quadtree(0, bounds);
	}
	
	/**
	 * Update method
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {	
		
		systemManager.update(gc, delta); // update all components
	}
	
	/**
	 * Render method
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		systemManager.render(gc, g); // render all components
	}
	
	/**
	 * Add a new entity to the chunk
	 * @param entity
	 * @param x
	 * @param y
	 */
	public void addEntity(Entity entity, float x, float y){
		
		/* Check if the x and y coordinate is located in the bounds */
		if( x > getBounds().getX() + getBounds().getWidth() || y > getBounds().getY() + getBounds().getHeight()) return;
		if( x < 0 || y < 0 ) return;
			
		/* Get the new coordinates as rounded numbers.
		   Remember that we have to get the tile */
		x = Math.round(x);
		y = Math.round(y);
		
		/* Get the array list of the corresponding coordinate */
		int id = (int) (y * getBounds().getY() + getBounds().getX());
		ArrayList<Entity> list = field[id];
		
		/* Check if we have to create a list */
		if(list == null){
			list = new ArrayList<>();
			field[id] = list;	
		}
		
		/* Add the entity to the list */
		list.add(entity);
		
		/* Add the entity to the quadtree */
		quadtree.insert(entity);
	}
	
	/**
	 * Move the entity to a new tile
	 * @param entity
	 * @param x
	 * @param y
	 */
	public void moveEntity(Entity entity, float x, float y){
		
		/* Check if the x and y coordinate is located in the bounds */
		if( x > getBounds().getX() + getBounds().getWidth() || y > getBounds().getY() + getBounds().getHeight()) return;
		if( x < 0 || y < 0 ) return;
		
		/* Get the new coordinates as rounded numbers.
		   Remember that we have to get the tile */
		x = Math.round(x);
		y = Math.round(y);
		
		/* Move the entity to the new tile */
		removeEntity(entity);
		addEntity(entity,x,y);	
	}
	
	/**
	 * Get the entities of the tile
	 * @param x
	 * @param y
	 * @return
	 */
	public ArrayList<Entity> getEntities(float x, float y){
		
		/* Check if the x and y coordinate is located in the bounds */
		if( x > getBounds().getX() + getBounds().getWidth() || y > getBounds().getY() + getBounds().getHeight()) return null;
		if( x < 0 || y < 0 ) return null;
		
		/* Get the new coordinates as rounded numbers.
		   Remember that we have to get the tile */
		x = Math.round(x);
		y = Math.round(y);
		
		/* Get the array list of the corresponding coordinate */
		int id = (int) (y * getBounds().getY() + getBounds().getX());
		ArrayList<Entity> list = field[id];
		
		/* Return the list */
		return list;
	}
	
	/**
	 * Get all entities of the chunk 
	 * @return
	 */
	public ArrayList<Entity> getAllEntities(){
		
		ArrayList <Entity> allEntities = new ArrayList<>();
		
		/* Loop through the fields */
		for(int i = field.length; i-- > 0;){
			
			ArrayList<Entity> list = field[i];
			if(list == null) continue;
			
			for(int j = list.size(); j-- > 0;)				
				allEntities.add(list.get(j));

		}	
		
		return allEntities;
	}
	
	/**
	 * Remove an existing entity from the chunk
	 * @param entity
	 */
	public void removeEntity(Entity entity){
		
		/* Loop through the fields */
		for(int i = field.length; i-- > 0;){
			
			ArrayList<Entity> list = field[0];
			if(list == null) continue;
			
			for(int j = list.size(); j-- > 0;){
				
				if(list.get(j) == entity){
					list.remove(entity);
					quadtree.remove(entity);
					if(list.size() == 0) list = null;					
				}
				
			}
			
		}	
		
	}
	
	/**
	 * Remove all entities of the chunk
	 */
	public void removeAllEntities(){
		
		ArrayList<Entity> allEntities = getAllEntities();
		
		for(Entity entity : allEntities)
			removeEntity(entity);
	}
	
	/**
	 * Get the quadtree
	 * @return
	 */
	public Quadtree getQuadtree(){
		
		return quadtree;
	}
	
	/**
	 * Get the field
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList[] getField(){
		
		return field;
	}
		
}