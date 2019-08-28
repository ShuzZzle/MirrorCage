package csystem;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * The system manager updates and renders all required system for this game
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public final class SystemManager {
	
	private static SystemManager sm; // Singleton pattern
	private ArrayList <CSystem> render; // render the entities
	private ArrayList <CSystem> update; // update the entities
	
	/**
	 * Private Constructor
	 */
	private SystemManager(){
		
		/* Create the lists */
		update = new ArrayList<>();
		render = new ArrayList<>(); 
		
		/* Add all required update systems */
		update.add( CSAction.getInstance()); // Action
		update.add( CSMovement.getInstance()); // Movement
		update.add( CSRenderer.getInstance()); // Renderer
		update.add( CSInput.getInstance()); // Input
		update.add( CSHealth.getInstance()); // Health
		update.add( CSBattle.getInstance()); // Battle
		update.add( CSInteraction.getInstance()); // Interaction
	
		/* Add all required render systems */
		render.add( CSRenderer.getInstance()); // Rectangle renderer
	}
	
	/**
	 * Get the instance of this singleton class
	 * @return
	 */
	public static SystemManager getInstance(){
		
		if(sm == null)
			sm = new SystemManager();
		
		return sm;
	}

   /**
    * Render method
    * @param gc
    * @param sbg
    * @param g
    * @throws SlickException
    */
	public void render(GameContainer gc, Graphics g) throws SlickException {
	
		for(CSystem renderSystem : render)		
			renderSystem.render(gc, g);
	}

   /**
    * Update method
    * @param gc
    * @param sbg
    * @param delta
    * @throws SlickException
    */
	public void update(GameContainer gc, int delta) throws SlickException {
	
		for(CSystem updateSystem : update)		
			updateSystem.update(gc, delta);
	}

}