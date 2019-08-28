package gamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.lessvoid.nifty.Nifty;
import event.Event;

public final class GameOver extends GameState {

	/**
	 * Constructor
	 * @param id
	 */
	public GameOver(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Init the game state
	 */
	@Override
	protected void initState(GameContainer gc, StateBasedGame sbg) {
		// TODO Auto-generated method stub	
	}

	/**
	 * Prepare the GUI
	 */
	@Override
	protected void prepareGui(Nifty nifty, StateBasedGame sbg) {
		// TODO Auto-generated method stub	
	}

	/**
	 * Render method
	 */
	@Override
	protected void renderGame(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub	
	}

	/**
	 * Update method
	 */
	@Override
	protected void updateGame(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub	
	}

	/**
	 * This method is called if this state has been entered
	 */
	@Override
	protected void enterState(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub		
	}

	/**
	 * This method is called if this state has been leaved
	 */
	@Override
	protected void leaveState(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub	
	}

	/**
	 * Handle a given event
	 */
	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method is called if the mouse has been clicked
	 */
	@Override
	public void mouseClicked(int button, int x, int y, int arg3) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method is called if the mouse has been dragged
	 */
	@Override
	public void mouseDragged(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
	}

	/**
	 * This method is called if the mouse has been moved
	 */
	@Override
	public void mouseMoved(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method is called if the mouse has been pressed
	 */
	@Override
	public void mousePressed(int button, int x, int y) {
		// TODO Auto-generated method stub	
	}

	/**
	 * This method is called if the mouse has been released
	 */
	@Override
	public void mouseReleased(int button, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method is called if the mouse wheel has been moved
	 */
	@Override
	public void mouseWheelMoved(int delta) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method is called if a key has been pressed
	 */
	@Override
	public void keyPressed(int arg0, char arg1) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method is called if a key has been released
	 */
	@Override
	public void keyReleased(int arg0, char arg1) {
		// TODO Auto-generated method stub
		
	}

}
