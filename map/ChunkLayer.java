package map;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import component.Camera;
import component.CameraView;
import component.ComponentManager;
import entity.Entity;

/**
 * Represent a specific layer in a chunk
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public abstract class ChunkLayer {
	
	protected Rectangle bounds;
	
	/**
	 * Constructor
	 * @param bounds
	 */
	public ChunkLayer(Rectangle bounds){
		
		this.bounds = bounds;
	}
	
	/**
	 * Update method
	 * @param gc
	 * @param sbg
	 * @param delta
	 */
	public abstract void update(GameContainer gc, int delta) throws SlickException;

	/**
	 * Render method
	 * @param gc
	 * @param sbg
	 * @param g
	 * @param bounds
	 */
	public abstract void render(GameContainer gc, Graphics g) throws SlickException;
	
	/**
	 * Get the bounds of the layer
	 * @return
	 */
	public Rectangle getBounds() {
		return bounds;
	}
	
	/**
	 * Get the visible area of the chunk in tiles
	 * @return
	 */
	public Rectangle getVisibleArea(){
		
		ComponentManager cm = ComponentManager.getInstance();	
		ArrayList<Entity> allCameraEntities = cm.getAllEntities(Camera.class); // get the camera entity
		Entity camera = allCameraEntities.get(0);
		
		CameraView view = cm.get(CameraView.class, camera); // get the view of the camera in tiles
		
		if(view != null) return new Rectangle(view.x, view.y, view.width, view.height);
		else return null;
	}
}