package event;

public class Event {
		
	private final String type; // type
	private final Object[] args; // arguments
	
	/**
	 * Constructor
	 * @param type
	 * @param args
	 */
	public Event(String type, Object... args){
		
		this.type = type;
		
		/* Store the arguments */
		if(args.length > 0)	
			this.args = args;
		else
			this.args = null;			
	}
	
	/**
	 * Constructor 2
	 * @param type
	 * @param args
	 */
	public Event(String type){
		
		this.type = type;
		
		args = null;
	}
	
	/**
	 * Get the type of the event
	 * @return
	 */
	public String getType(){	
		return type;	
	}

	/**
	 * Get the arguments
	 * @return
	 */
	public Object[] getArgs(){	
		return args;
	}

}
