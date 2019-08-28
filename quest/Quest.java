package quest;

import core.Lua;
import event.Event;
import event.EventEntity;
import event.EventManager;

/**
 * This quest class manages a certain task that the player have to do
 * @author 
 *
 */
public class Quest implements EventEntity {

	private String name; // the name of the quest
	private String questFlag; // indicates the state of the quest (Unknown, Active, Completed)
	private Lua lua; // able to call lua scripts

	/**
	 * Constructor
	 * @param questName
	 */
	public Quest(String questName){
		
		/* Setup */
		name = questName;
		lua = new Lua();
		lua.doFile("./script/quest/" + name + ".lua");
		setQuestFlag("Unknown"); // set the quest flag to unknown
		
		/* Call the lua script */
		lua.call("onCreate", this);	
	}
	
	/**
	 * Start the quest
	 */
	public void start(){
		
		setQuestFlag("Active"); // set the quest flag to active
		lua.call("onStart",this); // call the lua method
	}
	
	/**
	 * Abort the quest
	 */
	public void abort(){
		setQuestFlag("Unknown"); // set the quest flag to Unknown
		lua.call("onAbort",this); // call the lua method	
	}
	
	/**
	 * Complete the quest
	 */
	public void complete(){
		
		setQuestFlag("Completed"); // set the quest flag to active
		lua.call("onComplete",this); // call the lua method
	}
	
	/**
	 * Get the quest name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the quest flag of this quest
	 */
	public String getQuestFlag(){
		
		return questFlag;
	}	
	
	/**
	 * Set the quest flag for this quest
	 * @param questFlag
	 */
	public void setQuestFlag(String questFlag){
		
		this.questFlag = questFlag;	
	}

	/**
	 * Subscribe to the given event
	 */
	@Override
	public void subscribeEvent(String eventName) {
		
		EventManager em = EventManager.getInstance();
		
		if(em != null)
			em.subscribe(this, eventName);
		else
			System.out.println("Error: Before you can subscribe to an event, you have to create and event manager!");
	}

	/**
	 * Describe the given event
	 */
	@Override
	public void describeEvent(String eventName) {

		EventManager em = EventManager.getInstance();
		
		if(em != null)
			em.describe(this, eventName);
		else
			System.out.println("Error: Before you can describe an event, you have to create and event manager!");	
	}

	/**
	 * Handle the incoming event
	 */
	@Override
	public void handleEvent(Event event) {
		
		lua.call("onHandleEvent", event); // handle the event in the lua script
	}
	
}