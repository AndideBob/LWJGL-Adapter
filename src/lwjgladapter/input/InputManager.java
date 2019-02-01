package lwjgladapter.input;

import java.util.HashMap;
import java.util.HashSet;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import lwjgladapter.input.gamepad.Gamepad;
import lwjgladapter.input.gamepad.GamepadAxis;
import lwjgladapter.input.gamepad.GamepadButton;
import lwjgladapter.input.gamepad.GamepadID;
import lwjgladapter.logging.Logger;

import static org.lwjgl.glfw.GLFW.*;

public class InputManager {

	public static InputManager instance = null;
	
	private double mousePositionX;
	private double mousePositionY;
	
	private HashMap<MouseButton,ButtonState> mouseButtons;
	private HashMap<KeyboardKey,ButtonState> keyboard;
	private HashSet<Gamepad> gamepads;
	
	public InputManager(long window) throws DublicateInputManagerException {
		if(instance == null){
			instance = this;
			mouseButtons = new HashMap<>();
			keyboard = new HashMap<>();
			gamepads = new HashSet<>();
			// Initialize Gamepads
			initializeGamepads();
			// Setup callbacks
			glfwSetKeyCallback(window, getKeyCallback());
			glfwSetMouseButtonCallback(window, getMouseCallback());
			glfwSetCursorPosCallback(window, getCursorPositionCallback());
		}
		else{
			throw new DublicateInputManagerException("InputManager is a Singleton Object and it can only be initialized once!");
		}
	}
	
	private void initializeGamepads(){
		for(GamepadID id : GamepadID.values()){
			String gamePadName = glfwGetJoystickName(id.getGlfwValue());
			if(gamePadName != null){
				Logger.log(id.toString() + " is plugged in as '" + gamePadName + "'.");
				gamepads.add(new Gamepad(id, gamePadName));
			}
			else{
				Logger.logDebug(id.toString() + " is not plugged in.");
			}
		}
	}
	
	// UPDATE
	
	public void update(){
		// Set ReleasedStates from last Frame to UP
		updateMouseStates();
		updateKeyStates();
		updateGamePads();
		// Poll new events
		glfwPollEvents();
	}
	
	private void updateMouseStates(){
		for(MouseButton button : mouseButtons.keySet()){
			if(mouseButtons.get(button).equals(ButtonState.RELEASED)){
				mouseButtons.put(button, ButtonState.UP);
			}
		}
	}
	
	private void updateKeyStates(){
		for(KeyboardKey button : keyboard.keySet()){
			if(keyboard.get(button).equals(ButtonState.RELEASED)){
				keyboard.put(button, ButtonState.UP);
			}
		}
	}
	
	private void updateGamePads(){
		for(Gamepad gamepad : gamepads){
			gamepad.update();
		}
	}
	
	// GETTERS
	
	public ButtonState getGamepadButtonState(GamepadID gamepadID, GamepadButton button){
		for(Gamepad gamepad : gamepads){
			if(gamepad.getID().equals(gamepadID)){
				return gamepad.getButtonState(button);
			}
		}
		Logger.logError("Trying to access button of not plugged in Gamepad(" + gamepadID.toString() + ")!");
		return ButtonState.UP;
	}
	
	public float getGamepadAxisState(GamepadID gamepadID, GamepadAxis axis){
		for(Gamepad gamepad : gamepads){
			if(gamepad.getID().equals(gamepadID)){
				return gamepad.getAxisState(axis);
			}
		}
		Logger.logError("Trying to access axis of not plugged in Gamepad(" + gamepadID.toString() + ")!");
		return 0f;
	}
	
	public ButtonState getKeyState(KeyboardKey key){
		if(keyboard.containsKey(key)){
			return keyboard.get(key);
		}
		keyboard.put(key, ButtonState.UP);
		return ButtonState.UP;
	}
	
	public ButtonState getMouseState(MouseButton key){
		if(mouseButtons.containsKey(key)){
			return mouseButtons.get(key);
		}
		mouseButtons.put(key, ButtonState.UP);
		return ButtonState.UP;
	}
	
	public double getMousePositionX(){
		return mousePositionX;
	}
	
	public double getMousePositionY(){
		return mousePositionY;
	}
	
	public int getMousePositionXAsInt(){
		return (int) Math.round(mousePositionX);
	}
	
	public int getMousePositionYAsInt(){
		return (int) Math.round(mousePositionY);
	}
	
	// SETTERS
		
	private void setMousePosition(double posX, double posY){
		mousePositionX = posX;
		mousePositionY = posY;
	}
	
	private void setMouseButton(int glfwValue, int action){
		MouseButton button = MouseButton.fromGLFWButton(glfwValue);
		if(button.equals(MouseButton.NONE)){
			return;
		}
		ButtonState newState = ButtonState.fromGLFWAction(action);
		mouseButtons.put(button, newState);
	}
	
	private void setKeyboard(int glfwValue, int action){
		KeyboardKey button = KeyboardKey.fromGLFWButton(glfwValue);
		if(button.equals(KeyboardKey.NONE)){
			return;
		}
		ButtonState newState = ButtonState.fromGLFWAction(action);
		keyboard.put(button, newState);
	}
	
	public GLFWKeyCallback getKeyCallback(){
		return new KeyCallback();
	}
	
	public GLFWMouseButtonCallback getMouseCallback(){
		return new MouseCallback();
	}
	
	public GLFWCursorPosCallback getCursorPositionCallback(){
		return new CursorPositionCallback();
	}
	
	private class KeyCallback extends GLFWKeyCallback{
		@Override
		public void invoke(long window, int key, int scancode, int action, int mods) {
			instance.setKeyboard(key, action);
		}
	}
	
	private class MouseCallback extends GLFWMouseButtonCallback{
		@Override
		public void invoke(long window, int button, int action, int mods) {
			instance.setMouseButton(button, action);
		}
	}
	
	private class CursorPositionCallback extends GLFWCursorPosCallback{
		@Override
		public void invoke(long window, double xpos, double ypos) {
			instance.setMousePosition(xpos, ypos);
		}
	}

}
