package core;

/**
 * Diverse collection of ingame properties
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public abstract class GameProperties {
	
	/* The name of the game. The name will be shown on 
	 * the top of the windows for instance */
	public static final String GAME_NAME = "Book of Kings";
	
	/* Some properties for building the game */
	public static final boolean showFPS = false;
	public static final boolean enableVSync = false;
	public static final boolean updateOnlyWhenVisible = true;
	public static final boolean forceExit = true;
	public static final boolean max60FPS = false;
	
	/* Enable or disable specific game states */
	public static final boolean ENABLE_GAMESTATE_INTRO = true;
	public static final boolean ENABLE_GAMESTATE_TITLESCREEN = false;
	public static final boolean ENABLE_GAMESTATE_LOAD = false;
	public static final boolean ENABLE_GAMESTATE_PLAY = true;
	public static final boolean ENABLE_GAMESTATE_GAMEOVER = false;
	
	/* Set the debug mode to true or false
	 * If the debug mode is enabled, you will have access to certain
	 * developer ingame tools like live coding
	 * Note that you will properly have less fps than normal */
	public static final boolean DEBUG_MODE = true;
	
	/* Properties for the ingame quadtrees */
	public static final boolean QUADTREE_SHOW_BOUNDS = false;
	public static final int QUADTREE_MAX_ENTITIES_PER_NODE = 10;
	public static final int QUADTREE_MAX_LEVELS = 5;

	/* Properties for the tiled map */
	public static boolean ENABLE_TILEDMAP_COLLISION_BORDER = false;	
}