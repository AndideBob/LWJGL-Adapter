package lwjgladapter.physics.raycasting;

public class Line {
	
	private int originX;
	private int originY;
	
	private int destinationX;
	private int destinationY;
	
	private double length;
	
	public Line(int originX, int originY, int destinationX, int destinationY) {
		this.originX = originX;
		this.originY = originY;
		this.destinationX = destinationX;
		this.destinationY = destinationY;
		recalculateLength();
	}

	public void setOrigin(int originX, int originY) {
		this.originX = originX;
		this.originY = originY;
		recalculateLength();
	}
	
	public int getOriginX() {
		return originX;
	}

	public int getOriginY() {
		return originY;
	}

	public void setDestination(int destinationX, int destinationY) {
		this.destinationX = destinationX;
		this.destinationY = destinationY;
		recalculateLength();
	}

	public int getDestinationX() {
		return destinationX;
	}
	
	public int getDestinationY() {
		return destinationY;
	}
	
	public double getLenth(){
		return length;
	}

	private void recalculateLength(){
		length = Math.sqrt(Math.pow(1f * destinationX - originX, 2f) + Math.pow(1f * destinationY - originY, 2f));
	}
}
