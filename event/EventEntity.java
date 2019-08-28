package event;

public interface EventEntity {
	
	/**
	 * Subscribe a specific event
	 * @param eventName
	 */
	public void subscribeEvent(String eventName);
	
	/**
	 * Describe a specific event
	 * @param eventName
	 */
	public void describeEvent(String eventName);
	
	/**
	 * Handle the given event
	 * @param event
	 */
	public void handleEvent(Event event);

}
