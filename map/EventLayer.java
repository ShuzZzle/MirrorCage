package map;

import java.util.HashMap;
import java.util.Map.Entry;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import action.Action;
import action.ActionQuery;
import component.Collision;
import core.Lua;
import collision.CollisionDetection;
import entity.Entity;

/**
 * Stores and manages the event tiles
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class EventLayer extends ChunkLayer {
	
	private Lua lua; // possible to call Lua functions
	private HashMap <String, Rectangle> tiles; // store the rectangle tiles
	private HashMap <String, Action> actions;
	private ActionQuery actionQuery;
	
	/**
	 * Constructor
	 * @param bounds
	 */
	public EventLayer(Rectangle bounds) {
		super(bounds);	
		lua = new Lua();
		tiles = new HashMap<>();
		actions = new HashMap<>();
		actionQuery = ActionQuery.getInstance();
	}

	/**
	 * Unused update method
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// Nothing to do here	
	}

	/**
	 * Unused render method
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// Nothing to do here	
	}
	
	/**
	 * Add a new event tile
	 * @param name
	 * @param fileName
	 * @param bounds
	 */
	public void addTile(String name, Rectangle bounds, Action action){
		
		tiles.put(name, bounds);
		actions.put(name, action);
	}
	
	/**
	 * Get the bounds of an existing tile
	 * @param name
	 * @return
	 */
	public Rectangle getTileBounds(String name){
		
		if(!tiles.isEmpty())
			return tiles.get(name);
		
		return null;
	}
		
	/**
	 * Remove an existing event tile
	 * @param name
	 * @return
	 */
	public void removeTile(String name){
		
		actions.remove(name);
		tiles.remove(name);
	}
	
	/**
	 * Check if another shape collides with an event tile
	 * @param collision
	 * @return
	 */
	public boolean intersects(Entity entity){
		
		/* Setup */
		Collision collision = entity.get(Collision.class);
		CollisionDetection cd = new CollisionDetection();
		
		/* Check the collision component */
		if(collision == null) return false;
		
		
		/* Loop through the tiles and check for collision */
		for(Entry <String, Rectangle> tile : tiles.entrySet()){	
			
			if(cd.detect(tile.getValue(), collision.rect)){
				
				
				actionQuery.addAction(actions.get(tile.getKey()));
				return true;
			}
		}
		
		return false;
	}
	
}