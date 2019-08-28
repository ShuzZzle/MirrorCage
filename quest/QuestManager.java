package quest;

import java.util.ArrayList;

import core.Lua;

/**
 * This quest manager class manages the quest behavior in the game
 * @author 
 *
 */
public class QuestManager {
	
	private static QuestManager qm; // singleton
	private Lua lua; // lua interface
	private ArrayList <Quest> allActiveQuests; // the active quest
	private ArrayList <Quest> allQuests; // stores all available quests
	
	/**
	 * Private Constructor
	 */
	private QuestManager(){
		
		allQuests = new ArrayList<>();
		allActiveQuests = new ArrayList<>();
		lua = new Lua();
	}
	
	/**
	 * Get the instance of the singleton class
	 * @return
	 */
	public static QuestManager getInstance(){
		
		if(qm == null)
			qm = new QuestManager();
		
		return qm;	
	}
	
	/**
	 * Load the quests
	 */
	public void load(){
		
		lua.doFile("./script/load/loadQuests.lua");
		lua.call("onLoad", this);
	}

	/**
	 * Add the quest
	 * @param quest
	 */
	public void addQuest(Quest quest){

		allQuests.add(quest);
	}
	
	/**
	 * Get a certain quest by name
	 * @param questName
	 * @return
	 */
	public Quest getQuest(String questName){

		for(Quest quest : allQuests){

			if(quest.getName().equals(questName))
				return quest;		
		}

		return null;
	}
	
	/**
	 * Get all quests
	 * @return
	 */
	public ArrayList<Quest> getAllQuests() {
		return allQuests;
	}
	
	/**
	 * Get all quests that are active
	 */
	public ArrayList <Quest> getAllActiveQuests(){
		
		return allActiveQuests;
	}

	/**
	 * Activate the quest
	 */
	public void addActiveQuest(Quest quest){
		
		allActiveQuests.add(quest);
	}

	/**
	 * Remove an active quest
	 * @param quest
	 */
	public void removeActiveQuest(Quest quest){
		
		allActiveQuests.remove(quest);
	}

}