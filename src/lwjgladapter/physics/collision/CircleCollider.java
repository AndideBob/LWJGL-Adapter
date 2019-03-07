package lwjgladapter.physics.collision;

import lwjgladapter.physics.PhysicsHelper;
import lwjgladapter.physics.collision.base.Collider;
import lwjgladapter.physics.collision.base.Collision;
import lwjgladapter.physics.collision.exceptions.CollisionNotSupportedException;

public class CircleCollider extends Collider {

	private float radius;
	
	private int positionX;
	private int positionY;
	
	public CircleCollider(float radius) {
		super(PhysicsHelper.getNextColliderID());
		this.radius = radius;
	}
	
	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPosition(int positionX, int positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
	}

	@Override
	public Collision getCollisionWith(Collider other) throws CollisionNotSupportedException{
		if(other instanceof CircleCollider){
			return intersectsWithCircle((CircleCollider)other);
		}
		if(other instanceof RectCollider){
			return intersectsWithRect((RectCollider)other);
		}
		return super.getCollisionWith(other);
	}

	private boolean intersectsWithPoint(int pointX, int pointY){
		int deltaX = getPositionX() - pointX;
		int deltaY = getPositionY() - pointY;
		return (Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) < Math.pow(radius, 2);
	}
	
	private Collision intersectsWithCircle(CircleCollider other){
		double xDiff = other.getPositionX() - getPositionX();
		double yDiff = other.getPositionY() - getPositionY();
		double distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
		double radiusDistance = getRadius() + other.getRadius();
		if(distance <= radiusDistance){
			double differenceFactor = (getRadius() - ((radiusDistance - distance) / 2)) / getRadius();
			int x = getPositionX() + (int) Math.round(xDiff * differenceFactor);
			int y = getPositionY() + (int) Math.round(yDiff * differenceFactor);
			return new Collision(PhysicsHelper.generateCollisionKey(this, other), x, y);
		}
		return null;
	}

	private Collision intersectsWithRect(RectCollider other) {
		int nearestX = Math.max(other.getPositionX(), Math.min(positionX, other.getPositionX() + other.getWidth()));
		int nearestY = Math.max(other.getPositionY(), Math.min(positionY, other.getPositionY() + other.getHeight()));
		if(intersectsWithPoint(nearestX, nearestY)){
			return new Collision(PhysicsHelper.generateCollisionKey(this, other), nearestX, nearestY);
		}
		return null;
	}
}
