package csystem;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import component.Hero;
import component.KeyControl;
import component.MouseControl;
import component.Position;
import component.Talk;
import component.View;
import core.BookOfKings;
import entity.Entity;

/**
 * Interaction system
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class CSInteraction extends CSystem implements KeyListener, MouseListener {
	
	/* Attributes */
	private static CSInteraction csInteraction; // singleton instance
	private Entity hero;
	private ArrayList <Entity> talkEntities; // entities which hold the talk component
	private ArrayList <Entity> viewEntities; // entities which hold the view component
	
	/**
	 * Private Constructor
	 */
	private CSInteraction(){
		
		/* Create the array lists */
		talkEntities = new ArrayList<>();
		viewEntities = new ArrayList<>();
		
		/* Notify that we want to receive key and mouse events */
		Input input = BookOfKings.game.getInput();
		input.addKeyListener(this);	
		input.addMouseListener(this);	
	}
	
	/**
	 * Get the static instance
	 * @return
	 */
	public static CSInteraction getInstance(){
		
		if(csInteraction == null)
			csInteraction = new CSInteraction();
		
		return csInteraction;	
	}

	/**
	 * Update method
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		/* Check if the current chunk exists */
		if(getCurrentChunk() == null) return;
		
		/* Clear the old list */
		talkEntities.clear();
		
		/* Get the hero */
		hero = getHero();
		
		/* Check if an entity collide with an event tile */	
		for(Entity entity : getCurrentChunk().getAllEntities()){
			getCurrentChunk().getEventLayer().intersects(entity);
		}
		
		/* Loop through the entities to get those who support a ingame talk */
		for(Entity entity : getCurrentChunk().getAllEntities()){
			
			/* Get the required components */
			Talk talk = entity.get(Talk.class);
			Position position = entity.get(Position.class);
			View view = entity.get(View.class);
			
			/* Add the new entity if he holds all required components */
			if(talk != null && position != null) talkEntities.add(entity);
			if(view != null) viewEntities.add(entity);
		}
		
		/* View */
		for(Entity entity : getCurrentChunk().getAllEntities()){
			
			/* Get the required components */
			Position position = entity.get(Position.class);
			View view = entity.get(View.class);
			if(position == null || view == null) continue;
			
			for(Entity other : getCurrentChunk().getAllEntities()){
				
				if(other == entity) continue;
				
				Position otherPosition = other.get(Position.class);
				if(position == null || otherPosition == null) continue;
				
				/* Check for the interaction */
				float distance = position.vector.distance(otherPosition.vector); // get the distance
				if(distance > view.radius) {
					
					view.seenEntities.remove(other);
					continue;
				} // if the distance is bigger than the view, break up the cheak
				if(!checkInteraction(view.direction, otherPosition.vector.copy().sub(position.vector), view.angle)) {
					
					continue;
				}
				
				boolean seenFlag = false;
				
				ArrayList <Entity> seenEntities = view.seenEntities;
				
				for(Entity seen : seenEntities){
					if(seen == other){ 
						
						
						seenFlag = true;
						break;
					
					}
				}
				
				if(!seenFlag){
					view.seenEntities.add(other);
					entity.call("onNotice", other);
				}
					
			
			}
			
			
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
	 * Notify if a key has been pressed
	 */
	@Override
	public void keyPressed(int key, char character) {
		
		/* Check if the hero wants to talk */
		if(key != Input.KEY_T) return;
		
		/* Get the required components */
		Position position = hero.get(Position.class);
		KeyControl keyControl = hero.get(KeyControl.class);
		View view = hero.get(View.class);
		
		/* Check if the hero supports the talk interaction */
		if(!keyControl.isEnabled(key)) return;
		
		/* Find the entity to talk to */
		for(Entity entity : talkEntities){
			
			/* Get the components */
			Position talkPosition = entity.get(Position.class);
			//Talk talk = entity.get(Talk.class); Not used
			
			/* Check for the interaction */
			float distance = position.vector.distance(talkPosition.vector); // get the distance	
			if(distance > 1.5f) continue; // if the distance is bigger than the view, break up the cheak
			if(!checkInteraction(view.direction, talkPosition.vector.copy().sub(position.vector), view.angle)) continue;
				
			/* Call the lua scripts */
			hero.call("onInteract", entity);
			entity.call("onInteract", hero);	
		}

	}

	/**
	 * Notify if a key has been released
	 */
	@Override
	public void keyReleased(int key, char character) {
		
		// Nothing to do here	
	}

	/**
	 * Notify that the input events have been ended
	 */
	@Override
	public void inputEnded() {
		
		// Nothing to do here	
	}

	/**
	 * Notify that the input events have been started
	 */
	@Override
	public void inputStarted() {
		
		// Nothing to do here		
	}

	/**
	 * Notify that we want to accept any input
	 */
	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	/**
	 * Set a new input
	 */
	@Override
	public void setInput(Input input) {
		
		// Nothing to do here	
	}
	
	/**
	 * Get the hero entity
	 * @return
	 */
	private Entity getHero(){
		
		return getComponentManager().getAllEntities(Hero.class).get(0);
	}

	/**
	 * Check the interaction between two entities
	 * @param vector1
	 * @param vector2
	 * @param angle
	 * @return
	 */
	private boolean checkInteraction(Vector2f viewDirection, Vector2f targetPosition, float angle){
		
		/* Calculate the grad value */
		float grad = (float) Math.toDegrees(Math.atan2(Math.abs(targetPosition.x - viewDirection.x), Math.abs(targetPosition.y - viewDirection.y)));
		
		/* Check if the angle of the rad is less than the given angle divided by two */
		if(grad <= angle / 2) return true;
		else return false;
	}

	@Override
	public void mouseClicked(int button, int arg1, int arg2, int arg3) {
		
		/* Check if the hero wants to talk */
		if(button != Input.MOUSE_LEFT_BUTTON) return;
		
		/* Get the required components */
		Position position = hero.get(Position.class);
		MouseControl mouseControl = hero.get(MouseControl.class);
		View view = hero.get(View.class);
		
		/* Check if the hero supports the talk interaction */
		if(!mouseControl.isEnabled(button)) return;
		
		/* Find the entity to talk to */
		for(Entity entity : talkEntities){
			
			/* Get the components */
			Position talkPosition = entity.get(Position.class);
			//Talk talk = entity.get(Talk.class); Not used
			
			/* Check for the interaction */
			float distance = position.vector.distance(talkPosition.vector); // get the distance	
			if(distance > 1.5f) continue; // if the distance is bigger than the view, break up the cheak
			if(!checkInteraction(view.direction, talkPosition.vector.copy().sub(position.vector), view.angle)) continue;
				
			/* Call the lua scripts */
			hero.call("onInteract", entity);
			entity.call("onInteract", hero);	
		}
		
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(int arg0) {
		// TODO Auto-generated method stub
		
	}


}