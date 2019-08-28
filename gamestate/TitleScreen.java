package gamestate;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import core.BookOfKings;
import core.GameValues;
import de.lessvoid.nifty.Nifty;
import event.Event;
import gui.Label;

/**
 * Title screen game state
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public final class TitleScreen extends GameState {
	
	/* Constants */
	public static final float LABEL_VISIBILTY_TIME = 0.0015f;
	
	/* Attributes */
	private Label label; // the label itself
	private float labelTime; // time for displaying the label
	private Music music; // background music
	private Image image; // background image

	/**
	 * Constructor
	 * @param id
	 */
	public TitleScreen(int id) {
		super(id);
	}
	
	/**
	 * Init the game state
	 */
	@Override
	protected void initState(GameContainer gc, StateBasedGame sbg) {
	
		/* Create the music */
		try {
			music = new Music("./res/music/Menu.wav");
			image = new Image("./res/gui/titlescreen/forest-painting.jpg");
			image = image.getScaledCopy(1.7f);
		} 
		catch (SlickException e) {
			e.printStackTrace();
		}
		
		labelTime = 0f;
	}

	/**
	 * Prepare the GUI
	 */
	@Override
	protected void prepareGui(Nifty nifty, StateBasedGame sbg) {
		
		nifty.addXml("./res/gui/titlescreen.xml"); // load the gui
	}

	/**
	 * Render method
	 */
	@Override
	protected void renderGame(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		/* Show the background */
		g.drawImage(image, 0, 0);
	}

	/**
	 * Update method
	 */
	@Override
	protected void updateGame(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		/* Calculate the time */
		float deltaTime = 1.0f / delta;
		labelTime += LABEL_VISIBILTY_TIME * deltaTime;
		
		/* Set the opacity of the label */
		float opacity = (float) (0.5 * (Math.sin(labelTime) + 1));
		label.setOpacity(opacity);
	}

	/**
	 * This method is called if this state has been entered
	 */
	@Override
	protected void enterState(GameContainer container, StateBasedGame game) throws SlickException {

		getNifty().gotoScreen("titlescreen-screen");
		
		label = getNifty().getScreen("titlescreen-screen").findControl("label-controller", Label.class);
		
		if(!music.playing())
			music.loop();
	}
	
	/**
	 * This method is called if this state has been leaved
	 */
	@Override
	protected void leaveState(GameContainer container, StateBasedGame game) throws SlickException {
		
		music.stop();
	}
	
	/**
	 * This method is called if a key has been pressed
	 */
	@Override
	public void keyPressed(int key, char arg1) {
		
		/* Exit the game */
		if(key == Input.KEY_ESCAPE){		
			System.out.println("Info: The game has been quit...");
			BookOfKings.game.exit();    
		}
		
		/* Play the success sound */
		try {
			Sound success = new Sound("./res/sound/success-low.wav");
			success.play();
		} 
		catch (SlickException e) {
			e.printStackTrace();
		}
			
		/* Go to the menu */
		getNifty().gotoScreen("load-custom-game-state-screen");
		sbg.enterState(GameValues.GAMESTATE_LOAD_ID);	
	}
	
	/*=========================================================================
	 * =============== The methods below are not used =========================
	 ======================================================================= */

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
	 * This method is called if a key has been released
	 */
	@Override
	public void keyReleased(int arg0, char arg1) {
		// TODO Auto-generated method stub
		
	}

}