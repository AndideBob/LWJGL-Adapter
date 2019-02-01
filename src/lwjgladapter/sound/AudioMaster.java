package lwjgladapter.sound;

import org.lwjgl.openal.*;

import lwjgladapter.logging.Logger;

import static org.lwjgl.openal.ALC10.*;

import java.util.HashMap;

public class AudioMaster {

	public static AudioMaster instance = null;
	
	private long device;
	private long context;
	
	private HashMap<Object, Sound> soundLibrary;
	
	public AudioMaster() throws DublicateAudioMasterException{
		if(instance == null){
			instance = this;
			soundLibrary = new HashMap<>();
			init();
		}
		else{
			throw new DublicateAudioMasterException("InputManager is a Singleton Object and it can only be initialized once!");
		}
	}
	
	private void init(){
		String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
		device = alcOpenDevice(defaultDeviceName);
		
		int[] attributes = {0};
		context = alcCreateContext(device, attributes);
		alcMakeContextCurrent(context);
		
		ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
		ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);
		
		if(!alCapabilities.OpenAL10) {
		    //OpenAL 1.0 is not supported
		}
	}
	
	public void loadSound(Object key, String filePath){
		if(soundLibrary.containsKey(key)){
			Logger.log("Sound for " + key.toString() + " already exists and will be overwritten!");
			soundLibrary.get(key).unload();
			soundLibrary.remove(key);
		}
		Sound newSound = new Sound(filePath);
		soundLibrary.put(key, newSound);
	}
	
	public void playSound(Object key){
		if(soundLibrary.containsKey(key)){
			soundLibrary.get(key).play();
		}
		else{
			Logger.logError("Sound " + key.toString() + " does not exist in Memory!");
		}
	}
	
	public void destroy(){
		for(Sound sound : soundLibrary.values()){
			sound.unload();
		}
		alcDestroyContext(context);
		alcCloseDevice(device);
	}

}
