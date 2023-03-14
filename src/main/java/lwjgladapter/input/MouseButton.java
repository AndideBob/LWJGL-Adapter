package lwjgladapter.input;

import static org.lwjgl.glfw.GLFW.*;

public enum MouseButton {
	NONE(Integer.MIN_VALUE),
	LEFT(GLFW_MOUSE_BUTTON_LEFT),
	RIGHT(GLFW_MOUSE_BUTTON_RIGHT),
	MIDDLE(GLFW_MOUSE_BUTTON_MIDDLE);
	
	private int glfwValue;
	
	private MouseButton(int glfwValue){
		this.glfwValue = glfwValue;
	}
	
	public static MouseButton fromGLFWButton(int mouseButton){
		for(MouseButton button : values()){
			if(button.glfwValue == mouseButton){
				return button;
			}
		}
		return NONE;
	}
}
