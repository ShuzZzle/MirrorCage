package core;

import gamestate.GameOver;
import gamestate.Intro;
import gamestate.Load;
import gamestate.Play;
import gamestate.TitleScreen;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Boarding point of the game
 * @author Bengt, Marlo, Alexander, Niclas
 */
public class BookOfKings extends StateBasedGame {
	
	/* Attributes */
	public static AppGameContainer game; // The object holds the current game
	
    /**
     * Constructor
     * @param name
     */
    public BookOfKings(String name) {
		super(name);
	}

    /**
     * Main method
     * @param args the command line arguments
     * @throws org.newdawn.slick.SlickException
     */
    public static void main(String[] args) throws SlickException {
    	
    	/* Create the game object which holds the whole game */
    	game = new AppGameContainer(new BookOfKings(GameProperties.GAME_NAME));
    	
    	/* Enable or disable some game properties */
        game.setDisplayMode((int)(game.getScreenWidth() * 0.8), (int)(game.getScreenHeight() * 0.8), false);     
       /* game.setVSync(GameProperties.enableVSync);
        game.setShowFPS(GameProperties.showFPS); 
        game.setAlwaysRender(true);
        game.setUpdateOnlyWhenVisible( GameProperties.updateOnlyWhenVisible); 
        game.setForceExit(GameProperties.forceExit);
        if(GameProperties.max60FPS) game.setTargetFrameRate(60);*/
        game.setSmoothDeltas(true);
        game.setShowFPS(true);
        game.setVerbose(false);
        game.setAlwaysRender(true);
        
        //game.setVSync(true);
        
        /* Start the game */
        game.start();
    }
 
    /**
     * Create the required game states
     * @param gc
     */
    @Override
	public void initStatesList(GameContainer gc) throws SlickException {
    	
    	//if(GameProperties.ENABLE_GAMESTATE_INTRO) addState( new Intro( GameValues.GAMESTATE_INTRO_ID) );
    	//if(GameProperties.ENABLE_GAMESTATE_TITLESCREEN) addState( new TitleScreen( GameValues.GAMESTATE_TITLESCREEN_ID) );
    	//if(GameProperties.ENABLE_GAMESTATE_LOAD) addState( new Load( GameValues.GAMESTATE_LOAD_ID) );
    	if(GameProperties.ENABLE_GAMESTATE_PLAY) addState( new Play( GameValues.GAMESTATE_PLAY_ID) );
    	//if(GameProperties.ENABLE_GAMESTATE_GAMEOVER) addState( new GameOver( GameValues.GAMESTATE_GAMEOVER_ID) );
    }
    
    /**
     * Handle the keyboard inputs
     * @see org.newdawn.slick.state.StateBasedGame#keyPressed(int, char)
     */
    @Override
	public void keyPressed(int key, char c) {
    	
       super.keyPressed(key, c);

       /* Change the display to full screen */
	   if (key == Input.KEY_F11 ) {
		        	  
		   try {
	    		if(!game.isFullscreen())
	    			game.setDisplayMode(game.getScreenWidth(), game.getScreenHeight(), true); // enable fullscreen
	    		
				else
					game.setDisplayMode((int)(game.getScreenWidth() * 0.8), (int)(game.getScreenHeight() * 0.8), false); // disable fullscreen
			} 
	    	catch (SlickException e) {	
	    		System.out.println("Error: Failed to switch the screen mode!");
				e.printStackTrace();			
			}  
	  }
  }

}