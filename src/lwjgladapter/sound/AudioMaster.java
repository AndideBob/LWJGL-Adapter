package lwjgladapter.sound;

import org.lwjgl.openal.*;

import lwjgladapter.logging.Logger;
import lwjgladapter.sound.exceptions.AudioNotInMemoryException;
import lwjgladapter.sound.exceptions.DublicateAudioMasterException;
import lwjgladapter.sound.exceptions.IllegalSoundValueException;

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
	
	public void loadSingleInstanceSound(Object key, String filePath){
		checkIfNewSoundIsLoadedAlready(key);
		//Add differentiation between Single and Multi-InstanceSounds
		Sound newSound = new SingleInstanceSound(key.toString(), filePath);
		soundLibrary.put(key, newSound);
	}
	
	public void loadMultiInstanceSound(Object key, String filePath, int maxNumberOfInstances){
		checkIfNewSoundIsLoadedAlready(key);
		//Add differentiation between Single and Multi-InstanceSounds
		Sound newSound = new MultiInstanceSound(key.toString(), filePath, maxNumberOfInstances);
		soundLibrary.put(key, newSound);
	}
	
	private void checkIfNewSoundIsLoadedAlready(Object key){
		if(soundLibrary.containsKey(key)){
			Logger.log("Sound for " + key.toString() + " already exists and will be overwritten!");
			try{
				soundLibrary.get(key).unload();
			}
			catch(AudioNotInMemoryException e){
				Logger.logError("Error unloading " + key.toString() + ": " + e.getMessage());
			}
			soundLibrary.remove(key);
		}
	}
	
	public void manageSound(Object key, boolean looping, float volume, float pitch) throws AudioNotInMemoryException, IllegalSoundValueException{
		if(soundLibrary.containsKey(key)){
			Sound sound = soundLibrary.get(key);
			sound.setLooping(looping);
			sound.setPitch(pitch);
			sound.setGain(volume);
			if(sound instanceof SingleInstanceSound){
				if(sound.isPlaying()){
					sound.pause();
					sound.play();
				}
			}
		}
		else{
			throw new AudioNotInMemoryException("Sound " + key.toString() + " does not exist in Memory!");
		}
	}
	
	public void playSound(Object key) throws AudioNotInMemoryException{
		if(soundLibrary.containsKey(key)){
			soundLibrary.get(key).play();
		}
		else{
			throw new AudioNotInMemoryException("Sound " + key.toString() + " does not exist in Memory!");			
		}
	}
	
	public void pauseSound(Object key) throws AudioNotInMemoryException {
		if(soundLibrary.containsKey(key)){
			soundLibrary.get(key).pause();
		}
		else{
			throw new AudioNotInMemoryException("Sound " + key.toString() + " does not exist in Memory!");
		}
	}
	
	public void stopSound(Object key) throws AudioNotInMemoryException {
		if(soundLibrary.containsKey(key)){
			soundLibrary.get(key).stop();
		}
		else{
			throw new AudioNotInMemoryException("Sound " + key.toString() + " does not exist in Memory!");
		}
	}
	
	public void stopAllSounds(Object key) throws AudioNotInMemoryException {
		if(soundLibrary.containsKey(key)){
			if(soundLibrary.get(key) instanceof MultiInstanceSound){
				MultiInstanceSound sound = (MultiInstanceSound)soundLibrary.get(key);
				sound.stopAll();
			}
			else{
				stopSound(key);
			}
		}
		else{
			throw new AudioNotInMemoryException("Sound " + key.toString() + " does not exist in Memory!");
		}
	}
	
	public void destroy() {
		for(Object key : soundLibrary.keySet()){
			try{
				soundLibrary.get(key).unload();
			}
			catch(AudioNotInMemoryException e){
				Logger.logError("Error unloading " + key.toString() + ": " + e.getMessage());
			}
		}
		alcDestroyContext(context);
		alcCloseDevice(device);
	}

	

}
