package lwjgladapter.sound;

import java.nio.*;

import lwjgladapter.sound.exceptions.AudioNotInMemoryException;
import lwjgladapter.sound.exceptions.IllegalSoundValueException;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.libc.LibCStdlib.*;

/**
 * MultiInstanceSound is a sound which will appear many times in a game.
 * it is initialized with the maximum instances it can have, e.g. the
 * times the sound will be played at the same time.
 * Changes made to pitch, float and loop settings will only apply the
 * next time the sound is played.
 * 
 * @author AndideBob
 *
 */
class MultiInstanceSound extends Sound {
	
	private int bufferPointer;
	
	private int instanceCounter;
	private int instances;
	private int[] sourcePointers;
	
	private float pitch;
	private float gain;
	private boolean looping;
	
	/**
	 * Initializes the Sound and loads it into memory.
	 * 
	 * @param fileName		The file that should be loaded for the sound 
	 * @param instances		The number of instances of the sound that can be played at the same time (min. 1)
	 */
	public MultiInstanceSound(String key, String fileName, int instances){
		super(key);
		if(instances < 1){
			throw new IllegalArgumentException("MultiSound can not be initialized with less than 1 instance");
		}
		this.instances = instances;
		
		//Allocate space to store return information from the function
		stackPush();
		IntBuffer channelsBuffer = stackMallocInt(1);
		stackPush();
		IntBuffer sampleRateBuffer = stackMallocInt(1);

		ShortBuffer rawAudioBuffer = stb_vorbis_decode_filename(fileName, channelsBuffer, sampleRateBuffer);

		//Retreive the extra information that was stored in the buffers by the function
		int channels = channelsBuffer.get();
		int sampleRate = sampleRateBuffer.get();
		//Free the space we allocated earlier
		stackPop();
		stackPop();
		
		//Find the correct OpenAL format
		int format = -1;
		if(channels == 1) {
		    format = AL_FORMAT_MONO16;
		} else if(channels == 2) {
		    format = AL_FORMAT_STEREO16;
		}

		//Request space for the buffer
		bufferPointer = alGenBuffers();

		//Send the data to OpenAL
		alBufferData(bufferPointer, format, rawAudioBuffer, sampleRate);

		//Free the memory allocated by STB
		free(rawAudioBuffer);
		
		instanceCounter = 0;
		sourcePointers = new int[instances];
		//Set Source Pointers & Assign our buffer to the source
		for(int i = 0; i < instances; i++){
			sourcePointers[i] = alGenSources();
			alSourcei(sourcePointers[i], AL_BUFFER, bufferPointer);
		}
		
		//Set Default Values for Sounds
		this.pitch = 1f;
		this.gain = 1f;
		this.looping = false;
		
		super.loaded = true;
	}
	
	/**
	 * Plays a new instance of the sound using the current settings for
	 * gain, pitch and looping.
	 * @throws AudioNotInMemoryException If the sound was already unloaded 
	 */
	
	@Override
	public void play() throws AudioNotInMemoryException{
		checkMemory();
		alSourcef(sourcePointers[instanceCounter], AL_PITCH, pitch);
		alSourcef(sourcePointers[instanceCounter], AL_GAIN, gain);
		alSourcei(sourcePointers[instanceCounter], AL_LOOPING, looping ? AL_TRUE : AL_FALSE);
		alSourcePlay(sourcePointers[instanceCounter]);
		instanceCounter = (instanceCounter + 1) % instances;
	}
	
	/**
	 * Checks whether any instance of the sound is playing.
	 * 
	 * @return true if any instance of the sound is playing.
	 * @throws AudioNotInMemoryException If the sound was already unloaded
	 */
	@Override
	public boolean isPlaying() throws AudioNotInMemoryException{
		checkMemory();
		for(int i = 0; i < instances; i++){
			if(alGetSourcei(sourcePointers[i], AL_SOURCE_STATE) == AL_PLAYING){
				return true;
			}
		}	
		return false;
	}
	
	/**
	 * Pause is not possible for MultiInstanceSound
	 */
	@Override
	public void pause(){
		// Pause is not possible for MultiInstanceSound
	}
	
	/**
	 * Stops the last played instance of the sound
	 * @throws AudioNotInMemoryException If the sound was already unloaded
	 */
	@Override
	public void stop() throws AudioNotInMemoryException{
		checkMemory();
		instanceCounter = (instanceCounter + 1) % instances;
		alSourceStop(sourcePointers[instanceCounter]);
	}
	
	/**
	 * Stops all instances of the sound
	 * @throws AudioNotInMemoryException If the sound was already unloaded
	 */
	public void stopAll() throws AudioNotInMemoryException{
		checkMemory();
		for(int i = 0; i < instances; i++){
			alSourceStop(sourcePointers[i]);
		}
	}
	
	@Override
	public void unload() throws AudioNotInMemoryException{
		checkMemory();
		if(isPlaying()){
			stopAll();
		}
		alDeleteBuffers(bufferPointer);
		for(int i = 0; i < instances; i++){
			alDeleteSources(sourcePointers[i]);
		}
		super.loaded = false;
	}

	/**
	 * Changes the pitch setting for this sound.
	 * 
	 * @param pitch 	The float value for the pitch
	 * @throws AudioNotInMemoryException If the sound was already unloaded
	 * @throws IllegalSoundValueException If pitch is not within the bounds
	 */
	@Override
	public void setPitch(float pitch) throws IllegalSoundValueException, AudioNotInMemoryException {
		checkMemory();
		if(pitch < MIN_PITCH || pitch > MAX_PITCH){
			throw new IllegalSoundValueException("Sound Pitch out of bounds! Value must be between [" + MIN_PITCH +
					"] and [" + MAX_PITCH + "]!");
		}
		this.pitch = pitch;
	}

	/**
	 * Changes the gain setting for this sound.
	 * 
	 * @param gain 	The float value for the gain
	 * @throws AudioNotInMemoryException If the sound was already unloaded
	 * @throws IllegalSoundValueException If pitch is not within the bounds
	 */
	@Override
	public void setGain(float gain) throws IllegalSoundValueException, AudioNotInMemoryException {
		checkMemory();
		if(gain < MIN_GAIN || gain > MAX_GAIN){
			throw new IllegalSoundValueException("Sound Pitch out of bounds! Value must be between [" + MIN_GAIN +
					"] and [" + MAX_GAIN + "]!");
		}
		this.gain = gain;
	}

	/**
	 * Changes the loop setting for this sound.
	 * 
	 * @param looping 	True if the sound should loop
	 * @throws AudioNotInMemoryException If the sound was already unloaded
	 */
	@Override
	public void setLooping(boolean looping) throws AudioNotInMemoryException {
		checkMemory();
		this.looping = looping;
	}
}