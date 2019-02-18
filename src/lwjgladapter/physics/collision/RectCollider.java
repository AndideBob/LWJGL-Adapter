package lwjgladapter.physics.collision;

import lwjgladapter.physics.collision.base.Collider;
import lwjgladapter.physics.collision.exceptions.CollisionNotSupportedException;

public class RectCollider extends Collider {

	private int positionX;
	private int positionY;
	private int width;
	private int height;
	
	public RectCollider(int positionX, int positionY, int width, int height) {
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
	public boolean intersects(Collider other) throws CollisionNotSupportedException{
		if(other instanceof RectCollider){
			return intersectsWithRect((RectCollider)other);
		}
		return super.intersects(other);
	}

	private boolean intersectsWithRect(RectCollider other){
		boolean checkA = getPositionX() < other.getPositionX() + other.getWidth();
		boolean checkB = getPositionX() + getWidth() > other.getPositionX();
		boolean checkC = getPositionY() < other.getPositionY() + other.getHeight();
		boolean checkD = getPositionY() + getHeight() > other.getPositionY();
		return checkA && checkB && checkC && checkD;
	}

	
}
