package lwjgladapter.physics.collision;

import lwjgladapter.physics.collision.base.Collider;
import lwjgladapter.physics.collision.exceptions.CollisionNotSupportedException;

public class CircleCollider extends Collider {

	private float radius;
	
	private int positionX;
	private int positionY;
	
	public CircleCollider(float radius) {
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
	public boolean intersects(Collider other) throws CollisionNotSupportedException{
		if(other instanceof CircleCollider){
			return intersectsWithCircle((CircleCollider)other);
		}
		if(other instanceof RectCollider){
			return intersectsWithRect((RectCollider)other);
		}
		return super.intersects(other);
	}

	private boolean intersectsWithPoint(int pointX, int pointY){
		int deltaX = getPositionX() - pointX;
		int deltaY = getPositionY() - pointY;
		return (Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) < Math.pow(radius, 2);
	}
	
	private boolean intersectsWithCircle(CircleCollider other){
		double xDiff = Math.pow((other.getPositionX() - getPositionX()), 2);
		double yDiff = Math.pow((other.getPositionY() - getPositionY()), 2);
		double distance = Math.sqrt(xDiff + yDiff);
		double radiusDistance = getRadius() + other.getRadius();
		return distance <= radiusDistance;
	}

	private boolean intersectsWithRect(RectCollider other) {
		int nearestX = Math.max(other.getPositionX(), Math.min(positionX, other.getPositionX() + other.getWidth()));
		int nearestY = Math.max(other.getPositionY(), Math.min(positionY, other.getPositionY() + other.getHeight()));
		return intersectsWithPoint(nearestX, nearestY);
	}
}
