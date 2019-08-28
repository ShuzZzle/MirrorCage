package component;

import map.Chunk;

/**
 * Info component
 * This component is essential for every entity
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class Info extends Component {
	
	/* Attributes */
	public String name; 
	public String scriptName; // name of the lua script file
	public Chunk currentChunk; // the current chunk
	
	/**
	 * Constructor 1
	 * @param name
	 * @param scriptName
	 */
	public Info(String name, String scriptName){
		
		this.name = name;
		this.scriptName = scriptName;
	}

	/**
	 * Print the inner attributes of the class
	 */
	@Override
	public String toString() {
		return "Info [name=" + name + ", scriptName=" + scriptName
				+ ", currentChunk=" + currentChunk.getName() + "]";
	}

}