package lwjgladapter;

public class GameWindowConstants {

	public static int DEFAULT_SCREEN_WIDTH = 640;
	public static int DEFAULT_SCREEN_HEIGHT = 360;
	
	private static int SCREEN_WIDTH;
	private static int SCREEN_HEIGHT;
	
	private static float SCALE_FACTOR_X;
	private static float SCALE_FACTOR_Y;
	
	private static long CURRENT_FPS;
	
	public static final String FILEPATH_DIRECTORY = System.getProperty("user.dir");
	
	static{
		setSCREEN_WIDTH(DEFAULT_SCREEN_WIDTH);
		setSCREEN_HEIGHT(DEFAULT_SCREEN_HEIGHT);
	}
	
	public static int getSCREEN_WIDTH() {
		return SCREEN_WIDTH;
	}

	public static void setSCREEN_WIDTH(int amount) {
		SCREEN_WIDTH = amount;
		SCALE_FACTOR_X = 1f * SCREEN_WIDTH / DEFAULT_SCREEN_WIDTH;
	}

	public static int getSCREEN_HEIGHT() {
		return SCREEN_HEIGHT;
	}

	public static void setSCREEN_HEIGHT(int amount) {
		SCREEN_HEIGHT = amount;
		SCALE_FACTOR_Y = 1f * SCREEN_HEIGHT / DEFAULT_SCREEN_HEIGHT;
	}

	public static float getSCALE_FACTOR_X() {
		return SCALE_FACTOR_X;
	}

	public static float getSCALE_FACTOR_Y() {
		return SCALE_FACTOR_Y;
	}

	public static long getCURRENT_FPS() {
		return CURRENT_FPS;
	}

	public static void setCURRENT_FPS(long cURRENT_FPS) {
		CURRENT_FPS = cURRENT_FPS;
	}
}
