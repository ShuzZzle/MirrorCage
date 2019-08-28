package csystem;

import map.Chunk;
import map.World;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import component.ComponentManager;

/**
 * The component system calculate the new values of the object.
 * In addition they render oder update something in the game
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public abstract class CSystem {
	
	/* Attributes */
	protected ComponentManager cm = ComponentManager.getInstance(); // relation between an entity and a component
	protected World world = World.getInstance(); // world with all chunks
	
	/**
	 * Constructor
	 */
	public CSystem() {
		
	}
	
	/**
	 * Abstract update method
	 */
	public abstract void update(GameContainer gc, int delta) throws SlickException;
	
	/**
	 * Abstract render methods
	 */
	public abstract void render(GameContainer gc, Graphics g) throws SlickException;

	/**
	 * Get the current chunk
	 * @return
	 */
	public Chunk getCurrentChunk() {
		return world.getCurrentChunk();
	}
	
	/**
	 * Get the component manager
	 * @return
	 */
	public ComponentManager getComponentManager(){
		
		return cm;
	}

}