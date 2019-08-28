package csystem;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import component.Health;
import component.Hero;
import entity.Entity;

/**
 * Update the health of the entities
 * This system removes the dead entities from the game.
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public final class CSHealth extends CSystem {
	
	/* Attributes */
	private static CSHealth csHealth;

	/**
	 * Private Constructor
	 */
	private CSHealth(){

	}
	
	/**
	 * Get the static instance
	 * @return
	 */
	public static CSHealth getInstance(){
		
		if(csHealth == null)
			csHealth = new CSHealth();
		
		return csHealth;
	}

	/**
	 * Update method
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		/* Check if the current chunk exists */
		if(getCurrentChunk() == null) return;
		
		/* Loop through the entities */
		for(Entity entity : getCurrentChunk().getAllEntities()){
			
			/* Get the required components */
			Health health = entity.get(Health.class);
			
			/* Add the new entity if he holds all required components */
			if(health == null) continue;
			
			if(health.currentHp <= 0) // check if the entity is dead
				 entityDied(entity);	
		}	
	}
	
	/**
	 * Unused render method
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		// Nothing to do here	
	}
	
	/**
	 * A entity died
	 * @param hero
	 */
	private void entityDied(Entity entity){
		
		/* Check if a monster or the hero died */
		Hero marker = entity.get(Hero.class);
		
		if(marker == null){ // Don't remove the hero
			entity.call("onDie");
			entity.finalize();
			entity = null;
		}
		else {
			entity.call("onDie");			
		}
	}

}