package event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class EventTable {
	
	private Map <String, ArrayList<EventEntity>> events; // represent an event like 'E_EXPLOSION'
	
	/**
	 * Constructor
	 */
	public EventTable(){
		events = new HashMap<>();
	}
	
	/**
	 * Set a new entity to a custom event type
	 * @param eventType
	 * @param entity
	 */
	public void setEntity(String eventType, EventEntity entity){
		
		if(!events.isEmpty()){ // Check if we have any entities subscribed to this event type
			
			ArrayList <EventEntity> entities = events.get(eventType); // Get the entities
			
			entities.add(entity); // Add the entity
			
			events.put(eventType, entities); // Set all entities to the table
		}
		else {
			
			ArrayList <EventEntity> entities = new ArrayList<>();

			entities.add(entity); // Add the entity
			
			events.put(eventType, entities); // Set all entities to the table
		
		}
	}
	
	/**
	 * Remove a specific entity
	 * @param eventType
	 * @param entity
	 */
    public void removeEntity(String eventType, EventEntity entity){
    	
		if(!events.isEmpty()){ // Check if we have any entities subscribed to this event type
			
			/* Get all entities subscribed to the specific event type */
			ArrayList <EventEntity> entities = events.get(eventType); // Get the entities
			
			/* Remove the specific entity */
			if(!entities.isEmpty()){				
				entities.remove(entity); // Remove the entity
				events.put(eventType, entities); // Set all entities to the table
			}
		}    		
    }
    
    /**
     * Check if the given entity exists in this table
     * @param entity
     * @return
     */
    public boolean isExisting(EventEntity entity){
    	
    	   /* Get the iterator */
    	   Iterator<Entry<String, ArrayList<EventEntity>>> it = events.entrySet().iterator();
    	   
    	   /* Get all subscribers of a certain event */
    	   while (it.hasNext()) {
    	        @SuppressWarnings("rawtypes")
				Map.Entry pairs = it.next();
    	        @SuppressWarnings("unchecked")
				ArrayList<EventEntity> subscribers = (ArrayList<EventEntity>) pairs.getValue();
    	        
    	        if(subscribers != null)
    	        	if(subscribers.contains(entity)) return true;
    	        		
    	        it.remove(); // avoids a ConcurrentModificationException
    	   }
    	    
    	   return false;	
    }
	
    /**
     * Get all entities by the event type
     * @param eventType
     * @return
     */
	public List<EventEntity> getEntities (String eventType){
		
		return events.get(eventType);

	}

	/**
	 * Check if this table is empty
	 * @return
	 */
	public boolean isEmpty(){
		return events.isEmpty();
	}
	
}
