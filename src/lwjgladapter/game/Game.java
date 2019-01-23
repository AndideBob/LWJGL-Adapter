package lwjgladapter.game;

public abstract class Game {
	
	protected boolean gameOver = false;

	public Game() {
		reset();
	}
	
	public final boolean isGameOver(){
		return gameOver;
	}
	
	protected void reset(){
		gameOver = false;
	}

	public abstract void loadResources();
	
	public abstract void update();
	
	public abstract void draw();
}
