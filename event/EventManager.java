package event;

import java.util.List;

public final class EventManager {
	
	private static EventManager eventManager; // Attribute for this singleton pattern
	private EventTable table; // Stores all subscribed entities
	
	/**
	 * Private Constructor
	 */
	private EventManager(){
		
		table = new EventTable();
	}
	
	/**
	 * Get the instance of this singleton class
	 */
	public static EventManager getInstance(){
		
		if(eventManager == null)
			eventManager = new EventManager();
		
		return eventManager;
	}
	
	/**
	 * Subscribe an entity
	 * @param entity
	 */
 	public void subscribe(EventEntity entity, String eventName){
		
 		table.setEntity(eventName, entity); // Subscribe each event
				
	}
	
	/**
	 * Describe an entity
	 * @param entity
	 */
	public void describe(EventEntity entity, String eventName){
		
		table.removeEntity(eventName, entity); // Describe each event
			
	}
	
	/**
	 * Dispatch the event to all subscribed event entities
	 * @param event
	 */
	public void dispatch(Event event){
		
		String eventType = event.getType(); // Get the event type
		
		if(!table.isEmpty()){
			
			List<EventEntity> entities = table.getEntities(eventType); // Get all subscribed entities
			
			for(EventEntity entity : entities)
				entity.handleEvent(event); // Let the subscribed entities handle this event
		}
	
	}
	
}
