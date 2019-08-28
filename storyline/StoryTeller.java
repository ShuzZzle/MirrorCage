package storyline;

import java.util.HashMap;

import core.Lua;

/**
 * Stores and manages all story chapters
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class StoryTeller {
	
	
	private static StoryTeller st; // singleton
	private Lua lua; // lua interface
	private StoryChapter currentChapter; // the current chapter
	private HashMap <String, StoryChapter> allChapters; // stores all chapters
	
	/**
	 * Private Constructor
	 */
	private StoryTeller(){
		
		allChapters = new HashMap<>();	
		lua = new Lua();
	}
	
	/**
	 * Get the instance of the singleton class
	 * @return
	 */
	public static StoryTeller getInstance(){
		
		if(st == null)
			st = new StoryTeller();
		
		return st;	
	}
	
	/**
	 * Load the story
	 */
	public void load(){
		
		lua.doFile("./script/load/loadStory.lua");
		lua.call("onLoad", this);
	}

	/**
	 * Add a new chapter
	 * @param name
	 * @param storyChapter
	 */
	public void addChapter(String name, StoryChapter storyChapter){

		allChapters.put(name, storyChapter);
	}
	
	/**
	 * Get an existing chapter
	 * @param storyChapterName
	 * @return
	 */
	public StoryChapter getChapter(String storyChapterName){

		return allChapters.get(storyChapterName);
	}
	
	/**
	 * Remove an existing chapter
	 * @param storyChapterName
	 */
	public void removeChapter(String storyChapterName){
		
		allChapters.remove(storyChapterName);
	}

	/**
	 * Set the current chapter
	 * @param storyChapterName
	 */
	public void setCurrentChapter(String storyChapterName){
		
		currentChapter = getChapter(storyChapterName);
	}
	
	/**
	 * Get the current chapter
	 * @return
	 */
	public StoryChapter getCurrentChapter(){
		
		return currentChapter;
	}
}
