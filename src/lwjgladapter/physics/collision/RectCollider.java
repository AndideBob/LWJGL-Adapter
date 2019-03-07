package lwjgladapter.physics.collision;

import lwjgladapter.physics.PhysicsHelper;
import lwjgladapter.physics.collision.base.Collider;
import lwjgladapter.physics.collision.base.Collision;
import lwjgladapter.physics.collision.exceptions.CollisionNotSupportedException;

public class RectCollider extends Collider {

	private int positionX;
	private int positionY;
	private int width;
	private int height;
	
	public RectCollider(int positionX, int positionY, int width, int height) {
		super(PhysicsHelper.getNextColliderID());
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public Collision getCollisionWith(Collider other) throws CollisionNotSupportedException{
		if(other instanceof RectCollider){
			return intersectsWithRect((RectCollider)other);
		}
		return super.getCollisionWith(other);
	}

	private Collision intersectsWithRect(RectCollider other){
		int intersectionXLeft = Math.max(getPositionX(), other.getPositionX());
		int intersectionYBottom = Math.max(getPositionY(), other.getPositionY());
		int intersectionXRight = Math.min(getPositionX() + getWidth(), other.getPositionX() + other.getWidth());
		int intersectionYTop = Math.min(getPositionY() + getHeight(), other.getPositionY() + other.getHeight());
		if(intersectionXLeft < intersectionXRight && intersectionYBottom < intersectionYTop){
			int intersectionWidth = intersectionXRight - intersectionXLeft;
			int intersectionHeight = intersectionYBottom - intersectionYTop;
			int centerX = intersectionXLeft + (int) Math.round(intersectionWidth / 2);
			int centerY = intersectionYTop + (int) Math.round(intersectionHeight / 2);
			return new Collision(PhysicsHelper.generateCollisionKey(this, other), centerX, centerY);
		}
		return null;
	}

	
}
