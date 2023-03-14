package lwjgladapter.logging;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;

public class LoggedErrorCallback implements GLFWErrorCallbackI{

	@Override
	public void invoke(int error, long description) {
		String descriptionString = GLFWErrorCallback.getDescription(description);
		Logger.logError(descriptionString);
	}

}
