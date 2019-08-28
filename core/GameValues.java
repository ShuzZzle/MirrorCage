package core;

import org.newdawn.slick.Input;

/**
 * Different collection of constants needed in the game
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public abstract class GameValues {
	
	/* The IDs of the different game states */
	public static final int GAMESTATE_INTRO_ID = 100;
	public static final int GAMESTATE_TITLESCREEN_ID = 200;
	public static final int GAMESTATE_LOAD_ID = 300;
	public static final int GAMESTATE_PLAY_ID = 400;
	public static final int GAMESTATE_GAMEOVER_ID = 500;
	
	/* Key assignments */
	public static final int KEY_MOVE_UP = Input.KEY_W;
	public static final int KEY_MOVE_DOWN = Input.KEY_S;
	public static final int KEY_MOVE_RIGHT = Input.KEY_D;
	public static final int KEY_MOVE_LEFT = Input.KEY_A;
	
	/* Tile specific constants */
	public static final int TILE_SIZE = 32;
	public static final float TILE_TO_PIXEL = 32.0f;
	public static final float PIXEL_TO_TILE = (1.0f / TILE_TO_PIXEL);
	public static final int TILE_OFFSET = 5;
	public static final int TILE_MINUS_OFFSET = -5;
	public static final int TILE_PLUS_OFFSET = 5;
}