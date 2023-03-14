package lwjgladapter.sound;

import java.nio.*;

import lwjgladapter.sound.exceptions.AudioNotInMemoryException;
import lwjgladapter.sound.exceptions.IllegalSoundValueException;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.libc.LibCStdlib.*;

class SingleInstanceSound extends Sound {
	
	private int bufferPointer;
	
	protected int sourcePointer;
	
	public SingleInstanceSound(String key, String fileName){
		super(key);
		
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
		
		//Set Source Pointer
		sourcePointer = alGenSources();
		
		//Assign our buffer to the source
		alSourcei(sourcePointer, AL_BUFFER, bufferPointer);
		
		super.loaded = true;
	}
	
	@Override
	public void play() throws AudioNotInMemoryException{
		checkMemory();
		alSourcePlay(sourcePointer);
	}
	
	@Override
	public boolean isPlaying() throws AudioNotInMemoryException{
		checkMemory();
		return alGetSourcei(sourcePointer, AL_SOURCE_STATE) == AL_PLAYING;
	}
	
	@Override
	public void pause() throws AudioNotInMemoryException{
		checkMemory();
		alSourcePause(sourcePointer);
	}
	
	@Override
	public void stop() throws AudioNotInMemoryException{
		checkMemory();
		alSourceStop(sourcePointer);
	}
	
	@Override
	public void unload() throws AudioNotInMemoryException{
		checkMemory();
		if(isPlaying()){
			stop();
		}
		alDeleteBuffers(bufferPointer);
		alDeleteSources(sourcePointer);
		super.loaded = false;
	}

	@Override
	public void setPitch(float pitch) throws IllegalSoundValueException, AudioNotInMemoryException {
		checkMemory();
		if(pitch < MIN_PITCH || pitch > MAX_PITCH){
			throw new IllegalSoundValueException("Sound Pitch out of bounds! Value must be between [" + MIN_PITCH +
					"] and [" + MAX_PITCH + "]!");
		}
		alSourcef(sourcePointer, AL_PITCH, pitch);
	}

	@Override
	public void setGain(float gain) throws IllegalSoundValueException, AudioNotInMemoryException {
		checkMemory();
		if(gain < MIN_GAIN || gain > MAX_GAIN){
			throw new IllegalSoundValueException("Sound Pitch out of bounds! Value must be between [" + MIN_GAIN +
					"] and [" + MAX_GAIN + "]!");
		}
		alSourcef(sourcePointer, AL_GAIN, gain);
	}

	@Override
	public void setLooping(boolean looping) throws AudioNotInMemoryException {
		checkMemory();
		alSourcei(sourcePointer, AL_LOOPING, looping ? AL_TRUE : AL_FALSE);
	}
}