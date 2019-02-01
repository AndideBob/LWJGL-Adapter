package lwjgladapter.sound;

import java.nio.*;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.libc.LibCStdlib.*;

class Sound {
	
	private int bufferPointer;
	
	private int sourcePointer;
	
	public Sound(String fileName){
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
	}
	
	public void play(){
		//Play the sound
		alSourcePlay(sourcePointer);
	}
	
	public void unload(){
		//Delete Buffers
		alDeleteBuffers(bufferPointer);
		//Delete Source Pointer
		alDeleteSources(sourcePointer);
	}
}