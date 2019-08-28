package component;

import java.util.Arrays;

/**
 * Talk Component
 * There is a weird error in Lua. Thats why you have to add an implicit parameter for each key... :(
 * @author Bengt, Marlo, Alexander, Niclas
 */
public class Talk extends Component {
	
	/* Attributes */
	public String sentences[];
	
	/**
	 * Constructor 1
	 * @param sentence
	 */
	public Talk(String sentence){
		
		sentences = new String[1];
		sentences[0] = sentence;
	}
	
	/**
	 * Constructor 2
	 * @param sentence1
	 * @param sentence2
	 */
	public Talk(String sentence1, String sentence2){
		
		sentences = new String[2];
		sentences[0] = sentence1;
		sentences[1] = sentence2;
	}
	
	/**
	 * Constructor 3
	 * @param sentence1
	 * @param sentence2
	 * @param sentence3
	 */
	public Talk(String sentence1, String sentence2, String sentence3){
		
		sentences = new String[2];
		sentences[0] = sentence1;
		sentences[1] = sentence2;
		sentences[2] = sentence3;
	}

	/**
	 * Print the attributes
	 */
	@Override
	public String toString() {
		return "Talk [sentences=" + Arrays.toString(sentences) + "]";
	}

}