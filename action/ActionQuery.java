package action;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Stores the current actions as a query
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class ActionQuery implements Iterable<Action> {
	
	private static ActionQuery actionQuery;
	public ArrayList<Action> query;
	
	/**
	 * Private constructor
	 */
	private ActionQuery(){
		
		query = new ArrayList<>();
	}
	
	/**
	 * Get the instance of the singleton class
	 * @return
	 */
	public static ActionQuery getInstance(){
		
		if(actionQuery == null)
			actionQuery = new ActionQuery();
		
		return actionQuery;	
	}
	
	/**
	 * Add a new action
	 * @param action
	 */
	public void addAction(Action action){
		
		query.add(action);
	}
	
	/**
	 * Get an existing action by the index
	 * @param index
	 * @return
	 */
	public Action getAction(int index){
		
		return query.get(index);
	}
	
	/**
	 * Remove an existing action
	 * @param action
	 * @return
	 */
	public boolean removeAction(Action action){
		
		return query.remove(action);
	}
	
	/**
	 * Remove an existing action by the index
	 * @param index
	 * @return
	 */
	public Action removeAction(int index){
		
		return query.remove(index);
	}

	/**
	 * Check if the query is empty
	 * @return
	 */
	public boolean isEmpty(){
		
		return query.isEmpty();
	}
		
	/**
	 * Get the iterator
	 */
	@Override
	public Iterator<Action> iterator() {
		Iterator<Action> iterator = query.iterator();
		return iterator;
	}
	
}