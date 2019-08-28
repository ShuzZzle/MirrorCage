package map;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import core.GameValues;

public class SandstormLayer extends ChunkLayer {
	
	private float speed; // speed of the clouds
	private Image sand; // visible cloud image
	private Vector2f direction; // movement direction
	private ArrayList<Vector2f> cloudPosition; // position of the multiple images
	private Vector2f translation; // current translation

	/**
	 * Constructor
	 * @param pathToImage
	 * @param bounds
	 * @throws SlickException 
	 */
	public SandstormLayer(String pathToImage, Rectangle bounds) throws SlickException{	
		
		/* Setup */
		super(bounds);
		speed = 20f;
		cloudPosition = new ArrayList<>();
		translation = new Vector2f(0,0);
		direction = new Vector2f(1,0.2f).normalise();
		sand = new Image(pathToImage);
		//sand.setImageColor(0, 0, 0, 0.07f);
	
		/* Fill the screen with clouds */
		for(float x = 0; x < bounds.getWidth() + sand.getWidth() * GameValues.PIXEL_TO_TILE; x += sand.getWidth() * GameValues.PIXEL_TO_TILE)
			for(float y = 0; y < bounds.getHeight() + sand.getHeight() * GameValues.PIXEL_TO_TILE; y += sand.getHeight() * GameValues.PIXEL_TO_TILE)
				cloudPosition.add(new Vector2f(x, y));
	}

	/**
	 * Update method
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		/* Calculate the translation and the time */
		float deltaTime = 0.001f * delta;	
		translation.x = deltaTime * speed; // x
		
		if(translation.x > getBounds().getWidth()) translation.x -= getBounds().getWidth(); // x
		
		/* Translate the clouds and check if the clouds exceed the bounds */
		for(Vector2f cloudp : cloudPosition){			
			
			cloudp.add(translation); // increase the position
			
			/* Do the check */
			if(cloudp.x > getBounds().getX() + getBounds().getWidth()){
				
				cloudp.x -= getBounds().getWidth() + sand.getWidth() * GameValues.PIXEL_TO_TILE + 2; // decrease the x value
			}
			
		}
	}

	/**
	 * Render method
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		/* Draw the clouds for every 'cloud rectangle' */
		for(Vector2f position : cloudPosition)			
			g.drawImage(sand, position.x * GameValues.TILE_TO_PIXEL, position.y * GameValues.TILE_TO_PIXEL);
	}

	
	/**
	 * Get the speed of the clouds
	 * @return
	 */
	public float getSpeed() {
		return speed;
	}
	
	/**
	 * Set the speed of the clouds
	 * @param speed
	 */

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	/**
	 * Get the cloud image
	 * @return
	 */

	public Image getCloud() {
		return sand;
	}
	
	/**
	 * Set the new cloudimage
	 * @param cloud
	 */

	public void setCloud(Image cloud) {
		this.sand = cloud;
		
		cloudPosition = new ArrayList<>();
		translation = new Vector2f(0,0);
		this.sand = cloud;
		//cloud.setImageColor(0, 0, 0, 0.07f);
	
		/* Fill the screen with clouds */
		for(float x = bounds.getX(); x < bounds.getX() + bounds.getWidth() + cloud.getWidth(); x += cloud.getWidth())
			for(float y = bounds.getY(); y < bounds.getY() + bounds.getHeight() + cloud.getHeight(); y += cloud.getHeight())
				cloudPosition.add(new Vector2f(x, y));	
	}
	
	/**
	 * Get the movement direction of the clouds
	 * @return
	 */

	public Vector2f getDirection() {
		return direction;
	}
	
	/**
	 * Set the movement direction of the clouds
	 * @param direction
	 */

	public void setDirection(Vector2f direction) {
		this.direction = direction.normalise();
	}

	
	
	
}
