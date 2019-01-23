package lwjgladapter.input;

import static org.lwjgl.glfw.GLFW.*;

public enum ButtonState {
	RELEASED(GLFW_RELEASE),
	PRESSED(GLFW_PRESS),
	DOWN(GLFW_REPEAT),
	UP(Integer.MIN_VALUE);
	
	private int glfwValue;
	
	private ButtonState(int glfwValue){
		this.glfwValue = glfwValue;
	}
	
	public static ButtonState fromGLFWAction(int action){
		for(ButtonState state : values()){
			if(state.glfwValue == action){
				return state;
			}
		}
		return UP;
	}
}
