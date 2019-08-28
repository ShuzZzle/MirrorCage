package map;

import java.util.ArrayList;
import java.util.Stack;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import collision.CollisionDetection;
import component.Camera;
import component.Collision;
import component.ComponentManager;
import component.Info;
import component.KeyControl;
import component.Position;
import component.Translation;
import component.Velocity;
import component.Zoom;
import core.GameValues;
import core.Lua;
import entity.Entity;
import event.Event;
import event.EventEntity;
import event.EventManager;

/**
 * The chunk class updates and renders the corresponding environment and stores all
 * entities which are located within the chunk
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public final class Chunk implements EventEntity {
	
	/* Common attributes */
	private Lua lua; // able to call lua scripts
	private String name; // name of the chunk
	private String fileName; // path to the the file that holds the tiles
	private Rectangle bounds; // bounds of the chunk to prevent that the camera renders any inappropriate tiles
	private Stack <ChunkLayer> layer; // the different layers (tile layer, event layer, cloud layer, etc. )
	
	/* The layer of the chunk */
	private TiledMap tiledMap; 
	private TileLayer groundLayer; // ground tiles (like paths, gras, etc)
	private TileLayer obstacleLayer; // obstacle tiles ( like fences, stones, trees, etc)
	private CollisionLayer collisionLayer;
	private EventLayer eventLayer; // event tiles for certain events (like teleport to another chunk, etc)
	private EntityLayer entityLayer; // the entities of this chunk
	private TileLayer upperLayer; // the upper tile layer (like bridges, roofs, etc)
	private CloudLayer cloudLayer; // cloud layer to show the shadows of the clouds
	private SandstormLayer sandstormLayer; // show a sound storm in front of the screen
	
	private Translation cameraTranslation;
	private Zoom cameraZoom;
	
	/**
	 * Constructor
	 * @param name
	 * @param luaName
	 * @param fileName
	 * @param bounds
	 * @param showClouds
	 * @throws SlickException 
	 */
	public Chunk(String name, String luaName, String fileName, Rectangle bounds, String weather) {
		
		/* Setup the attributes */
		super();
		this.name = name;
		this.fileName = fileName;
		this.bounds = bounds;	
		layer = new Stack<>();
		
		/* Create and set the layer */
		try {
			tiledMap = new TiledMap("./res/maps/" + fileName);
			
			groundLayer = new TileLayer(0, tiledMap, bounds);
			obstacleLayer = new TileLayer(1, tiledMap, bounds);
			collisionLayer = new CollisionLayer(1, tiledMap, bounds);
			eventLayer = new EventLayer(bounds);
			entityLayer = new EntityLayer(bounds);
			upperLayer = new TileLayer(2, tiledMap, bounds);
			if(weather.equals("clouds")) cloudLayer = new CloudLayer("./res/image/clouds.png", bounds);
			if(weather.equals("sandstorm")) sandstormLayer = new SandstormLayer("./res/image/Sandstorm.png", bounds);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		addLayer(groundLayer); 
		addLayer(obstacleLayer); 
		addLayer(collisionLayer);
		addLayer(eventLayer); 
		addLayer(entityLayer); 
		addLayer(upperLayer);
		if(weather.equals("clouds")) addLayer(cloudLayer); // render the clouds
		if(weather.equals("sandstorm")) addLayer(sandstormLayer);
		
	    /* Setup the lua environment and call the init method
	     * of the corresponding file */
		lua = new Lua();
		lua.doFile("./script/chunk/" + luaName);
		lua.call("onInit", this);
		
		/* Get the translation and zoom of the camera */
		Entity camera = ComponentManager.getInstance().getAllEntities(Camera.class).get(0);
		cameraTranslation = camera.get(Translation.class);
		cameraZoom = camera.get(Zoom.class);
	}
	
	/**
	 * Update method
	 * @param gc
	 * @param delta
	 * @throws SlickException 
	 */
	public void update(GameContainer gc, int delta) throws SlickException {
		
		// Update all layers
		for(int i = 0; i < layer.size(); i++)	
			layer.get(i).update(gc, delta);	
	}
	
	/**
	 * Render method
	 * @param gc
	 * @param g
	 * @throws SlickException 
	 */
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		g.translate(-cameraTranslation.x, -cameraTranslation.y);
		g.scale(cameraZoom.value, cameraZoom.value);
		
		// Render all layers
		for(int i = 0; i < layer.size(); i++)	
			layer.get(i).render(gc, g);

		g.resetTransform(); // important the the GUI
	}

	/**
	 * Add a new entity to the chunk
	 * @param entity
	 */
	public void addEntity(Entity entity){
		
		Position position = entity.get(Position.class); // get the position
		
		if(position != null)
			entityLayer.addEntity(entity, position.vector.x, position.vector.y);
		else {
			Info info = entity.get(Info.class);	// get the info component
			System.out.println("ERROR: You have to declare a position to add the entity '"
							    + info.name + "' to a chunk!");
		}
	}
	
	/**
	 * Get all entities from a specific tile position
	 * @param x
	 * @param y
	 * @return
	 */
	public ArrayList<Entity> getEntities(float x, float y){
		
		return entityLayer.getEntities(x, y);
	}
	
	public void moveEntity(Entity entity){
		
		Position position = entity.get(Position.class); // get the position
		
		if(position != null)
			entityLayer.moveEntity(entity, position.vector.x, position.vector.y);
		else {
			Info info = entity.get(Info.class);	// get the info component
			System.out.println("ERROR: You have to declare a position to move the entity '"
							    + info.name + "'!");
		}
	}
	
	/**
	 * Get all existing entities
	 * @return
	 */
	public ArrayList<Entity> getAllEntities() {
		return entityLayer.getAllEntities();
	}
	
	/**
	 * Remove the entity from the chunk
	 * @param entity
	 * @return 
	 */
	public void removeEntity(Entity entity){
		
		entityLayer.removeEntity(entity);
	}
	
	/**
	 * Remove all existing entities
	 */
	public void removeAllEntities(){
		
		entityLayer.removeAllEntities();
	}

	/**
	 * Get the name of the chunk
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the path to the corresponding file
	 * @return
	 */
	public String getPathToTileFile() {
		return fileName;
	}

	/**
	 * Get the bounds as a rectangle
	 * @return
	 */
	public Rectangle getBounds() {
		return bounds;
	}
	
	/**
	 * Get the different layers
	 * @return
	 */
	public Stack<ChunkLayer> getLayer() {
		return layer;
	}
	
	/**
	 * Get the view of the camera within the chunk
	 * @return
	 */
	public Rectangle getView(){
		
		return entityLayer.getVisibleArea();
	}
	
	/**
	 * Check if a specific tile is blocked
	 * @param (int)x
	 * @param (int)y
	 * @return
	 */
	public boolean isBlocked(float x, float y){
		
		/* Check if the x and y coordinate is located in the bounds */
		if( x > getBounds().getX() + getBounds().getWidth() || y > getBounds().getY() + getBounds().getHeight()) return true;
		if( x < 0 || y < 0 ) return true;
		
		return obstacleLayer.getTileImage((int)x, (int)y) != null ? true : false;	
	}
	
	/**
	 * Check if the entity collide with an obstacle
	 * @param entity
	 * @return
	 */
	public boolean intersects(Entity entity){
		
		/* Check the collision with the map */
		if(collisionLayer.intersects(entity)) return true;
			
		@SuppressWarnings("unchecked")
		ArrayList<Entity> potentialCollision =  (ArrayList<Entity>) entityLayer.getQuadtree().retrieve(entity);
		
		for(Entity potential :  potentialCollision ){
			
			CollisionDetection detection = new CollisionDetection();
			
			
			if(potential.equals(entity)) continue;
			
			Position pPosition = potential.get(Position.class);
			Collision pCollision = potential.get(Collision.class);
			if(pCollision == null|| pPosition == null) continue;
			
		
			Collision eCollision = entity.get(Collision.class);
			Position ePosition = entity.get(Position.class);
			Rectangle rect1 = eCollision.rect;
			rect1.setX(ePosition.vector.x);
			rect1.setY(ePosition.vector.y);
			
			Rectangle rect2 = pCollision.rect;
			rect2.setX(pPosition.vector.x);
			rect2.setY(pPosition.vector.y);
			
		
			if(detection.detect(rect1, rect2)){

				return true;
				
				
			}
			else {
				
				Vector2f eV = new Vector2f(eCollision.rect.getCenter());
				Vector2f pV = new Vector2f(pCollision.rect.getCenter());
				
				float distance = eV.distance(pV);
				
				if(distance > eCollision.rect.getWidth() * 0.71 + pCollision.rect.getWidth() * 0.71){
				eCollision.onceCollided = false;
				pCollision.onceCollided = false;
				}
				
				
			}
			
			
			
			
		}
		
		
		
		
		
		/* Check the collision with the entities */
		/*CollisionDetection detection = new CollisionDetection();
		ArrayList<Entity> potentialCollision =  (ArrayList<Entity>) entityLayer.getQuadtree().retrieve(entity);
		
		for(Entity potential : potentialCollision){
			
			if(potential.equals(entity)) continue;
			
			Position pPosition = entity.get(Position.class);
			Collision pCollision = entity.get(Collision.class);
			if(pCollision == null|| pPosition == null) continue;
			
		
			Collision eCollision = entity.get(Collision.class);
			Position ePosition = entity.get(Position.class);
			Rectangle rect1 = eCollision.rect;
			rect1.setX(ePosition.vector.x);
			rect1.setY(ePosition.vector.y);
			
			Rectangle rect2 = pCollision.rect;
			rect2.setX(pPosition.vector.x);
			rect2.setY(pPosition.vector.y);
			
		
			if(detection.detect(rect1, rect2)){
				
				entity.call("onCollide", potential);
				potential.call("onCollide", entity);
				
				return true;
				
				
			}
		}*/
		
		
		return false;
		
		//return collisionLayer.intersects(entity);
	}
	
	/**
	 * Get the lua object
	 * @return
	 */
	public Lua getLua() {
		return lua;
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
	
		lua.call("onHandleEvent", event);	 // call the specific lua event function	
	}
	
	/**
	 * Add a new layer
	 */
	private void addLayer(ChunkLayer chunkLayer){
		
		layer.add(chunkLayer);
	}
	
	/**
	 * Remove an existing layer
	 */
	@SuppressWarnings("unused")
	private boolean removeLayer(ChunkLayer chunkLayer){
		
		return layer.remove(chunkLayer);	
	}	
	
	/**
	 * Get the entity layer
	 * @return
	 */
	public EntityLayer getEntityLayer(){
		
		return (EntityLayer) layer.get(4);
	}
	
	/**
	 * Get the event layer
	 * @return
	 */
	public EventLayer getEventLayer(){
		
		return (EventLayer) layer.get(3);
	}
}