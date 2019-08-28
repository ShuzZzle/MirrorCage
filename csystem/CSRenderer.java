package csystem;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import component.Collision;
import component.DisplayAnimation;
import component.DisplayImage;
import component.DisplayRect;
import component.Position;
import core.GameValues;
import entity.Entity;

/**
 * Renderer system
 * Display the entities as rectangles, images or as a animation
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class CSRenderer extends CSystem {
	
	/* Attributes */
	private static CSRenderer csRenderer; // singleton instance
	private Rectangle view; // current view of the camera
	private ArrayList <Entity> rectsToRender; // the rectangle entities to render
	private ArrayList <Entity> imagesToRender; // the image entities to render
	private ArrayList <Entity> animationsToRender; // the animation entities to render
	
	/**
	 * Private Constructor
	 */
	private CSRenderer(){
		
		rectsToRender = new ArrayList<>();
		imagesToRender = new ArrayList<>();
		animationsToRender = new ArrayList<>();
	}
	
	/**
	 * Get the static instance
	 * @return
	 */
	public static CSRenderer getInstance(){
		
		if(csRenderer == null)
			csRenderer = new CSRenderer();
		
		return csRenderer;	
	}

	/**
	 * Update method
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		/* Check if the current chunk exists */
		if(getCurrentChunk() == null) return;
		
		/* Get the current view */
		view = getCurrentChunk().getView();
		
		/* Clear the old array lists */
		rectsToRender.clear();
		imagesToRender.clear();
		animationsToRender.clear();
		
		/* Loop through the entities and check which one supports this renderer */
		for(int x = (int) view.getX(); x < view.getX() + view.getWidth(); x++)
			for(int y = (int) view.getY(); y < view.getY() +  view.getHeight(); y++){
				
				/* Get the entities of that tile */
				ArrayList<Entity> entities = getCurrentChunk().getEntities(x, y);
				
				/* Check each entity of that tile */
				if(entities != null)				
					for(Entity entity : entities){
							
						/* Get the required components */
						DisplayRect displayRect = entity.get(DisplayRect.class);
						DisplayImage displayImage = entity.get(DisplayImage.class);
						DisplayAnimation displayAnimation = entity.get(DisplayAnimation.class);
						
						/* Add a new entity */
						if(displayRect != null) rectsToRender.add(entity);						
						if(displayImage != null) imagesToRender.add(entity);	
						if(displayAnimation != null) animationsToRender.add(entity);					
					}	
				
				/* Continue the loop */
				else continue;
			}
		
	}

	/**
	 * Render method
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		/* Loop through the entities which supports the rect renderer */
		for(Entity entity : rectsToRender){
			
			/* Get the components */
			Position position = entity.get(Position.class);
			DisplayRect displayRect = entity.get(DisplayRect.class);
			
			/* Check if the entity holds the required components */
			if(position == null || displayRect == null) continue;
			
			/* Render the entity block */
			g.setColor(displayRect.color);
			g.fillRect(position.vector.x * GameValues.TILE_TO_PIXEL, 
					   position.vector.y * GameValues.TILE_TO_PIXEL,
					   displayRect.rect.getWidth() * GameValues.TILE_TO_PIXEL,
					   displayRect.rect.getHeight() * GameValues.TILE_TO_PIXEL);
		}
		
		/* Loop through the entities which supports the image renderer */
		for(Entity entity : imagesToRender){
			
			/* Get the components */
			Position position = entity.get(Position.class);
			DisplayImage displayImage = entity.get(DisplayImage.class);
			Collision collision = entity.get(Collision.class);
			
			/* Check if the entity holds the required components */
			if(position == null || displayImage == null || collision == null) continue;
			
			/* Render the entity image */
			float posX = position.vector.x;
			float posY = position.vector.y;
			
			if(displayImage.image.getWidth() * GameValues.PIXEL_TO_TILE > collision.rect.getWidth())		
				posX -= displayImage.image.getWidth() * GameValues.PIXEL_TO_TILE - collision.rect.getWidth();

			if(displayImage.image.getHeight() * GameValues.PIXEL_TO_TILE > collision.rect.getHeight())
				posY -= displayImage.image.getHeight() * GameValues.PIXEL_TO_TILE - collision.rect.getHeight();

			
			displayImage.image.draw(posX * GameValues.TILE_TO_PIXEL, posY * GameValues.TILE_TO_PIXEL);
			//g.drawImage(displayImage.image, posX * GameValues.TILE_TO_PIXEL, posY * GameValues.TILE_TO_PIXEL);
			
		}
		
		/* Loop through the entities which supports the image renderer */
		for(Entity entity : animationsToRender){
			
			/* Get the components */
			Position position = entity.get(Position.class);
			DisplayAnimation displayAnimation= entity.get(DisplayAnimation.class);
			
			/* Check if the entity holds the required components */
			if(position == null || displayAnimation == null) continue;
			
			/* Render the entity image */
			g.drawAnimation(displayAnimation.list.get(displayAnimation.current), 
					        position.vector.x * GameValues.TILE_TO_PIXEL, 
					        position.vector.y * GameValues.TILE_TO_PIXEL);			
		}
		
	}
	
}