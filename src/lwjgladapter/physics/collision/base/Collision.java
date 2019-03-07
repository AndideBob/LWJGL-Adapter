package lwjgladapter.physics.collision.base;

public class Collision {

	private CollisionKey key;
	
	int positionX;
	
	int positionY;

	public Collision(CollisionKey key, int positionX, int positionY) {
		this.key = key;
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	public CollisionKey getKey() {
		return key;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public long getOtherID(long originalColliderID) {
		if(key.getObjectAID() == originalColliderID){
			return key.getObjectBID();
		}
		return key.getObjectAID();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Collision other = (Collision) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
}
