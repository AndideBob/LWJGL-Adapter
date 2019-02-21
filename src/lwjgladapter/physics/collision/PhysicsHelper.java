package lwjgladapter.physics.collision;

import java.util.HashMap;

import lwjgladapter.logging.Logger;
import lwjgladapter.physics.collision.base.Collider;
import lwjgladapter.physics.collision.base.CollisionKey;
import lwjgladapter.physics.collision.exceptions.CollisionNotSupportedException;
import lwjgladapter.physics.collision.exceptions.DublicatePhysicsHelperException;

public class PhysicsHelper {
	
	private static PhysicsHelper instance = null;

	private Long colliderCounter;
	private HashMap<CollisionKey, Boolean> cachedCollisions = new HashMap<>();
	
	private PhysicsHelper() throws DublicatePhysicsHelperException{
		if(instance == null){
			instance = this;
			colliderCounter = Long.MIN_VALUE;
		}
		else{
			throw new DublicatePhysicsHelperException("PhysicsHelper is a Singleton Object and it can only be initialized once!");
		}
	}
	
	public static PhysicsHelper getInstance(){
		if(instance == null){
			try {
				return new PhysicsHelper();
			} catch (DublicatePhysicsHelperException e) {
				Logger.logError(e);
			}
		}
		return instance;
	}
	
	private long getNextColliderKey(){
		long value = colliderCounter;
		colliderCounter = (colliderCounter + 1) % Long.MAX_VALUE;
		return value;
	}
	
	/**
	 * Clears the cache for collisions. This should be called on each iteration of the game.
	 */
	
	public void resetCollisions(){
		cachedCollisions.clear();
	}
	
	/**
	 * This method is used to determine whether two colliders are intersecting. Collisions are being cached
	 * and need to be reset using {@link #resetCollisions() resetCollisions}.
	 * Collisions are being checked commutatively which means the order in which the parameters are passed in
	 * do not matter.
	 * 
	 * @param colliderA The first collider.
	 * @param colliderB The second collider.
	 * @return Return whether the two colliders are intersecting.
	 * @throws CollisionNotSupportedException This exception is thrown if neither collider implements checking for an intersection
	 * with the other
	 */
	
	public boolean checkCollisionBetween(Collider colliderA, Collider colliderB) throws CollisionNotSupportedException{
		CollisionKey key = generateCollisionKey(colliderA, colliderB);
		if(cachedCollisions.containsKey(key)){
			return cachedCollisions.get(key);
		}
		Boolean collision = Boolean.FALSE;
		try{
			collision = colliderA.intersects(colliderB);
		}
		catch(CollisionNotSupportedException e){// Try checking collision in reverse for missing Implementations
			collision = colliderB.intersects(colliderA);
		}
		cachedCollisions.put(key, collision);
		return collision;
	}

	private CollisionKey generateCollisionKey(Collider colliderA, Collider colliderB){
		if(!colliderA.wasKeySet()){
			colliderA.setKey(getNextColliderKey());
		}
		if(!colliderB.wasKeySet()){
			colliderB.setKey(getNextColliderKey());
		}
		return new CollisionKey(colliderA.getKey(), colliderB.getKey());
	}
}
