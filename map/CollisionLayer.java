package map;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import collision.CollisionDetection;
import component.Collision;
import component.Position;
import core.GameProperties;
import core.GameValues;
import entity.Entity;

public class CollisionLayer extends ChunkLayer {
	
	private int layerId;
	private TiledMap map;
	private ArrayList<Rectangle> obstacles;
	private CollisionDetection detection;
	
	/**
	 * Constructor
	 * @param layerId
	 * @param map
	 * @param bounds
	 */
	public CollisionLayer(int layerId, TiledMap map, Rectangle bounds) {
		super(bounds);
		
		/* Setup */
		this.layerId = layerId;
		this.map = map;	
		obstacles = new ArrayList<>();
		detection = new CollisionDetection();
		
		/* Fill the obstacles rects */
		for(int y = 0; y < bounds.getHeight(); y++)
			for(int x = 0; x < bounds.getWidth(); x++)
				if(map.getTileImage(x, y, layerId) != null)
					obstacles.add(new Rectangle(x, y, 1, 1));
	}
	
	/**
	 * Check if an entity intersects an obstacle
	 * @param entity
	 * @return
	 */
	public boolean intersects(Entity entity){
		
		/* Get and set up the components */
		Position position = entity.get(Position.class);
		Collision collision = entity.get(Collision.class);
		collision.rect.setX(position.vector.x);
		collision.rect.setY(position.vector.y);
		
		if(position == null || collision == null) return false;
		
		/* Check the collision */
		for(Rectangle obstacle : obstacles){
			
			if(detection.detect(collision.rect, obstacle)) return true;
			else continue;
		}
		
		
		return false;
				
				/* Get the obstacle as a rect */
				/*Rectangle obstacle = obstacles.get((int) (position.vector.y * map.getWidth() + position.vector.x));
				if(obstacle == null) return false;
				
				if(detection.detect(collision.rect, obstacle)) return true;
			
			
		return false;*/
	}
	
	/**
	 * Add a new obstacle
	 * @param x
	 * @param y
	 */
	public void add(int x, int y){
		
		obstacles.add(y * map.getWidth() + x, new Rectangle(x, y, 1, 1));
	}
	
	/**
	 * Remove an existing obstacle
	 * @param x
	 * @param y
	 */
	public void remove(int x, int y){
		
		obstacles.remove(y * map.getWidth() + x);
	}

	/**
	 * Unused update method
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		// Nothing to do here	
	}

	/**
	 * Render method
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		if(!GameProperties.ENABLE_TILEDMAP_COLLISION_BORDER) return;
		
		for(Rectangle rect :  obstacles){
			
			/* Render the entity block */
			g.setColor(Color.black);
			g.drawRect(rect.getX() * GameValues.TILE_TO_PIXEL, 
					   rect.getY() * GameValues.TILE_TO_PIXEL,
					   rect.getWidth() * GameValues.TILE_TO_PIXEL,
					   rect.getHeight()* GameValues.TILE_TO_PIXEL);
		}
		
	}
	
}