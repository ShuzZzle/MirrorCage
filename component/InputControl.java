package component;

import org.newdawn.slick.Input;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.Control;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;

import core.BookOfKings;
import entity.Entity;

public class InputControl extends Component implements InputProviderListener {
	
	/* Attributes */
	public Entity owner;
	public InputProvider provider;
	
	/**
	 * Constructor
	 */
	public InputControl(){
		
		/* Get the owner of the component */
		ComponentManager componentManager = ComponentManager.getInstance();
		owner = componentManager.getEntity(this);
		
		/* Get the provider */
		Input input = BookOfKings.game.getInput();
		provider = new InputProvider(input);
	}
	
	/**
	 * Bind a new command
	 */
	public void bindCommand(Control control, Command command){
		
		provider.bindCommand(control, command);
	}
	
	/**
	 * Unbind an existing command by the control
	 */
	public void unbindCommand(Control control){
		
		provider.unbindCommand(control);
	}

	/**
	 * Unused control pressed notifier
	 */
	@Override
	public void controlPressed(Command command) {
		
		// Nothing to do here
	}

	/**
	 * Unused control released notifier
	 */
	@Override
	public void controlReleased(Command command) {
		
		// Nothing to do here
	}

}