package component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import entity.Entity;

/**
 * Manages the components of the entity
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public final class ComponentManager {
	
	/* Attributes */
	private static ComponentManager componentManager;
	@SuppressWarnings("rawtypes")
	private HashMap <Class, HashMap <Entity, ? extends Component>> store;
	
	/**
	 * Private Constructor
	 */
	private ComponentManager(){
		
		store = new HashMap<>();
	}
	
	/**
	 * Get the instance of the static class
	 * @return
	 */
	public static ComponentManager getInstance(){
		
		if(componentManager == null)
			componentManager = new ComponentManager();
		
		return componentManager;
	}
	
	/**
	 * Add a new relation between a component and an entity
	 * @param component
	 * @param entity
	 */
	public <T extends Component> void add(T component, Entity entity){
		
		@SuppressWarnings("unchecked")
		HashMap <Entity, T> pair = (HashMap<Entity, T>) store.get(component.getClass());
		
		if(pair == null){
			pair = new HashMap<>();
			store.put(component.getClass(), pair);	
		}
			
		pair.put(entity, component);		
	}
	
	/**
	 * Get the corresponding entity of the given component
	 * @param component
	 * @return
	 */
	public <T extends Component> Entity getEntity(T component){
		
		/* Get the pair of entities and components */
		@SuppressWarnings("unchecked")
		HashMap <Entity, T> pair = (HashMap<Entity, T>) store.get(component.getClass());
		if(pair == null) return null;
		
		/* Get the component by looping through all existing entries */
		for(Entry< Entity, ? extends Component> entry : pair.entrySet())	
			if(entry.getValue() == component)
				return entry.getKey(); // return the entity
		
		/* We didn't find an entity */
		return null;
	}
	
	/**
	 * Get a certain component from an entity
	 * @param componentClass
	 * @param entity
	 * @return
	 */
	public <T extends Component> T get(Class<T> componentClass, Entity entity){
		
		@SuppressWarnings("unchecked")
		HashMap <Entity, T> pair = (HashMap<Entity, T>) store.get(componentClass);
		
		if(pair == null) return null;
		
		return pair.get(entity);
	}
	
	/**
	 * Get all component of the given entity
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> ArrayList<T> getAll(Entity entity){
		
		ArrayList<T> allComponents = new ArrayList<>();
		
		for(@SuppressWarnings("rawtypes") Entry<Class, HashMap<Entity, ? extends Component>> entry : store.entrySet()){
			
			T component = (T) get(entry.getKey(), entity);
			
			if(component != null)
				allComponents.add(component);
		}
		
		return allComponents;
	}
	
	/**
	 * Get all entities of the given component
	 * @param entity
	 * @return
	 */
	public <T extends Component> ArrayList<Entity> getAllEntities(Class<T> componentClass){
		
		ArrayList<Entity> allEntities = new ArrayList<>();
		
		for(@SuppressWarnings("rawtypes") Entry<Class, HashMap<Entity, ? extends Component>> entry : store.entrySet()){
			
			if(entry.getKey() == componentClass){
				HashMap<Entity, ? extends Component> map = entry.getValue();
				
				for( Entry<Entity, ? extends Component> mapEntry : map.entrySet()){
					
					Entity entity = mapEntry.getKey();
					allEntities.add(entity);
				}
			}

		}
		
		return allEntities;
	}
	
	/**
	 * Check if the entity contains a certain component
	 * @param componentClass
	 * @param entity
	 * @return
	 */
	public <T extends Component> boolean contains(Class<T> componentClass, Entity entity){
		
		return get(componentClass, entity) == null ? false : true;
	}
	
	/**
	 * Remove a certain component from an entity
	 * @param componentClass
	 * @param entity
	 * @return
	 */
	public <T extends Component> T remove(Class<T> componentClass, Entity entity){
		
		@SuppressWarnings("unchecked")
		HashMap <Entity, T> pair = (HashMap<Entity, T>) store.get(componentClass);
		
		if(pair == null) return null;
		
		return pair.remove(entity);	
	}

	/**
	 * Remove all component of the entity
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> ArrayList<T> removeAll(Entity entity){
		
		ArrayList<T> allComponents = new ArrayList<>();
		
		/* Loop through all components and remove the entities */
		for(@SuppressWarnings("rawtypes") Entry<Class, HashMap<Entity, ? extends Component>> entry : store.entrySet()){
	
			/* Remove the value */	
			HashMap<Entity, ? extends Component> pair = entry.getValue();
			
			if(pair != null)
				allComponents.add((T) pair.remove(entity));
		}
		
		return allComponents;
	}
	
}