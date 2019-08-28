package gamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import core.GameValues;
import de.lessvoid.nifty.Nifty;
import event.Event;
import gui.Label;

/**
 * Intro Game State
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public final class Intro extends GameState {
	
	/* Constants */
	public static final float WAIT_FOR_SHOW_LABEL = 2000; // in miliseconds
	public static final float LABEL_VISIBILITY_DURATION = 4000; // in miliseconds

	/* Attributes */
	private Label label;
	private float waitForShowLabelTime;
	private float labelVisibilityDurationTime;

	/**
	 * Constructor
	 * @param id
	 */
	public Intro(int id) {
		super(id);
	}

	/**
	 * Init the game state
	 */
	@Override
	protected void initState(GameContainer gc, StateBasedGame sbg) {
		
		waitForShowLabelTime = 0;
		labelVisibilityDurationTime = 0;
	}

	/**
	 * Prepare the GUI
	 */
	@Override
	protected void prepareGui(Nifty nifty, StateBasedGame sbg) {
		
		nifty.addXml("./res/gui/intro.xml");
	}

	/**
	 * Update method
	 */
	@Override
	protected void updateGame(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		float deltaTime = 1.0f / delta;
		
		/* Wait to show the epic label */
		if(waitForShowLabelTime < WAIT_FOR_SHOW_LABEL) waitForShowLabelTime += deltaTime;
		else {
			labelVisibilityDurationTime += deltaTime;
			label.setOpacity(1.0f);
			
			/* Go to the title screen */
			if(labelVisibilityDurationTime > LABEL_VISIBILITY_DURATION){
				
				sbg.enterState(GameValues.GAMESTATE_TITLESCREEN_ID);
				getNifty().gotoScreen("titlescreen-screen");
				
			}
		}
				
	}
	
	/**
	 * This method is called if this state has been entered
	 */
	@Override
	protected void enterState(GameContainer container, StateBasedGame sbg) throws SlickException {
		
		getNifty().gotoScreen("intro-screen"); // show the screen
		
		label = getNifty().getScreen("intro-screen").findControl("label-controller", Label.class);
		label.setOpacity(0.0f);
	}

	/*=========================================================================
	 * =============== The methods below are not used =========================
	 ======================================================================= */
	
	/**
	 * Render method
	 */
	@Override
	protected void renderGame(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub	
	}
	
	/**
	 * This method is called if this state has been leaved
	 */
	@Override
	protected void leaveState(GameContainer container, StateBasedGame game) throws SlickException {
	
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