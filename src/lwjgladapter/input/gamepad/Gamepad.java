package lwjgladapter.input.gamepad;


import static org.lwjgl.glfw.GLFW.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashMap;

import lwjgladapter.input.ButtonState;

public class Gamepad {

	private GamepadID id;
	
	private String name;
	
	private HashMap<GamepadButton, ButtonState> buttons;
	
	private HashMap<GamepadAxis, Float> axis;
	
	public Gamepad(GamepadID id, String name) {
		this.id = id;
		this.name = name;
		buttons = new HashMap<>();
		axis = new HashMap<>();
	}

	public GamepadID getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void update() {
		updateAxis();
		updateButtons();
	}
	
	private void updateAxis(){
		FloatBuffer buffer = glfwGetJoystickAxes(id.getGlfwValue());
		if(buffer != null){
			float[] axisArray = buffer.array();
			for(int i = 0; i < axisArray.length; i++){
				for(GamepadAxis gpa : GamepadAxis.values()){
					if(i == gpa.getGlfwValue()){
						axis.put(gpa, axisArray[i]);
						break;
					}
				}
			}
		}
	}
	
	private void updateButtons(){
		ByteBuffer buffer = glfwGetJoystickButtons(id.getGlfwValue());
		if(buffer != null){
			byte[] buttonArray = buffer.array();
			for(int i = 0; i < buttonArray.length; i++){
				for(GamepadButton gpb : GamepadButton.values()){
					if(i == gpb.getGlfwValue()){
						ButtonState oldState = getButtonState(gpb);
						buttons.put(gpb, getNextState(oldState, buttonArray[i]));
						break;
					}
				}
			}
		}
	}
	
	private ButtonState getNextState(ButtonState oldState, byte pressed){
		switch(oldState){
		case DOWN:
			return pressed == 1 ? ButtonState.DOWN : ButtonState.RELEASED;
		case PRESSED:
			return pressed == 1 ? ButtonState.DOWN : ButtonState.RELEASED;
		case RELEASED:
			return pressed == 1 ? ButtonState.PRESSED : ButtonState.UP;
		case UP:
			return pressed == 1 ? ButtonState.PRESSED : ButtonState.UP;
		}
		return ButtonState.UP;
	}

	public ButtonState getButtonState(GamepadButton button) {
		// TODO Auto-generated method stub
		return null;
	}

	public float getAxisState(GamepadAxis axis2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
