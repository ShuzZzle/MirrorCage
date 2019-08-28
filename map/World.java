package map;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.script.ScriptException;

import core.Lua;

/**
 * The world class stores and manages all available chunks
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public final class World {
	
	private static World map; // singleton instance
	private Lua lua; // possible to call lua scripts
	private Chunk currentChunk; // the current (active) chunk
	private HashMap <String, Chunk> allChunks; // Stores all chunks
	
	/**
	 * Constructor
	 * @param path (to the corresponding lua file)
	 * @throws ScriptException
	 */
	private World(){
		
		allChunks = new HashMap<>(); // Create the hash map to store the chunks	
		lua = new Lua();
	}
	
	/**
	 * Get the instance of the static class
	 * @return
	 */
	public static World getInstance(){
		
		if(map == null)
			map = new World();
		
		return map;
	}
	
	/**
	 * Load the world
	 */
	public void load(){
		lua.doFile("./script/load/loadMap.lua"); // load all chunks
		lua.call("onLoad", this);
	}

	/**
	 * Add a specific chunk by value
	 * @param chunk
	 */
	public void addChunk(Chunk chunk){
		
		allChunks.put(chunk.getName(), chunk);
		
		System.out.println("INFO: New chunk has been added: " + chunk.getName());
	}

	/**
	 * Get a specific chunk by name
	 * @param name
	 * @return
	 */
	public Chunk getChunk(String name){
		
		if(!allChunks.isEmpty()) // prevent errors
			return allChunks.get(name); // get the chunk
		
		return null; // Otherwise return null
	}
	
	/**
	 * Remove a specific chunk by name
	 * @param name
	 */
	public void removeChunk(String name){
		
		if(!allChunks.isEmpty()){	
			
			allChunks.remove(name);
			System.out.println("Info: A chunk has been removed: " + name);
		} 
		
	}
	
	/**
	 * Remove a specific chunk by the value
	 * @param name
	 */
	public void removeChunk(Chunk chunk){
		
		/* Check if the hash map is empty to prevent errors */
		if(!allChunks.isEmpty()){
			
			/* Loop through all entries */
			for(Entry<String, Chunk> entry : allChunks.entrySet()){
				
				/* Check if we have got the chunk */
				if(entry.getValue() == chunk){
					allChunks.remove(entry.getKey()); // Remove by key
					System.out.println("INFO: A chunk has been removed: " + entry.getKey());
				}	
			}		
		} 	
	}
	
	/**
	 * Get the current chunk
	 * @return
	 */
	public Chunk getCurrentChunk(){
		return currentChunk;		
	}
	
	/**
	 * Set the current chunk by name
	 * @param name
	 */
	public void setCurrentChunk(String name){
		
		/* Check if we have any chunks stored */
		if(!allChunks.isEmpty()){
			
			if(currentChunk != null) currentChunk.getLua().call("onLeave"); // leave the old chunk
			currentChunk = allChunks.get(name); // Get the chunk
			if(currentChunk != null) currentChunk.getLua().call("onEnter"); // enter the new chunk
			
			System.out.println("INFO: Current chunk: " + currentChunk.getName());	
		}
		/* Otherwise print an error message */
		else {	
			
			System.out.println("Error: There is no chunk " + name + "in the chunk list. You cant set a current chunk if you haven't stored any chunks!");		
		}
	}
	
	/**
	 * Get the lua object
	 * @return
	 */
	public Lua getLua(){
		
		return lua;
	}

}