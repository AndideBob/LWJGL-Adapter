package lwjgladapter.game;

import lwjgladapter.datatypes.LWJGLAdapterException;

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

	public abstract void loadResources() throws LWJGLAdapterException;
	
	public abstract void update(long deltaTimeInMs) throws LWJGLAdapterException;
	
	public abstract void draw() throws LWJGLAdapterException;
}
