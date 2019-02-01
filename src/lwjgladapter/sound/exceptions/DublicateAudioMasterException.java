package lwjgladapter.sound.exceptions;

import lwjgladapter.datatypes.LWJGLAdapterException;

public class DublicateAudioMasterException extends LWJGLAdapterException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2997552882429336593L;
	

	public DublicateAudioMasterException(String message) {
		super(message);
	}

	public DublicateAudioMasterException(Throwable cause) {
		super(cause);
	}

	public DublicateAudioMasterException(String message, Throwable cause) {
		super(message, cause);
	}

	public DublicateAudioMasterException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
