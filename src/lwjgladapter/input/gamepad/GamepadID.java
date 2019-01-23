package lwjgladapter.input.gamepad;

import static org.lwjgl.glfw.GLFW.*;

public enum GamepadID {
	GAMEPAD_1(GLFW_JOYSTICK_1),
	GAMEPAD_2(GLFW_JOYSTICK_2),
	GAMEPAD_3(GLFW_JOYSTICK_3),
	GAMEPAD_4(GLFW_JOYSTICK_4),
	GAMEPAD_5(GLFW_JOYSTICK_5),
	GAMEPAD_6(GLFW_JOYSTICK_6),
	GAMEPAD_7(GLFW_JOYSTICK_7),
	GAMEPAD_8(GLFW_JOYSTICK_8),
	GAMEPAD_9(GLFW_JOYSTICK_9),
	GAMEPAD_10(GLFW_JOYSTICK_10),
	GAMEPAD_11(GLFW_JOYSTICK_11),
	GAMEPAD_12(GLFW_JOYSTICK_12),
	GAMEPAD_13(GLFW_JOYSTICK_13),
	GAMEPAD_14(GLFW_JOYSTICK_14),
	GAMEPAD_15(GLFW_JOYSTICK_15),
	GAMEPAD_16(GLFW_JOYSTICK_16);

	private int glfwValue;
	
	private GamepadID(int glfwValue){
		this.glfwValue = glfwValue;
	}

	public int getGlfwValue() {
		return glfwValue;
	}
}
