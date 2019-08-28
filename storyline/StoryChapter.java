package storyline;

import core.Lua;
import event.Event;
import event.EventEntity;
import event.EventManager;

/**
 * Represents a part of the full story
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class StoryChapter implements EventEntity {
	
	private String name; // name of the chapter
	private Lua lua; // lua interface
	private boolean running; // indicates whether this part is running or not
	
	/**
	 * Constructor
	 * @param chapterName
	 */
	public StoryChapter(String chapterName, String luaName){
		
		name = chapterName;
			
		/* Create the lua interface */
		lua = new Lua();
		lua.doFile("./script/story/" + luaName + ".lua");
		lua.call("onCreate",this);
		running = false;
	}
	
	/**
	 * Start the story chapter
	 */
	public void start(){
		
		running = true;
		lua.call("onStart", this);
	}
	
	/**
	 * Complete the story chapter
	 */
	public void complete(){

		if(running) { 
			lua.call("onComplete", this);
			running = false;
		}
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

	
	/**
	 * Get the name
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	

	/**
	 * Get the lua interface
	 * @return the lua
	 */
	public Lua getLua() {
		return lua;
	}
	

	/**
	 * Check if this part is running
	 * @return the running
	 */
	public boolean isRunning() {
		return running;
	}

}