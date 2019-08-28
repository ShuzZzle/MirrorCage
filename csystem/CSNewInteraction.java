package csystem;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;


import org.newdawn.slick.command.KeyControl;

import core.BookOfKings;
import core.GameProperties;

public class CSNewInteraction extends CSystem implements InputProviderListener {
	
	private static CSNewInteraction csNewInteraction;
	private InputProvider provider;
	
	/**
	 * Private constructor
	 */
	private CSNewInteraction(){
		
		provider = new InputProvider(BookOfKings.game.getInput());
		provider.bindCommand(new KeyControl(Input.KEY_LEFT), new BasicCommand("run"));
	}
	
	/**
	 * Get the instance of the singleton class
	 * @return
	 */
	public static CSNewInteraction getInstance(){
		
		if(csNewInteraction == null)
			csNewInteraction = new CSNewInteraction();
		
		return csNewInteraction;	
	}
	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Notify if a control has been pressed
	 * @param command
	 */
	@Override
	public void controlPressed(Command command){
		
		/* Cast the type of the command or exit this method by mess */
		if(command instanceof BasicCommand) command = (BasicCommand) command;
		else return;
		
		/* Print that the control has been pressed */
		if(GameProperties.DEBUG_MODE) System.out.println("INFO: Control pressed: " + command);
	}

	/**
	 * Notify if a control has been released
	 * @param command
	 */
	@Override
	public void controlReleased(Command command){

		/* Cast the type of the command or exit this method by mess */
		if(command instanceof BasicCommand) command = (BasicCommand) command;
		else return;
		
		/* Print that the control has been released */
		if(GameProperties.DEBUG_MODE) System.out.println("INFO: Control released: " + command);	
	}

}