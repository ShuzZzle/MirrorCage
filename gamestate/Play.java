package gamestate;

import map.World;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import quest.QuestManager;
import storyline.StoryTeller;
import core.Lua;

public final class Play extends GameState {
	
	private World world; // represents the game world
	private StoryTeller storyTeller; // manages the story
	private QuestManager questManager; // manages all quests

	/**
	 * Constructor
	 * @param id
	 */
	public Play(int id) {
		super(id);
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
		/* Create the story */
		storyTeller = StoryTeller.getInstance();
		storyTeller.load();
		
		/* Create the quests */
		questManager = QuestManager.getInstance();
		questManager.load();
		
		/* Create the important entities */
		Lua lua = new Lua(); 	
		lua.doFile("./script/load/loadEntities.lua"); // load the important entities
		lua.call("onLoad");
		
		/* Create the world */
		world = World.getInstance();
		world.load();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		world.getCurrentChunk().render(gc, g);	
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		world.getCurrentChunk().update(gc, delta);
	}

}