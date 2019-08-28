package entity;

import java.util.ArrayList;

import component.Component;
import component.ComponentManager;
import component.Info;
import core.Lua;
import event.Event;
import event.EventEntity;
import event.EventManager;

/**
 * This entity class manages the corresponding lua behaviors and components
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public final class Entity implements EventEntity {

	/* Attributes */
	private Lua lua; // able to call lua scripts
	
	/**
	 * Constructor
	 */
	public Entity(){
		
		lua = new Lua(); // initialize lua
	}
	
	/**
	 * Kind of destructor
	 * finalize() is called when the object implementing it gets collected by the Java garbage collector
	 */
	public void finalize(){
	
		ComponentManager cm = ComponentManager.getInstance();  // get the manager
		cm.removeAll(this); // remove all components
	}
	
	/**
	 * Call method with one parameter
	 * Call a certain method from the corresponding lua file
	 * @param methodname
	 * @param parameters
	 */
	public void call(String methodName, Object parameter){
		/* Get the info component to access the right lua file of the entity */
		Info info = get(Info.class);
		
		/* Handle the lua file method */
		if(info != null){
			
			/* Fetch and update the file */
			lua.doFile("./script/entity/" + info.scriptName); // compile the file
			
			/* Create the parameters */
			Object[] parameters = {this, parameter};
			
			lua.call(methodName, parameters); // call the method
		}
		else {
			System.out.println("ERROR: Create an info component to access the lua file!");
		}	
	}
	
	/**
	 * Call method with multiple parameters
	 * Call a certain method from the corresponding lua file
	 * @param methodname
	 * @param parameters
	 */
	public void call(String methodName, Object... parameters){

		/* Get the info component to access the right lua file of the entity */
		Info info = get(Info.class);
		
		/* Handle the lua file method */
		if(info != null){
			
			/* Fetch and update the file */
			lua.doFile("./script/entity/" + info.scriptName); // compile the file
			
			/* Create the parameters */
			Object[] p = new Object[parameters.length +1];
			p[0] = this;
			
			for(int i = 0; i < parameters.length; i++)
				p[i + 1] = parameters[i];
				
			/* Call the function */
			lua.call(methodName, p); // call the method
		}
		else {
			System.out.println("ERROR: Create an info component to access the lua file!");
		}	

	}
	
	/**
	 * Add a new component
	 * @param component
	 */
	public <T extends Component> void add(T component){
		
		ComponentManager cm = ComponentManager.getInstance(); // get the manager

		cm.add(component, this); // add the component
	}

	/**
	 * Get a component
	 * @param componentName
	 * @return
	 */
	public <T extends Component> T get(Class <T> componentClass){
		
		ComponentManager cm = ComponentManager.getInstance(); // get the manager
		
		return cm.get(componentClass, this);	 // get the component		
	}	
		
	/**
	 * Get all existing components
	 * @return
	 */
	public <T extends Component> ArrayList<T> getAll(){
		
		ComponentManager cm = ComponentManager.getInstance();
		
		return cm.getAll(this);
	}
	
	/**
	 * Remove an existing component
	 * @param component
	 * @return
	 */
	public <T extends Component> T remove(Class <T> componentClass){
		
		ComponentManager cm = ComponentManager.getInstance(); // get the manager
		
		return cm.remove(componentClass, this); // remove the component
	}
	
	/**
	 * Remove all existing components
	 * @return
	 */
	public <T extends Component> ArrayList<T> removeAll(){
		
		ComponentManager cm = ComponentManager.getInstance();  // get the manager
		
		return cm.removeAll(this); // remove all corresponding components
	}
	
	/**
	 * Subscribe the given event
	 * @param eventName
	 */
	@Override
	public void subscribeEvent(String eventName) {
		
		EventManager em = EventManager.getInstance(); // get the manager
		em.subscribe(this, eventName); // subscribe the event
	}

	/**
	 * Describe the given event
	 * @param eventName
	 */
	@Override
	public void describeEvent(String eventName) {

		EventManager em = EventManager.getInstance(); // get the manager
		em.describe(this, eventName); // describe the event
	}

	/**
	 * Handle the given event
	 */
	@Override
	public void handleEvent(Event event) {
		
		call("onHandleEvent", event);	 // call the specific lua event function
	}

}