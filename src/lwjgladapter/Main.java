package lwjgladapter;

import lwjgladapter.datatypes.Color;

public class Main {

		public static void main(String[] args) {			
			GameWindow gameWindow = new GameWindow(640, 480, Color.BLACK, "The great Dreamer");
			
			gameWindow.run(null);
		}

}
