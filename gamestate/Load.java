package gamestate;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import gui.Label;
import gui.ProgressBar;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import event.Event;

/** 
 * This game state loads the game by getting the required resources
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public final class Load extends GameState {
		
	@SuppressWarnings("unused")
	private Screen screen;
	private ProgressBar progress;
	private Label label;
	private Image magicCircle;
	private Music music;
	
	/* Constants */
	private final static float resourceDuration = 400; // in miliseconds
	private final static float textDuration = 4000; // in miliseconds
	private final static float angleChange = 0.006f;
	private final static String[] loadingText = {
		"Ziel des Spieles ist es, zu gewinnen.",
		"Benutze Maus und Tastertur zum interagieren.",
		"Investiere Zeit und vor allem Geld, um erfolgreicher zu sein.",
		"Die trockene Simulation wird zum spannenden Spiel, wenn du Kopfh�rer benutzt.",
		"Wenn Bugs auftreten sollten, sind diese urheberrechtlich gesch�tzt.",
		"Ben�tigt der Ladebalken mehr als 10s, ist dein PC nicht f�r dieses Spiel geeignet.",
		"Die Lehre der Thermodynamik besagt, das Glas eine gefrorene, unterk�hlte Fl�ssigkeit ist.",
		"Die Wahrscheinlichkeit, zu sterben wird dir in Mikromort angegeben (1:1.000.000).",
		"Manchmal ist es sinnvoller einen Kampf zu meiden",
		"Nur wer alles erkundet, kann auch alle Achievments bekommen.",
		"Spreche die NPCs im Spiel an, um hilfreiche Ratschl�ge zu bekommen."
	};
	
	private float deltaTime;
	private float resourceTime;
	private float rotationAngle; // rotation of the image
	@SuppressWarnings("unused")
	private TrueTypeFont font; // stores the font type
	private float textTime; // kind of counter for the text
	private int loadingIndex;
	
	/** The next resource to load */
	private DeferredResource nextResource;

	/**
	 * Constructor
	 * @param id
	 * @param scriptName
	 */
	public Load(int id) {
		super(id);
		
		rotationAngle = 20;
		textTime = 0;
		resourceTime = 0;
	}

	/**
	 * Init the game state
	 */
	@Override
	protected void initState(GameContainer gc, StateBasedGame sbg) {
	
		try {
			music = new Music("./res/music/Sailing.wav");
			magicCircle = new Image("./res/image/magic-circle.png");
		} 
		catch (SlickException e) {
			e.printStackTrace();
		}
		

		

		
	}

	/**
	 * Prepare the GUI
	 */
	@Override
	protected void prepareGui(Nifty nifty, StateBasedGame sbg) {

		getNifty().addXml("/res/gui/load.xml"); // load the gui
	}
	
	private void loadingFinished(){
		
		
	}

	/**
	 * Render method
	 */
	@Override
	protected void renderGame(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		/* Draw the loading image */
		g.drawImage(magicCircle, (int) ( gc.getWidth() / 2 - magicCircle.getWidth() / 2) , gc.getWidth() / 20);
	}

	/**
	 * Update method
	 */
	@Override
	protected void updateGame(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		/* Update the time and the rotation angle */
		deltaTime = 1.0f / delta;
		rotationAngle = rotationAngle + angleChange * deltaTime;
		magicCircle.setRotation(rotationAngle);
		
		if(rotationAngle > 360)
			rotationAngle -= 360;
		
		textTime += deltaTime;
		resourceTime += deltaTime;
		
		if (nextResource != null) {
			try {
				nextResource.load();
				// slow down loading for example purposes
				try { /*Thread.sleep(1000);*/ } catch (Exception e) {}
			} 
			catch (IOException e) {
				throw new SlickException("Failed to load: "+ nextResource.getDescription(), e);
			}
			
			nextResource = null;
		}
		
		/* Check if we have to switch the text */
		if(textTime >= textDuration){
			
			textTime -= textDuration;
			loadingIndex = new Random().nextInt(loadingText.length) ;
			if(label.getTextRenderer() != null) label.setText(loadingText[loadingIndex]);
		}
		
		/* If we have to load the remaining resources */
		if (LoadingList.get().getRemainingResources() > 0 && resourceTime >= resourceDuration) {
			
			resourceTime -= resourceDuration;
			
			nextResource = LoadingList.get().getNext();
			
			float total = LoadingList.get().getTotalResources();
			float remain = LoadingList.get().getRemainingResources();
			progress.set((total-remain)/total);
		} 
			
		else 		
			loadingFinished();

	}

	/**
	 * This method is called if this state has been entered
	 */
	@Override
	protected void enterState(GameContainer container, StateBasedGame game) throws SlickException {
		
		/* Setup the screen */
		getNifty().gotoScreen("screen");
		progress = new ProgressBar();
		progress = getNifty().getScreen("load-custom-game-state-screen").findControl("my-progressbar", ProgressBar.class); // get the progress bar
		label = new Label();
		label = getNifty().getScreen("load-custom-game-state-screen").findControl("label-controller", Label.class); // get the progress bar

		
		/* Load the stuff for the loading screen */
		loadingIndex = new Random().nextInt(loadingText.length);
		
		//label.setText(loadingText[loadingIndex]);
		
		/* Create the ttf font */
		font = createFont(Font.TRUETYPE_FONT, "./res/font/Prototype.ttf", true);
		
		/* Play the music */
		music.loop();
		
		/* Finally load the following resources */
		loadResources();
	}

	/**
	 * This method is called if this state has been leaved
	 */
	@Override
	protected void leaveState(GameContainer container, StateBasedGame game) throws SlickException {
		
		music.stop();
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
	
	/**
	 * You can load your resources in this method
	 * @throws SlickException
	 */
	private void loadResources() throws SlickException{
		
		LoadingList.setDeferredLoading(true);
		
		new Music("./res/music/Menu.wav");
		new Music("./res/music/Menu.wav");
		new Music("./res/music/Menu.wav");
		new Music("./res/music/Menu.wav");
		new Music("./res/music/Menu.wav");
		new Music("./res/music/Menu.wav");
		new Music("./res/music/Menu.wav");
		new Music("./res/music/Menu.wav");
		new Music("./res/music/Menu.wav");
		new Music("./res/music/Menu.wav");
		new Music("./res/music/Menu.wav");
		new Music("./res/music/Menu.wav");
		new Music("./res/music/Menu.wav");
		new Music("./res/music/Menu.wav");
		new Music("./res/music/Menu.wav");
		new Music("./res/music/Menu.wav");
		new Music("./res/music/Menu.wav");
		new Music("./res/music/Menu.wav");
		new Music("./res/music/Menu.wav");
		new Music("./res/music/Menu.wav");	
	}
	
	/**
	 * Create a custom font for the loading screen
	 * @param fontFormat
	 * @param fontPath
	 * @param antiAlias
	 * @return
	 */
	private TrueTypeFont createFont(int fontFormat, String fontPath, boolean antiAlias){
		
		/* Get the font resource */
		InputStream inputStream	= ResourceLoader.getResourceAsStream(fontPath);
		
		/* Create the font */
		try {
			Font awtFont = Font.createFont(fontFormat, inputStream);
			awtFont = awtFont.deriveFont(30f); // set font size
			TrueTypeFont font = new TrueTypeFont(awtFont, antiAlias);
			return font;
		} 
		catch (FontFormatException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}