package action;

import org.newdawn.slick.command.Control;

/**
 * The action class defines an action in the game like firing, moving or setting a bomb
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Action {
	
	private final Control control; // defines the key or mouse control
	private final ActionCommand command; // command like fire, go, jump, etc...
	
	/**
	 * Constructor 1
	 * @param owner
	 * @param command
	 */
	public Action(ActionCommand command){
		
		this.control = null;
		this.command = command;
	}

	/**
	 * Constructor 2
	 * @param control
	 * @param command
	 */
	public Action(Control control, ActionCommand command){
		
		this.control = control;
		this.command = command;
	}
			
	/**
	 * Get the control
	 * @return
	 */
	public Control getControl() {
		return control;
	}
		
	/**
	 * Get the command event
	 * @return
	 */
	public ActionCommand getCommand() {
		return command;
	}

	/**
	 * Check if the action contains a control
	 * @return
	 */
	public boolean containControl(){
		
		return control == null ? false : true;
	}
}