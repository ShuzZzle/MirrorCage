package collision;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;

import component.Info;
import component.Position;
import core.GameProperties;
import core.GameValues;
import entity.Entity;

/**
 * Efficient collision detection
 * @author Bengt, Marlo, Alexander, Niclas
 */
public class Quadtree {
 
	/* Constants */
    public final static int MAX_ENTITIES = GameProperties.QUADTREE_MAX_ENTITIES_PER_NODE; // How many objects a node can hold before it splits
    public final static int MAX_LEVELS = GameProperties.QUADTREE_MAX_LEVELS; // Defines the deepest level subnode

    /* Attributes */
    private int level; // The current level
    private ArrayList<Entity> entities; // An array list of the objects (player, enemy etc)
    private Rectangle bounds; // Represent the 2D space
    private Quadtree[] nodes; // The four subnodes
 
    /**
     * Constructor
     * @param pLevel
     * @param pBounds
     */
    public Quadtree(int level, Rectangle bounds) {
    	
        entities = new ArrayList<>();
        nodes = new Quadtree[4];
        this.bounds = bounds;
    }   
    
    /**
     * Insert the entity into the quadtree
     * If the node exceeds the capacity, it will split 
     * and add all objects to their corresponding subnodes.
     * @param entity
     */
    public void insert(Entity entity) {
    	
    	/* Check if the entity holds the required position component */
    	if(!checkForPosition(entity)){
    		Info info = entity.get(Info.class);
    		System.out.println("ERROR: You have the give the entity '" + info.name + "' a position component to insert it into a quadtree!");
    		return;
    	}

    	/* Insert the entity into the sub quadtree if required */
        if (nodes[0] != null) {
        	
			int index = getIndex(entity);
			
			if (index != -1) {
				nodes[index].insert(entity);
				return;
			}
        }

        /* Add the entity to all entities */
        entities.add(entity);

        /* Create four new quadtrees if the node exceeds the capacity */
        if(entities.size() > MAX_ENTITIES && level < MAX_LEVELS) { // Check for exceeding...

           if(nodes[0] == null) split();  // If we haven't got any subtress, we will create four new one
           
           /* Split all entities to the corresponding quadtree */
           for(int i = entities.size(); i-- > 0;){
        	   
        	   int index = getIndex(entities.get(i)); // Get the index of the subtree
        	   if (index != -1) nodes[index].insert(entities.remove(i)); // Insert the entity        	   
           }       
      	}
        
    }
    
    /**
     * Return all entities which can collide with the target entity
     * @param entity
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList retrieve(Entity entity) {
        
    	ArrayList collisionRects = new ArrayList(); 

        int index = getIndex(entity); // Get the index of the subtree

        if (index != -1 && nodes[0] != null) { // If there are any subtrees
          collisionRects = nodes[index].retrieve(entity); // Get the objects
        }

        /* Add and return the rects that can collide */
        collisionRects.addAll(entities);
        return collisionRects; // Returns all objects
    }
    
    /**
     * Draw the bounds to make them visible if required
     * Note that you have to deal with lower FPS by drawing the bounds
     * @param gc
     * @param g
     */
    public void drawBounds(GameContainer gc, Graphics g){
    	
    	if(nodes[0] != null){
	    	nodes[0].drawBounds(gc, g);
	    	nodes[1].drawBounds(gc, g);
	    	nodes[2].drawBounds(gc, g);
	    	nodes[3].drawBounds(gc, g);	
    	}
    	
    	g.setLineWidth(2);
    	g.drawRect(bounds.getX() * GameValues.TILE_TO_PIXEL, 
    			   bounds.getY() * GameValues.TILE_TO_PIXEL, 
    			   (bounds.getX() + bounds.getWidth()) * GameValues.TILE_TO_PIXEL, 
    			   (bounds.getY() + bounds.getHeight()) * GameValues.TILE_TO_PIXEL);	
    	g.setLineWidth(1);
    }
    
    /**
     * Check if the quadtrees are empty
     * @return
     */
    public Boolean isEmpty(){
        return entities.isEmpty();
    }
    
    /**
     * Remove an entity from the quadtree
     * @param entity
     */
    public boolean remove(Entity entity){
    	
    	/* Try to remove the entity from this depth level */
    	if(entities != null && !entities.isEmpty()){		
    		entities.remove(entity);
    		return true;
    	}
    	
    	/* Search in the other quadtrees */
    	for(Quadtree node : nodes)  		
    		if(node.remove(entity)) 
    			return true;

    	/* If we didn't find anything */
    	return false;
    }
    
    /**
     * Recursively clear the quadtrees
     */
    public void clear() {
        entities.clear(); // Clear the actual tree

        for (int i = 0; i < nodes.length; i++) { // Loop throught the subtrees
          if (nodes[i] != null) {
            nodes[i].clear();
            nodes[i] = null;
          }
        }
    }
  
    /**
     * Split the node into four subnodes
     */
    private void split() {   
    	
        /* Get the data for calculating */
        int x = (int) bounds.getX(); // x
        int y = (int) bounds.getY(); // y
        int subWidth = (int)(bounds.getWidth() / 2); // width
        int subHeight = (int)(bounds.getHeight() / 2); // height

        /* Create four new quadtrees */
        nodes[0] = new Quadtree(level + 1, new Rectangle(x + subWidth, y, subWidth, subHeight)); // first
        nodes[1] = new Quadtree(level + 1, new Rectangle(x, y, subWidth, subHeight)); // second
        nodes[2] = new Quadtree(level + 1, new Rectangle(x, y + subHeight, subWidth, subHeight)); // third
        nodes[3] = new Quadtree(level + 1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight)); // fourth
    }

    /**
     * Get the bounds of the quadtree
     * @return
     */
    public Rectangle getBounds() {
		return bounds;
	}
     
    /** 
     * Set the bounds of the quadtree
     * @param bounds
     */
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	/**
     * Determine which node the object belongs to. 
     * -1 means object cannot completely fit within a child node 
     * and is part of the parent node
     * @param entity
     */
    private int getIndex(Entity entity) {
    	
    	/* Setup */
        int index = -1; // Default value
        
        /* Get the position component */
        Position position = entity.get(Position.class);

        /* Get the center */
        double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
        double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);

        // Entities can completely fit within the top quadrants or bottom quadrants */
        boolean topQuadrant = (position.vector.x < horizontalMidpoint && position.vector.y < horizontalMidpoint);
        boolean bottomQuadrant = (position.vector.y > horizontalMidpoint);

        // Entities can completely fit within the left quadrants
        if (position.vector.x < verticalMidpoint && position.vector.y < verticalMidpoint) {
        	
           if (topQuadrant) index = 1;
           else if (bottomQuadrant) index = 2;           
         }
         // Entities can completely fit within the right quadrants
         else if (position.vector.x > verticalMidpoint) {
        	 
          if (topQuadrant) index = 0;      
          else if (bottomQuadrant)  index = 3;   
        }

        return index;
    }
    
    /**
     * Check if the entity holds the required position component 
     * @param entity
     * @return
     */
    private boolean checkForPosition(Entity entity){
    	
    	Position position = entity.get(Position.class);   	
    	return position == null ? false : true;	
    }
    
}