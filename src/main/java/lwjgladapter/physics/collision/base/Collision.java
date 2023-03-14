package lwjgladapter.physics.collision.base;

import lombok.Getter;
import lwjgladapter.maths.vectors.Vector2;

@Getter
public class Collision {

	private CollisionKey key;

	private Vector2 position;

	public Collision(CollisionKey key, int positionX, int positionY) {
		this.key = key;
		position = new Vector2(positionX, positionY);
	}

	public long getOtherID(long originalColliderID) {
		if(key.objectAID() == originalColliderID){
			return key.objectBID();
		}
		return key.objectAID();
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
			return other.key == null;
		} else return key.equals(other.key);
	}
}
