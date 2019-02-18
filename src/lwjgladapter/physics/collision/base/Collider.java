package lwjgladapter.physics.collision.base;

import lwjgladapter.physics.collision.exceptions.CollisionNotSupportedException;

public abstract class Collider {
	
	private long key;
	private boolean wasKeySet = false;
	
	/**
	 * This method should only be called by the {@link lwjgladapter.physics.collision.PhysicsHelper#generateCollisionKey(Collider, Collider) PhysicsHelper}
	 * 
	 * @param key They key used to identify this collider.
	 */
	
	public final void setKey(long key){
		if(!wasKeySet){
			this.key = key;
			wasKeySet = true;
		}
	}
	
	public boolean wasKeySet(){
		return wasKeySet;
	}
	
	public long getKey(){
		return key;
	}

	public boolean intersects(Collider other) throws CollisionNotSupportedException{
		throw new CollisionNotSupportedException("No collision defined between " + this.getClass().getName() + " and " + other.getClass().getName() + "!");
	}
	
}
