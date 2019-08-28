package csystem;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;

import component.KeyControl;
import component.MouseControl;
import core.BookOfKings;
import entity.Entity;

/**
 * Input system
 * Handles the input and notify all entities which support the input about the new input events.
 * 
 * Used components:
 *   KeyControl
 *   MouseControl
 *  
 * @author Bengt, Marlo, Alexander, Niclas
 */
public class CSInput extends CSystem implements MouseListener, KeyListener {
	
	/* Attributes */
	private static CSInput csInput;
	private ArrayList <Entity> keyEntities; // all entities which support the key control
	private ArrayList <Entity> mouseEntities;  // all entities which support the mouse control
	
	/**
	 * Private Constructor
	 */
	private CSInput(){
		
		keyEntities = new ArrayList<>();
		mouseEntities = new ArrayList<>();
		
		/* Notify, that we want to receive any input */
		BookOfKings.game.getInput().addKeyListener(this);
		BookOfKings.game.getInput().addMouseListener(this);
		
	}
	
	/**
	 * Get the static instance
	 * @return
	 */
	public static CSInput getInstance(){
			
		if(csInput == null)
			csInput = new CSInput();
		
		return csInput;
	}

	/**
	 * Update method
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		/* Check if the current chunk exists */
		if(getCurrentChunk() == null) return;
		
		/* Clear the old lists */
		keyEntities.clear();
		mouseEntities.clear();
	    
	    /* Loop trough the entities and check which entity supports key or mouse events */
	    for(Entity entity : getCurrentChunk().getAllEntities()){    	
	    	if(entity.get(KeyControl.class) != null) keyEntities.add(entity);
	    	if(entity.get(MouseControl.class) != null) mouseEntities.add(entity);
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
	 * Unused: Indicates that the input has been started
	 */
	@Override
	public void inputStarted() {
		
		// Nothing to do here
	}
	
	/**
	 * Unused: Indicates that the input has been ended
	 */
	@Override
	public void inputEnded() {
		
		// Nothing to do here
	}

	/**
	 * Notify, that we want to accept input
	 */
	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	/**
	 * Unused: Set the new input
	 */
	@Override
	public void setInput(Input input) {
		//Nothing to do here	
	}

	/**
	 * Indicates that the mouse has been clicked
	 */
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		
		/* Loop through the entities and perform the mouse event */
		for(Entity entity : mouseEntities){
			
			MouseControl mouseControl = entity.get(MouseControl.class);
			
			if(mouseControl.isEnabled(button))
				entity.call("onMouseClicked", button, x, y, clickCount);
		}
		
	}

	/**
	 * Indicates that the mouse has been dragged
	 * Disabled because it cost to much performance
	 */
	@Override
	public void mouseDragged(int x1, int y1, int x2, int y2) {

		/* Loop through the entities and perform the mouse event */
		/*for(Entity entity : mouseEntities)
			entity.call("onMouseDragged", x1, y1, x2, y2);*/	
	}

	/**
	 * Indicates that the mouse has been moved
	 * Disabled because it cost to much performance
	 */
	@Override
	public void mouseMoved(int x1, int y1, int x2, int y2) {
		
		/* Loop through the entities and perform the mouse event */
		/*for(Entity entity : mouseEntities)
			entity.call("onMouseMoved", x1, y1, x2, y2);*/		
	}

	/**
	 * Indicates that the mouse has been pressed
	 */
	@Override
	public void mousePressed(int button, int x, int y) {
		
		/* Loop through the entities and perform the mouse event */
		for(Entity entity : mouseEntities){
			
			MouseControl mouseControl = entity.get(MouseControl.class);
			
			if(mouseControl.isEnabled(button))
				entity.call("onMousePressed", button, x, y);
		}
		
	}

	/**
	 * Indicates that the mouse has been released
	 */
	@Override
	public void mouseReleased(int button, int x, int y) {
		
		/* Loop through the entities and perform the mouse event */
		for(Entity entity : mouseEntities){
			
			MouseControl mouseControl = entity.get(MouseControl.class);
			
			if(mouseControl.isEnabled(button))
				entity.call("onMouseReleased", button, x, y);
		}
		
	}

	/**
	 * Indicates that the mouse wheel has been moved
	 */
	@Override
	public void mouseWheelMoved(int value) {
		
		/* Loop through the entities and perform the mouse event */
		for(Entity entity : mouseEntities)
				entity.call("onMouseWheelMoved", value);
	}

	/**
	 * Indicates that a key has been pressed
	 */
	@Override
	public void keyPressed(int key, char character) {
	
		/* Loop through the entities and perform the key event */
		for(Entity entity : keyEntities){
			
			KeyControl keyControl = entity.get(KeyControl.class);
			
			if(keyControl.isEnabled(key))
				entity.call("onKeyPressed", key, character);
		}
			
	}

	/**
	 * Indicates that a key has been pressed
	 */
	@Override
	public void keyReleased(int key, char character) {


		/* Loop through the entities and perform the key event */
		for(Entity entity : keyEntities){
			
			KeyControl keyControl = entity.get(KeyControl.class);
			
			if(keyControl.isEnabled(key))
				entity.call("onKeyReleased", key, character);
		}
		
	}

}