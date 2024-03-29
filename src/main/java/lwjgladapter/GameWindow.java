package lwjgladapter;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import lwjgladapter.datatypes.Color;
import lwjgladapter.datatypes.LWJGLAdapterException;
import lwjgladapter.game.Game;
import lwjgladapter.input.InputManager;
import lwjgladapter.logging.LoggedErrorCallback;
import lwjgladapter.logging.Logger;
import lwjgladapter.sound.AudioMaster;

public class GameWindow {

	private long window;
	private Game game;
	private InputManager input;
	private AudioMaster audio;
	
	private int windowSizeX;
	private int windowSizeY;
	private String windowTitle;
	private Color clearColor;
	
	
	private boolean initialized;
	private long deltaTime;
	private long lastUpdateTime;
	private int desiredFPS;
	private long lastTime;
	private long secondCounter;
	private long fpsCounter;
	private long variableYieldTime;
	
	public GameWindow(int sizeX, int sizeY, Color clearColor, String title, int desiredFPS) {
		windowSizeX = sizeX;
		windowSizeY = sizeY;
		GameWindowConstants.setSCREEN_WIDTH(sizeX);
		GameWindowConstants.setSCREEN_HEIGHT(sizeY);
		windowTitle = title;
		initialized = false;
		this.clearColor = clearColor;
		this.desiredFPS = desiredFPS;
		lastUpdateTime = System.currentTimeMillis();
		secondCounter = 1000l;
		fpsCounter = 1;
	}
	
	public void run(Game game) {
		try{
			Logger.log("Application started with LWJGL v." + Version.getVersion() + "!");
			init(game);
			loop();
		}
		catch(Exception e){
			Logger.logError(e);
		}
		finally{
			// Free Sound Memory
			audio.destroy();
			
			// Free the window callbacks and destroy the window
			glfwFreeCallbacks(window);
			glfwDestroyWindow(window);

			// Terminate GLFW and free the error callback
			glfwTerminate();
			glfwSetErrorCallback(null).free();
			Logger.free();
		}
	}
	
	private void init(Game game) throws Exception {
		glfwSetErrorCallback(GLFWErrorCallback.create(new LoggedErrorCallback()));

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(windowSizeX, windowSizeY, windowTitle, NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup Input manager
		input = new InputManager(window);
		// Setup Audio Master
		audio = new AudioMaster();

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
		
		this.game = game;
		
		initialized = true;
	}
	
	private void loop() throws InterruptedException {
		if(!initialized){
			throw new IllegalStateException("Game Window must be initialized before running!");
		}
		try{
			// This line is critical for LWJGL's interoperation with GLFW's
			// OpenGL context, or any context that is managed externally.
			// LWJGL detects the context that is current in the current thread,
			// creates the GLCapabilities instance and makes the OpenGL
			// bindings available for use.
			GL.createCapabilities();
			
			glEnable(GL_TEXTURE_2D);
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			
			game.loadResources();
	
			// Set the clear color
			glClearColor(clearColor.getRed(), clearColor.getGreen(), clearColor.getBlue(), clearColor.getAlpha());
	
			// Run the rendering loop until the user has attempted to close
			// the window or has pressed the ESCAPE key.
			while ( !glfwWindowShouldClose(window) ) {
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
				
				game.draw();
	
				glfwSwapBuffers(window); // swap the color buffers
	
				// Poll for window events. The key callback above will only be
				// invoked during this call.
				input.update();
				
				updateTime();
				game.update(deltaTime);
				sync(desiredFPS);
				
				if(game.isGameOver()){
					glfwSetWindowShouldClose(window, true);
				}
			}
		}
		catch(LWJGLAdapterException e){
			Logger.logError(e);
			glfwSetWindowShouldClose(window, true);
		}
	}
	
	private void updateTime() throws InterruptedException{
		long currentTime = System.currentTimeMillis();
		deltaTime = currentTime - lastUpdateTime;
		lastUpdateTime = currentTime;
		secondCounter -= deltaTime;
		if(secondCounter <= 0){
			secondCounter += 1000l;
			GameWindowConstants.setCURRENT_FPS(fpsCounter);
			fpsCounter = 1;
		}
		else{
			fpsCounter++;
		}
	}
	
	/**
     * An accurate sync method that adapts automatically
     * to the system it runs on to provide reliable results.
     *
     * @param fps The desired frame rate, in frames per second
     * @author kappa (On the LWJGL Forums)
     */
    private void sync(int fps) {
        if (fps <= 0) return;
         
        long sleepTime = 1000000000 / fps; // nanoseconds to sleep this frame
        // yieldTime + remainder micro & nano seconds if smaller than sleepTime
        long yieldTime = Math.min(sleepTime, variableYieldTime + sleepTime % (1000*1000));
        long overSleep = 0; // time the sync goes over by
         
        try {
            while (true) {
                long t = System.nanoTime() - lastTime;
                 
                if (t < sleepTime - yieldTime) {
                    Thread.sleep(1);
                }else if (t < sleepTime) {
                    // burn the last few CPU cycles to ensure accuracy
                    Thread.yield();
                }else {
                    overSleep = t - sleepTime;
                    break; // exit while loop
                }
            }
        } catch (InterruptedException e) {
        	e.printStackTrace();
        }finally{
        	lastTime = System.nanoTime() - Math.min(overSleep, sleepTime);
           
            // auto tune the time sync should yield
            if (overSleep > variableYieldTime) {
                // increase by 200 microseconds (1/5 a ms)
                variableYieldTime = Math.min(variableYieldTime + 200*1000, sleepTime);
            }
            else if (overSleep < variableYieldTime - 200*1000) {
                // decrease by 2 microseconds
                variableYieldTime = Math.max(variableYieldTime - 2*1000, 0);
            }
        }
    }

}
