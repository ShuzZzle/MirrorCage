package csystem;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;

import core.BookOfKings;
import core.Lua;
import action.Action;
import action.ActionCommand;
import action.ActionQuery;

public class CSAction extends CSystem implements InputProviderListener {
	
	private static final String PATH_TO_ACTION_DIRECTORY = "./script/action/";
	
	private Lua lua; // lua interface
	private static CSAction csAction; // singleton instance
	private ActionQuery actionQuery; // stores all available actions
	private ArrayList<Action> delete; // action objects to delete
	private InputProvider provider;
	
	/**
	 * Private Constructor
	 */
	private CSAction(){
		
		provider = new InputProvider(BookOfKings.game.getInput());
		provider.addListener(this);
		actionQuery = ActionQuery.getInstance();
		lua = new Lua();
	}
	
	/**
	 * Get the instance of the singleton class
	 * @return
	 */
	public static CSAction getInstance(){
		
		if(csAction == null)
			 csAction = new CSAction();
		
		return csAction;
	}

	/**
	 * Update method
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		
		for(Iterator<Action> it = actionQuery.iterator(); it.hasNext();){
			
			Action action = it.next();
			if(action.containControl()) {
							
				provider.bindCommand(action.getControl(), action.getCommand());
				continue;
			}

			doAction(action);
			it.remove();
			
		}
			
	}
	
	/**
	 * Do the action immediately
	 * @param action
	 */
	private void doAction(Action action){
		
		/* Fetch and refresh the corresponding lua file */
		String name = action.getCommand().getName() + ".lua";
		lua.doFile(PATH_TO_ACTION_DIRECTORY + name);
		
		/* Call the 'onAction' function */
		if(action.getCommand().getArgs() == null)
			lua.call("onAction");
		else
			lua.call("onAction", "Suedweg");

	}
	
	/**
	 * Unused render method
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		// Nothing to do here
	}

	@Override
	public void controlPressed(Command command) {
		
		
		
	}

	@Override
	public void controlReleased(Command command) {
		
		
		System.out.println("Hallo");
		
		/* Cast the type of the command or exit this method by mess */
		if(command instanceof ActionCommand) command = (ActionCommand) command;
		else return;
		
		/* Fetch and refresh the corresponding lua file */
		String name = ((BasicCommand) command).getName() + ".lua";
		lua.doFile(PATH_TO_ACTION_DIRECTORY + name);
		
		/* Call the 'onAction' function */
		if(((ActionCommand) command).getArgs() == null)
			lua.call("onAction");
		
	}

}