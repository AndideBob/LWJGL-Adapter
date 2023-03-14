package lwjgladapter.sound;

import lwjgladapter.sound.exceptions.AudioNotInMemoryException;
import lwjgladapter.sound.exceptions.IllegalSoundValueException;

abstract class Sound {
	
	public static final float MIN_PITCH = 0.05f;
	
	public static final float MAX_PITCH = 2f;
	
	public static final float MIN_GAIN = 0f;
	
	public static final float MAX_GAIN = 1f;
	
	protected boolean loaded = false;
	
	private String name;
	
	protected Sound(String name){
		this.name = name;
	}

	/**
	 * Plays or Resumes the Sound.
	 * @throws AudioNotInMemoryException If the sound was already unloaded.
	 */
	public abstract void play() throws AudioNotInMemoryException;

	/**
	 * Pauses the Sound.
	 * @throws AudioNotInMemoryException If the sound was already unloaded.
	 */
	public abstract void pause() throws AudioNotInMemoryException;
	
	/**
	 * Stops the Sound.
	 * @throws AudioNotInMemoryException If the sound was already unloaded.
	 */
	public abstract void stop() throws AudioNotInMemoryException;
	
	/**
	 * Checks if the sound is playing.
	 * 
	 * @return true if the sound is playing
	 * @throws AudioNotInMemoryException If the sound was already unloaded.
	 */
	public abstract boolean isPlaying() throws AudioNotInMemoryException;
	
	/**
	 * Sets the pitch of the sound.
	 * 
	 * @param pitch 	The pitch the sound should be set to.
	 * @throws AudioNotInMemoryException If the sound was already unloaded.
	 */
	public abstract void setPitch(float pitch) throws IllegalSoundValueException, AudioNotInMemoryException;
	
	/**
	 * Sets the gain of the sound.
	 * 
	 * @param gain 	The gain the sound should be set to.
	 * @throws AudioNotInMemoryException If the sound was already unloaded.
	 */
	public abstract void setGain(float gain) throws IllegalSoundValueException, AudioNotInMemoryException;
	
	/**
	 * Sets whether the sound should be looping.
	 * 
	 * @param looping 	True if the sound should loop.
	 * @throws AudioNotInMemoryException If the sound was already unloaded.
	 */
	public abstract void setLooping(boolean looping) throws AudioNotInMemoryException;
	
	/**
	 * Unloads the sound and frees up the memory used by it. After this
	 * no other method should be called on the class.
	 * @throws AudioNotInMemoryException If the sound was already unloaded
	 */
	public abstract void unload() throws AudioNotInMemoryException;
	
	/**
	 * Verifies if the sound is still in Memory.
	 * @throws AudioNotInMemoryException If the sound was already unloaded.
	 */
	protected void checkMemory() throws AudioNotInMemoryException{
		if(!loaded){
			throw new AudioNotInMemoryException("Sound " + name + " does not exist in Memory!");
		}
	}
}
