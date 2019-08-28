package gamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import event.Event;
import event.EventEntity;
import event.EventManager;

/**
 * The game state class manages a specific game state and handles the corresponding events
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public abstract class GameState extends BasicGameState implements EventEntity {
	
	protected final int id; // Represents this game state
	protected boolean isAcceptingInput; // this flat signals if we want to accept any input
	
	protected GameContainer gc;
	protected StateBasedGame sbg;
	
	/**
	 * Constructor
	 * Specific scriptName
	 * @param gameStateName
	 */
	public GameState(int id){
		
		this.id = id;
		isAcceptingInput = true;
		
		
	}

	/**
	 * Get the unique id of the game state
	 */
	@Override
	public int getID() {

		return id;
	}
	
	
	/**
	 * Notify if the input has ended
	 */
	@Override
	public void inputEnded() {
		
		// We dont need this method yet....	
	}

	/**
	 * Notify if the input has started
	 */
	@Override
	public void inputStarted() {
		
		// We dont need this method yet....		
	}

	/**
	 * Check if you want to get any input
	 */
	@Override
	public boolean isAcceptingInput() {
		
		// We dont need this method yet....	
		return isAcceptingInput;
	}
	
	/**
	 * Signal if you want to get any input
	 * @param flag
	 */
	public void setAcceptingInput(boolean flag){
		
		isAcceptingInput = flag;	
	}

	/**
	 * Set the input
	 */
	@Override
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method is called if a controller button has been pressed
	 */
	@Override
	public void controllerButtonPressed(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method is called if a controller button has been released
	 */
	@Override
	public void controllerButtonReleased(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method is called if a controller down button has been pressed
	 */
	@Override
	public void controllerDownPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method is called if a controller down button has been released
	 */
	@Override
	public void controllerDownReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method is called if a controller left button has been pressed
	 */
	@Override
	public void controllerLeftPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method is called if a controller left button has been released
	 */
	@Override
	public void controllerLeftReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method is called if a controller right button has been pressed
	 */
	@Override
	public void controllerRightPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method is called if a controller rught button has been released
	 */
	@Override
	public void controllerRightReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method is called if a controller up button has been pressed
	 */
	@Override
	public void controllerUpPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method is called if a controller up button has been released
	 */
	@Override
	public void controllerUpReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Subscribe to the given event
	 */
	@Override
	public void subscribeEvent(String eventName) {
		
		EventManager em = EventManager.getInstance();
		
		if(em != null)
			em.subscribe(this, eventName);
		else
			System.out.println("Error: Before you can subscribe to an event, you have to create and event manager!");
	}

	/**
	 * Describe the given event
	 */
	@Override
	public void describeEvent(String eventName) {

		EventManager em = EventManager.getInstance();
		
		if(em != null)
			em.describe(this, eventName);
		else
			System.out.println("Error: Before you can describe an event, you have to create and event manager!");
		
	}

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
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

	@Override
	public void keyPressed(int arg0, char arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

}