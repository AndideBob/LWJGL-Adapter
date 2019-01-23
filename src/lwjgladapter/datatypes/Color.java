package lwjgladapter.datatypes;

public class Color {
	
	public static final Color RED = new Color(1f,0f,0f,0f);
	public static final Color GREEN = new Color(0f,1f,0f,0f);
	public static final Color BLUE = new Color(0f,0f,1f,0f);
	public static final Color WHITE = new Color(1f,1f,1f,0f);
	public static final Color BLACK = new Color(0f,0f,0f,0f);

	private float red;
	private float green;
	private float blue;
	private float alpha;
	
	public Color(float red, float green, float blue, float alpha) {
		this.red = clampColorValue(red);
		this.green = clampColorValue(green);
		this.blue = clampColorValue(blue);
		this.alpha = clampColorValue(alpha);
	}

	private float clampColorValue(float value) {
		if(value < 0f){
			return 0f;
		}
		if(value > 1f){
			return 1f;
		}
		return value;
	}

	public float getRed() {
		return red;
	}

	public float getGreen() {
		return green;
	}

	public float getBlue() {
		return blue;
	}

	public float getAlpha() {
		return alpha;
	}
}
