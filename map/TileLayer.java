package map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import component.Camera;
import component.ComponentManager;
import component.Translation;
import component.Zoom;
import core.GameValues;
import entity.Entity;

/**
 * Represent a certain tiled layer of the chunk
 * @author Bengt, Marlo, Alexander, Niclas
 *
 */
public class TileLayer extends ChunkLayer {
	
	private final int layerId;
	private final TiledMap map;
	private Rectangle cameraView; // in tiles
	

	
	/**
	 * Constructor
	 * @param layerId
	 * @param pathToFile
	 * @param bounds
	 * @throws SlickException 
	 */
	public TileLayer(int layerId, TiledMap map, Rectangle bounds) throws SlickException{
		super(bounds);
		this.layerId = layerId;
		this.map = map;
		cameraView = null; // default
		
	
	}

	/**
	 * Update method
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		/* Prepare the area to render */
		cameraView = getVisibleArea(); // get the view of the camera in tiles	
	}

	/**
	 * Render method
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {

		if(cameraView != null){
			for(int x = (int) cameraView.getX(); x < cameraView.getX() + cameraView.getWidth(); x++)
				for(int y = (int) cameraView.getY(); y < cameraView.getY() + cameraView.getHeight(); y++)
					if(map.getTileImage(x, y, layerId) != null)
						g.drawImage(map.getTileImage(x, y, layerId), x * GameValues.TILE_TO_PIXEL, y * GameValues.TILE_TO_PIXEL);
		}
		
	}

	/**
	 * Get the property of a specific tile
	 * @param x
	 * @param y
	 * @param propertyName
	 * @param def
	 * @return
	 */
	public String getProperty(int x, int y, String propertyName, String def){
		
		int tileID = map.getTileId(x, y, layerId);
		return map.getTileProperty(tileID, propertyName, def);
	}
	
	/**
	 * Get the layer id
	 * @return
	 */
	public int getLayerId() {
		return layerId;
	}
	
	/**
	 * Get the tiled map
	 * @return
	 */
	public TiledMap getMap() {
		return map;
	}
	
	/**
	 * Get the tile image of a specific tile
	 * @param x
	 * @param y
	 * @return
	 */
	public Image getTileImage(int x, int y){
		
		return map.getTileImage(x, y, layerId);
	}

}