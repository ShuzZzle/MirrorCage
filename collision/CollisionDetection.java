package collision;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * Detects a collision between two given shapes
 * @author Bengt, Marlo, Alexander, Niclas
 */
public class CollisionDetection {
    
    /**
     * Check the collision between the two shapes
     * @param shape1
     * @param shape2
     * @return 
     */
    public Boolean detect(Shape shape1, Shape shape2){
  
        return shape1.intersects(shape2);
    }  
    
    /**
     * Check if the shape is an instance of a circle
     * @param shape
     * @return 
     */
    public Boolean isCircle(Shape shape){
        
        if(shape instanceof Circle)
            return true;
        else
            return false;
    }
    
    /**
     * Check if the shape is an instance of a rectangle
     * @param shape
     * @return 
     */
    public Boolean isRectangle(Shape shape){
        
        if(shape instanceof Rectangle)
            return true;
        else
            return false;   
    }
       
}