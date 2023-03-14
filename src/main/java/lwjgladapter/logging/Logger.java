package lwjgladapter.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Logger {

	private static final String PREFIX_ERROR = "ERROR: ";
	private static final String PREFIX_GAME = "GAME_: ";
	private static final String PREFIX_DEBUG = "DEBUG: ";
	
	private static BufferedWriter fileWriter; 
	
	private static boolean logFileInitialized = false;
	
	public static void initializeLogFile(File file) throws FileNotFoundException{
		fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		logFileInitialized = true;
	}
	
	public static void free() {
		if(logFileInitialized){
			logFileInitialized = false;
			try{
				fileWriter.close();
			}
			catch(IOException e){
				logError(e);
			}
		}
	}
	
	public static void logError(String message){
		logInternal(PREFIX_ERROR + message);
	}
	
	public static void logError(Exception e){
		Throwable throwable = e.getCause();
		String text = e.getClass().getName() + ":"; 
		text += (throwable != null ? throwable.toString() : "") + e.getMessage();
		for(StackTraceElement element : e.getStackTrace()){
			text += "\n" + element.toString();
		}
		logInternal(PREFIX_ERROR + text);
	}
	
	public static void log(String message){
		logInternal(PREFIX_GAME + message);
	}
	
	public static void logDebug(String message){
		logInternal(PREFIX_DEBUG + message);
	}
	
	private static void logInternal(String message){
		if(logFileInitialized && fileWriter != null){
			try {
				fileWriter.write(message + "\n");
			} catch (IOException e) {
				logFileInitialized = false;
				logError(e);
			}
		}
		System.out.println(message);
	}

}
