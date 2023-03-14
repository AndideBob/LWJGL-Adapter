package lwjgladapter.input.gamepad;

import static org.lwjgl.glfw.GLFW.*;

public enum GamepadAxis {
	LEFT_X(GLFW_GAMEPAD_AXIS_LEFT_X),
	LEFT_Y(GLFW_GAMEPAD_AXIS_LEFT_Y),
	RIGHT_X(GLFW_GAMEPAD_AXIS_RIGHT_X),
	RIGHT_Y(GLFW_GAMEPAD_AXIS_RIGHT_Y),
	TRIGGER_LEFT(GLFW_GAMEPAD_AXIS_LEFT_TRIGGER),
	TRIGGER_RIGHT(GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER);

	private int glfwValue;
	
	private GamepadAxis(int glfwValue){
		this.glfwValue = glfwValue;
	}

	public int getGlfwValue() {
		return glfwValue;
	}
}
