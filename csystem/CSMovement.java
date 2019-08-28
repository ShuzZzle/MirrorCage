package csystem;

import map.Chunk;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import component.Camera;
import component.CameraView;
import component.Collision;
import component.ComponentManager;
import component.Evade;
import component.Flee;
import component.KeyControl;
import component.Mass;
import component.Position;
import component.Pursuit;
import component.Seek;
import component.Steering;
import component.Teleport;
import component.Translation;
import component.Velocity;
import component.Wander;
import component.Zoom;
import core.BookOfKings;
import core.GameValues;
import entity.Entity;

/**
 * Movement system
 * Handles the movement of all entities like key movement or AI patterns like seek, flee, pursuit, etc.
 * 
 * Used components:
 *   Position
 *   Velocity
 *   Steering
 *   Mass
 *   Seek
 *   Flee
 *   Pursuit
 *   Evade
 *   Wander
 *   View
 *   Zoom
 *   Translation
 * 
 * Corresponding Tutsplus tutorials (sighted on 12.03.2014):
 *   http://gamedevelopment.tutsplus.com/tutorials/understanding-steering-behaviors-seek--gamedev-849
 *   http://gamedevelopment.tutsplus.com/tutorials/understanding-steering-behaviors-flee-and-arrival--gamedev-1303
 *   http://gamedevelopment.tutsplus.com/tutorials/understanding-steering-behaviors-pursuit-and-evade--gamedev-2946
 *   http://gamedevelopment.tutsplus.com/tutorials/understanding-steering-behaviors-movement-manager--gamedev-4278
 *   http://gamedevelopment.tutsplus.com/tutorials/understanding-steering-behaviors-wander--gamedev-1624
 *  
 *  @author Bengt, Marlo, Alexander, Niclas
 */
public class CSMovement extends CSystem {
	
	/* Attributes */
	private static CSMovement csMovement;
	private float cameraTranslationX, cameraTranslationY;
	
	/**
	 * Private Constructor
	 */
	private CSMovement(){
		
		cameraTranslationX = 0;
		cameraTranslationY = 0;
	}
	
	/**
	 * Get the instance
	 * @return
	 */
	public static CSMovement getInstance(){
		
		if(csMovement == null)
			csMovement = new CSMovement();
		
		return csMovement;
	}

	/**
	 * Update Method
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		/* Check if the current chunk exists */
		if(getCurrentChunk() == null) return;
		
		/* Perform the camera movement */
		Entity camera = ComponentManager.getInstance().getAllEntities(Camera.class).get(0); // get the camera
		performCameraMovement(camera, gc.getWidth(), gc.getHeight(), delta); // perform
		
		/* Loop through the entities and perform the different movement patterns */
		for(Entity entity : getCurrentChunk().getAllEntities()){

			performKeyMovement(entity, delta); // Mainly hero movement
			performAIMovement(entity, delta); // monster and NPC movement
		} 
			
	}
	
	/**
	 * Unused render method
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		// Nothing to do here 
	}
	
	/**
	 * Perform the AI movement
	 * For instance: seek, pursuit, flee, evade
	 * @param entity
	 * @param delta
	 */
	private void performAIMovement(Entity entity, int delta){
		
		/* Most if the following code lines presuppose these components */
    	Position position = entity.get(Position.class);
    	Velocity velocity = entity.get(Velocity.class);
    	Steering steering = entity.get(Steering.class);	
    	Mass mass = entity.get(Mass.class);	
    	Seek seek = entity.get(Seek.class);
    	Pursuit pursuit = entity.get(Pursuit.class);
    	Flee flee = entity.get(Flee.class);
    	Evade evade = entity.get(Evade.class);
    	Wander wander = entity.get(Wander.class);
    	Teleport teleport = entity.get(Teleport.class);
    	
    	/* These components are required! */
    	if(position == null || velocity == null || steering == null || mass == null) return;

    	/* Perform the AI behavior and calculate the new steering 
    	 * Note that the order matters. Entities which hold the seek and pursuit component
    	 * are not allowed to perform both movements, because they are nearly identical! 
    	 * Its just a precaution for stupid team members. Sorry :) */
    	
    	if(pursuit != null)  // pursuit the target
    		steering.vector = doPursuit(entity, pursuit.target, pursuit.slowingRadius);
    	
    	if(seek != null && pursuit == null) // seek the target
    		steering.vector = doSeek(entity, seek.targetPosition, seek.slowingRadius); 
    	 	
    	if(evade != null) // evade the target
    		steering.vector = doEvade(entity, evade.target, evade.speedingRadius);
    	
    	if(flee != null && evade == null) // flee from the target
    		steering.vector = doFlee(entity, flee.targetPosition, flee.speedingRadius); 
    	
    	if(wander != null) // wander around
    		steering.vector = doWander(entity);
    	
    	if(teleport != null) // teleport to another location
    		doTeleport(entity);
    	
    	/* Calculate the new velocity with the given steering */
    	velocity.vector.add(steering.vector);
    	velocity.vector = truncate(velocity.vector, velocity.max);
    	
    	/* Calculate the new position with the given velocity */
    	position.vector.add(velocity.vector.copy().scale(0.001f * delta));
    	
    	/* Check for collision */
    	if(getCurrentChunk().intersects(entity)) // on collision
    		position.vector.sub(velocity.vector.copy().scale(0.001f * delta)); // go back	
	}

	/**
	 * Perform the key movement
	 * @param entity
	 * @param delta
	 */
	private void performKeyMovement(Entity entity, int delta){
		
		/* Get the input */
		Input input = BookOfKings.game.getInput();
		
		/* Get the component */
		Position position = entity.get(Position.class);
		Velocity velocity = entity.get(Velocity.class);
		KeyControl keyControl = entity.get(KeyControl.class);
		Collision collision = entity.get(Collision.class);
		
		/* Check if the entity holds all required components */
		if(position == null || velocity == null || keyControl == null || collision == null) return;
		
		/* Check if a certain movement key is pressed */
		if(input.isKeyDown(GameValues.KEY_MOVE_UP) && keyControl.isEnabled(GameValues.KEY_MOVE_UP)) // up
			moveUp(entity, position.vector, velocity.vector, delta);
		
		if(input.isKeyDown(GameValues.KEY_MOVE_DOWN) && keyControl.isEnabled(GameValues.KEY_MOVE_DOWN))  // down
			moveDown(entity, position.vector, velocity.vector, delta);
		
		if(input.isKeyDown(GameValues.KEY_MOVE_RIGHT) && keyControl.isEnabled(GameValues.KEY_MOVE_RIGHT)) // right
			moveRight(entity, position.vector, velocity.vector, delta);
		
		if(input.isKeyDown(GameValues.KEY_MOVE_LEFT) && keyControl.isEnabled(GameValues.KEY_MOVE_LEFT)) // left 
			moveLeft(entity, position.vector, velocity.vector, delta);
	}
	
	/**
	 * Perform the camera movement
	 * @param entity
	 * @param screenWidth (in pixel)
	 * @param screenHeight (in pixel)
	 * @param delta
	 */
	private void performCameraMovement(Entity entity, float screenWidth, float screenHeight, int delta){
		
		/* Get the required components */
		Camera camera = entity.get(Camera.class);
		Position focus = entity.get(Position.class);
		CameraView view = entity.get(CameraView.class);
		Zoom zoom = entity.get(Zoom.class);
		Translation translation = entity.get(Translation.class);
		
		/* Get the current chunk */
		Chunk currentChunk = getCurrentChunk();
		if(currentChunk == null) return;
		
		/* Check if the camera holds all required components */
		if(camera == null || focus == null || view == null || zoom == null || translation == null) return;
		
		/* Set the width and height of the view. 
		 * It has the same proportions like the game window */
		view.width = screenWidth * GameValues.PIXEL_TO_TILE / zoom.value;
		view.height = screenHeight * GameValues.PIXEL_TO_TILE / zoom.value;
		
		/* Set the x and y value of the view. 
		 * The focus have to be centered in the view (except he exceeds the view) */
		view.x = focus.vector.x - view.width / 2 ;
		view.y = focus.vector.y - view.height / 2 ;
		
		/* Now we have to check if the focus exceed the view */
		Rectangle chunkBounds = currentChunk.getBounds();
		
		if(view.x < chunkBounds.getX()) 
			view.x = chunkBounds.getX();
		
		if(view.y < chunkBounds.getY()) 
			view.y = chunkBounds.getY();

		if(view.x  > chunkBounds.getX() + chunkBounds.getWidth() - view.width) 
			view.x = chunkBounds.getX() + chunkBounds.getWidth() - view.width;
		
		if(view.y > chunkBounds.getY() + chunkBounds.getHeight() - view.height) 
			view.y = chunkBounds.getY() + chunkBounds.getHeight() - view.height;
		
		/* Set the translation in pixel */
		cameraTranslationX = view.x * GameValues.TILE_TO_PIXEL * zoom.value;
		cameraTranslationY = view.y * GameValues.TILE_TO_PIXEL * zoom.value;
		translation.x = cameraTranslationX;
		translation.y = cameraTranslationY;
	}
		
	/**
	 * Move the entity up
	 * @param host
	 * @param position
	 * @param velocity
	 * @param delta
	 */
	private void moveUp(Entity host, Vector2f position, Vector2f velocity, int delta){
		
		position.y -= velocity.y * 0.001f * delta;
		
		if(getCurrentChunk().intersects(host)) 
			position.y += velocity.y * 0.001f * delta;
	}
	
	/**
	 * Move the entity down
	 * @param host
	 * @param position
	 * @param velocity
	 * @param delta
	 */
	private void moveDown(Entity host, Vector2f position, Vector2f velocity, int delta){
		
		position.y += velocity.y * 0.001f * delta;
		
		if(getCurrentChunk().intersects(host)) 
			position.y -= velocity.y * 0.001f * delta;
	}
	
	/**
	 * Move the entity right
	 * @param host
	 * @param position
	 * @param velocity
	 * @param delta
	 */
	private void moveRight(Entity host, Vector2f position, Vector2f velocity, int delta){
		
		position.x += velocity.x * 0.001f * delta;
		
		if(getCurrentChunk().intersects(host)) 
			position.x -= velocity.x * 0.001f * delta;
	}
	
	/**
	 * Move the entity left
	 * @param host
	 * @param position
	 * @param velocity
	 * @param delta
	 */
	private void moveLeft(Entity host, Vector2f position, Vector2f velocity, int delta){
		
		position.x -= velocity.x * 0.001f * delta;
		
		
		if(getCurrentChunk().intersects(host)) 
			position.x += velocity.x * 0.001f * delta;
	}
	
	/**
	 * Perform a seek AI movement
	 * @param host
	 * @param targetPosition
	 * @param slowingRadius
	 * @return
	 */
	private Vector2f doSeek(Entity host, Vector2f targetPosition, float slowingRadius){
		
    	/* Get the required components */
    	Position position = host.get(Position.class);
    	Velocity velocity = host.get(Velocity.class);
    	Steering steering = host.get(Steering.class);	
    	Mass mass = host.get(Mass.class);	
    	
    	/* Calculate the desired vector, then 
    	 * prepare the length of that vector. 
    	 * You have to get the distance in addition */
    	Vector2f desired = targetPosition.copy().sub(position.vector); // vector between the target and the host
    	float distance = desired.length(); // distance between the target and the host
    	desired.normalise(); // Normalize the vector for later calculation
    	
    	/* Slow down the host if he is in the slowing radius of the target */
    	if(distance <= slowingRadius) desired.scale(velocity.max * distance / slowingRadius);
    	else desired.scale(velocity.max);
    	
    	/* Calculate the new steering */
    	steering.vector = desired.copy().sub(velocity.vector);
    	steering.vector = truncate(steering.vector, steering.max);
    	steering.vector.scale( 1.0f / mass.value);

    	/* Return the steering vector */
		return steering.vector;
	}
	
	/**
	 * Perform a pursuit AI movement
	 * @param host
	 * @param target
	 * @param slowingRadius
	 * @return
	 */
	private Vector2f doPursuit(Entity host, Entity target, float slowingRadius){
		
    	/* Get the required components of the host */
    	Position position = host.get(Position.class);
    	Velocity velocity = host.get(Velocity.class);
    	Steering steering = host.get(Steering.class);	
    	
    	/* Get and check the required components of the target */
    	Position targetPosition = target.get(Position.class);
    	Velocity targetVelocity = target.get(Velocity.class);
    	if(targetPosition == null || targetVelocity == null) return steering.vector;
		
		/* Calculate the target future velocity */
		float distance = targetPosition.vector.distance(position.vector);
		float updatesNeeded = distance / velocity.max;
		Vector2f tv = targetVelocity.vector.copy();
		tv.scale(updatesNeeded);
		
		/* Calculate the future position */
		Vector2f targetFuturePosition = target.get(Position.class).vector.copy().add(tv);
		
		/* Calculate the steering */
		return doSeek(host, targetFuturePosition, slowingRadius);
	}
	
	/**
	 * Perform a flee AI movement
	 * @param host
	 * @param targetPosition
	 * @param speedingRadius
	 * @return
	 */
	private Vector2f doFlee(Entity host, Vector2f targetPosition, float speedingRadius){
		
    	/* Get the required components */
    	Position position = host.get(Position.class);
    	Velocity velocity = host.get(Velocity.class);
    	Steering steering = host.get(Steering.class);	
    	Mass mass = host.get(Mass.class);	
    	
    	/* Calculate the desired vector, then 
    	 * prepare the length of that vector. 
    	 * You have to get the distance in addition */
    	Vector2f desired = targetPosition.copy().sub(position.vector); // vector between the target and the host
    	float distance = desired.length(); // distance between the target and the host
    	desired.normalise(); // Normalize the vector for later calculation
    	
    	/* Slow down the host if he is in the slowing radius of the target */
    	if(distance > speedingRadius) desired.scale(0);
    	else desired.scale(velocity.max);
    	
    	/* Calculate the new steering */
    	steering.vector = desired.copy().sub(velocity.vector);
    	steering.vector = truncate(steering.vector, steering.max);
    	steering.vector.scale( 1.0f / mass.value);
    	
    	/* Return the steering vector */
		return steering.vector;
	}
	
	/**
	 * Perform an evade AI movement
	 * @param host
	 * @param target
	 * @param speedingRadius
	 * @return
	 */
	private Vector2f doEvade(Entity host, Entity target, float speedingRadius){
		
    	/* Get the required components of the host */
    	Position position = host.get(Position.class);
    	Velocity velocity = host.get(Velocity.class);
    	Steering steering = host.get(Steering.class);	
    	
    	/* Get and check the required components of the target */
    	Position targetPosition = target.get(Position.class);
    	Velocity targetVelocity = target.get(Velocity.class);
    	if(targetPosition == null || targetVelocity == null) return steering.vector;
		
		/* Calculate the target future velocity */
		float distance = targetPosition.vector.distance(position.vector);
		float updatesNeeded = distance / velocity.max;
		Vector2f tv = targetVelocity.vector.copy();
		tv.scale(updatesNeeded);
		
		/* Calculate the future position */
		Vector2f targetFuturePosition = target.get(Position.class).vector.copy().add(tv);
		
		/* Calculate the steering */
		return doFlee(host, targetFuturePosition, speedingRadius);
	}
	
	/**
	 * Perform the wander AI movement
	 * @param host
	 * @return
	 */
	private Vector2f doWander(Entity host){
		
    	/* Get the required components of the host */
    	Velocity velocity = host.get(Velocity.class);
    	Steering steering = host.get(Steering.class);
    	Wander wander = host.get(Wander.class);
		
    	/* Get the circle center */
		Vector2f circleCenter = velocity.vector.copy().normalise().scale(wander.circleDistance);
		
		/* Calculate the displacement */
		Vector2f displacement = new Vector2f(0,-1);
		displacement.scale(wander.circleRadius);
		displacement = setAngle(displacement, wander.wanderAngle);
		
		/* Change wanderAngle just a bit, so it
		 * won't have the same value in the  next game frame. */
		wander.wanderAngle += Math.random() * wander.angleChange - wander.angleChange * .5;
		
		/* Calculate the steering */
		circleCenter.add(displacement);
		steering.vector = circleCenter;
		
		/* Return the steering */
		return steering.vector;
	}
	
	/**
	 * Perform the teleport movement
	 * @param host
	 */
	private void doTeleport(Entity host){
		
		/* Get the required components */
		Position position = host.get(Position.class);
		Teleport teleport = host.get(Teleport.class);
		
		/* Check if we want to perform an teleport */
		if(teleport.perform == false) return;
		teleport.perform = false; // perform the teleport just once
		
		/* Set the new position */
		if(teleport.position == null) return;
		position.vector = teleport.position;
	}

    /**
     * Truncate the force if it exceeds the max force
     * @param force
     * @param maxForce
     */
	private Vector2f truncate(Vector2f force, float maxForce){
    	
    	if(maxForce <= 0) return force; // Not allowed to divide by zero
    	
    	if(force.length() > maxForce) 		
    		force.normalise().scale(maxForce);
    	
    	return force;
    }
	
	/**
	 * Set the angle of the current vector
	 * @param vector
	 * @param angle
	 * @return
	 */
	private Vector2f setAngle(Vector2f vector, float angle){
		
		vector = vector.copy(); // duplicate the vector
		float length = vector.length(); // get the vectors length
		
		vector.x = (float) (Math.cos(angle) * length); // x
		vector.y = (float) (Math.sin(angle) * length); // y
		
		return vector;
	}

}